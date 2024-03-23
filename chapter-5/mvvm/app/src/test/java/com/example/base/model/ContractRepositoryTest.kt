package com.example.base.model

import org.junit.Test

import org.junit.Assert.*

class ContractRepositoryTest {
    private val repository = ContractRepository()
    private val contract = DraftContractInput(
        HouseholdInput("Smith", "Cleaning"),
        HouseholdInput("Lee", "Cooking")
    )
    @Test
    fun `Returns true after a draft contract is submitted`() {
        assertTrue(repository.submit(contract))
    }
    @Test
    fun `Invokes callback after a draft contract is submitted`() {
        var received: DraftContractInput? = null
        repository.onSubmitListener = {
            received = it
        }
        assertNull(received)
        repository.submit(contract)
        assertEquals(contract, received)
    }
}