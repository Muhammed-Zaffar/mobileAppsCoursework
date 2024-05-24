package com.example.myapplication.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class DatePickerState(
    var selectedDate: MutableState<Long> = mutableStateOf(System.currentTimeMillis()),
    var showing: MutableState<Boolean> = mutableStateOf(false),
    var onConfirm: (Long) -> Unit = {},
    var onCancel: () -> Unit = {}
)
