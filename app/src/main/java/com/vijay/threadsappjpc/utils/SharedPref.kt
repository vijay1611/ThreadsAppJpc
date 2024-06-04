package com.vijay.threadsappjpc.utils

import android.content.Context

object SharedPref {

    fun storeData(name:String,email:String,bio:String,userName:String,imageUrl:String,context:Context){
        val sharedPreference = context.getSharedPreferences("users", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()

        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("bio",bio)
        editor.putString("userName",userName)
        editor.putString("imageUrl",imageUrl)
        editor.apply()
    }
    fun getUserName(context: Context):String{
        val sharedPre= context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPre.getString("userName","")!!
    }
    fun getName(context: Context):String{
        val sharedPre= context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPre.getString("name","")!!
    }
    fun getEmail(context: Context):String{
        val sharedPre= context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPre.getString("email","")!!
    }
    fun getBio(context: Context):String{
        val sharedPre= context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPre.getString("bio","")!!
    }
    fun getimageUrl(context: Context):String{
        val sharedPre= context.getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPre.getString("imageUrl","")!!
    }


}