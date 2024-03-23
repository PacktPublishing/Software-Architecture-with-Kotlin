package com.example.base.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.base.R
import com.example.base.model.ContractRepository
import com.example.base.model.DraftContractInput
import com.example.base.view.ConfirmationFragment
import com.example.base.view.ContractDraftFragment
import com.example.base.viewmodel.DraftContractViewModel

class MainActivity : AppCompatActivity(), Presenter {
    private val contractRepository: ContractRepository = ContractRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contractDraftFragment = ContractDraftFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, contractDraftFragment).commit()
    }

    override fun submitContract(contract: DraftContractInput) {
        contractRepository.onSubmitListener = {
            val confirmationFragment = ConfirmationFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, confirmationFragment).commit()
        }
        contractRepository.submit(contract)
    }
}