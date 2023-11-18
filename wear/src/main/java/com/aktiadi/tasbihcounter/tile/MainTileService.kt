package com.aktiadi.tasbihcounter.tile

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.ColorBuilders.argb
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
import androidx.wear.protolayout.LayoutElementBuilders.HorizontalAlignmentProp
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.StateBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.TimelineBuilders.Timeline
import androidx.wear.protolayout.expression.AppDataKey
import androidx.wear.protolayout.expression.DynamicBuilders.DynamicType
import androidx.wear.protolayout.expression.DynamicDataBuilders.DynamicDataValue
import androidx.wear.protolayout.material.Button
import androidx.wear.protolayout.material.CircularProgressIndicator
import androidx.wear.protolayout.material.Colors
import androidx.wear.protolayout.material.Text
import androidx.wear.protolayout.material.Typography
import androidx.wear.protolayout.material.layouts.EdgeContentLayout
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.tools.LayoutRootPreview
import com.google.android.horologist.compose.tools.buildDeviceParameters
import com.google.android.horologist.tiles.SuspendingTileService

private const val RESOURCES_VERSION = "1"

/**
 * Skeleton for a tile with no images.
 */
@OptIn(ExperimentalHorologistApi::class)
class MainTileService : SuspendingTileService() {

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ): ResourceBuilders.Resources {
        return ResourceBuilders.Resources.Builder().setVersion(RESOURCES_VERSION).build()
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): Tile {
        val count =
            requestParams.currentState.keyToValueMapping[AppDataKey<DynamicType>(COUNTER_KEY)]?.intValue
                ?: 0
        val singleTileTimeline = Timeline.Builder().addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder().setLayout(
                LayoutElementBuilders.Layout.Builder().setRoot(tileLayout(this, count)).build()
            ).build()
        ).build()
        return Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline).build()
    }
}

const val SUBHANALLAH = "Subhanallah"
const val ALHAMDULILLAH = "Alhamdulillah"
const val ALLAHUAKBAR = "Allahu Akbar"
private const val COUNTER_KEY = "counter"
fun countToText(count: Int): String = when (count % 100) {
    in 0..32 -> SUBHANALLAH
    in 33..65 -> ALHAMDULILLAH
    else -> ALLAHUAKBAR
}

private fun tileLayout(
    context: Context, count: Int
): LayoutElementBuilders.LayoutElement {
    return EdgeContentLayout.Builder(buildDeviceParameters(context.resources)).setEdgeContent(
        CircularProgressIndicator.Builder().setProgress(count.toFloat() / 100f).build()
    ).setPrimaryLabelTextContent(
        Text.Builder(context, countToText(count)).setColor(argb(Colors.DEFAULT.onSurface))
            .setTypography(Typography.TYPOGRAPHY_TITLE2).build()
    ).setSecondaryLabelTextContent(
        Text.Builder(context, "$count/100").setColor(argb(Colors.DEFAULT.onSurface))
            .setTypography(Typography.TYPOGRAPHY_CAPTION2).build()
    ).setContent(
        Button.Builder(
            context, ModifiersBuilders.Clickable.Builder().setOnClick(
                ActionBuilders.LoadAction.Builder().setRequestState(
                    StateBuilders.State.Builder().addKeyToValueMapping(
                        AppDataKey(COUNTER_KEY), DynamicDataValue.fromInt((count + 1) % 100)
                    ).build()
                ).build()
            ).build()
        ).setTextContent("+", Typography.TYPOGRAPHY_DISPLAY1).build()
    ).build()
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