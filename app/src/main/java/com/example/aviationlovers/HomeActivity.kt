package com.example.aviationlovers

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        findViewById<TextView>(R.id.textView).text = currentUser?.email + "\n" + "\n" + currentUser?.displayName

        findViewById<Button>(R.id.signOutBtn).setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Set up the bottom navigation menu listener
        val bottomNavigation: BottomNavigationView = findViewById(R.id.BottomNav)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    // Already on the home activity, do nothing
                    true
                }
                R.id.flights -> {
                    // Launch the FlightsActivity
                    val flightsIntent = Intent(this, FlightsActivity::class.java)
                    startActivity(flightsIntent)
                    true
                }
                else -> false
            }
        }
    }
}
