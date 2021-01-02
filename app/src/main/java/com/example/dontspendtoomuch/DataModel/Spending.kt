package com.example.dontspendtoomuch.DataModel

import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class Spending (snapshot: DataSnapshot?) {
    lateinit var id: String
    lateinit var spendingTitle: String
    lateinit var spendingDate: String
    lateinit var spendingCategory: String
    var spendingAmount by Delegates.notNull<Long>()

    init {
        try {
            val data: HashMap<String, Any> = snapshot!!.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            spendingTitle = data["spendingTitle"] as String
            spendingDate = data["spendingDate"] as String
            spendingCategory = data["spendingCategory"] as String
            spendingAmount = data["spendingAmount"] as Long
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}