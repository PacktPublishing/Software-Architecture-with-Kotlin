package com.example.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.base.R
import com.example.base.controller.Controller
import com.example.base.model.DraftContractInput
import com.example.base.model.HouseholdInput

class ContractDraftFragment : Fragment() {
    lateinit var controller: Controller
    lateinit var inflated: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_contract_draft, container, false)
        controller = activity as Controller

        inflated.findViewById<Button>(R.id.submit_button)
            ?.setOnClickListener {
                controller.submitContract(
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