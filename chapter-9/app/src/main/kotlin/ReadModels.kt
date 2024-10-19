import java.time.Instant
import java.util.UUID

data class MyHouseholdSummary(
    val householdName: String,
    val contractsWithOthers: Map<UUID, ContractWithOthers> = emptyMap(),
)

data class ContractWithOthers(
    val contractId: UUID,
    val version: Int,
    val householdName: String,
    val serviceProvided: String,
    val serviceReceived: String,
    val draftedAt: Instant,
    val weAgreedAt: Instant? = null,
    val theyAgreedAt: Instant? = null
)

fun ContractDraftedEvent.playAsSummary(summary: MyHouseholdSummary): MyHouseholdSummary {
    require (summary.householdName == draftedByHousehold ||
        summary.householdName == counterpartyHousehold)
    require(summary.contractsWithOthers.containsKey(contractId).not())

    val contract = if (summary.householdName == draftedByHousehold) {
        ContractWithOthers(
                contractId = contractId,
                version = targetVersion,
                householdName = counterpartyHousehold,
                serviceProvided = serviceProvided,
                serviceReceived = serviceReceived,
                draftedAt = time,
        )
    } else {
        ContractWithOthers(
                contractId = contractId,
                version = targetVersion,
                householdName = counterpartyHousehold,
                serviceProvided = serviceReceived,
                serviceReceived = serviceProvided,
                draftedAt = time,
            )
    }

    return summary.copy(contractsWithOthers = summary.contractsWithOthers.plus(contractId to contract))
}

fun ContractAmendedEvent.playAsSummary(summary: MyHouseholdSummary): MyHouseholdSummary {
    val contract = summary.contractsWithOthers[contractId]
    require(contract != null)

    val amended = if (amendedByHousehold == summary.householdName) {
        contract.copy(
            serviceReceived = serviceReceivedUpdate ?: contract.serviceReceived,
            serviceProvided = serviceProvidedUpdate ?: contract.serviceProvided,
        )
    } else {
        contract.copy(
            serviceReceived = serviceProvidedUpdate ?: contract.serviceProvided,
            serviceProvided = serviceReceivedUpdate ?: contract.serviceReceived,
        )
    }
    return summary.copy(
        contractsWithOthers = summary.contractsWithOthers.plus(contractId to amended)
    )
}

fun ContractAgreedEvent.playAsSummary(
    summary: MyHouseholdSummary
): MyHouseholdSummary {
    val contract = summary.contractsWithOthers[contractId]
    require(contract != null)

    val agreed = if (agreedByHousehold == summary.householdName) {
        contract.copy(weAgreedAt = time)
    } else {
        contract.copy(theyAgreedAt = time)
    }
    return summary.copy(
        contractsWithOthers = summary.contractsWithOthers
            .plus(contractId to agreed)
    )
}

fun List<ContractEvent>.playAsSummary(summary: MyHouseholdSummary): MyHouseholdSummary {
    var current = summary

    if (isEmpty()) return summary

    forEach { event ->
        current = when (event) {
            is ContractDraftedEvent -> event.playAsSummary(current)
            is ContractAmendedEvent -> event.playAsSummary(current)
            is ContractAgreedEvent -> event.playAsSummary((current))
            else -> throw IllegalArgumentException("Unsupported event")
        }
    }
    return current
}

data class MyHouseholdSummaryQuery(
    val householdName: String
)

fun MyHouseholdSummaryQuery.handle(
    eventStore: EventStore<UUID, ContractEvent>
): MyHouseholdSummary = eventStore.get { event ->
    when (event) {
        is ContractDraftedEvent -> event.draftedByHousehold == householdName ||
            event.counterpartyHousehold == householdName
        is ContractAmendedEvent -> event.amendedByHousehold == householdName
        is ContractAgreedEvent -> event.agreedByHousehold == householdName
        else -> false
    }
}.playAsSummary(MyHouseholdSummary(householdName))