package com.example.mangacat.ui.screens.manga.detailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.mangacat.R
import com.example.mangacat.data.network.Resource
import com.example.mangacat.domain.model.Cover

@Composable
fun Covers(
    coverList: Resource<List<Cover>>,
    mangaId: String,
    modifier: Modifier = Modifier
) {
    when (coverList) {
        is Resource.Loading -> {}
        is Resource.Success ->
            Success(
                coverList = coverList.data,
                mangaId = mangaId,
                modifier = modifier
            )
        is Resource.Error -> {}
    }
}

@Composable
private fun Success(
    coverList: List<Cover>,
    mangaId: String,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(coverList) {
            CoverItem(cover = it, mangaId = mangaId)
        }
    }
}

@Composable
private fun CoverItem(
    cover: Cover,
    mangaId: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "Volume ${cover.volume}",
            style = MaterialTheme.typography.titleLarge
        )
        SubcomposeAsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data("https://uploads.mangadex.org/covers/${mangaId}/${cover.fileName}")
                .crossfade(true)
                .build(),
            contentDescription = "",
            loading = { CircularProgressIndicator() },
        )
    }
}