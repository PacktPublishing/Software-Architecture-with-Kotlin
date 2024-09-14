package adapter

import core.Contract
import core.Household
import core.port.ContractRepository
import core.port.ContractService

class ContractServiceImpl(
    private val contractRepository: ContractRepository,
) : ContractService {
    override fun draftContract(
        householdA: Household,
        householdB: Household,
        serviceProvidedByHouseholdA: String,
        serviceProvidedByHouseholdB: String,
    ): Contract =
        draftContract(
            householdA,
            householdB,
            serviceProvidedByHouseholdA,
            serviceProvidedByHouseholdB,
        ).also { contractRepository.save(it) }
}
