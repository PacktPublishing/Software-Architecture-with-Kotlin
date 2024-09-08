package com.example.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.base.R
import com.example.base.viewmodel.Command
import com.example.base.viewmodel.DraftContractViewModel
import com.example.base.viewmodel.toModel

class ContractDraftFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflated = inflater.inflate(R.layout.fragment_contract_draft, container, false)
        val command = activity as Command
        val viewModel = ViewModelProvider(activity as AppCompatActivity).get(DraftContractViewModel::class.java)
        inflated.findViewById<Button>(R.id.submit_button)
            ?.setOnClickListener {
                viewModel.toModel()?.let {
                    command.submitContract(it)
                }
            }
        inflated.findViewById<EditText>(R.id.your_household_name_edit)?.bind {
            viewModel.yourHouseholdName = it
        }
        inflated.findViewById<EditText>(R.id.your_household_service_edit)?.bind {
            viewModel.yourHouseholdService = it
        }
        inflated.findViewById<EditText>(R.id.your_neighbor_name_edit)?.bind {
            viewModel.yourNeighborName = it
        }
        inflated.findViewById<EditText>(R.id.your_neighbor_service_edit)?.bind {
            viewModel.yourNeighborService = it
        }
        return inflated
    }
}

fun EditText.bind(consume: (String) -> Unit) {
    consume(text.toString())
    addTextChangedListener {
        consume(it.toString())
    }
}