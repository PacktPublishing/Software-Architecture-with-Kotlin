package com.example.base.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.base.R
import com.example.base.model.ContractRepository
import com.example.base.model.DraftContractInput
import com.example.base.model.HouseholdInput
import com.example.base.view.ConfirmationFragment
import com.example.base.view.ContractDraftFragment

class MainActivity : AppCompatActivity(), Controller {
    private val contractRepository: ContractRepository = ContractRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contractDraftFragment = ContractDraftFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, contractDraftFragment).commit()
    }

    override fun submitContract(contract: DraftContractInput) {
        contractRepository.submit(contract)

        val confirmationFragment = ConfirmationFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, confirmationFragment).commit()
    }
}

interface Controller {
    fun submitContract(contract: DraftContractInput)
}