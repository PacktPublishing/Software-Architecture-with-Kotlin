package fcis.shell.rest

import fcis.core.Contract
import fcis.core.ErrorType
import org.springframework.http.HttpStatus

fun Contract.toDto(): ContractDto =
    ContractDto(
        partyA.household.name,
        partyB.household.name,
        this.partyA.serviceProvided,
        this.partyB.serviceProvided,
        this.contractState.name,
    )

fun ErrorType.toHttpStatus(): HttpStatus =
    when (this) {
        ErrorType.HOUSEHOLD_NOT_FOUND -> HttpStatus.NOT_FOUND
        ErrorType.SAME_HOUSEHOLD_IN_CONTRACT -> HttpStatus.BAD_REQUEST
    }
