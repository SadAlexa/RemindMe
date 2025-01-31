package com.gpluslf.remindme.updates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gpluslf.remindme.core.domain.NotificationDataSource
import com.gpluslf.remindme.updates.presentation.model.NotificationAction
import com.gpluslf.remindme.updates.presentation.model.NotificationUi
import com.gpluslf.remindme.updates.presentation.model.toNotification
import com.gpluslf.remindme.updates.presentation.model.toNotificationUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotificationsState(val notifications: List<NotificationUi> = emptyList())

class NotificationsViewModel(
    private val userId: Long,
    private val repository: NotificationDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(NotificationsState(emptyList()))
    val state = _state.onStart {
        loadData()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = NotificationsState(emptyList())
    )

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllNotifications(userId).collect { notification ->
                _state.update { state ->
                    state.copy(notifications = notification.map { it.toNotificationUi() })
                }
            }
        }
    }

    private fun upsertNotification(notification: NotificationUi) = viewModelScope.launch {
        repository.upsertNotification(notification.copy(isRead = true).toNotification())
    }

    private fun deleteNotification(notification: NotificationUi) = viewModelScope.launch {
        repository.deleteNotification(notification.toNotification())
    }

    fun onAction(action: NotificationAction) {
        when (action) {
            is NotificationAction.Click -> {
                upsertNotification(action.item)
            }

            is NotificationAction.Delete -> {
                deleteNotification(action.item)
            }
        }
    }
}