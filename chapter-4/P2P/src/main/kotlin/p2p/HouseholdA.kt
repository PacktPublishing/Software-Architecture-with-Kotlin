
package p2p

import Party
import ServiceContract
import agree
import bothPartiesHaveDifferentNames
import isHouseholdInvolved
import partyAgreed
import java.net.InetSocketAddress
import java.time.Instant

const val HOUSEHOLD_A = "A"
const val HOUSEHOLD_B = "B"
const val HOST_A = "localhost"
const val HOST_B = "localhost"
const val PORT_A = 7001
const val PORT_B = 7002
const val PLUMBING = "Plumbing"
const val CLEANING = "Cleaning"
const val BABYSITTING = "Babysitting"

fun main() {
    val node =
        UdpNode(
            InetSocketAddress(HOST_A, PORT_A),
            ServiceContractConvertor,
        ) { it.receivedByHouseholdA() }
    node.start()
    val contract =
        ServiceContract(
            id = 1,
            partyA = Party(HOUSEHOLD_A, PLUMBING, null),
            partyB = Party(HOUSEHOLD_B, CLEANING, null),
        )
    node.produce(contract, InetSocketAddress(HOST_B, PORT_B))
    println("Submitted service contract: ${contract.id}")
    loopForever(1000) { node.consume() }
}

private fun ServiceContract.receivedByHouseholdA() =
    if (bothPartiesHaveDifferentNames().not() ||
        partyAgreed(HOUSEHOLD_A) ||
        isHouseholdInvolved(HOUSEHOLD_A).not()
    ) {
        println("No response to service contract: $this")
        null
    } else {
        println("Agreed to service contract: $id")
        agree(HOUSEHOLD_A) { Instant.now() }
    }
