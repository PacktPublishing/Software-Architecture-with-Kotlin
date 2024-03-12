package com.example.base.model

class ContractRepository {
    var onSubmitListener: DraftContractSubmittedListener? = null

    fun submit(contract: DraftContractInput): Boolean {
        // Submit the draft contract for validation and persistence
        return true.also {
            onSubmitListener?.invoke(contract)
        }.also {
            println("Persisted contract: $contract")
        }
    }
}

typealias DraftContractSubmittedListener = (DraftContractInput) -> Unit