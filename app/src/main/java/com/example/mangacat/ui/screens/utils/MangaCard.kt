package com.example.mangacat.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun MangaCard(
    title: String,
    cover: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = title)
            Divider(
                modifier = Modifier.padding(5.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Row {
                Image(
                    imageVector = cover,
                    contentDescription = "",
                    modifier = Modifier.weight(0.2f),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(0.8f)
                ) {
                    content()
                }
            }
        }
    }
}