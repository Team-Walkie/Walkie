package com.whyranoid.presentation.screens.mypage.addpost

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.presentation.theme.WalkieColor
import com.whyranoid.presentation.theme.WalkieTheme
import com.whyranoid.presentation.theme.WalkieTypography
import com.whyranoid.presentation.viewmodel.SelectHistoryViewModel
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun SelectHistoryScreen(
    onHistorySelected: (RunningHistory) -> Unit,
) {
    val viewModel = koinViewModel<SelectHistoryViewModel>()
    val historyListState = viewModel.historyList.collectAsStateWithLifecycle()
    val selectedState = viewModel.selectedHistory.collectAsStateWithLifecycle()

    val calendarState = rememberSelectableCalendarState(
        initialMonth = YearMonth.now(),
        initialSelection = listOf(LocalDate.now()),
        initialSelectionMode = SelectionMode.Single,
    )

    LaunchedEffect(calendarState.selectionState.selection.first()) {
        val date = calendarState.selectionState.selection.first()
        viewModel.getHistoryList(date.year, date.month.value, date.dayOfMonth)
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                style = WalkieTypography.Title,
                text = "러닝 기록",
                modifier = Modifier.align(Alignment.Center).padding(bottom = 12.dp),
            )
        }
        SelectableCalendar(
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
                        WalkieColor.GrayDefault
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

        Spacer(
            modifier = Modifier
                .padding(top = 48.dp)
                .height(2.dp)
                .fillMaxWidth()
                .background(WalkieColor.GrayDefault),
        )

        Text(
            text = "${calendarState.selectionState.selection.first().dayOfMonth}일",
            style = WalkieTypography.SubTitle,
            modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
        )

        val gridState = rememberLazyGridState()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            state = gridState,
        ) {
            items(historyListState.value.size) { index ->
                val history = historyListState.value[index]
                HistoryWithTime(
                    isSelected = selectedState.value == history,
                    modifier = Modifier.fillMaxWidth().height(40.dp).clip(RoundedCornerShape(12.dp))
                        .clickable {
                            viewModel.selectHistory(history)
                        },
                    runningHistory = history,
                )
            }
        }
    }

    selectedState.value?.let { runningHistory ->
        Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Button(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp).align(Alignment.BottomCenter),
                onClick = { onHistorySelected(runningHistory) },
                colors = buttonColors(containerColor = WalkieColor.Primary),
            ) {
                Text(text = "선택", color = Color.White)
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun HistoryWithTime(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    runningHistory: RunningHistory,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = WalkieColor.GrayDefault,
                shape = RoundedCornerShape(12.dp),
            )
            .background(color = if (isSelected) WalkieColor.Primary else Color.White),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = SimpleDateFormat("HH:mm").format(runningHistory.finishedAt),
            style = WalkieTypography.SubTitle.copy(color = if (isSelected) Color.White else Color.Black),
        )
    }
}

@Composable
@Preview
fun PreviewSelectHistoryContent() {
    WalkieTheme {
        SelectHistoryScreen(onHistorySelected = {})
    }
}
