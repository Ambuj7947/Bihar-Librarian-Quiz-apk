package com.example.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.StudyMaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyMaterialDao {
    @Query("SELECT * FROM study_materials ORDER BY dateAddedMillis DESC")
    fun getAllMaterialsFlow(): Flow<List<StudyMaterialEntity>>

    @Query("SELECT * FROM study_materials WHERE unitCategory = :unitCategory ORDER BY dateAddedMillis DESC")
    fun getMaterialsByUnitFlow(unitCategory: String): Flow<List<StudyMaterialEntity>>

    @Query("SELECT * FROM study_materials WHERE id = :id LIMIT 1")
    suspend fun getMaterialById(id: Long): StudyMaterialEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaterial(material: StudyMaterialEntity): Long

    @Update
    suspend fun updateMaterial(material: StudyMaterialEntity)

    @Delete
    suspend fun deleteMaterial(material: StudyMaterialEntity)

    @Query("DELETE FROM study_materials")
    suspend fun deleteAllMaterials()
}
