package com.vijay.threadsappjpc.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vijay.threadsappjpc.navigation.Routes
import com.vijay.threadsappjpc.viewModels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navHostController: NavHostController) {

    val authViewModel : AuthViewModel = viewModel()
    val firebaseValue by authViewModel.firebaseUser.observeAsState()
    val errorMsg = authViewModel.error.observeAsState()

    val context = LocalContext.current

    errorMsg?.let {
        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                )
                Box(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = {
                    Text(text = "Email")
                },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ), singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(value = password, onValueChange = { password = it }, label = {
                    Text(text = "Password")
                },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ), singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(modifier = Modifier.height(30.dp))
                ElevatedButton(
                    onClick = {
                        if (email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(context, "Some fields are missing", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            authViewModel.login(email, password, context)
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Login",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                    )
                }
                TextButton(
                    onClick = {
                        navHostController.navigate(Routes.Register.routes) {
                            popUpTo(navHostController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "New User? Create Account...",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }