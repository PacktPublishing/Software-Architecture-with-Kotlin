package com.example.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.base.R
import com.example.base.presenter.Presenter
import com.example.base.model.DraftContractInput
import com.example.base.model.HouseholdInput

class ContractDraftFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflated = inflater.inflate(R.layout.fragment_contract_draft, container, false)
        val presenter = activity as Presenter

        inflated.findViewById<Button>(R.id.submit_button)
            ?.setOnClickListener {
                presenter.submitContract(
                    DraftContractInput(
                        initiator = HouseholdInput(
                            householdName = inflated.findViewById<EditText>(R.id.your_household_name_edit).text.toString(),
                            serviceProvided = inflated.findViewById<EditText>(R.id.your_household_service_edit).text.toString(),
                        ),
                        neighbor = HouseholdInput(
                            householdName = inflated.findViewById<EditText>(R.id.your_neighbor_name_edit).text.toString(),
                            serviceProvided = inflated.findViewById<EditText>(R.id.your_neighbor_service_edit).text.toString(),
                        )
                    )
                )
            }
        return inflated
    }
}