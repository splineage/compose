package com.example.compose.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/12 5:04 PM
 * @desc
 */

val MaterialTheme.dimens: Dimens
    get() = Dimens()

class Dimens(
    /** 0dp */
    val none: Dp = 0.dp,
    /** 4dp */
    val verySmall: Dp = 4.dp,
    /** 8dp */
    val small: Dp = 8.dp,
    /** 16dp */
    val medium: Dp = 16.dp,
    /** 24dp */
    val large: Dp = 24.dp,
    /** 36dp */
    val veryLarge: Dp = 36.dp
)