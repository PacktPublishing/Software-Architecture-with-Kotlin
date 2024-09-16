import java.time.Instant

data class Contract(
    val partyA: Party,
    val partyB: Party,
    val contractState: ContractState,
)

data class Party(
    val household: Household,
    val serviceProvided: String,
    val agreedAt: Instant? = null,
    val completedAt: Instant? = null,
)

enum class ContractState {
    DRAFTED,
    UNDER_REVIEW,
    AGREED,
    REJECTED,
    PARTIALLY_EXERCISED,
    FULLY_EXERCISED,
    WITHDRAWN,
}

data class Household(
    val name: String,
    val emailAddress: String
)
