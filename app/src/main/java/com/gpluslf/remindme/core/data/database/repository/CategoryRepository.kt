package com.gpluslf.remindme.core.data.database.repository

import com.gpluslf.remindme.core.data.database.daos.CategoryDAOs
import com.gpluslf.remindme.core.data.database.entities.CategoryEntity


class CategoryRepository(private val categoryDAOs: CategoryDAOs) {

    fun getAllCategories(userId: Long) = categoryDAOs.getAllCategories(userId)

    fun getCategoryById(categoryId: Long) = categoryDAOs.getCategoryById(categoryId)

    suspend fun upsertCategory(category: CategoryEntity) = categoryDAOs.upsertCategory(category)

    suspend fun deleteCategory(category: CategoryEntity) = categoryDAOs.deleteCategory(category)
}