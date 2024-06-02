package com.vijay.threadsappjpc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.vijay.threadsappjpc.R
import com.vijay.threadsappjpc.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun Splash(navHostController: NavHostController){

ConstraintLayout(modifier = Modifier.fillMaxSize()) {

    val (image) = createRefs()
 Image(painter = painterResource(id = R.drawable.threadimage), contentDescription = "Splash Image",
     modifier = Modifier.constrainAs(image){
         top.linkTo(parent.top)
         bottom.linkTo(parent.bottom)
         start.linkTo(parent.start)
         end.linkTo(parent.end)
     }.size(120.dp))
}


    LaunchedEffect( true){
        delay(3000)
        if(FirebaseAuth.getInstance().currentUser!=null){
            navHostController.navigate(Routes.BottomNav.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop=true
            }
        }
        else{
            navHostController.navigate(Routes.Login.routes){
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop=true
            }
        }

    }
}