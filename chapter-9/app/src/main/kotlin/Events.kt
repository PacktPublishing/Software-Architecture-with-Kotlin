import java.time.Instant
import java.util.UUID

interface ContractEvent {
    val contractId: UUID
    val targetVersion: Int
    val time: Instant
}

data class ContractDraftedEvent(
    override val contractId: UUID,
    override val targetVersion: Int = 0,
    override val time: Instant,
    val draftedByHousehold: String,
    val counterpartHousehold: String,
    val serviceProvided: String,
    val serviceReceived: String,
) : ContractEvent

data class ContractAmendedEvent(
    override val contractId: UUID,
    override val targetVersion: Int,
    override val time: Instant,
    val amendedByHousehold: String,
    val serviceProvidedUpdate: String?,
    val serviceReceivedUpdate: String?,
) : ContractEvent

data class ContractAgreedEvent(
    override val contractId: UUID,
    override val targetVersion: Int,
    override val time: Instant,
    val agreedByHousehold: String,
) : ContractEvent


fun ContractDraftedEvent.play(): Contract = Contract(
    id = contractId,
    draftedAt = time,
    version = targetVersion,
    partyA = Party(
        householdName = draftedByHousehold,
        serviceProvided = serviceProvided
    ),
    partyB = Party(
        householdName = counterpartHousehold,
        serviceProvided = serviceReceived
    )
)

fun ContractAmendedEvent.play(current: Contract): Contract {
    validate(current, amendedByHousehold)
    return if (amendedByHousehold == current.partyA.householdName) {
        current.copy(
            version = targetVersion,
            updatedAt = time,
            partyA = current.partyA.copy(
                serviceProvided = serviceProvidedUpdate ?: current.partyA.serviceProvided
            ),
            partyB = current.partyB.copy(
                serviceProvided = serviceReceivedUpdate ?: current.partyB.serviceProvided
            )
        )
    } else {
        current.copy(
            version = targetVersion,
            updatedAt = time,
            partyA = current.partyA.copy(
                serviceProvided = serviceReceivedUpdate ?: current.partyA.serviceProvided
            ),
            partyB = current.partyB.copy(
                serviceProvided = serviceProvidedUpdate ?: current.partyB.serviceProvided
            )
        )

    }
}

fun ContractAgreedEvent.play(current: Contract): Contract {
    validate(current, agreedByHousehold)
    return if (agreedByHousehold == current.partyA.householdName) {
        current.copy(
            version = targetVersion,
            updatedAt = time,
            partyA = current.partyA.copy(agreedAt = time),
        )
    } else {
        current.copy(
            version = targetVersion,
            updatedAt = time,
            partyB = current.partyB.copy(agreedAt = time)
        )

    }
}

fun <T : ContractEvent> T.validate(current: Contract, expectedHouseholdName: String): T {
    require(contractId == current.id)
    require(targetVersion == current.version + 1)
    require(
        expectedHouseholdName == current.partyA.householdName ||
            expectedHouseholdName == current.partyB.householdName
    )
    return this
}

fun List<ContractEvent>.play(): Contract? {
    if (isEmpty()) return null

    var current: Contract = (first() as ContractDraftedEvent).play()
    var index = 1

    while (index < size) {
        val event = get(index++)
        current = when (event) {
            is ContractAmendedEvent -> event.play(current)
            is ContractAgreedEvent -> event.play((current))
            else -> throw IllegalArgumentException("Unsupported event")
        }
    }
    return current
}