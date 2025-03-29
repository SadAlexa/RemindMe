package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.domain.SharedUserList


data class SharedUserListUi(
    val usersId: Long,
    val listsSharedUserId: Long,
    val listId: Long
)

fun SharedUserList.toSharedUserListUi() = SharedUserListUi(
    usersId,
    listsSharedUserId,
    listId
)
