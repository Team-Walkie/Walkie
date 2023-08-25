package com.whyranoid.presentation.reusable

import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun datePicker(
    context: Context,
    year: Int?,
    month: Int?,
    day: Int?,
    onDateSelected: (Int, Int, Int) -> Unit,
) {
    DatePickerDialog(
        context,
        { _, year, month, day ->
            onDateSelected(year, month, day)
        },
        year ?: Calendar.getInstance().get(Calendar.YEAR),
        month ?: Calendar.getInstance().get(Calendar.MONTH),
        day ?: Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
    ).show()
}
