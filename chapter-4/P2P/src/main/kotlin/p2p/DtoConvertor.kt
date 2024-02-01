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
        buffer.putInt(dto.id)
        toBuffer(dto.partyA, buffer)
        toBuffer(dto.partyB, buffer)
    }

    private fun toBuffer(
        dto: Party,
        buffer: ByteBuffer,
    ) {
        toBuffer(dto.householdName, buffer)
        toBuffer(dto.service, buffer)

        if (dto.agreedAt == null) {
            buffer.putChar(ABSENT)
        } else {
            buffer.putChar(PRESENT)
            buffer.putLong(dto.agreedAt.epochSecond)
        }
    }

    private fun toBuffer(
        dto: String,
        buffer: ByteBuffer,
    ) {
        buffer.putInt(dto.length)
        buffer.put(dto.toByteArray())
    }

    override fun fromBuffer(buffer: ByteBuffer): ServiceContract {
        val id = buffer.getInt()
        val partyA = partyFromBuffer(buffer)
        val partyB = partyFromBuffer(buffer)
        return ServiceContract(id, partyA, partyB)
    }

    private fun partyFromBuffer(buffer: ByteBuffer): Party {
        val householdName = stringFromBuffer(buffer)
        val service = stringFromBuffer(buffer)

        val agreedAt =
            if (buffer.getChar() == PRESENT) {
                Instant.ofEpochSecond(buffer.getLong())
            } else {
                null
            }

        return Party(householdName, service, agreedAt)
    }

    private fun stringFromBuffer(buffer: ByteBuffer): String {
        val len = buffer.getInt()
        val bytes = ByteArray(len)
        buffer.get(bytes)
        return String(bytes)
    }
}
