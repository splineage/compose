package com.example.compose_sample.data

import androidx.annotation.DrawableRes

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:11 PM
 * @desc
 */
data class EmailAttachment(
    @DrawableRes
    val resId: Int,
    val contentDesc: String
)