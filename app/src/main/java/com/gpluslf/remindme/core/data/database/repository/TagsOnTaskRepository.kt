package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.TagsOnTaskDAOs
import com.gpluslf.remindme.core.data.database.entities.TagsOnTaskEntity

class TagsOnTaskRepository(private val tagsOnTaskDAOs: TagsOnTaskDAOs) {

    suspend fun getAllTagsOnTask(taskTitle: String, listTitle: String, userId: Long) =
        tagsOnTaskDAOs.getAllTagsOnTask(taskTitle, listTitle, userId)

    suspend fun upsertTagOnTask(tagsOnTask: TagsOnTaskEntity) =
        tagsOnTaskDAOs.upsertTagOnTask(tagsOnTask)

    suspend fun deleteTagOnTask(tagsOnTask: TagsOnTaskEntity) =
        tagsOnTaskDAOs.deleteTagOnTask(tagsOnTask)

}