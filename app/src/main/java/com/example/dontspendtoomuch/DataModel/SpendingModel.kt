package com.example.dontspendtoomuch.DataModel

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private const val TAG: String = "SpendingModel"

object SpendingModel : Observable() {
    private var mValueDataListener: ValueEventListener? = null
    private var mSpendingList: ArrayList<Spending>? = ArrayList()
    private val mSpendingsIdMap : HashMap<String, String>  = hashMapOf()

    private fun getDatabaseRef() : DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("Spending")
    }

    init {
        if (mValueDataListener != null) {
            getDatabaseRef()?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener = null

        mValueDataListener = object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    val data: ArrayList<Spending> = ArrayList()
                    if (dataSnapshot != null) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            try {
                                var spending = Spending(snapshot)
                                data.add(spending)
                                mSpendingsIdMap.put(spending.spendingTitle, spending.id)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        mSpendingList = data
                        setChanged()
                        notifyObservers()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                if (p0 != null) {
                    Log.i(TAG, "Line 51 data update cancelled, error = ${p0.message}")
                }
            }
        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getIDs() : HashMap<String, String> {
        return mSpendingsIdMap
    }

    fun getData() : ArrayList<Spending>? {
        return mSpendingList
    }

}