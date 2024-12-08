package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.TagDAOs
import com.gpluslf.remindme.core.data.database.entities.TagEntity


class TagRepository(private val tagDAOs: TagDAOs) {

    fun getAllTags(listTitle: String, userId: Long) = tagDAOs.getAllTags(listTitle, userId)

    fun getTagById(categoryId: Long) = tagDAOs.getTagById(categoryId)

    suspend fun upsertTag(tag: TagEntity) = tagDAOs.upsertTag(tag)

    suspend fun deleteTag(tag: TagEntity) = tagDAOs.deleteTag(tag)
}