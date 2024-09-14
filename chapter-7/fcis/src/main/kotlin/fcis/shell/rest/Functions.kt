package fcis.shell.rest

import arrow.core.Either
import fcis.core.Contract
import fcis.core.Error
import fcis.core.Household

typealias HouseholdLookup = (String) -> Household?

typealias ContractPersist = (Contract) -> Either<Error, Contract>
