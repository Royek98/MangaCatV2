package com.example.mangacat.ui.screens.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


// possibly to delete
@Composable
private fun ErrorScreen(
    retryAction: () -> Unit,
    topBar: @Composable () -> Unit,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { topBar() }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun ErrorMessage(
    retryAction: () -> Unit,
    errorMessages: List<String>?,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        errorMessages?.forEach { message ->
            Text(text = message, modifier = modifier)
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onError,
                contentColor = MaterialTheme.colorScheme.error
            ),
            onClick = { retryAction() }
        ) {
            Text(text = "Retry")
        }
    }
}