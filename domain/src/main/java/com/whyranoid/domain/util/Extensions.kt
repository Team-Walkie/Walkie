package com.whyranoid.domain.util

import java.text.SimpleDateFormat

val String.Companion.EMPTY: String get() = ""

val String.Companion.BLANK: String get() = " "

val String.Companion.DATE_FORMAT: String get() = "yyyy-MM-dd HH:mm:ss"

val dateFormatter = SimpleDateFormat(String.DATE_FORMAT)
