package com.example.kinopoisk.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.kinopoisk.ui.screens.navigation.KinopoinskNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    appState: KinopoiskAppState = rememberKinopoiskAppState()
) {

    Scaffold(
        modifier = modifier
            .navigationBarsPadding(),
        containerColor = Color.White,
    ) {
        KinopoinskNavHost(
            modifier = Modifier,
            navController = appState.navController,
        )
    }
}