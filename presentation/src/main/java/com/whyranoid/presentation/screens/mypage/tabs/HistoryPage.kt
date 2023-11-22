package com.whyranoid.presentation.screens.mypage.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTypography
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun HistoryPage(modifier: Modifier = Modifier, onDayClicked: (LocalDate) -> Unit) {
    val calendarState = rememberSelectableCalendarState(
        initialMonth = YearMonth.now(),
        initialSelection = listOf(LocalDate.now()),
        initialSelectionMode = SelectionMode.Single,
    )
    onDayClicked(calendarState.selectionState.selection[0])

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        SelectableCalendar(
            modifier = Modifier.padding(18.dp),
            calendarState = calendarState,
            horizontalSwipeEnabled = false,
            monthHeader = { monthState ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "이전 달",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                calendarState.monthState.currentMonth =
                                    calendarState.monthState.currentMonth.minusMonths(1)
                            },
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${monthState.currentMonth.year}년 ${monthState.currentMonth.month.value}월",
                        style = WalkieTypography.Title,
                    )
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "다음 달",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                calendarState.monthState.currentMonth =
                                    calendarState.monthState.currentMonth.plusMonths(1)
                            },
                    )
                }
            },
            daysOfWeekHeader = { dayOfWeeks ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(
                            RoundedCornerShape(
                                topEnd = 10.dp,
                                topStart = 10.dp,
                            ),
                        )
                        .padding(bottom = 16.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    dayOfWeeks.forEach { dayOfWeek ->
                        Text(
                            text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREA),
                            textAlign = TextAlign.Center,
                            style = WalkieTypography.Body1_ExtraBold,
                        )
                    }
                }
            },
            dayContent = { dateState ->
                val isSelected = calendarState.selectionState.selection[0] == dateState.date
                val color =
                    if (isSelected) {
                        Color.White
                    } else if (dateState.isFromCurrentMonth) {
                        Color.Black
                    } else {
                        Color(
                            0x55000000,
                        ) // TODO: Color 분리
                    }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                        .aspectRatio(ratio = 1f, matchHeightConstraintsFirst = true)
                        .clip(CircleShape)
                        .background(if (isSelected) WalkieColor.Primary else Color.Transparent)
                        .clickable {
                            onDayClicked(dateState.date)
                            calendarState.selectionState.selection = listOf(dateState.date)
                        },
                ) {
                    Text(
                        text = dateState.date.dayOfMonth.toString(),
                        textAlign = TextAlign.Center,
                        style = WalkieTypography.Body1,
                        color = color,
                    )
                }
            },
            monthContainer = { container ->
                Box(
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 10.dp,
                                bottomStart = 10.dp,
                            ),
                        ),
                ) {
                    container.invoke(PaddingValues(0.dp))
                }
            },
        )
    }
}
