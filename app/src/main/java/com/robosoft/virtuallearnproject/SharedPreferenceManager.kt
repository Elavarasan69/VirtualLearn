package com.robosoft.virtuallearnproject

import android.content.Context

class SharedPreferenceManager(val context: Context) {

    val sharedPreferences = context.getSharedPreferences("sharedPreference" , Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    fun getAccessToken() : String? {
        val accessToken = "Bearer "+ sharedPreferences.getString("accessToken", "")
        return accessToken
    }

    fun getPhoneNumber() : Long {
        return sharedPreferences.getLong("phone_no" , 0L)
    }

    fun updateAccessToken(newAccessToken : String){
        editor.putString("accessToken", newAccessToken).commit()
    }

    fun updatePhoneNo(newPhoneNo : Long){
        editor.putLong("phone_no", newPhoneNo).commit()
    }
//        val image = sharedPreferences.getString("profileImageIv","")
//        val name = sharedPreferences.getString("profileNameTv","")
//        val courses = sharedPreferences.getString("courses","")
//        val chapters = sharedPreferences.getString("chapters","")
//        val tests = sharedPreferences.getString("testsTv","")
//        val fullname = sharedPreferences.getString("fullNameTv","")
//        val username = sharedPreferences.getString("usernameTv","")
//        val email = sharedPreferences.getString("emailTv","")
//        val mobilenum = sharedPreferences.getString("mblnoTv","")

}
