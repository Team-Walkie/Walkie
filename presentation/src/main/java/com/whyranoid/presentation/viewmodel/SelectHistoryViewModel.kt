package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.repository.RunningHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class SelectHistoryViewModel(
    private val runningHistoryRepository: RunningHistoryRepository,
) : ViewModel() {
    private val _historyList: MutableStateFlow<List<RunningHistory>> = MutableStateFlow(emptyList())
    val historyList get() = _historyList.asStateFlow()

    private val _selectedHistory: MutableStateFlow<RunningHistory?> = MutableStateFlow(null)
    val selectedHistory get() = _selectedHistory.asStateFlow()

    private val _allRunningHistory: MutableStateFlow<List<LocalDate>> =
        MutableStateFlow(emptyList())
    val allRunningHistory get() = _allRunningHistory.asStateFlow()

    val curDay = LocalDate.now()

    fun getHistoryList(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            runningHistoryRepository.getByDate(year, month, day).onSuccess { data ->
                _historyList.value = data
            }
        }
    }

    fun selectHistory(runningHistory: RunningHistory) {
        _selectedHistory.value = runningHistory
    }

    fun getAllRunningHistory() {
        viewModelScope.launch {
            runningHistoryRepository.getAll().onSuccess { historys ->
                _allRunningHistory.value = historys.map {
                    Instant.ofEpochMilli(it.finishedAt).atZone(ZoneId.systemDefault()).toLocalDate()
                }
            }
        }
    }
}
