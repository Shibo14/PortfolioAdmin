package uz.khan.portfolioadmin.data

import com.google.firebase.database.FirebaseDatabase

class DataModifier {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val ref = firebaseDatabase.getReference("projects")

    fun control(available: Boolean, id: String) {
        ref.child(id).child("available").setValue(available)
    }
}