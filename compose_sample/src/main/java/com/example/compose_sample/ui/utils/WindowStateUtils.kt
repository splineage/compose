package com.example.compose_sample.ui.utils

import android.graphics.Rect
import androidx.window.layout.FoldingFeature
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * @author jskim
 * @email jskim@1thefull.com
 * @created 2023/04/13 4:44 PM
 * @desc
 */

sealed interface DevicePosture{
    object NormalPosture: DevicePosture

    data class BookPosture(
        val hingePosition: Rect
    ): DevicePosture

    data class Separating(
        val hingePosition: Rect,
        var orientation: FoldingFeature.Orientation
    ): DevicePosture
}

@OptIn(ExperimentalContracts::class)
fun isBookPosture(foldFeature: FoldingFeature?): Boolean{
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.HALF_OPENED &&
            foldFeature.orientation == FoldingFeature.Orientation.VERTICAL
}

@OptIn(ExperimentalContracts::class)
fun isSeparating(foldFeature: FoldingFeature?): Boolean{
    contract { returns(true) implies (foldFeature != null) }
    return foldFeature?.state == FoldingFeature.State.FLAT && foldFeature.isSeparating
}

enum class ReplyNavigationType{
    BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER
}

enum class ReplyNavigationContentPosition{
    TOP, CENTER
}

enum class ReplyContentType{
    SINGLE_PANE, DUAL_PANE
}