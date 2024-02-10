package com.example.kinopoisk

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import com.example.kinopoisk.ui.screens.AppScreen
import com.example.kinopoisk.ui.theme.KinopoiskTheme
import com.example.kinopoisk.ui.theme.darkScrim
import com.example.kinopoisk.ui.theme.lightScrim
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            KinopoiskTheme {

                DisposableEffect(Unit) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT,
                            detectDarkMode = {
                                isDarkTheme
                            }
                        ),
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = lightScrim,
                            darkScrim = darkScrim,
                            detectDarkMode = {
                                isDarkTheme
                            }
                        ),
                    )
                    onDispose { }
                }
                AppScreen()
            }
        }
    }
}