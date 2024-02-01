
package p2p

import agree
import bothPartiesHaveDifferentNames
import isHouseholdInvolved
import partyAgreed
import serviceReceivedBy
import withReceivedService
import java.net.InetSocketAddress
import java.time.Instant

fun main() {
    val householdName = "B"
    val node =
        UdpNode(InetSocketAddress(HOST_B, PORT_B), ServiceContractConvertor) { received ->
            if (received.bothPartiesHaveDifferentNames().not() ||
                received.partyAgreed(householdName) ||
                received.isHouseholdInvolved(householdName).not()
            ) {
                println("No response to the service contract $received")
                null
            } else if (received.serviceReceivedBy(householdName) == CLEANING) {
                received.withReceivedService(householdName, BABYSITTING).also {
                    println("Submitted the revised service contract of ID ${received.id}")
                }
            } else if (received.serviceReceivedBy(householdName) == BABYSITTING) {
                received.agree(householdName) { Instant.now() }.also {
                    println("Agreed with the service contract of ID ${received.id}")
                }
            } else {
                println("No response to the service contract of ID ${received.id}")
                null
            }
        }

    node.start()

    loopForever(1000) { node.consume() }
}
