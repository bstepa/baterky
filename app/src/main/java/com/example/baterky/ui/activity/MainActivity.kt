package com.example.baterky.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.baterky.Constant
import com.example.baterky.R
import com.example.baterky.utils.SharedPref
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.toolbar))

        sharedPref = SharedPref(this)
        sharedPref.saveString(Constant.CODE, "")

        fab.setOnClickListener { _ ->
            if (sharedPref.getString("email") == ""){
                Toast.makeText(this, "Musíte vytvořit company", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, CompanyActivity::class.java))
                return@setOnClickListener
            }

            scanQrCode()
        }
    }

    //Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_account -> {
                startActivity(Intent(this, CompanyActivity::class.java))
            }
            R.id.action_battery -> {
                startActivity(Intent(this, TicketActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun scanQrCode() {
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
                    sharedPref.saveString(Constant.CODE, result.contents)
                    startActivity(Intent(this, TicketActivity::class.java))
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}