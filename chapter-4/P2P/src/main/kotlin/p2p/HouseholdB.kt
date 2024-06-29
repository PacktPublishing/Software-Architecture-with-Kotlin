
package p2p

import ServiceContract
import agree
import bothPartiesHaveDifferentNames
import isHouseholdInvolved
import partyAgreed
import serviceReceivedBy
import withReceivedService
import java.net.InetSocketAddress
import java.time.Instant

fun main() {
    val node =
        UdpNode(
            InetSocketAddress(HOST_B, PORT_B),
            ServiceContractConvertor,
        ) { it.receivedByHouseholdB() }
    node.start()
    loopForever(1000) { node.consume() }
}

fun ServiceContract.receivedByHouseholdB() =
    if (bothPartiesHaveDifferentNames().not() ||
        partyAgreed(HOUSEHOLD_B) ||
        isHouseholdInvolved(HOUSEHOLD_B).not()
    ) {
        println("No response to service contract: $this")
        null
    } else if (serviceReceivedBy(HOUSEHOLD_B) == CLEANING) {
        println("Submitted revised service contract: $id")
        withReceivedService(HOUSEHOLD_B, BABYSITTING)
    } else if (serviceReceivedBy(HOUSEHOLD_B) == BABYSITTING) {
        println("Agreed to service contract: $id")
        agree(HOUSEHOLD_B) { Instant.now() }
    } else {
        println("No response to service contract: $id")
        null
    }
