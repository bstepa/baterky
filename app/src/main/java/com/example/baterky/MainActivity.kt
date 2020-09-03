package com.example.baterky

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        fab.setOnClickListener { _ ->
            if (sharedPreferences.getString("email", "") == ""){
                Toast.makeText(this, "Musíte se přihlásit", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }

            makeScan()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_account -> true
            R.id.action_battery -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun makeScan() {
        val scanner = IntentIntegrator(this)
        scanner.setPrompt("Scan 2D code")
        scanner.setCameraId(0)
        scanner.setOrientationLocked(true)
        scanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    val editor = sharedPreferences.edit()
                    editor.putString("code", result.contents).apply()
                    startActivity(Intent(this, FinalActivity::class.java))
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}