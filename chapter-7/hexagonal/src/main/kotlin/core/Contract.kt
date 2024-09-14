package core

data class Contract(
    val partyA: Party,
    val partyB: Party,
    val contractState: ContractState,
)
