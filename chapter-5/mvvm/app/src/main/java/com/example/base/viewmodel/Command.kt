package com.example.base.viewmodel

import com.example.base.model.DraftContractInput

interface Command {
    fun submitContract(contract: DraftContractInput)
}