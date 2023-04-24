package com.example.compose_sample.data

import androidx.annotation.DrawableRes

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:05 PM
 * @desc
 */
data class Account(
    val id: Long,
    val uid: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val altEmail: String,
    @DrawableRes val avatar: Int,
    var isCurrentAccount: Boolean = false
){
    val fulName: String = "$firstName $lastName"
}