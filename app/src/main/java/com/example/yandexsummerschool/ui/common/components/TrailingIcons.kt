package com.example.yandexsummerschool.ui.common.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.yandexsummerschool.R

@Composable
fun TrailingIconArrowRight() {
    Icon(
        painter = painterResource(R.drawable.icon_arrow_right),
        tint = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
        contentDescription = null,
    )
}

@Composable
fun TrailingIconArrowRightFilled() {
    Icon(
        painter = painterResource(R.drawable.icon_arrow_right_filled),
        tint = MaterialTheme.colorScheme.surfaceVariant,
        contentDescription = null,
    )
}
