package com.vijay.threadsappjpc.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.vijay.threadsappjpc.item_view.ThreadItem
import com.vijay.threadsappjpc.viewModels.HomeViewModel

@Composable
fun Home(navHostController: NavHostController){

    val homeViewModel : HomeViewModel = viewModel()
    val threadsAndUsers by homeViewModel.threadsAndUsers.observeAsState()



        LazyColumn{
            items(threadsAndUsers ?: emptyList()){pairs->
                ThreadItem(
                    pairs.first,
                    pairs.second,
                    navHostController,
                    FirebaseAuth.getInstance().currentUser!!.uid
                )
            }
        }
}
@Preview(showBackground = true)
@Composable
fun ShowHome() {
   //Home()
}