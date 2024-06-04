package com.vijay.threadsappjpc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vijay.threadsappjpc.screens.AddThreads
import com.vijay.threadsappjpc.screens.BottomNav
import com.vijay.threadsappjpc.screens.Home
import com.vijay.threadsappjpc.screens.Login
import com.vijay.threadsappjpc.screens.Notification
import com.vijay.threadsappjpc.screens.Profile
import com.vijay.threadsappjpc.screens.Register
import com.vijay.threadsappjpc.screens.Search
import com.vijay.threadsappjpc.screens.Splash

@Composable
fun NavGraph(navController : NavHostController){
    NavHost(navController = navController, startDestination =Routes.Splash.routes){
        composable(Routes.Splash.routes){
            Splash(navController)

        }
        composable(Routes.Home.routes){
            Home()
        }
        composable(Routes.Notification.routes){
            Notification()
        }
        composable(Routes.Profile.routes){
            Profile(navController)
        }
        composable(Routes.Login.routes){
           Login(navController)
        }
        composable(Routes.Register.routes){
           Register(navController)
        }
        composable(Routes.Search.routes){
            Search()
        }
        composable(Routes.AddThreads.routes){
             AddThreads()
        }
        composable(Routes.BottomNav.routes){
            BottomNav()
        }
    }
}