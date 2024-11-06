package com.example.compose
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.matin.happystore.core.designsystem.theme.AppTypography
import com.matin.happystore.core.designsystem.theme.backgroundDark
import com.matin.happystore.core.designsystem.theme.backgroundDarkHighContrast
import com.matin.happystore.core.designsystem.theme.backgroundDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.backgroundLight
import com.matin.happystore.core.designsystem.theme.backgroundLightHighContrast
import com.matin.happystore.core.designsystem.theme.backgroundLightMediumContrast
import com.matin.happystore.core.designsystem.theme.errorContainerDark
import com.matin.happystore.core.designsystem.theme.errorContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.errorContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.errorContainerLight
import com.matin.happystore.core.designsystem.theme.errorContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.errorContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.errorDark
import com.matin.happystore.core.designsystem.theme.errorDarkHighContrast
import com.matin.happystore.core.designsystem.theme.errorDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.errorLight
import com.matin.happystore.core.designsystem.theme.errorLightHighContrast
import com.matin.happystore.core.designsystem.theme.errorLightMediumContrast
import com.matin.happystore.core.designsystem.theme.inverseOnSurfaceDark
import com.matin.happystore.core.designsystem.theme.inverseOnSurfaceDarkHighContrast
import com.matin.happystore.core.designsystem.theme.inverseOnSurfaceDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.inverseOnSurfaceLight
import com.matin.happystore.core.designsystem.theme.inverseOnSurfaceLightHighContrast
import com.matin.happystore.core.designsystem.theme.inverseOnSurfaceLightMediumContrast
import com.matin.happystore.core.designsystem.theme.inversePrimaryDark
import com.matin.happystore.core.designsystem.theme.inversePrimaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.inversePrimaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.inversePrimaryLight
import com.matin.happystore.core.designsystem.theme.inversePrimaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.inversePrimaryLightMediumContrast
import com.matin.happystore.core.designsystem.theme.inverseSurfaceDark
import com.matin.happystore.core.designsystem.theme.inverseSurfaceDarkHighContrast
import com.matin.happystore.core.designsystem.theme.inverseSurfaceDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.inverseSurfaceLight
import com.matin.happystore.core.designsystem.theme.inverseSurfaceLightHighContrast
import com.matin.happystore.core.designsystem.theme.inverseSurfaceLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onBackgroundDark
import com.matin.happystore.core.designsystem.theme.onBackgroundDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onBackgroundDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onBackgroundLight
import com.matin.happystore.core.designsystem.theme.onBackgroundLightHighContrast
import com.matin.happystore.core.designsystem.theme.onBackgroundLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onErrorContainerDark
import com.matin.happystore.core.designsystem.theme.onErrorContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onErrorContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onErrorContainerLight
import com.matin.happystore.core.designsystem.theme.onErrorContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.onErrorContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onErrorDark
import com.matin.happystore.core.designsystem.theme.onErrorDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onErrorDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onErrorLight
import com.matin.happystore.core.designsystem.theme.onErrorLightHighContrast
import com.matin.happystore.core.designsystem.theme.onErrorLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryContainerDark
import com.matin.happystore.core.designsystem.theme.onPrimaryContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryContainerLight
import com.matin.happystore.core.designsystem.theme.onPrimaryContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryDark
import com.matin.happystore.core.designsystem.theme.onPrimaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryLight
import com.matin.happystore.core.designsystem.theme.onPrimaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.onPrimaryLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryContainerDark
import com.matin.happystore.core.designsystem.theme.onSecondaryContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryContainerLight
import com.matin.happystore.core.designsystem.theme.onSecondaryContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryDark
import com.matin.happystore.core.designsystem.theme.onSecondaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryLight
import com.matin.happystore.core.designsystem.theme.onSecondaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.onSecondaryLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceDark
import com.matin.happystore.core.designsystem.theme.onSurfaceDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceLight
import com.matin.happystore.core.designsystem.theme.onSurfaceLightHighContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceVariantDark
import com.matin.happystore.core.designsystem.theme.onSurfaceVariantDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceVariantDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceVariantLight
import com.matin.happystore.core.designsystem.theme.onSurfaceVariantLightHighContrast
import com.matin.happystore.core.designsystem.theme.onSurfaceVariantLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryContainerDark
import com.matin.happystore.core.designsystem.theme.onTertiaryContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryContainerLight
import com.matin.happystore.core.designsystem.theme.onTertiaryContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryDark
import com.matin.happystore.core.designsystem.theme.onTertiaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryLight
import com.matin.happystore.core.designsystem.theme.onTertiaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.onTertiaryLightMediumContrast
import com.matin.happystore.core.designsystem.theme.outlineDark
import com.matin.happystore.core.designsystem.theme.outlineDarkHighContrast
import com.matin.happystore.core.designsystem.theme.outlineDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.outlineLight
import com.matin.happystore.core.designsystem.theme.outlineLightHighContrast
import com.matin.happystore.core.designsystem.theme.outlineLightMediumContrast
import com.matin.happystore.core.designsystem.theme.outlineVariantDark
import com.matin.happystore.core.designsystem.theme.outlineVariantDarkHighContrast
import com.matin.happystore.core.designsystem.theme.outlineVariantDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.outlineVariantLight
import com.matin.happystore.core.designsystem.theme.outlineVariantLightHighContrast
import com.matin.happystore.core.designsystem.theme.outlineVariantLightMediumContrast
import com.matin.happystore.core.designsystem.theme.primaryContainerDark
import com.matin.happystore.core.designsystem.theme.primaryContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.primaryContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.primaryContainerLight
import com.matin.happystore.core.designsystem.theme.primaryContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.primaryContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.primaryDark
import com.matin.happystore.core.designsystem.theme.primaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.primaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.primaryLight
import com.matin.happystore.core.designsystem.theme.primaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.primaryLightMediumContrast
import com.matin.happystore.core.designsystem.theme.scrimDark
import com.matin.happystore.core.designsystem.theme.scrimDarkHighContrast
import com.matin.happystore.core.designsystem.theme.scrimDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.scrimLight
import com.matin.happystore.core.designsystem.theme.scrimLightHighContrast
import com.matin.happystore.core.designsystem.theme.scrimLightMediumContrast
import com.matin.happystore.core.designsystem.theme.secondaryContainerDark
import com.matin.happystore.core.designsystem.theme.secondaryContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.secondaryContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.secondaryContainerLight
import com.matin.happystore.core.designsystem.theme.secondaryContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.secondaryContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.secondaryDark
import com.matin.happystore.core.designsystem.theme.secondaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.secondaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.secondaryLight
import com.matin.happystore.core.designsystem.theme.secondaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.secondaryLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceBrightDark
import com.matin.happystore.core.designsystem.theme.surfaceBrightDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceBrightDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceBrightLight
import com.matin.happystore.core.designsystem.theme.surfaceBrightLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceBrightLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerDark
import com.matin.happystore.core.designsystem.theme.surfaceContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighDark
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighLight
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighestDark
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighestDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighestDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighestLight
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighestLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerHighestLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLight
import com.matin.happystore.core.designsystem.theme.surfaceContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowDark
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowLight
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowestDark
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowestDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowestDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowestLight
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowestLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceContainerLowestLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceDark
import com.matin.happystore.core.designsystem.theme.surfaceDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceDimDark
import com.matin.happystore.core.designsystem.theme.surfaceDimDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceDimDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceDimLight
import com.matin.happystore.core.designsystem.theme.surfaceDimLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceDimLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceLight
import com.matin.happystore.core.designsystem.theme.surfaceLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceLightMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceVariantDark
import com.matin.happystore.core.designsystem.theme.surfaceVariantDarkHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceVariantDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.surfaceVariantLight
import com.matin.happystore.core.designsystem.theme.surfaceVariantLightHighContrast
import com.matin.happystore.core.designsystem.theme.surfaceVariantLightMediumContrast
import com.matin.happystore.core.designsystem.theme.tertiaryContainerDark
import com.matin.happystore.core.designsystem.theme.tertiaryContainerDarkHighContrast
import com.matin.happystore.core.designsystem.theme.tertiaryContainerDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.tertiaryContainerLight
import com.matin.happystore.core.designsystem.theme.tertiaryContainerLightHighContrast
import com.matin.happystore.core.designsystem.theme.tertiaryContainerLightMediumContrast
import com.matin.happystore.core.designsystem.theme.tertiaryDark
import com.matin.happystore.core.designsystem.theme.tertiaryDarkHighContrast
import com.matin.happystore.core.designsystem.theme.tertiaryDarkMediumContrast
import com.matin.happystore.core.designsystem.theme.tertiaryLight
import com.matin.happystore.core.designsystem.theme.tertiaryLightHighContrast
import com.matin.happystore.core.designsystem.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun HappyStoreTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }
  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

