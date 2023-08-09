package com.whyranoid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whyranoid.domain.model.running.RunningHistory
import com.whyranoid.domain.repository.RunningHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SelectHistoryViewModel(
    private val runningHistoryRepository: RunningHistoryRepository,
) : ViewModel() {
    private val _historyList: MutableStateFlow<List<RunningHistory>> = MutableStateFlow(emptyList())
    val historyList get() = _historyList.asStateFlow()

    private val _selectedHistory: MutableStateFlow<RunningHistory?> = MutableStateFlow(null)
    val selectedHistory get() = _selectedHistory.asStateFlow()

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
}
