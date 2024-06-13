package com.vijay.threadsappjpc.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.vijay.threadsappjpc.model.ThreadModel
import com.vijay.threadsappjpc.model.UserModel

class SearchViewModel:ViewModel() {
    private val db = FirebaseDatabase.getInstance()
    val users =db.getReference("users")

    private val _usersList = MutableLiveData<List<UserModel>>()
    val usersList : LiveData<List<UserModel>> = _usersList

    init {
        fetchThreadAndUsers {
            _usersList.value = it
        }
    }
    private fun fetchThreadAndUsers(onResult : (List<UserModel>) -> Unit){
        users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<UserModel>()

                for(threadSnopShot in snapshot.children){
                    val user = threadSnopShot.getValue(UserModel::class.java)
                    user.let{
                        result.add(it!!)
                    }
                }
                onResult(result)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}