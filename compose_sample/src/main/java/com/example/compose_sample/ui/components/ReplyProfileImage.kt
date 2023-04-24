package com.example.compose_sample.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 5:48 PM
 * @desc
 */

@Composable
fun ReplyProfileImage(
    drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier
){
    Image(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableResource),
        contentDescription = description
    )
}

@Preview
@Composable
private fun ReplyProfileImagePreview(){
    ReplyProfileImage(
        drawableResource = com.example.compose_sample.R.drawable.avatar_0,
        description = "Profile Image"
    )
}