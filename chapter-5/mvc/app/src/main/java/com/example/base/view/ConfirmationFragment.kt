package com.example.base.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.base.R


class ConfirmationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflated = inflater.inflate(R.layout.fragment_confirmation, container, false)

        val yourHouseholdName = arguments?.getString("yourHouseholdName")
        val yourHouseholdService = arguments?.getString("yourHouseholdService")
        val yourNeighborName = arguments?.getString("yourNeighborName")
        val yourNeighborService = arguments?.getString("yourNeighborService")
        inflated.findViewById<TextView>(R.id.your_household_summary).text =
            "Your household \"$yourHouseholdName\" providing ${yourHouseholdService}"
        inflated.findViewById<TextView>(R.id.your_neighbor_summary).text =
            "your neighbor \"$yourNeighborName\" providing ${yourNeighborService}"

        return inflated
    }
}