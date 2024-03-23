package com.example.base.viewmodel

import com.example.base.model.DraftContractInput
import com.example.base.model.HouseholdInput
import org.junit.Test
import org.junit.Assert.*

class DraftContractViewModelTest {
    val smith = "Smith"
    val cleaning = "Cleaning"
    val lee = "Lee"
    val cooking = "Cooking"

    @Test
    fun `do not create model if the view model is empty`() {
        assertNull(DraftContractViewModel().toModel())
    }
    @Test
    fun `do not create model if at least one field is null`() {

        listOf(
            DraftContractViewModel().apply { yourHouseholdName = smith },
            DraftContractViewModel().apply { yourHouseholdService = cleaning },
            DraftContractViewModel().apply { yourNeighborName = lee },
            DraftContractViewModel().apply { yourNeighborService = cooking },
            DraftContractViewModel().apply {
                yourHouseholdName = smith
                yourHouseholdService = cleaning
            },
            DraftContractViewModel().apply {
                yourHouseholdName = smith
                yourNeighborName = lee
            },
            DraftContractViewModel().apply {
                yourHouseholdName = smith
                yourNeighborService = cooking
            },
            DraftContractViewModel().apply {
                yourHouseholdService = cleaning
                yourNeighborName = lee
            },
            DraftContractViewModel().apply {
                yourHouseholdService = cleaning
                yourNeighborService = cooking
            },
            DraftContractViewModel().apply {
                yourNeighborName = lee
                yourNeighborService = cooking
            },
            DraftContractViewModel().apply {
                yourHouseholdName = smith
                yourHouseholdService = cleaning
                yourNeighborName = lee
            },
            DraftContractViewModel().apply {
                yourHouseholdName = smith
                yourHouseholdService = cleaning
                yourNeighborService = cooking
            },
            DraftContractViewModel().apply {
                yourHouseholdName = smith
                yourNeighborName = lee
                yourNeighborService = cooking
            },
            DraftContractViewModel().apply {
                yourHouseholdService = cleaning
                yourNeighborName = lee
                yourNeighborService = cooking
            },
        ).forEach { incompleteData ->
            assertNull(incompleteData.toModel())
        }
    }

    @Test
    fun `create model when all fields are present`() {
        val viewModel = DraftContractViewModel().apply {
            yourHouseholdName = "Smith"
            yourHouseholdService = "Cleaning"
            yourNeighborName = "Lee"
            yourNeighborService = "Cooking"
        }
        val model = DraftContractInput(
            HouseholdInput("Smith", "Cleaning"),
            HouseholdInput("Lee", "Cooking")
        )
        assertEquals(model, viewModel.toModel())
    }
}