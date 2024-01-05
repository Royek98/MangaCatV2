package com.example.mangacat.ui.screens.read

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Read
import com.example.mangacat.ui.screens.home.ErrorScreen
import com.example.mangacat.ui.screens.home.LoadingScreen
import com.example.mangacat.ui.screens.utils.TopAppBarBackAndAction
import kotlin.reflect.KFunction0

@Composable
fun ReadScreen(
    viewModel: ReadViewModel,
    navigateBack: () -> Unit,
) {
    ReadContent(
        readUiState = viewModel.readUiState,
        showBar = viewModel.showBar,
        showBarChangeState = viewModel::showBarChangeState,
        retryRead = viewModel::getReadPages,
        navigateBack = navigateBack
    )
}

@Composable
private fun ReadContent(
    readUiState: Resource<Read>,
    showBar: Boolean,
    showBarChangeState: () -> Unit,
    navigateBack: () -> Unit,
    retryRead: () -> Unit
) {
    when (readUiState) {
        is Resource.Loading -> LoadingScreen()
        is Resource.Success -> ReadSuccess(readUiState.data, showBarChangeState)
        is Resource.Error -> ErrorScreen(retryRead)
    }

    if (showBar) {
        TopAppBarBackAndAction(
            actionImageVector = Icons.Default.Settings,
            actionContentDescription = "Settings",
            action = { /*todo*/ },
            navigateBack = navigateBack
        )
    }
}

@Composable
fun ReadSuccess(
    read: Read,
    showBarChangeState: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier.clickable { showBarChangeState() }
    ) {
        items(read.data) {
            SubcomposeAsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data("https://uploads.mangadex.org/data/${read.hash}/$it")
                    .crossfade(true)
                    .build(),
                loading = { CircularProgressIndicator() },
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
