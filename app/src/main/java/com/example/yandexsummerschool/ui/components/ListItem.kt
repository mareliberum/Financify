package com.example.yandexsummerschool.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

data class ListItemData(
	//lead
	val lead: String? = null,
	//content
	val title: String,
	val subtitle: String? = null,
	val value: String? = null,
	//trail
	val trail: @Composable() (() -> Unit)? = null,
)

@Composable
fun ListItem(
	listItemData: ListItemData,
	modifier: Modifier = Modifier,
	leadingIconBackground: Color = MaterialTheme.colorScheme.secondary,
) {
	Row(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = 12.dp, horizontal = 16.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		// lead
		if (listItemData.lead != null) {
			Row(
				modifier = Modifier
					.wrapContentWidth()
					.padding(end = 16.dp),
				verticalAlignment = Alignment.CenterVertically,
			) {
				Box(
					modifier = Modifier
						.size(24.dp)
						.background(
							color = leadingIconBackground,
							shape = CircleShape
						),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = listItemData.lead,
						style = MaterialTheme.typography.bodyLarge,
					)
				}
			}
		}
		//Content
		Row(
			modifier = Modifier.weight(1f),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
		) {
			Column {
				Text(
					text = listItemData.title,
					style = MaterialTheme.typography.bodyLarge,
					overflow = TextOverflow.Ellipsis,
				)
				if (listItemData.subtitle != null) {
					Text(
						text = listItemData.subtitle,
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.surfaceVariant,
						overflow = TextOverflow.Ellipsis,
					)
				}
			}

			if (listItemData.value != null) {
				Text(
					text = listItemData.value,
					style = MaterialTheme.typography.bodyLarge,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					modifier = Modifier.padding(start = 8.dp)
				)
			}
		}
		// Trail
		if (listItemData.trail != null) {
			Row(
				modifier = Modifier
					.wrapContentWidth()
					.padding(start = 16.dp),
				verticalAlignment = Alignment.CenterVertically,
			) {
				listItemData.trail.invoke()
			}
		}
	}

	HorizontalDivider(
		thickness = 1.dp,
		color = MaterialTheme.colorScheme.outlineVariant
	)
}