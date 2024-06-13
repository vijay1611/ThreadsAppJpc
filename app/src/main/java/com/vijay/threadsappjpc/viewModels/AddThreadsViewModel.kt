package com.vijay.threadsappjpc.viewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.vijay.threadsappjpc.model.ThreadModel
import com.vijay.threadsappjpc.model.UserModel
import com.vijay.threadsappjpc.utils.SharedPref
import java.util.UUID

class AddThreadsViewModel:ViewModel() {

        private val db = FirebaseDatabase.getInstance()
        val userRef =db.getReference("threads")

        private val _isPosted = MutableLiveData<Boolean>()
        val isPosted : LiveData<Boolean> = _isPosted

        private val storageRef = Firebase.storage.reference
        private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")


         fun saveImage(
            thread:String,
            userId:String,
            imageUri:Uri
            ) {
            val uploadTask = imageRef.putFile(imageUri)
            uploadTask.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener {
                    saveData(thread,userId,it.toString())
                }
            }
        }

        fun saveData(
            thread:String,
            userID:String,
            image:String
           ) {

            val threadData = ThreadModel(thread,image,userID,System.currentTimeMillis().toString())

            userRef.child(userRef.push().key!!).setValue(threadData)
                .addOnSuccessListener {
                    _isPosted.postValue(true)
                }.addOnFailureListener{
                    _isPosted.postValue(false)
                }
        }

}