package com.example.portfolio.data

import uz.khan.portfolioadmin.data.models.ProjectModel
import com.google.firebase.database.FirebaseDatabase

/**
 * @Author: Temur
 * @Date: 02/08/2022
 */

class FirebaseDataSender {
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val ref = firebaseDatabase.getReference("projects")

    fun send(projectModel: ProjectModel, onSuccess:() -> Unit) {
        val key = ref.push().key ?: ""
        projectModel.id = key
        ref.child(key).setValue(projectModel).addOnSuccessListener {
            onSuccess()
        }
    }
}