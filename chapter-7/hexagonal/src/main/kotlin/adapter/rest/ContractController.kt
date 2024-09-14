package adapter.rest

import core.Contract
import core.ContractState
import core.port.ContractService
import core.port.HouseholdRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/contracts/")
class ContractController(
    private val contractService: ContractService,
    private val householdRepository: HouseholdRepository,
) {
    @PostMapping(
        value = ["draft"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun draftContract(
        @RequestBody request: DraftContractRequest,
    ): ResponseEntity<ContractDto> {
        val householdA = householdRepository.findByName(request.householdA) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val householdB = householdRepository.findByName(request.householdB) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val contract =
            contractService.draftContract(
                householdA,
                householdB,
                request.serviceProvidedByHouseholdA,
                request.serviceProvidedByHouseholdB,
            )
        return ResponseEntity(contract.toDto(ContractState.DRAFTED.name), HttpStatus.CREATED)
    }
}

fun Contract.toDto(state: String): ContractDto =
    ContractDto(
        partyA.household.name,
        partyB.household.name,
        this.partyA.serviceProvided,
        this.partyB.serviceProvided,
        state,
    )
