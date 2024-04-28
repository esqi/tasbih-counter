package com.aktiadi.tasbihcounter.tile

import android.content.pm.PackageManager
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.expression.AppDataKey
import androidx.wear.protolayout.expression.DynamicBuilders
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService

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
    private val audioManager : AudioManager? by lazy { getSystemService(AUDIO_SERVICE) as? AudioManager }

    private fun audioOutputAvailable(type: Int): Boolean {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
            return false
        }
        return audioManager?.getDevices(AudioManager.GET_DEVICES_OUTPUTS)?.any { it.type == type } == true
    }

    fun isSpeakerAvailable() : Boolean = audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)
    fun isBluetoothAvailable() : Boolean = audioOutputAvailable(AudioDeviceInfo.TYPE_BLUETOOTH_A2DP)

    private fun playNotification(){
        val audioManager: Any = getSystemService(AUDIO_SERVICE)
    }

    private val vibrator : Vibrator? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            (getSystemService(VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
        } else {
            getSystemService(VIBRATOR_SERVICE) as? Vibrator
        }
    }

    private fun vibrate(vibrationEffect: VibrationEffect = VibrationEffect.createOneShot(500, 255)) {
        vibrator?.vibrate(vibrationEffect)
    }

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        val count =
            requestParams.currentState.keyToValueMapping[AppDataKey<DynamicBuilders.DynamicType>(
                COUNTER_KEY
            )]?.intValue ?: 0
        if (isGoingToChange(count)) vibrate()
        val singleTileTimeline = TimelineBuilders.Timeline.Builder().addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder().setLayout(
                LayoutElementBuilders.Layout.Builder().setRoot(tileLayout(this, count)).build()
            ).build()
        ).build()
        return TileBuilders.Tile.Builder().setResourcesVersion(RESOURCES_VERSION)
            .setTileTimeline(singleTileTimeline).build()
    }

    companion object {
        private const val RESOURCES_VERSION = "1"
    }
}