package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.CategoryDAOs
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity
import com.gpluslf.remindme.core.data.mappers.toCategory
import com.gpluslf.remindme.core.data.mappers.toCategoryEntity
import com.gpluslf.remindme.core.domain.Category
import com.gpluslf.remindme.core.domain.CategoryDataSource
import kotlinx.coroutines.flow.map


class CategoryRepository(private val categoryDAOs: CategoryDAOs): CategoryDataSource {

    override fun getAllCategories(userId: Long) = categoryDAOs.getAllCategories(userId).map { flow -> flow.map { it.toCategory() } }

    override fun getCategoryById(categoryId: Long) = categoryDAOs.getCategoryById(categoryId)?.toCategory()

    override suspend fun upsertCategory(category: Category) = categoryDAOs.upsertCategory(category.toCategoryEntity())

    override suspend fun deleteCategory(category: Category) = categoryDAOs.deleteCategory(category.toCategoryEntity())
}