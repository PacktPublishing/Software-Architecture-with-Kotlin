import java.time.Instant
import java.util.UUID

fun main() {
    eventSourceUsage()
}

fun eventSourceUsage() {
    val contractId = UUID.randomUUID()
    val eventStore = EventStore<UUID, ContractEvent>()

    val createdEvent = ContractDraftedEvent(
        contractId = contractId,
        time = Instant.now(),
        draftedByHousehold = "HouseholdA",
        counterpartyHousehold = "HouseholdB",
        serviceProvided = "Cleaning",
        serviceReceived = "Babysitting"
    )
    val amendedEvent = ContractAmendedEvent(
        contractId = contractId,
        targetVersion = 1,
        time = Instant.now(),
        amendedByHousehold = "HouseholdB",
        serviceReceivedUpdate = "Dish washing",
        serviceProvidedUpdate = null
    )
    val agreedEventByHouseholdA = ContractAgreedEvent(
        contractId = contractId,
        targetVersion = 2,
        time = Instant.now(),
        agreedByHousehold = "HouseholdA"
    )
    val agreedEventByHouseholdB = ContractAgreedEvent(
        contractId = contractId,
        targetVersion = 3,
        time = Instant.now(),
        agreedByHousehold = "HouseholdB"
    )

    listOf(
        createdEvent,
        amendedEvent,
        agreedEventByHouseholdA,
        agreedEventByHouseholdB
    ).forEach { eventStore.append(contractId, it) }

    val aggregate = eventStore.get(contractId)?.play()
    println("Aggregate is of version: ${aggregate?.version}")
}
