package com.example.aviationlovers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aviationlovers.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.example.aviationlovers.databinding.ActivityFlightsBinding
import java.util.*
import kotlin.collections.ArrayList


class FlightsActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dataList: ArrayList<DataFile>
    private lateinit var adapter: MyAdapter
    private lateinit var binding: ActivityFlightsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

        binding = ActivityFlightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@FlightsActivity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager
//        binding.search.clearFocus()

        val builder = AlertDialog.Builder(this@FlightsActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)

        val dialog = builder.create()
        dialog.show()

        dataList = ArrayList()
        adapter = MyAdapter(this@FlightsActivity, dataList)
        binding.recyclerView.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("FlightsList")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataFile = itemSnapshot.getValue(DataFile::class.java)
                    if (dataFile != null) {
                        dataList.add(dataFile)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        val addButton = findViewById<FloatingActionButton>(R.id.add_button)
        addButton.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })



        val email = intent.getStringExtra("email")
        val displayName = intent.getStringExtra("displayName")

        // Set up the bottom navigation menu listener
        val bottomNavigation: BottomNavigationView = findViewById(R.id.BottomNav)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.flights -> {
                    // Already on the FlightsActivity, do nothing
                    true
                }
                R.id.home -> {
                    // Launch the HomeActivity and pass back the email and display name
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("name", displayName)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // Set the selected item in the bottom navigation to be the flights icon
        bottomNavigation.selectedItemId = R.id.flights
    }

    fun searchList(text: String) {
        val searchList = java.util.ArrayList<DataFile>()
        for (dataClass in dataList) {
            if (dataClass.dataRoute?.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}
