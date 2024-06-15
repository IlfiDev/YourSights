package com.ilfidev.yoursights.UiElements.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ilfidev.yoursights.MainScreen
import com.ilfidev.yoursights.UiElements.RegisterScreen


@Composable
fun LoginScreen(navController: NavController) {
    var loginField by remember {
        mutableStateOf("")
    }
    var passwordField by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
        Text(text = "You Sights", style = TextStyle(fontSize = 24.sp))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Войти")
            TextField(value = loginField, onValueChange = {loginField = it}, placeholder = {Text("Логин")}, )
            TextField(value = passwordField, onValueChange = {passwordField = it}, placeholder = {Text("Пароль")}, visualTransformation = PasswordVisualTransformation())
            Button(onClick = { navController.navigate(MainScreen)}) {
                Text("Войти")
            }
        }

        Text("Зарегистрироваться", modifier = Modifier.clickable { navController.navigate(
            RegisterScreen
        ) })
    }
}