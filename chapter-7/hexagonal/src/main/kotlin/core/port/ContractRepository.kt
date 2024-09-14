package core.port

import core.Contract

interface ContractRepository {
    fun save(contract: Contract)
}
