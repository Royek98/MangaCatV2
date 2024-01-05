package com.example.mangacat.ui.screens.manga.detailScreen.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.mangacat.R

@Composable
@OptIn(ExperimentalLayoutApi::class)
fun TitleFlowRow(
    title: String,
    flowRowScope: @Composable () -> Unit
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )

    val paddingSmall = dimensionResource(id = R.dimen.padding_small)
    FlowRow(
        modifier = Modifier.padding(paddingSmall),
        horizontalArrangement = Arrangement.spacedBy(paddingSmall),
        content = { flowRowScope() },
        verticalArrangement = Arrangement.spacedBy(paddingSmall)
    )
}

@Composable
fun OutlinedCard(
    text: String,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.OutlinedCard(
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        )
    }
}