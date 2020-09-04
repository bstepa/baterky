package com.example.baterky.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.baterky.Constant
import com.example.baterky.R
import com.example.baterky.model.Ticket
import com.example.baterky.retrofit.ApiService
import com.example.baterky.retrofit.RetrofitBuilder
import com.example.baterky.utils.SharedPref
import kotlinx.android.synthetic.main.activity_ticket.*
import kotlinx.android.synthetic.main.main_activity.toolbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TicketActivity : AppCompatActivity() {

    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        sharedPref = SharedPref(this)

        setUpToolbar()

        val companyId = sharedPref.getString(Constant.COMPANY_ID)
        val code = sharedPref.getString(Constant.CODE)
        battery_serial_number.setText(code)

        next_button.setOnClickListener{
            if (battery_serial_number.text.toString() == ""){
                error_ico.error = "Vyplňte sériové číslo"
                return@setOnClickListener
            }

            val ticket = Ticket(
                addressLine1 = battery_address1.text.toString(),
                addressLine2 = battery_address2.text.toString(),
                city = battery_city.text.toString(),
                companyId = companyId,
                email = battery_email.text.toString(),
                description = battery_description.text.toString(),
                serialNumber = code,
                zipCode = battery_zip.text.toString()
            )

            makeTicketApiCall(ticket)
        }

        cancel_button.setOnClickListener{
            startActivity(Intent(this@TicketActivity, MainActivity::class.java))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpToolbar() {
        val _toolbar = toolbar as Toolbar
        setSupportActionBar(_toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun makeTicketApiCall(ticket: Ticket){
        val call = RetrofitBuilder.buildService(ApiService::class.java).postTicket(ticket)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@TicketActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@TicketActivity, MainActivity::class.java))
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(
                    this@TicketActivity,
                    "Odesláno",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this@TicketActivity, MainActivity::class.java))
            }
        })
    }
}