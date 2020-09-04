package com.example.baterky.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.baterky.Constant
import com.example.baterky.R
import com.example.baterky.model.Company
import com.example.baterky.retrofit.ApiService
import com.example.baterky.retrofit.RetrofitBuilder
import com.example.baterky.utils.SharedPref
import kotlinx.android.synthetic.main.activity_company.*
import kotlinx.android.synthetic.main.main_activity.toolbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompanyActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)

        sharedPref = SharedPref(this)
        fillInitData()
        setUpToolbar()

        next_button.setOnClickListener {
            if (email.text.toString() == "") {
                error.error = "Vyplňte email"
                return@setOnClickListener
            }

            val company = Company(
                addressLine1 = address1.text.toString(),
                addressLine2 = address2.text.toString(),
                companyId = company_id.text.toString(),
                city = city.text.toString(),
                email = email.text.toString(),
                phone = phone.text.toString(),
                title = company_title.text.toString(),
                zipCode = zip.text.toString()
            )

            sharedPref.saveCompanyValues(company)

            makeCompanyApiCall(company)
        }

        cancel_button.setOnClickListener {
            startActivity(Intent(this@CompanyActivity, MainActivity::class.java))
        }
    }

    private fun setUpToolbar() {
        val _toolbar = toolbar as Toolbar
        setSupportActionBar(_toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
    }

    private fun makeCompanyApiCall(company: Company) {
        val call = RetrofitBuilder.buildService(ApiService::class.java).postCompany(company)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@CompanyActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@CompanyActivity, MainActivity::class.java))
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Toast.makeText(
                    this@CompanyActivity,
                    "Company vytvořena, můžete skenovat",
                    Toast.LENGTH_LONG
                ).show()
                startActivity(Intent(this@CompanyActivity, MainActivity::class.java))
            }
        })
    }

    private fun fillInitData() {
        address1.setText(sharedPref.getString(Constant.ADDRESS1))
        address2.setText(sharedPref.getString(Constant.ADDRESS2))
        company_title.setText(sharedPref.getString(Constant.TITLE))
        city.setText(sharedPref.getString(Constant.CITY))
        email.setText(sharedPref.getString(Constant.EMAIL))
        phone.setText(sharedPref.getString(Constant.PHONE))
        zip.setText(sharedPref.getString(Constant.ZIP))
        company_id.setText(sharedPref.getString(Constant.COMPANY_ID))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}