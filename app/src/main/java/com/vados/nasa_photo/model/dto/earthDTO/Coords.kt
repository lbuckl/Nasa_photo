package com.vados.nasa_photo.model.dto.earthDTO

data class Coords(
    val attitude_quaternions: AttitudeQuaternions,
    val centroid_coordinates: CentroidCoordinatesX,
    val dscovr_j2000_position: DscovrJ2000Position,
    val lunar_j2000_position: LunarJ2000Position,
    val sun_j2000_position: SunJ2000Position
)