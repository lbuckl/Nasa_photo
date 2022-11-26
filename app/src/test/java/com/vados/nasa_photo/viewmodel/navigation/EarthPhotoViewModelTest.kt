package com.vados.nasa_photo.viewmodel.navigation

import android.app.Application
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTOItem
import org.junit.Assert
import org.junit.Test
import java.util.Objects

class EarthPhotoViewModelTest {

    @Test
    fun replaceLinksInArrayTest(){
        //input params
        val inputParams = arrayListOf(
            EarthPhotoDTOItem("Something", "2022/12/06","TestLinkOne"),
            EarthPhotoDTOItem("Something", "2022/11/22","TestLinkTwo"),
            EarthPhotoDTOItem("Something", "2018/05/01","TestLinkThree")
        ) as EarthPhotoDTO

        //input params
        val expected= arrayListOf(
            EarthPhotoDTOItem("Something",
                "2022-12-06",
                "https://epic.gsfc.nasa.gov/archive/enhanced/2022-12-06/png/TestLinkOne.png"),
            EarthPhotoDTOItem(
                "Something",
                "2022-11-22",
                "https://epic.gsfc.nasa.gov/archive/enhanced/2022-11-22/png/TestLinkTwo.png"),
            EarthPhotoDTOItem(
                "Something",
                "2018-05-01",
                "https://epic.gsfc.nasa.gov/archive/enhanced/2018-05-01/png/TestLinkThree.png")
        ) as EarthPhotoDTO

        Assert.assertEquals(expected,EarthPhotoViewModel().replaceLinksInArray(inputParams))
    }
}