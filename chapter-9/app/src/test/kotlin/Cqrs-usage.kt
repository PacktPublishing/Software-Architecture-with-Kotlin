import java.util.UUID

fun main() {
    cqrsUsage()
}

fun cqrsUsage() {
    var contractId: UUID? = null
    val eventStore = EventStore<UUID, ContractEvent>()

    DraftContractCommand(
        draftedByHousehold = "HouseholdA",
        counterpartHousehold = "HouseholdB",
        serviceProvided = "Cleaning",
        serviceReceived = "Babysitting"
    ).handle(
        eventStore = eventStore,
        onSuccess = { contractId = it.contractId
            println("Contract drafted: $contractId") },
        onFailure = { "Failed to draft contract: $it"}
    )

    AmendContractCommand(
        contractId = contractId!!,
        amendedByHousehold = "HouseholdB",
        serviceReceivedUpdate = "Dish washing",
        serviceProvidedUpdate = null
    ).handle(eventStore = eventStore,
        onSuccess = { println("Contract amended: $contractId") },
        onFailure = { println("Failed to amend contract: $contractId")}
    )

    AgreeContractCommand(
        contractId = contractId!!,
        agreedByHousehold = "HouseholdA"
    ).handle(eventStore = eventStore,
        onSuccess = { println("Contract agreed: $contractId") },
        onFailure = { println("Failed to amend contract: $contractId")}
    )

    AgreeContractCommand(
        contractId = contractId!!,
        agreedByHousehold = "HouseholdB"
    ).handle(eventStore = eventStore,
        onSuccess = { println("Contract agreed: $contractId") },
        onFailure = { println("Failed to amend contract: $contractId")}
    )

    val aggregate = CurrentContractQuery(contractId!!).handle(eventStore)
    println("Aggregate is of version: ${aggregate?.version}")

    val summary = MyHouseholdSummaryQuery("HouseholdA").handle(eventStore)
    println("Summary: $summary")
}