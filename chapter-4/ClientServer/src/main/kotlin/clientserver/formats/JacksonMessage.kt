package clientserver.formats

import org.http4k.core.Body
import org.http4k.format.Jackson.auto
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

val serviceContractLens = Body.auto<ServiceContract>().toLens()
