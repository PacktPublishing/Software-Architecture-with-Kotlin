package fcis.shell.rest

import arrow.core.Either
import fcis.core.Error
import fcis.core.ErrorType
import fcis.core.Household

fun DraftContractRequest.ensureHouseholdExist(householdLookup: HouseholdLookup): Either<Error, Pair<Household, Household>> {
    val householdARecord = householdLookup(householdA)
    val householdBRecord = householdLookup(householdB)

    return if (householdARecord == null) {
        Either.Left(
            Error("Households not found: $householdA", ErrorType.HOUSEHOLD_NOT_FOUND),
        )
    } else if (householdBRecord == null) {
        Either.Left(
            Error("Households not found: $householdB", ErrorType.HOUSEHOLD_NOT_FOUND),
        )
    } else {
        Either.Right(
            householdARecord to householdBRecord,
        )
    }
}
