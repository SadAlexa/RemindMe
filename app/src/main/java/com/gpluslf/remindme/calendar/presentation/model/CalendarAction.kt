package com.gpluslf.remindme.calendar.presentation.model

import java.time.LocalDate
import java.time.YearMonth

sealed interface CalendarAction {
    data class SetCurrentMonth(val month: YearMonth) : CalendarAction
    data object ToggleCalendar : CalendarAction
    data class SelectDay(val day: LocalDate) : CalendarAction
}
