package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow


interface TagDataSource {

    fun getTagById(tagId: Long): Flow<Tag?>

    fun getAllTags(listId: Long, userId: Long): Flow<List<Tag>>

    suspend fun upsertTag(tag: Tag)

    suspend fun deleteTag(tag: Tag)

}