package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MySharedPreferences(private val context: Context) {

    companion object {
        const val PREF_APP = "pref_app"

        @SuppressLint("StaticFieldLeak")
        private var instance: MySharedPreferences? = null

        fun initializeInstance(context: Context) {
            if (instance == null) {
                instance = MySharedPreferences(context)
            }
        }

        fun getInstance(): MySharedPreferences {
            if (instance == null) {
                throw IllegalStateException(
                    MySharedPreferences::class.java.simpleName +
                            " is not initialized, call initializeInstance(..) method first."
                )
            }
            return instance as MySharedPreferences
        }
    }


    fun getBooleanData(key: String?): Boolean {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
            .getBoolean(key, false)
    }

    fun getIntData(key: String?, default: Int = 0): Int {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key, default)
    }

    fun getLong(key: String?): Long {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getLong(key, 0)
    }

    fun getFloatData(key: String?): Float {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getFloat(key, -1f)
    }

    fun saveFloat(key: String?, `val`: Float) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putFloat(key, `val`)
            .apply()
    }

    fun saveLong(key: String, value: Long) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putLong(key, value)
            .apply()
    }

    fun getLongPreference(key: String, def: Long = 0): Long {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getLong(key, def)
    }

    // Get Data
    fun getStringData(key: String?): String? {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, null)
    }

    // Get Data

    fun getStringData(key: String?, defValue: String?): String? {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
            .getString(key, defValue)
    }

    // Save Data
    fun saveData(key: String?, `val`: String?) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit()
            .putString(key, `val`).apply()
    }

    fun saveData(key: String?, `val`: Int) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, `val`)
            .apply()
    }

    fun saveData(key: String?, `val`: Long) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putLong(key, `val`)
            .apply()
    }

    fun saveData(key: String?, `val`: Boolean) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(key, `val`)
            .apply()
    }

    fun getStringPreference(key: String, def: String = ""): String {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, def)!!
    }

    fun setStringPreference(key: String, value: String) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putString(key, value)
            .apply()
    }

    fun getIntPreference(key: String, def: Int = 0): Int {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getInt(key, def)
    }

    fun setIntPreference(key: String, value: Int) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putInt(key, value)
            .apply()
    }

    fun getBooleanPreference(key: String, def: Boolean = true): Boolean {
        return context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getBoolean(key, def)
    }

    fun setBooleanPreference(key: String, value: Boolean) {
        context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit().putBoolean(key, value)
            .apply()
    }


    fun saveObjectToSharedPreference(
        serializedObjectKey: String?,
        `object`: Any?
    ) {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        val sharedPreferencesEditor = sharedPreferences.edit()
        val gson = Gson()
        val serializedObject = gson.toJson(`object`)
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject)
        sharedPreferencesEditor.apply()
    }


    fun <GenericClass> getSavedObjectFromPreference(
        preferenceKey: String?,
        classType: Class<GenericClass>?
    ): GenericClass? {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        if (sharedPreferences.contains(preferenceKey)) {
            val gson = Gson()
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType)
        }
        return null
    }

    fun saveListInt(list: List<Int>?, key: String?, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        val gson = Gson()
        val json = gson.toJson(list)
        val editor = sharedPreferences.edit()
        editor.putString(key, json)
        editor.commit()
    }

    fun getListInt(key: String?, context: Context): List<Int> {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        var arrayItems: List<Int>? = null
        val serializedObject = sharedPreferences.getString(key, "")
        if (serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Int>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }
        if (arrayItems == null) arrayItems = ArrayList()
        return arrayItems
    }

    fun saveListInLocal(list: List<String>?, key: String?, context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        val gson = Gson()
        val json = gson.toJson(list)
        val editor = sharedPreferences.edit()
        editor.putString(key, json)
        editor.commit()
    }

    fun getList(key: String?, context: Context): List<String> {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        var arrayItems: List<String>? = null
        val serializedObject = sharedPreferences.getString(key, "")
        if (serializedObject != null) {
            val gson = Gson()
            val type = object : TypeToken<List<String>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }
        if (arrayItems == null) arrayItems = ArrayList()
        return arrayItems
    }

    inline fun <reified T> saveListObjectToSharePreference(
        key: String,
        list: MutableList<T>,
        context: Context
    ) {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        val gson = Gson()
        val json = gson.toJson(list)
        sharedPreferences.edit().putString(key, json).apply()
    }

    inline fun <reified T> getListObjectFromSharePreference(
        key: String,
        context: Context
    ): MutableList<T> {
        val sharedPreferences = context.getSharedPreferences(PREF_APP, 0)
        var arrayItems = mutableListOf<T>()
        val serializedObject: String? = sharedPreferences.getString(key, null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<MutableList<T?>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }
        return arrayItems
    }

//    fun saveObject(key: String?, currencyUnit: CurrencyList?) {
//        val prefsEditor = context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).edit()
//        val gson = Gson()
//        val json = gson.toJson(currencyUnit)
//        prefsEditor.putString(key, json)
//        prefsEditor.commit()
//    }
//
//    fun getObject(key: String?): CurrencyList {
//        val gson = Gson()
//        val json =
//            context.getSharedPreferences(PREF_APP, Context.MODE_PRIVATE).getString(key, "")
//        return gson.fromJson(json, CurrencyList::class.java)
//    }

    fun getSharedPrefEditor(pref: String?): SharedPreferences.Editor {
        return context.getSharedPreferences(pref, Context.MODE_PRIVATE).edit()
    }

    fun saveData(editor: SharedPreferences.Editor) {
        editor.apply()
    }

}