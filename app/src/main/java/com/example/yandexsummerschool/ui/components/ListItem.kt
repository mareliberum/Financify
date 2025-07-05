package com.example.yandexsummerschool.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ListItemData(
    // lead
    val lead: String? = null,
    // main content
    val title: String,
    val subtitle: String? = null,
    // trailing content
    val trailingText: String? = null,
    val trailingSubText: String? = null,
    val trailingIcon: (@Composable () -> Unit)? = null,
)

enum class ListItemSize(val size: Dp) {
    BIG(71.dp),
    SMALL(56.dp),
}

@Composable
fun ListItem(
    listItemData: ListItemData,
    modifier: Modifier = Modifier.height(56.dp),
    leadingIconBackground: Color = MaterialTheme.colorScheme.secondary,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
                .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Lead icon/label
        if (listItemData.lead != null) {
            Box(
                modifier =
                    Modifier
                        .size(24.dp)
                        .background(
                            color = leadingIconBackground,
                            shape = CircleShape,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = listItemData.lead,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
        // Main content + trailing in one Row
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Main content (title + subtitle)
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = listItemData.title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (listItemData.subtitle != null) {
                    Text(
                        text = listItemData.subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            // Trailing text block
            if (listItemData.trailingText != null) {
                Column(
                    modifier =
                        Modifier
                            .padding(start = 8.dp)
                            .wrapContentWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = listItemData.trailingText,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (listItemData.trailingSubText != null) {
                        Text(
                            text = listItemData.trailingSubText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            // Trailing icon
            if (listItemData.trailingIcon != null) {
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier.wrapContentWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    listItemData.trailingIcon.invoke()
                }
            }
        }
    }

    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
    )
}

@Composable
fun ListItem(
    title: String,
    modifier: Modifier = Modifier,
    textColor: Color = LocalContentColor.current,
    subtitle: String? = null,
    leadingIcon: Painter? = null,
    trailingText: String? = null,
    trailingSubText: String? = null,
    trailingIcon: Painter? = null,
    listItemSize: ListItemSize = ListItemSize.BIG,
    leadingIconBackground: Color = MaterialTheme.colorScheme.secondary,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(listItemSize.size)
                .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
                .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Lead icon/label
        if (leadingIcon != null) {
            Box(
                modifier =
                    Modifier
                        .size(24.dp)
                        .background(
                            color = leadingIconBackground,
                            shape = CircleShape,
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = textColor,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
        // Main content + trailing in one Row
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Main content (title + subtitle)
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = title,
                    color = textColor,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            // Trailing text block
            if (trailingText != null) {
                Column(
                    modifier =
                        Modifier
                            .padding(start = 8.dp)
                            .wrapContentWidth(),
                    horizontalAlignment = Alignment.End,
                ) {
                    Text(
                        text = trailingText,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (trailingSubText != null) {
                        Text(
                            text = trailingSubText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            // Trailing icon
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier.wrapContentWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = trailingIcon,
                        contentDescription = null,
                    )
                }
            }
        }
    }

    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
    )
}
