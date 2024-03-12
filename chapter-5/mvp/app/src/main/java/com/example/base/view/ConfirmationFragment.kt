package com.example.base.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.base.R
import com.example.base.presenter.Presenter

class ConfirmationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_confirmation, container, false)
        val presenter = activity as Presenter

        presenter.findLastSubmittedContract()?.also {
            inflated.findViewById<TextView>(R.id.your_household_summary).text =
                "Your household \"${it.initiator.householdName}\" providing ${it.initiator.serviceProvided}"
            inflated.findViewById<TextView>(R.id.your_neighbor_summary).text =
                "your neighbor \"${it.neighbor.householdName}\" providing ${it.neighbor.serviceProvided}"
        }
        return inflated
    }
}