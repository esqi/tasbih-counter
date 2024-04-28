package com.aktiadi.tasbihcounter.tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.StateBuilders
import androidx.wear.protolayout.expression.AppDataKey
import androidx.wear.protolayout.expression.DynamicDataBuilders.DynamicDataValue
import androidx.wear.protolayout.material.Chip
import androidx.wear.protolayout.material.ChipColors
import androidx.wear.protolayout.material.ChipDefaults
import androidx.wear.protolayout.material.CircularProgressIndicator
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.EdgeContentLayout
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters

const val SUBHANALLAH = "Subhanallah"
const val ALHAMDULILLAH = "Alhamdulillah"
const val ALLAHUAKBAR = "Allahu Akbar"
const val MAX = 100
const val COUNTER_KEY = "counter"
fun countToText(count: Int): String = when (count % MAX) {
    in 0..32 -> SUBHANALLAH
    in 33..65 -> ALHAMDULILLAH
    else -> ALLAHUAKBAR
}

fun isGoingToChange(count: Int): Boolean = when (count % MAX) {
    32 -> true
    65 -> true
    99 -> true
    else -> false
}

fun tileLayout(
    context: Context, count: Int
): LayoutElementBuilders.LayoutElement {
    val dua = countToText(count)
    val caption = Text.Builder(context, "$count/$MAX").setColor(argb(Colors.DEFAULT.onSurface))
        .setTypography(Typography.TYPOGRAPHY_CAPTION2).build()
    val clickable = ModifiersBuilders.Clickable.Builder().setOnClick(
        ActionBuilders.LoadAction.Builder().setRequestState(
            StateBuilders.State.Builder().addKeyToValueMapping(
                AppDataKey(COUNTER_KEY), DynamicDataValue.fromInt((count + 1) % MAX)
            ).build()
        ).build()
    ).build()
    val chipContent = Chip.Builder(context, clickable, buildDeviceParameters(context.resources))
        .setPrimaryLabelContent(dua).apply {
            if (isGoingToChange(count)) {
                setChipColors(
                    ChipColors.primaryChipColors(
                        Colors(
                            0xFFFF0000.toInt(),
                            0xFF303133.toInt(),
                            0xFF303133.toInt(),
                            0xFFFFFFFF.toInt()
                        )
                    )
                )
            } else {
                setChipColors(ChipDefaults.PRIMARY_COLORS)
            }
        }.build()
//    val vibrator = getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager?
//    val vibrationPattern = longArrayOf(0, 500, 50, 300)
//    //-1 - don't repeat
//    //-1 - don't repeat
//    val indexInPatternToRepeat = -1
//    vibrator!!.vibrate(vibrationPattern, indexInPatternToRepeat)
    return EdgeContentLayout.Builder(buildDeviceParameters(context.resources)).setEdgeContent(
        CircularProgressIndicator.Builder().setProgress(count.toFloat() / MAX.toFloat()).build()
    ).setSecondaryLabelTextContent(caption).setContent(chipContent).build()
}

@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TilePreviewSubhannalah() {
    LayoutRootPreview(root = tileLayout(LocalContext.current, 25))
}

@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TilePreviewSubhannalahEnd() {
    LayoutRootPreview(root = tileLayout(LocalContext.current, 32))
}

@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TilePreviewAlhamdulillah() {
    LayoutRootPreview(root = tileLayout(LocalContext.current, 50))
}

@Preview(
    device = WearDevices.SMALL_ROUND,
    showSystemUi = true,
    backgroundColor = 0xff000000,
    showBackground = true
)
@Composable
fun TilePreviewAllahuAkbar() {
    LayoutRootPreview(root = tileLayout(LocalContext.current, 75))
}