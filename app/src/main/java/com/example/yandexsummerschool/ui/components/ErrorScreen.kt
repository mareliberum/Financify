package com.example.yandexsummerschool.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
	message: String,
	onRetry: (() -> Unit)? = null,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(
				text = message,
				style = MaterialTheme.typography.bodyLarge,
				color = MaterialTheme.colorScheme.error
			)
			Spacer(modifier = Modifier.height(16.dp))
			if (onRetry != null) {
				Button(onClick = onRetry) {
					Text(text = "Попробовать снова")
				}
			}
		}
	}
}
