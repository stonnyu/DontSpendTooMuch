package com.example.dontspendtoomuch.DataModel

import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class Spending (snapshot: DataSnapshot?) {
    lateinit var id: String
    lateinit var spendingTitle: String
    lateinit var spendingDate: Date
    lateinit var spendingCategory: String
    var spendingAmount by Delegates.notNull<Double>()

    init {
        try {
            val data: HashMap<String, Any> = snapshot!!.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            spendingTitle = data["spendingTitle"] as String
            spendingDate = data["spendingDate"] as Date
            spendingCategory = data["spendingCategory"] as String
            spendingAmount = data["spendingAmount"] as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}