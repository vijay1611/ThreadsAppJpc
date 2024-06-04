package com.vijay.threadsappjpc.viewModels

import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
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
import com.google.firebase.storage.storage
import com.vijay.threadsappjpc.model.UserModel
import com.vijay.threadsappjpc.utils.SharedPref
import java.util.UUID


class AuthViewModel:ViewModel() {

    val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance()
    val userRef =db.getReference("users")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser : LiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error : LiveData<String> = _error

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    init {
        _firebaseUser.value = auth.currentUser
    }
    fun login(email:String,pass:String,context: Context){
            auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        _firebaseUser.postValue(auth.currentUser)
                        getData(auth.currentUser!!.uid,context )
                    }else{
                        _error.postValue(it.exception!!.message)
                    }
                }
    }

    private fun getData(uid: String,context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(userData!!.name,userData!!.email,userData!!.bio,userData!!.userName,userData!!.imageUrl,context)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun register(
        email:String,
        pass:String,
        name:String,
        bio:String,
        userName:String,
        photoUri:Uri,
        context: Context){
        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(email,pass,name,bio,userName,photoUri,auth.currentUser?.uid,context)
                }else{
                    _error.postValue(it.exception!!.message)
                }
            }
    }

    private fun saveImage(email: String, pass: String, name: String, bio: String, userName: String, photoUri: Uri, uid: String?,context:Context) {
            val uploadTask = imageRef.putFile(photoUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email,pass,name,bio,userName,it.toString(),uid,context)
            }
        }
    }

     fun saveData(email: String, pass: String, name: String, bio: String, userName: String, toString: String, uid: String?,context: Context) {
        val userData = UserModel(email,pass,name,bio,userName,toString,uid!!)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(name,email,bio,userName,toString, context)
            }
    }
     fun logOut(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}