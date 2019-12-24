package com.example.besfirmtestapp.Utils

import android.content.Context
import android.content.SharedPreferences

class SPHelper {

    fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("app_config", Context.MODE_PRIVATE)
    }

    fun putPreference(context: Context, key: String, value: Any) {
        val editor = getSharedPreferences(context).edit()
        if (value is Int) {
            editor.putInt(key, value)
        } else if (value is Short) {
            val data = value.toShort().toInt()
            editor.putInt(key, data)
        } else if (value is Byte) {
            val data = value.toByte().toInt()
            editor.putInt(key, data)
        } else if (value is Float) {
            editor.putFloat(key, value)
        } else if (value is Double) {
            val data = value.toDouble().toFloat()
            editor.putFloat(key, data)
        } else if (value is Long) {
            editor.putLong(key, value)
        } else if (value is Boolean) {
            editor.putBoolean(key, value)
        } else if (value is String) {
            editor.putString(key, value)
        }
        editor.commit()
    }

    fun getPreference(context: Context, key: String, defaultValue: Any): Any? {
        val sp = getSharedPreferences(context)
        if (defaultValue is Int || defaultValue is Short || defaultValue is Byte) {
            return sp.getInt(key, defaultValue as Int)
        }
        if (defaultValue is Float || defaultValue is Double) {
            return sp.getFloat(key, defaultValue as Float)
        }
        if (defaultValue is Long) {
            return sp.getLong(key, defaultValue)
        }
        if (defaultValue is Boolean) {
            return sp.getBoolean(key, defaultValue)
        }
        return if (defaultValue is String) {
            sp.getString(key, defaultValue)
        } else null
    }

    fun removePreference(context: Context, key: String) {
        getSharedPreferences(context).edit().remove(key).commit()
    }
}