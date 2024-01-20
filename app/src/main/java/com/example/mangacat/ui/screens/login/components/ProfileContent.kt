package com.example.mangacat.ui.screens.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mangacat.domain.model.User
import com.example.mangacat.utils.toTitle

@Composable
fun ProfileContent(user: User) {
    Column(
        modifier = Modifier.padding(start = 50.dp, end = 50.dp, top = 20.dp)
    ) {

        Text(
            text = user.username ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "Id",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "${user.id}",
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "Roles",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )


        user.roles?.let { roles ->
            repeat(roles.size) {
                Text(
                    text = user.roles[it].removeRole().lowercase().toTitle(),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                containerColor = MaterialTheme.colorScheme.errorContainer
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Logout")
        }

    }
}

private fun String.removeRole(): String =
    this.replace("ROLE_", "")
