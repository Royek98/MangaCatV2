package com.example.mangacat.ui.screens.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarBackAndAction(
    actionImageVector: ImageVector,
    actionContentDescription: String,
    action: () -> Unit,
    navigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .height(40.dp)
                    .width(40.dp)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
            }
        },
        actions = {
            IconButton(
                onClick = action,
                modifier = Modifier
                    .clip(
                        shape = CircleShape
                    )
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
                    .height(40.dp)
                    .width(40.dp)
            ) {
                Icon(imageVector = actionImageVector, contentDescription = actionContentDescription)
            }
        }
    )
}