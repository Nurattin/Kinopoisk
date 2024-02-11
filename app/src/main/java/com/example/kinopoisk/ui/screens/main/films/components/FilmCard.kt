package com.example.kinopoisk.ui.screens.main.films.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.kinopoisk.utils.shimmerBrush

@Preview
@Composable
private fun PreviewFilmCardFavorite() {
    MaterialTheme {
        FilmCard(
            modifier = Modifier,
            imageUrl = "",
            name = "Треугольник печали",
            genre = "Драма",
            year = "2022",
            isFavorite = true,
            onClick = {},
            onLongClick = {},
            onClickFavorite = {},
        )
    }
}

@Preview
@Composable
private fun PreviewFilmCard() {
    MaterialTheme {
        FilmCard(
            modifier = Modifier,
            imageUrl = "",
            name = "Треугольник печали",
            genre = "Драма",
            year = "2022",
            isFavorite = false,
            onClick = {},
            onLongClick = {},
            onClickFavorite = {},
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    genre: String,
    year: String,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    onClickFavorite: () -> Unit,
) {
    val feedback = LocalHapticFeedback.current
    val shapeCard = MaterialTheme.shapes.medium
    val shadowColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                spotShadowColor = shadowColor
                clip = true
                shape = shapeCard
                shadowElevation = 50f
            }
            .combinedClickable(
                onLongClick = {
                    feedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onLongClick()
                },
                onClick = onClick,
            ),
    ) {
        androidx.compose.material3.Surface(
            modifier = Modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .size(
                                width = 40.dp,
                                height = 63.dp,
                            ),
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(brush = shimmerBrush())
                            )
                        }
                    )
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyLarge,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = "$genre ($year)",
                            style = MaterialTheme.typography.bodyMedium,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            maxLines = 2,
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isFavorite,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    IconButton(
                        onClick = onClickFavorite,
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }
    }
}