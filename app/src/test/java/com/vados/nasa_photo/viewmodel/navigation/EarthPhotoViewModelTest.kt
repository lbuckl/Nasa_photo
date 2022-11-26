package com.vados.nasa_photo.viewmodel.navigation

import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTO
import com.vados.nasa_photo.model.dto.earthDTO.EarthPhotoDTOItem
import org.junit.Assert
import org.junit.Test

class EarthPhotoViewModelTest {

    @Test
    fun replaceLinksInArrayTest(){
        //input params
        val inputParams = arrayListOf(
            EarthPhotoDTOItem("Something", "2022-12-06 12:12:12","TestLinkOne"),
            EarthPhotoDTOItem("Something", "2022-11-22 12:12:12","TestLinkTwo"),
            EarthPhotoDTOItem("Something", "2018-05-01 12:12:12","TestLinkThree")
        )

        //input params
        val expected = arrayListOf(
            EarthPhotoDTOItem("Something",
                "2022-12-06 12:12:12",
                "https://epic.gsfc.nasa.gov/archive/enhanced/2022/12/06/png/TestLinkOne.png"),
            EarthPhotoDTOItem(
                "Something",
                "2022-11-22 12:12:12",
                "https://epic.gsfc.nasa.gov/archive/enhanced/2022/11/22/png/TestLinkTwo.png"),
            EarthPhotoDTOItem(
                "Something",
                "2018-05-01 12:12:12",
                "https://epic.gsfc.nasa.gov/archive/enhanced/2018/05/01/png/TestLinkThree.png")
        )

        Assert.assertEquals(expected,EarthPhotoViewModel().replaceLinksInArray(inputParams))
    }
}