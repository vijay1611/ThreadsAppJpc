package com.vijay.threadsappjpc.navigation

sealed class Routes(val routes:String){
    object Home : Routes("home")
    object AddThreads : Routes("addThreads")
    object Splash : Routes("splash")
    object Search : Routes("search")
    object Notification : Routes("notification")
    object Profile : Routes("profile")
    object BottomNav : Routes("bottomNav")
    object Login : Routes("login")
    object Register : Routes("register")
}
