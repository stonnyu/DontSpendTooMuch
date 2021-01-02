package com.example.dontspendtoomuch.DataModel

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

private const val TAG: String = "CategoryModel"

object CategoryModel : Observable() {
    private var mValueDataListener: ValueEventListener? = null
    private var mCategoryList: ArrayList<Category>? = ArrayList()

    private fun getDatabaseRef() : DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("Category")
    }

    init {
        reset()
    }

    fun reset() {
        if (mValueDataListener != null) {
            getDatabaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null
        Log.i(TAG, "Data init. Line 27")

        mValueDataListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.i(TAG, "Data is updated. Line 32")
                    val data: ArrayList<Category> = ArrayList()
                    if (dataSnapshot != null) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            try {
                                data.add(Category(snapshot))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        mCategoryList = data
                        Log.i(TAG, "Data updated. There are " + mCategoryList!!.size + " entrees in the cache.")
                        setChanged()
                        notifyObservers()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                if (p0 != null) {
                    Log.i(TAG, "Line 54 data update cancelled, error = ${p0.message}")
                }
            }
        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    /**
     * Get an article
     * @param id The id of a possible article
     * @return if an article is found return the article else return null
     */
    fun getCategory(id: String): Category {
        for(category in mCategoryList!!){
            if(category.id == id)
                return category
        }
        return Category(null)
    }

    fun getData() : ArrayList<Category>? {
        return mCategoryList
    }

}