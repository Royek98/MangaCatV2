package com.example.mangacat.ui.screens.login

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangacat.R
import com.example.mangacat.ui.AuthViewModel
import com.example.mangacat.ui.screens.login.components.LoginForm
import com.example.mangacat.ui.screens.login.components.LoginFormViewModel
import com.example.mangacat.ui.screens.login.components.ProfileContent
import com.example.mangacat.ui.screens.login.components.ProfileContentViewModel
import com.example.mangacat.ui.screens.utils.TemplateScaffold


//toDo Make LoginScreen depend on isAuthenticated - IF user is logged show profile screen ELSE show form to log in
// current LoginViewModel will be FormViewModel
// profile screen will depend on a new vm: ProfileViewModel
// current function LoginScreen will depend on AuthViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(
    viewModel: LoginFormViewModel = hiltViewModel<LoginFormViewModel>(),
    authViewModel: AuthViewModel,
    profileContentViewModel: ProfileContentViewModel = hiltViewModel<ProfileContentViewModel>(),
    navigateToHome: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onSecondary
    ) {
        val modifier = Modifier.padding(top = it.calculateTopPadding())
        if (authViewModel.isAuthenticated.collectAsState().value) {
            Template(image = R.drawable.logo_icon2, modifier = modifier) {
                ProfileContent(profileContentViewModel.user.collectAsState().value)
            }
        } else {
            Template(image = R.drawable.security_key2, modifier = modifier) {
                LoginForm(
                    username = viewModel.username.collectAsState().value,
                    password = viewModel.password.collectAsState().value,
                    visibility = viewModel.visibility.collectAsState().value,
                    credentialsError = viewModel.credentialsError.collectAsState().value,
                    isLoading = viewModel.isLoading.collectAsState().value,
                    focusManager = LocalFocusManager.current,
                    setPassword = viewModel::setPassword,
                    setUsername = viewModel::setUsername,
                    setVisibility = viewModel::setVisibility,
                    authenticate = viewModel::authenticate,
                    navigateToHome = navigateToHome
                )
            }
        }
    }
}

@Composable
private fun Template(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    content: @Composable() () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSecondary)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .weight(0.35f)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(5.dp))
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.65f)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
//    LoginContent(
//        username = "",
//        password = "",
//        visibility = false,
//        credentialsError = Pair(false, ""),
//        isLoading = false,
//        setUsername = {},
//        setPassword = {},
//        setVisibility = { },
//        authenticate = {},
//        navigateToHome = {}
//    )
}