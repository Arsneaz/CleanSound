package com.example.cleansound.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private val idlingResource = CountingIdlingResource("Global")

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        if (!idlingResource.isIdleNow) {
            idlingResource.decrement()
        }
    }

    val idlingResourceInstance: IdlingResource
        get() = idlingResource
}
