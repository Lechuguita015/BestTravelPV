package com.example.besttravel.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.besttravel.R
import com.example.besttravel.models.FavoriteModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


object AppPrefs {


    private fun getPrefs(context: Context): SharedPreferences
    {
        return context.getSharedPreferences(context.getString(R.string.app_name),MODE_PRIVATE)
    }

    // To save favorite data
    fun saveList(context: Context,mArrayList: ArrayList<FavoriteModel>)
    {
        val gson = Gson()
        val json = gson.toJson(mArrayList)
        val editor = getPrefs(context).edit()
        editor.putString("favorite",json)
        editor.apply()
    }

    // To get favorite data
    fun getList(context: Context): ArrayList<FavoriteModel> {
        var arrayItems: ArrayList<FavoriteModel> = ArrayList()

        if (getPrefs(context).contains("favorite"))
        {
            val serializedObject: String = getPrefs(context).getString("favorite", null)!!
            if (serializedObject != null) {
                val gson = Gson()
                val type: Type = object : TypeToken<List<FavoriteModel?>?>() {}.type
                arrayItems = gson.fromJson<ArrayList<FavoriteModel>>(serializedObject, type)
            }
        }
        return arrayItems
    }


}