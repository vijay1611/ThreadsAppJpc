package com.vijay.threadsappjpc.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.vijay.threadsappjpc.model.ThreadModel
import com.vijay.threadsappjpc.model.UserModel
import java.util.Objects

class HomeViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance()
    val thread =db.getReference("threads")

    private val _threadsAndUsers = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadsAndUsers : LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUsers

    init {
        fetchThreadAndUsers {
            _threadsAndUsers.value = it
        }
    }
    private fun fetchThreadAndUsers(onResult : (List<Pair<ThreadModel,UserModel>>) -> Unit){
        thread.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val result = mutableListOf<Pair<ThreadModel,UserModel>>()

                for(threadSnopShot in snapshot.children){
                    val thread = threadSnopShot.getValue(ThreadModel::class.java)
                    thread.let{
                        fetchUserFromThread(it!!){
                            user ->
                            result.add(0,it to user)

                            if(result.size == snapshot.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    fun fetchUserFromThread(thread : ThreadModel,onResult:(UserModel)->Unit){
            db.getReference("users").child(thread.userID)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(UserModel::class.java)
                        user?.let(onResult)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
    }

}