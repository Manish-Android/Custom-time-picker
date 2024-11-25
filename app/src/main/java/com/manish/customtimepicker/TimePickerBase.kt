package com.manish.customtimepicker

import android.content.Context
import android.text.format.DateFormat.is24HourFormat
import androidx.annotation.Keep
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

object TimePickerBase {

    fun showTimePicker(
        context: Context,
        title: String,
        fragmentManager: FragmentManager,
        time: Time? = null,
        showSystemTimeFormat: Boolean = false,
        callback: (Time) -> Unit
    ) {

        val isSystem24Hour = is24HourFormat(context)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val builder =
            MaterialTimePicker.Builder()
                .setTheme(R.style.BaseTheme_TimePicker)
                .setTimeFormat(if (showSystemTimeFormat) clockFormat else TimeFormat.CLOCK_12H)
                .setTitleText(title)

        time?.let {
            builder.setHour(it.hour)
            builder.setMinute(it.minute)
        }


        val picker = builder.build()

        picker.addOnPositiveButtonClickListener {
            callback.invoke(Time(picker.minute, picker.hour))
        }

        picker.addOnNegativeButtonClickListener {
            picker.dismissAllowingStateLoss()
        }

        picker.show(fragmentManager, "TimePickerBase")
    }

}


@Keep
data class Time(
    val minute: Int,
    val hour: Int,
)