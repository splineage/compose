package com.example.compose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/12 6:08 PM
 * @desc
 */

val TextStyle.thin: TextStyle
    get() = this.copy(fontWeight = FontWeight.Thin)

val TextStyle.extraLight: TextStyle
    get() = this.copy(fontWeight = FontWeight.ExtraLight)

val TextStyle.light: TextStyle
    get() = this.copy(fontWeight = FontWeight.Light)

val TextStyle.normal: TextStyle
    get() = this.copy(fontWeight = FontWeight.Normal)

val TextStyle.medium: TextStyle
    get() = this.copy(fontWeight = FontWeight.Medium)

val TextStyle.semiBold: TextStyle
    get() = this.copy(fontWeight = FontWeight.SemiBold)

val TextStyle.bold: TextStyle
    get() = this.copy(fontWeight = FontWeight.Bold)

val TextStyle.extraBold: TextStyle
    get() = this.copy(fontWeight = FontWeight.ExtraBold)

val TextStyle.black: TextStyle
    get() = this.copy(fontWeight = FontWeight.Black)
