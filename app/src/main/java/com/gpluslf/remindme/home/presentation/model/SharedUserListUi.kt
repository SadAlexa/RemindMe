package com.gpluslf.remindme.home.presentation.model

import com.gpluslf.remindme.core.domain.SharedUserList


data class SharedUserListUi(
    val usersId: Long,
    val listsSharedUserId: Long,
    val listTitle: String
)

fun SharedUserList.toSharedUserListUi() = SharedUserListUi(
    usersId,
    listsSharedUserId,
    listTitle
)
