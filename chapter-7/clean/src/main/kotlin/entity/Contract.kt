package entity

data class Contract(
    val partyA: Party,
    val partyB: Party,
    val contractState: ContractState,
)
