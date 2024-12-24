package com.gpluslf.remindme.core.domain

import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {

    fun getCategoryById(categoryId: Long): Flow<Category?>

    fun getAllCategories(userId: Long): Flow<List<Category>>

    suspend fun upsertCategory(category: Category)

    suspend fun deleteCategory(category: Category)

}