package com.example.chapter5.model

data class DraftContractInput(
    val initiator: HouseholdInput,
    val neighbor: HouseholdInput
)
data class HouseholdInput(
    val householdName: String,
    val serviceProvided: String
)

class ContractRepository {
    fun submit(contract: DraftContractInput): Boolean {
        // Submit the draft contract for validation and persistence
        return true.also {
            println("Persisted contract: $contract")
        }
    }
}