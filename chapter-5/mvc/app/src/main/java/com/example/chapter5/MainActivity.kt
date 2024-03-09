package com.example.chapter5

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.chapter5.databinding.ActivityMainBinding
import com.example.chapter5.model.ContractRepository
import com.example.chapter5.model.DraftContractInput
import com.example.chapter5.model.HouseholdInput

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val contractRepository: ContractRepository = ContractRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)


        findViewById<Button>(R.id.your_neighbor_submit_button).setOnClickListener { yourHousehold ->
            yourHousehold.findNavController().navigate(R.id.your_neighbor_to_confirmation)

            val yourHouseholdNameField =
                findViewById<EditText>(R.id.your_household_name_textedit)
            val yourHouseholdServiceField =
                findViewById<EditText>(R.id.your_household_service_textedit)
            val yourNeighborNameField = findViewById<EditText>(R.id.your_neighbor_name_textedit)
            val yourNeighborServiceField =
                findViewById<EditText>(R.id.your_neighbor_service_textedit)

            val yourHouseholdName = yourHouseholdNameField.text.toString()
            val yourHouseholdService = yourHouseholdServiceField.text.toString()
            val yourNeighborName = yourNeighborNameField.text.toString()
            val yourNeighborService = yourNeighborServiceField.text.toString()

            val contract = DraftContractInput(
                initiator = HouseholdInput(yourHouseholdName, yourHouseholdService),
                neighbor = HouseholdInput(yourNeighborName, yourNeighborService)
            )

            contractRepository.submit(contract).also {
                println("Submitted contract: $contract")
            }
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            println("hello")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}