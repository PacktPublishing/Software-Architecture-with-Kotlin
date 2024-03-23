package com.example.base.viewmodel

import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.base.model.DraftContractInput
import com.example.base.model.HouseholdInput

class DraftContractViewModel : ViewModel() {
    var yourHouseholdName: String? = null
    var yourHouseholdService: String? = null
    var yourNeighborName: String? = null
    var yourNeighborService: String? = null
}

fun DraftContractViewModel.toModel(): DraftContractInput? =
    if (yourHouseholdName != null
        && yourHouseholdService != null
        && yourNeighborName != null
        && yourNeighborService != null
    ) {
        DraftContractInput(
            initiator = HouseholdInput(
                householdName = yourHouseholdName!!,
                serviceProvided = yourHouseholdService!!
            ),
            neighbor = HouseholdInput(
                householdName = yourNeighborName!!,
                serviceProvided = yourNeighborService!!
            )
        )
    } else {
        null
    }
