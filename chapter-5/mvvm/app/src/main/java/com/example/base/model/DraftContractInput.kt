package com.example.base.model

data class DraftContractInput(
    val initiator: HouseholdInput,
    val neighbor: HouseholdInput
)
data class HouseholdInput(
    val householdName: String,
    val serviceProvided: String
)