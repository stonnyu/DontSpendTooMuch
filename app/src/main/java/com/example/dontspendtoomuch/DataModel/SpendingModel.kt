package com.example.dontspendtoomuch.DataModel

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

private const val TAG: String = "SpendingModel"

object SpendingModel : Observable() {
    private var mValueDataListener: ValueEventListener? = null
    private var mSpendingList: ArrayList<Spending>? = ArrayList()

    private fun getDatabaseRef() : DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("Spending")
    }

    init {
        if (mValueDataListener != null) {
            getDatabaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null
        Log.i(TAG, "Data init. Line 27")

        mValueDataListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.i(TAG, "Data is updated. Line 32")
                    val data: ArrayList<Spending> = ArrayList()
                    if (dataSnapshot != null) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            try {
                                data.add(Spending(snapshot))
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        mSpendingList = data
                        Log.i(TAG, "Data updated. There are " + mSpendingList!!.size + " entrees in the cache.")
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

    fun getData() : ArrayList<Spending>? {
        return mSpendingList
    }

}