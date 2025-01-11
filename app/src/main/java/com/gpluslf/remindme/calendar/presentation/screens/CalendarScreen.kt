package com.gpluslf.remindme.calendar.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gpluslf.remindme.calendar.presentation.CalendarTaskState
import com.gpluslf.remindme.calendar.presentation.model.CalendarAction
import com.gpluslf.remindme.core.presentation.components.TaskItem
import com.gpluslf.remindme.core.presentation.components.sampleTask
import com.gpluslf.remindme.ui.theme.RemindMeTheme
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarScreen(
    taskState: CalendarTaskState,
    onAction: (CalendarAction) -> Unit,
    checkDay: (LocalDate) -> Boolean,
    modifier: Modifier = Modifier
) {
    val currentMonth = taskState.currentMonth
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            CalendarTopBar(
                taskState.currentMonth,
                isOpen = taskState.isCalendarOpen,
                onAction
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding),
        ) {
            if (taskState.isCalendarOpen) {
                MonthCalendar(
                    currentMonth = currentMonth,
                    currentDay = taskState.selectedDay,
                    daysOfWeek = daysOfWeek,
                    state = state,
                    onAction = onAction,
                    checkDay = checkDay
                )
            } else {
                WeeklyCalendar(
                    startDate = currentMonth.atDay(1),
                    endDate = currentMonth.atEndOfMonth(),
                    currentMonth = currentMonth,
                    currentDate = taskState.selectedDay,
                    firstDayOfWeek = firstDayOfWeek,
                    onAction = onAction,
                    checkDay = checkDay,
                )
            }
            MonthChips(currentMonth, onClick = {
                onAction(CalendarAction.SelectMonth(it))
            })
            Spacer(modifier = Modifier.size(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(taskState.tasks) { task ->
                    TaskItem(
                        task = task,
                        onTagClick = {},
                        onTaskClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun MonthCalendar(
    currentMonth: YearMonth,
    currentDay: LocalDate,
    daysOfWeek: List<DayOfWeek>,
    state: CalendarState,
    onAction: (CalendarAction) -> Unit,
    checkDay: (LocalDate) -> Boolean
) {
    HorizontalCalendar(
        state = state,
        dayContent = { day ->
            MonthDay(
                day,
                checkDay(day.date),
                currentMonth,
                currentDay,
                onClick = { onAction(CalendarAction.SelectDay(day.date)) }
            )
        },
        monthHeader = {
            LaunchedEffect(it) {
                onAction(CalendarAction.SetCurrentMonth(it.yearMonth))
            }
            Column {
                DaysOfWeekTitle(daysOfWeek)
            }
        }
    )
}

@Composable
fun WeeklyCalendar(
    startDate: LocalDate,
    endDate: LocalDate,
    currentMonth: YearMonth,
    currentDate: LocalDate,
    firstDayOfWeek: DayOfWeek,
    onAction: (CalendarAction) -> Unit,
    checkDay: (LocalDate) -> Boolean,
) {
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    WeekCalendar(
        state = state,
        dayContent = { day ->
            WeekDay(
                day,
                checkDay(day.date),
                currentDate,
                currentMonth,
                onClick = { onAction(CalendarAction.SelectDay(day.date)) }
            )
        },
        weekHeader = {
            Column {
                DaysOfWeekTitle(daysOfWeek())
            }
        }
    )
}

@Composable
fun MonthChips(currentMonth: YearMonth, onClick: (YearMonth) -> Unit) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (1..12).forEach {
            FilterChip(
                selected = currentMonth.month.value == it,
                onClick = { onClick(YearMonth.of(currentMonth.year, it)) },
                label = {
                    Text(
                        YearMonth.of(currentMonth.year, it).month.getDisplayName(
                            TextStyle.SHORT,
                            Locale.getDefault()
                        )
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopBar(month: YearMonth, isOpen: Boolean, onAction: (CalendarAction) -> Unit) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    onAction(CalendarAction.ToggleCalendar)
                }
            ) {
                Text(
                    text = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()),
                    fontWeight = FontWeight.Bold
                )
                if (isOpen) {
                    Icon(Icons.Outlined.ArrowDropUp, contentDescription = "Collapse")
                } else {
                    Icon(Icons.Outlined.ArrowDropDown, contentDescription = "Expand")
                }
            }

        },
        actions = {
            Text(
                text = month.year.toString(),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(end = 10.dp)
            )
        }
    )

}


@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MonthDay(
    day: CalendarDay,
    isEventPresent: Boolean,
    currentMonth: YearMonth,
    currentDay: LocalDate,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    when (day.date) {
                        LocalDate.now() -> MaterialTheme.colorScheme.primaryContainer
                        currentDay -> MaterialTheme.colorScheme.surfaceDim
                        else -> Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                fontWeight = FontWeight.Medium,
                color =
                if (day.date.yearMonth == currentMonth)
                    MaterialTheme.colorScheme.onBackground
                else
                    MaterialTheme.colorScheme.outline

            )
        }

        Box(
            Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    if (isEventPresent)
                        MaterialTheme.colorScheme.primary
                    else Color.Transparent
                )
        )

    }
}


@Composable
fun WeekDay(
    day: WeekDay,
    isEventPresent: Boolean,
    currentDay: LocalDate,
    currentMonth: YearMonth,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(
                    when (day.date) {
                        LocalDate.now() -> MaterialTheme.colorScheme.primaryContainer
                        currentDay -> MaterialTheme.colorScheme.surfaceDim
                        else -> Color.Transparent
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.date.dayOfMonth.toString(),
                fontWeight = FontWeight.Medium,
                color = if (day.date.yearMonth == currentMonth) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.outline
            )
        }

        Box(
            Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(
                    if (isEventPresent)
                        MaterialTheme.colorScheme.primary
                    else Color.Transparent
                )
        )
    }
}

@Preview
@Composable
private fun CalendarScreenPreview() {
    RemindMeTheme {
        Surface {
            CalendarScreen(
                taskState = CalendarTaskState(
                    tasks = (1..10).map { sampleTask },
                    isCalendarOpen = false
                ),
                {},
                { false }
            )
        }
    }
}

