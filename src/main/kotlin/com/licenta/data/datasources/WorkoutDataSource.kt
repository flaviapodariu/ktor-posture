package com.licenta.data.datasources

import com.licenta.data.db.UsersExercise
import com.licenta.data.models.response.WorkoutRes

interface WorkoutDataSource {

    suspend fun getWorkoutByUser(userId: Int) : List<WorkoutRes>
    suspend fun insertWorkout(userId: Int, workout: List<UsersExercise>) : Boolean
    suspend fun updateWorkout(userId: Int) : Boolean

}