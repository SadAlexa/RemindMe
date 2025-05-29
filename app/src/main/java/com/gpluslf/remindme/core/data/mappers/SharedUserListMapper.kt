package com.gpluslf.remindme.core.data.mappers

import com.gpluslf.remindme.core.data.database.entities.SharedUserListEntity
import com.gpluslf.remindme.core.domain.SharedUserList


fun SharedUserListEntity.toSharedUserList() = SharedUserList(
    usersId,
    listsSharedUserId,
    listId.toString()
)

fun SharedUserList.toSharedUserListEntity() = SharedUserListEntity(
    usersId,
    listsSharedUserId,
    listId.toUUID()
)
