package com.example.aviationlovers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FlightsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights)

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
}
