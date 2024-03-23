package com.example.base.presenter

import com.example.base.model.DraftContractInput

interface Presenter {
    fun submitContract(contract: DraftContractInput)
}