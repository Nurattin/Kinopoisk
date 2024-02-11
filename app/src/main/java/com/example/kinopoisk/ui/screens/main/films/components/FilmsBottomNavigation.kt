package com.example.kinopoisk.ui.screens.main.films.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.ui.screens.main.films.FilmsType

@Composable
fun FilmsBottomNavigation(
    selectedFilmsType: FilmsType,
    onClickType: (FilmsType) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            FilmsType.entries.forEach { type ->

                val updateTransition = updateTransition(
                    targetState = type == selectedFilmsType,
                    label = "animateBottomNavigation",
                )
                val containerColor by updateTransition.animateColor(
                    label = "animateContainerColor",
                ) {
                    when (it) {
                        true -> MaterialTheme.colorScheme.primary
                        false -> MaterialTheme.colorScheme.primary.copy(alpha = .5f)
                    }
                }
                val contentColor by updateTransition.animateColor(
                    label = "animateContentColor",
                ) {
                    when (it) {
                        true -> MaterialTheme.colorScheme.background
                        false -> MaterialTheme.colorScheme.primary
                    }
                }

                Button(
                    onClick = {
                        onClickType(type)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = containerColor,
                        contentColor = contentColor
                    ),
                    contentPadding = PaddingValues(
                        vertical = 16.dp,
                        horizontal = 32.dp,
                    )
                ) {
                    Text(
                        text = stringResource(id = type.text),
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}
