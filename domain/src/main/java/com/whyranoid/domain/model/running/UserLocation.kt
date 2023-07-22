package com.whyranoid.domain.model.running

sealed class UserLocation {
    object NotTracking : UserLocation()
    data class Tracking(val lat: Double, val lng: Double) : UserLocation()
}
