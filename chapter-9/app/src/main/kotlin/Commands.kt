import java.time.Instant
import java.util.UUID

data class DraftContractCommand(
    val draftedByHousehold: String,
    val counterpartHousehold: String,
    val serviceProvided: String,
    val serviceReceived: String,
)

data class AmendContractCommand(
    val contractId: UUID,
    val amendedByHousehold: String,
    val serviceProvidedUpdate: String?,
    val serviceReceivedUpdate: String?,
)

data class AgreeContractCommand(
    val contractId: UUID,
    val agreedByHousehold: String,
)

data class Failure<T>(
    val request: T,
    val message: String? = null,
    val error: Throwable? = null
)

fun DraftContractCommand.handle(
    eventStore: EventStore<UUID, ContractEvent>,
    onSuccess: (ContractDraftedEvent) -> Unit,
    onFailure: (Failure<DraftContractCommand>) -> Unit
) {
    if (draftedByHousehold == counterpartHousehold) {
        onFailure(Failure(this, "Same household is not allowed: $draftedByHousehold"))
    } else {
        ContractDraftedEvent(
            contractId = UUID.randomUUID(),
            time = Instant.now(),
            draftedByHousehold = draftedByHousehold,
            counterpartyHousehold = counterpartHousehold,
            serviceReceived = serviceReceived,
            serviceProvided = serviceProvided
        ).also{
            eventStore.append(it.contractId, it)
        }.also(onSuccess)
    }
}

fun AmendContractCommand.handle(
    eventStore: EventStore<UUID, ContractEvent>,
    onSuccess: (ContractAmendedEvent) -> Unit,
    onFailure: (Failure<AmendContractCommand>) -> Unit) {

    validate(
        eventStore = eventStore,
        contractId = contractId,
        householdName = amendedByHousehold,
        onSuccess = { contract ->
            ContractAmendedEvent(
                contractId = contractId,
                targetVersion = contract.version + 1,
                time = Instant.now(),
                amendedByHousehold = amendedByHousehold,
                serviceProvidedUpdate = serviceProvidedUpdate,
                serviceReceivedUpdate = serviceReceivedUpdate
            ).also { eventStore.append(contractId, it)
            }.also(onSuccess)
        },
        onFailure = { onFailure(it)}
    )
}

fun AgreeContractCommand.handle(
    eventStore: EventStore<UUID, ContractEvent>,
    onSuccess: (ContractAgreedEvent) -> Unit,
    onFailure: (Failure<AgreeContractCommand>) -> Unit) {

    validate(
        eventStore = eventStore,
        contractId = contractId,
        householdName = agreedByHousehold,
        onSuccess = { contract ->
            ContractAgreedEvent(
                contractId = contractId,
                targetVersion = contract.version + 1,
                time = Instant.now(),
                agreedByHousehold
            ).also { eventStore.append(contractId, it)
            }.also(onSuccess)
        },
        onFailure = { onFailure(it)}
    )
}

fun <T> T.validate(
    eventStore: EventStore<UUID, ContractEvent>,
    contractId: UUID,
    householdName: String,
    onSuccess: (Contract) -> Unit,
    onFailure: (Failure<T>) -> Unit) {
    val events = eventStore.get(contractId)
    if (events == null) {
        onFailure(Failure(this, "Contract not found: $contractId"))
    } else {
        val contract = events.play()
        if (contract == null) {
            onFailure(Failure(this, "Failed to reconstruct Contract: $contractId"))
        } else if (contractId != contract.id) {
            onFailure(Failure(this, "Contract ID mismatched. Expected: $contractId, was: ${contract.id}"))
        } else if (householdName != contract.partyA.householdName
            && householdName != contract.partyB.householdName) {
            onFailure(Failure(this, "Household not found in contract: $householdName"))
        } else {
            onSuccess(contract)
        }
    }
}