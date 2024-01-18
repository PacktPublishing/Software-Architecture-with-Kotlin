package clientserver.formats

import java.time.Instant

data class ServiceContract(
    val id: Int,
    val partyA: Party,
    val partyB: Party,
)

data class Party(
    val householdName: String,
    val service: String,
    val agreedAt: Instant? = null,
)
