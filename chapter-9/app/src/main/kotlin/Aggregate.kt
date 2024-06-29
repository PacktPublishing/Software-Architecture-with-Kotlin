import java.time.Instant
import java.util.UUID

data class Contract(
    val id: UUID,
    val draftedAt: Instant,
    val updatedAt: Instant? = null,
    val version: Int,
    val partyA: Party,
    val partyB: Party,
)

data class Party(
    val householdName: String,
    val serviceProvided: String,
    val agreedAt: Instant? = null
)