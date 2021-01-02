package com.example.dontspendtoomuch.DataModel

import com.google.firebase.database.DataSnapshot
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates

class Category (snapshot: DataSnapshot?) {
    lateinit var id: String
    lateinit var categoryTitle: String
    var categorySpendings: Any? = null

    init {
        try {
            val data: HashMap<String, Any> = snapshot!!.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            categoryTitle = data["categoryTitle"] as String
            categorySpendings = data["categorySpendings"]
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun toString(): String {
        return categoryTitle
    }
}