package ru.yangel.hackathon.login.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.yangel.hackathon.login.presentation.viewModel.LoginViewModel
import ru.yangel.hackathon.login.presentation.viewModel.UserState
import ru.yangel.hackathon.ui.common.AppOutlinedTextField
import ru.yangel.hackathon.ui.common.PrimaryButton
import ru.yangel.hackathon.ui.theme.Error
import ru.yangel.hackathon.ui.theme.Primary
import ru.yangel.hackathon.ui.theme.Type15
import ru.yangel.hackathon.ui.theme.Type24

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = koinViewModel(), navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var userUiState = loginViewModel.userUiState.collectAsStateWithLifecycle()
        val userState by loginViewModel.userState.collectAsStateWithLifecycle()
        Text(
            text = "Тинькофф\nПоздравления", style = Type24, modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(64.dp))
        AppOutlinedTextField(
            value = userUiState.value.username,
            onValueChange = { loginViewModel.changeUserName(it) },
            label = "Логин",
            placeholder = "Введите логин",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )
        Spacer(modifier = Modifier.size(16.dp))
        AppOutlinedTextField(
            value = userUiState.value.password,
            label = "Пароль",
            visualTransformation = PasswordVisualTransformation(),
            placeholder = "Введите пароль",
            onValueChange = { loginViewModel.changePassword(it) },
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )
        Spacer(modifier = Modifier.size(48.dp))
        PrimaryButton(
            text = "Войти",
            modifier = Modifier.padding(start = 32.dp, end = 32.dp),
            onClick = { loginViewModel.login() }

        )
        when (userState) {
            is UserState.Initial -> {}
            is UserState.Success -> {
                navController.navigate("bottomNavigation")
            }

            is UserState.Loading -> {
                CircularProgressIndicator(
                    color = Primary,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(top = 16.dp),
                    strokeWidth = 3.dp
                )
            }

            is UserState.Error -> {
                AnimatedVisibility(visible = true) {
                    Text(
                        text = "Произошла ошибка, попробуйте еще раз",
                        style = Type15,
                        color = Error
                    )
                }
            }
        }
    }
}