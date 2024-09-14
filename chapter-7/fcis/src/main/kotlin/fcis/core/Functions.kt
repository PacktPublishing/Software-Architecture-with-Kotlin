package fcis.core

import arrow.core.Either

fun draftContract(
    householdA: Household,
    householdB: Household,
    serviceProvidedByHouseholdA: String,
    serviceProvidedByHouseholdB: String,
): Either<Error, Contract> =
    if (householdA.name == householdB.name) {
        Either.Left(
            Error("Parties must be from different households", ErrorType.SAME_HOUSEHOLD_IN_CONTRACT),
        )
    } else {
        Either.Right(
            Contract(
                partyA = Party(householdA, serviceProvidedByHouseholdA),
                partyB = Party(householdB, serviceProvidedByHouseholdB),
                contractState = ContractState.DRAFTED,
            ),
        )
    }
