package fcis.shell.rest

import arrow.core.Either
import arrow.core.flatMap
import fcis.core.draftContract
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contracts/")
class ContractControllerShell(
    private val householdLookup: HouseholdLookup,
    private val contractPersist: ContractPersist,
) {
    @PostMapping(
        value = ["draft"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun draft(
        @RequestBody draftContractRequest: DraftContractRequest,
    ): ResponseEntity<ContractDto> =
        draftContractRequest
            .ensureHouseholdExist(householdLookup)
            .flatMap { (householdA, householdB) ->
                draftContract(
                    householdA,
                    householdB,
                    draftContractRequest.serviceProvidedByHouseholdA,
                    draftContractRequest.serviceProvidedByHouseholdB,
                )
            }.flatMap { contractPersist(it) }
            .flatMap { Either.Right(it.toDto()) }
            .fold(
                { error -> ResponseEntity(error.type.toHttpStatus()) },
                { contractDto -> ResponseEntity(contractDto, HttpStatus.CREATED) },
            )
}
