package com.example.pokemonapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokemonapp.data.local.entities.UserEntity


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    // Cambié esta consulta para solo obtener el usuario por el email
    @Query("SELECT * FROM users_table WHERE email = :email")
    suspend fun loginUserByEmail(email: String): UserEntity?

    // Nuevo método para obtener todos los usuarios
    @Query("SELECT * FROM users_table")
    suspend fun getAllUsers(): List<UserEntity> // Obtiene todos los usuarios de la tabla

}