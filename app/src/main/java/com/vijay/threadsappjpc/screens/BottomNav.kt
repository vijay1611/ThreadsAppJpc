package com.vijay.threadsappjpc.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vijay.threadsappjpc.model.BottomNavItem
import com.vijay.threadsappjpc.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(){
        val navController1 = rememberNavController()
    Scaffold (bottomBar = { BottomBar(navController1)}){innerPadding->
        NavHost(navController = navController1,modifier= Modifier.padding(innerPadding), startDestination =Routes.Home.routes){

            composable(Routes.Home.routes){
                Home()
            }
            composable(Routes.Search.routes){
                Search()
            }
            composable(Routes.AddThreads.routes){
                AddThreads()
            }
            composable(Routes.Notification.routes){
                Notification()
            }
            composable(Routes.Profile.routes){
                Profile(navController1)
            }

        }
    }
}
@Composable
fun BottomBar(navController1: NavHostController) {
    val backStackEntry = navController1.currentBackStackEntryAsState()
    val list = listOf(
        BottomNavItem("Home",Routes.Home.routes,Icons.Rounded.Home),
        BottomNavItem("Search",Routes.Search.routes,Icons.Rounded.Search),
        BottomNavItem("AddThreads",Routes.AddThreads.routes,Icons.Rounded.Add),
        BottomNavItem("Profile",Routes.Profile.routes,Icons.Rounded.Person),
        BottomNavItem("Notification",Routes.Notification.routes,Icons.Rounded.Notifications)
    )
    BottomAppBar {
        list.forEach{
            val selected = it.route == backStackEntry?.value?.destination?.route
            NavigationBarItem(selected = selected, onClick = { navController1.navigate(it.route){
                popUpTo(navController1.graph.findStartDestination().id){
                    saveState = true
                }
                launchSingleTop = true
            } }, icon = { Icon(imageVector = it.icon, contentDescription = it.title) })

        }
        }
    
}