package com.whyranoid.presentation.util

import kotlin.math.min

fun <T> List<T>.chunkedList(size: Int): List<List<T>> {
    val newList = mutableListOf<List<T>>()
    var index = 0
    while (index < this.size) {
        val chunk = this.subList(index, min(index + size, this.size))
        newList.add(chunk)
        index += size
    }
    return newList
}