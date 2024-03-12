package com.example.base.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.base.R
import com.example.base.model.ContractRepository
import com.example.base.model.DraftContractInput
import com.example.base.view.ConfirmationFragment
import com.example.base.view.ContractDraftFragment

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
            confirmationFragment.lastSubmittedContract = it
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, confirmationFragment).commit()
        }
        contractRepository.submit(contract)
    }
}