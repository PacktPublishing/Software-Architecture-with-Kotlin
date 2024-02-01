package p2p

import Party
import ServiceContract
import java.nio.ByteBuffer
import java.time.Instant

interface DtoConvertor<E> {
    fun allocate(): ByteBuffer

    fun toBuffer(
        dto: E,
        buffer: ByteBuffer,
    )

    fun fromBuffer(buffer: ByteBuffer): E
}

private const val PRESENT = '1'
private const val ABSENT = '0'

object ServiceContractConvertor : DtoConvertor<ServiceContract> {
    override fun allocate(): ByteBuffer {
        return ByteBuffer.allocate(1024)
    }

    override fun toBuffer(
        dto: ServiceContract,
        buffer: ByteBuffer,
    ) {
        buffer.putInt(dto.id).putParty(dto.partyA).putParty(dto.partyB)
    }

    private fun ByteBuffer.putParty(dto: Party): ByteBuffer = putString(dto.householdName).putString(dto.service).putInstant(dto.agreedAt)

    private fun ByteBuffer.putInstant(dto: Instant?): ByteBuffer =
        if (dto == null) {
            putChar(ABSENT)
        } else {
            putChar(PRESENT).putLong(dto.epochSecond)
        }

    private fun ByteBuffer.putString(dto: String): ByteBuffer = putInt(dto.length).put(dto.toByteArray())

    override fun fromBuffer(buffer: ByteBuffer): ServiceContract = ServiceContract(buffer.getInt(), buffer.getParty(), buffer.getParty())

    private fun ByteBuffer.getParty(): Party = Party(getString(), getString(), getInstant())

    private fun ByteBuffer.getInstant(): Instant? =
        if (getChar() == PRESENT) {
            Instant.ofEpochSecond(getLong())
        } else {
            null
        }

    private fun ByteBuffer.getString(): String {
        val bytes = ByteArray(getInt())
        get(bytes)
        return String(bytes)
    }
}
