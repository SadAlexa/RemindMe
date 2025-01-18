package com.gpluslf.remindme.core.data.repository

import com.gpluslf.remindme.core.data.database.daos.TagDAOs
import com.gpluslf.remindme.core.data.mappers.toTag
import com.gpluslf.remindme.core.data.mappers.toTagEntity
import com.gpluslf.remindme.core.domain.Tag
import com.gpluslf.remindme.core.domain.TagDataSource
import kotlinx.coroutines.flow.map


class TagRepository(private val tagDAOs: TagDAOs) : TagDataSource {

    override fun getAllTags(listTitle: String, userId: Long) =
        tagDAOs.getAllTags(listTitle, userId).map { flow -> flow.map { it.toTag() } }

    override fun getTagById(tagId: Long) = tagDAOs.getTagById(tagId).map { it?.toTag() }

    override suspend fun upsertTag(tag: Tag) = tagDAOs.upsertTag(tag.toTagEntity())

    override suspend fun deleteTag(tag: Tag) = tagDAOs.deleteTag(tag.toTagEntity())
}