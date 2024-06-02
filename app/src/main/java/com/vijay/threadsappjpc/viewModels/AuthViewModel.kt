package com.vijay.threadsappjpc.viewModels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import com.vijay.threadsappjpc.model.UserModel
import java.util.UUID

class AuthViewModel:ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef =db.getReference("users")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser : LiveData<FirebaseUser> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    init {
        _firebaseUser.value = auth.currentUser
    }
    fun login(email:String,pass:String){
            auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        _firebaseUser.postValue(auth.currentUser)
                    }else{
                        _error.postValue("Something went wrong")
                    }
                }
    }
    fun register(
        email:String,
        pass:String,
        name:String,
        bio:String,
        userName:String,
        photoUri:Uri){
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email,pass,name,bio,userName,photoUri,auth.currentUser?.uid)
                }else{
                    _error.postValue("Something went wrong")
                }
            }
    }

    private fun saveImage(email: String, pass: String, name: String, bio: String, userName: String, photoUri: Uri, uid: String?) {
            val uploadTask = imageRef.putFile(photoUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email,pass,name,bio,userName,it.toString(),uid)
            }
        }
    }

    private fun saveData(email: String, pass: String, name: String, bio: String, userName: String, toString: String, uid: String?) {
        val userData = UserModel(email,pass,name,bio,userName,toString)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {

            }
    }


}