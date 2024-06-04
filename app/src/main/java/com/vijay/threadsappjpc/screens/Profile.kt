package com.vijay.threadsappjpc.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vijay.threadsappjpc.navigation.Routes
import com.vijay.threadsappjpc.viewModels.AuthViewModel

@Composable
fun Profile(navHostController: NavHostController){

    val authViewModel : AuthViewModel = viewModel()
    val firebaseValue = authViewModel.firebaseUser.observeAsState()

    LaunchedEffect(firebaseValue){
        if(firebaseValue!=null){
            navHostController.navigate(Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
    }


        Text(text = "Profile",
        modifier = Modifier.clickable {
            authViewModel.logOut()
        }

            )
}