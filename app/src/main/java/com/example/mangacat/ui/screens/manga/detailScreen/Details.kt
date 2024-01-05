package com.example.mangacat.ui.screens.manga.detailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.mangacat.R
import com.example.mangacat.domain.model.Manga
import com.example.mangacat.ui.screens.manga.detailScreen.utils.OutlinedCard
import com.example.mangacat.ui.screens.manga.detailScreen.utils.TitleFlowRow

@Composable
fun Details(
    manga: Manga,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = manga.description.en!!)

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.padding_small)))

        PairColumn(
            leftColumn = {
                TitleFlowRow(title = "Publication year") {
                    OutlinedCard(text = manga.year.toString())
                }
            },
            rightColumn = {
                TitleFlowRow(title = "Status") {
                    OutlinedCard(text = manga.status.name)
                }
            }
        )

        PairColumn(
            leftColumn = {
                TitleFlowRow(title = "Author") {
                    OutlinedCard(text = manga.author)
                }
            },
            rightColumn = {
                TitleFlowRow(title = "Artist") {
                    OutlinedCard(text = manga.artist)
                }
            }
        )

        PairColumn(
            leftColumn = {
                if (manga.publicationDemographic?.name != null) {
                    TitleFlowRow(title = "Demographic") {
                        OutlinedCard(text = manga.publicationDemographic.name)
                    }
                }
            },
            rightColumn = {
                if (manga.format.isNotEmpty()) {
                    TitleFlowRow(title = "Format") {
                        repeat(manga.format.size) {
                            OutlinedCard(text = manga.format[it] ?: "")
                        }
                    }
                }
            }
        )

        PairColumn(
            leftColumn = {
                if (manga.themes.isNotEmpty()) {
                    TitleFlowRow(title = "Themes") {
                        repeat(manga.themes.size) {
                            OutlinedCard(text = manga.themes[it] ?: "")
                        }
                    }
                }
            },
            rightColumn = {
                TitleFlowRow(title = "Content Rating") {
                    OutlinedCard(text = manga.contentRating.name)
                }
            }
        )

        if (manga.genres.isNotEmpty()) {
            TitleFlowRow(title = "Genres") {
                repeat(manga.genres.size) {
                    OutlinedCard(text = manga.genres[it] ?: "")
                }
            }
        }

    }
}

@Composable
private fun PairColumn(
    leftColumn: @Composable () -> Unit,
    rightColumn: @Composable () -> Unit
) {
    Row {
        Column(modifier = Modifier.weight(0.5f)) {
            leftColumn()
        }
        Column(modifier = Modifier.weight(0.5f)) {
            rightColumn()
        }
    }
}