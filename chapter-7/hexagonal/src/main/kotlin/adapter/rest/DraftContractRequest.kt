package adapter.rest

data class DraftContractRequest(
    val householdA: String,
    val householdB: String,
    val serviceProvidedByHouseholdA: String,
    val serviceProvidedByHouseholdB: String,
)

data class ContractDto(
    val householdA: String,
    val householdB: String,
    val serviceProvidedByHouseholdA: String,
    val serviceProvidedByHouseholdB: String,
    val contractState: String,
)
