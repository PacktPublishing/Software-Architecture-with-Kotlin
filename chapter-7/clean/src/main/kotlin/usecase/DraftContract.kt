package usecase

import entity.Contract
import entity.ContractState
import entity.Household
import entity.Party

fun draftContract(
    householdA: Household,
    householdB: Household,
    serviceProvidedByHouseholdA: String,
    serviceProvidedByHouseholdB: String,
): Contract {
    require(householdA != householdB) { "Parties must be from different households" }

    return Contract(
        Party(
            householdA,
            serviceProvidedByHouseholdA,
        ),
        Party(
            householdB,
            serviceProvidedByHouseholdB,
        ),
        ContractState.DRAFTED,
    )
}
