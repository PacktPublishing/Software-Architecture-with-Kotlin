package com.example.base.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.base.R
import com.example.base.model.DraftContractInput
import com.example.base.presenter.Presenter
import com.example.base.viewmodel.DraftContractViewModel

class ConfirmationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_confirmation, container, false)
        val viewModel = ViewModelProvider(activity as AppCompatActivity).get(DraftContractViewModel::class.java)
        inflated.findViewById<TextView>(R.id.your_household_summary).text =
            "Your household \"${viewModel.yourHouseholdName}\" providing ${viewModel.yourHouseholdService}"
        inflated.findViewById<TextView>(R.id.your_neighbor_summary).text =
            "your neighbor \"${viewModel.yourNeighborName}\" providing ${viewModel.yourNeighborService}"
        return inflated
    }
}