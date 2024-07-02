import java.util.UUID

data class CurrentContractQuery(
    val contractId: UUID
)

fun CurrentContractQuery.handle(
    eventStore: EventStore<UUID, ContractEvent>
): Contract? = eventStore.get(contractId)?.play()