package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface TagsOnTaskDataSource {

    fun getAllTagsOnTask(taskTitle: String, listTitle: String, userId: Long): Flow<List<TagsOnTask>>

    suspend fun upsertTagOnTask(tagsOnTask: TagsOnTask)

    suspend fun deleteTagOnTask(tagsOnTask: TagsOnTask)
}