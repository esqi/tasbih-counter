package com.aktiadi.tasbihcounter.tile

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TileUIKtTest {

    @Test
    fun countToText0() {
        assertEquals(SUBHANALLAH, countToText(0))
    }

    @Test
    fun countToText1() {
        assertEquals(SUBHANALLAH, countToText(1))
    }

    @Test
    fun countToText32() {
        assertEquals(SUBHANALLAH, countToText(32))
    }

    @Test
    fun countToText33() {
        assertEquals(ALHAMDULILLAH, countToText(33))
    }

    @Test
    fun countToText34() {
        assertEquals(ALHAMDULILLAH, countToText(34))
    }

    @Test
    fun countToText65() {
        assertEquals(ALHAMDULILLAH, countToText(65))
    }

    @Test
    fun countToText66() {
        assertEquals(ALLAHUAKBAR, countToText(66))
    }

    @Test
    fun countToText67() {
        assertEquals(ALLAHUAKBAR, countToText(67))
    }

    @Test
    fun countToText98() {
        assertEquals(ALLAHUAKBAR, countToText(98))
    }

    @Test
    fun countToText99() {
        assertEquals(ALLAHUAKBAR, countToText(99))
    }

    @Test
    fun countToText100() {
        assertEquals(SUBHANALLAH, countToText(100))
    }

}