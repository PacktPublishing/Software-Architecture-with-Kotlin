package fcis.shell.rest

import arrow.core.Either
import fcis.core.Contract
import fcis.core.ContractState
import fcis.core.Error
import fcis.core.Household
import fcis.core.Party

typealias HouseholdLookup = (String) -> Household?

typealias ContractPersist = (Contract) -> Either<Error, Contract>

class DummyContractPersist : ContractPersist {
    override fun invoke(p1: Contract): Either<Error, Contract> {
        TODO("Not yet implemented")
    }
}

fun main() {
    val contract =
        Contract(
            Party(Household("A", listOf("a")), "ironing"),
            Party(Household("B", listOf("b")), "guttering"),
            ContractState.DRAFTED,
        )
    val persist = DummyContractPersist()
    val result = persist(contract)
}
