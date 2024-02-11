package com.example.kinopoisk.ui.screens.main.films

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.kinopoisk.R
import com.example.kinopoisk.ui.components.KinopoiskErrorScreen
import com.example.kinopoisk.ui.components.KinopoiskLoadingScreen
import com.example.kinopoisk.utils.shimmerBrush


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmsDetailScreen(
    modifier: Modifier = Modifier,
    poster: String,
    name: String,
    descriptor: String,
    genre: String,
    country: String,
    loading: Boolean,
    error: String?,
    onClickBack: () -> Unit,
    onClickRetry: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val bodyMediumStyle = MaterialTheme.typography.bodyMedium
    val isDarkTheme = isSystemInDarkTheme()

    val scrollState = rememberScrollState()

    DisposableEffect(focusManager) {
        focusManager.clearFocus(true)
        onDispose { }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                TopAppBar(
                    windowInsets = WindowInsets.statusBars,
                    navigationIcon = {
                        IconButton(onClick = onClickBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {}
                )
            }
        }
    ) {
        if (!error.isNullOrBlank()) {
            KinopoiskErrorScreen(
                errorText = error,
                onClickRetry = onClickRetry,
            )
        } else if (loading) {
            KinopoiskLoadingScreen()
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    model = poster,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(brush = shimmerBrush())
                        )
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 32.dp,
                            vertical = 20.dp
                        )
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = descriptor,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isDarkTheme) Color.LightGray else Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                bodyMediumStyle.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color.DarkGray
                                ).toSpanStyle()
                            ) {
                                append("${context.getString(R.string.genres)}: ")
                            }
                            withStyle(
                                bodyMediumStyle.copy(
                                    color = if (isDarkTheme) Color.LightGray else Color.Gray
                                ).toSpanStyle()
                            ) {
                                append(genre)
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                bodyMediumStyle.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = Color.DarkGray
                                ).toSpanStyle()
                            ) {
                                append("${context.getString(R.string.countries)}: ")
                            }
                            withStyle(
                                bodyMediumStyle.copy(
                                    color = if (isDarkTheme) Color.LightGray else Color.Gray
                                ).toSpanStyle()
                            ) {
                                append(country)
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}