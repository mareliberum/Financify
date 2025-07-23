package com.example.yandexsummerschool.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = GreenPrimary,
        onPrimary = Color.White,
        secondary = GreenDark,
        surface = darkSurface,
        surfaceContainer = darkSurfaceContainer,
        onSurface = Color.White,
        tertiary = darkTertiary,
        surfaceVariant = darkSurfaceVariant,
        outlineVariant = darkOutlineVariant,
        background = darkBackground,
        onBackground = Color.White,
    )
private val LightColorScheme =
    lightColorScheme(
        primary = GreenPrimary,
        onPrimary = onGreenPrimary,
        secondary = GreenLight,
        surface = surface,
        surfaceContainer = surfaceContainer,
        onSurface = onSurfaceContainer,
        tertiary = territary,
        surfaceVariant = surfaceVariant,
        outlineVariant = outlineVariant,
	/* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
	 */
    )

@Composable
fun YandexSummerSchoolTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}
