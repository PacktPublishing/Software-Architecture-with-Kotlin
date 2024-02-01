package p2p

import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

class UdpNode<T>(
    val address: SocketAddress,
    val convertor: DtoConvertor<T>,
    val transformer: (T) -> T?,
) {
    private val inbound: ByteBuffer = convertor.allocate()
    private val outbound: ByteBuffer = convertor.allocate()
    private var channel: DatagramChannel? = null

    fun start() {
        channel =
            DatagramChannel.open()
                .bind(address)

        println("Started on $$address")
    }

    fun produce(
        payload: T,
        target: SocketAddress,
    ): Int {
        outbound.clear()
        convertor.toBuffer(payload, outbound)
        outbound.flip()

        return channel!!.send(outbound, target)
    }

    fun consume(): Int {
        return channel?.let { c ->
            inbound.clear()
            val address: SocketAddress = c.receive(inbound)
            inbound.rewind()
            val received = convertor.fromBuffer(inbound)

            val transformed = transformer(received)

            transformed?.let { t ->
                outbound.clear()
                convertor.toBuffer(t, outbound)
                outbound.flip()
                c.send(outbound, address)
            }
        } ?: 0
    }
}
