package com.example.mangacat.ui.screens.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mangacat.R

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel<LoginViewModel>()) {
    LoginContent(
        viewModel.username.collectAsState().value,
        viewModel.password.collectAsState().value,
        viewModel.visibility.collectAsState().value,
        viewModel.credentialsError.collectAsState().value,
        viewModel.isLoading.collectAsState().value,
        viewModel::setUsername,
        viewModel::setPassword,
        viewModel::setVisibility,
        viewModel::authenticate,
        viewModel::getToken
    )
}

@Composable
private fun LoginContent(
    username: String,
    password: String,
    visibility: Boolean,
    credentialsError: Pair<Boolean, String>,
    isLoading: Boolean,
    setUsername: (String) -> Unit,
    setPassword: (String) -> Unit,
    setVisibility: () -> Unit,
    authenticate: () -> Unit,
    getToken: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onSecondary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.security_key),
            contentDescription = "",
            modifier = Modifier
                .weight(0.35f)
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.65f)
        ) {
            Form(
                username = username,
                password = password,
                visibility = visibility,
                credentialsError = credentialsError,
                isLoading = isLoading,
                focusManager = focusManager,
                setUsername = setUsername,
                setPassword = setPassword,
                setVisibility = setVisibility,
                authenticate = authenticate,
                getToken = getToken
            )
        }
    }
}

@Composable
private fun Form(
    username: String,
    password: String,
    visibility: Boolean,
    credentialsError: Pair<Boolean, String>,
    isLoading: Boolean,
    focusManager: FocusManager,
    setPassword: (String) -> Unit,
    setUsername: (String) -> Unit,
    setVisibility: () -> Unit,
    authenticate: () -> Unit,
    getToken: () -> Unit,
) {
    Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 20.dp)
    ) {

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.login_message_part_1))
                }
                append(" ")
                append(stringResource(id = R.string.login_message_part_2))
            },
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(20.dp))

        if (credentialsError.first) {
            Text(
                text = credentialsError.second,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(5.dp))
        }

        Text(
            text = stringResource(id = R.string.label_username_email)
        )
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedTextField(
            value = username,
            enabled = !isLoading,
            onValueChange = { setUsername(it) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        Spacer(modifier = Modifier.size(15.dp))

        Text(text = stringResource(id = R.string.label_password))
        Spacer(modifier = Modifier.size(5.dp))
        OutlinedTextField(
            value = password,
            enabled = !isLoading,
            onValueChange = { setPassword(it) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation =
            if (visibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                if (visibility) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                setVisibility()
                            },
                        tint = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.VisibilityOff,
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                setVisibility()
                            },
                        tint = Color.Gray
                    )
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = stringResource(id = R.string.label_forgot_password),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.size(15.dp))

        Confirm(
            isLoading = isLoading,
            authenticate = authenticate
        )
        
        Button(onClick = {
            getToken()
        }) {
            Text(text = "Testing")
        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            buildAnnotatedString {
                append(stringResource(id = R.string.register_message_part_1))
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(id = R.string.register_message_part_2))
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun Confirm(
    isLoading: Boolean,
    authenticate: () -> Unit
) {
    if (isLoading) {
        Text(text = "Loading...")
    } else {
        Button(
            onClick = { authenticate() },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(text = "Login")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}