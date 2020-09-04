package com.example.baterky.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.baterky.Constant
import com.example.baterky.model.Company


class SharedPref(context: Context) {
    var sharedPref: SharedPreferences =
        context.getSharedPreferences(Constant.KEY, Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return sharedPref.getString(key, "")!!
    }

    fun saveString(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value).apply()
    }

    fun saveCompanyValues(company: Company){
        saveString(Constant.ADDRESS1, company.addressLine1)
        saveString(Constant.ADDRESS2, company.addressLine2)
        saveString(Constant.CITY, company.city)
        saveString(Constant.EMAIL, company.email)
        saveString(Constant.PHONE, company.phone)
        saveString(Constant.TITLE, company.title)
        saveString(Constant.ZIP, company.zipCode)
        saveString(Constant.COMPANY_ID, company.companyId)
    }
}
