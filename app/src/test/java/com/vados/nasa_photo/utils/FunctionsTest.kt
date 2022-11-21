package com.vados.nasa_photo.utils

import com.vados.nasa_photo.R
import org.junit.Assert
import org.junit.Test

class FunctionsTest {

    @Test
    fun getAppThemeInList() {
        Assert.assertEquals(R.style.Theme_Nasa_photo,returnThemeByID(THEME_LIGHT))
        Assert.assertEquals(R.style.Theme_Dark,returnThemeByID(THEME_DARK))
        Assert.assertEquals(R.style.Theme_Red,returnThemeByID(THEME_RED))
        Assert.assertEquals(R.style.Theme_Space,returnThemeByID(THEME_SPACE))
    }

    @Test
    fun getAppThemeAnother() {
        Assert.assertEquals(R.style.Theme_Space,returnThemeByID(-1))
        Assert.assertEquals(R.style.Theme_Space,returnThemeByID(4))
    }
}