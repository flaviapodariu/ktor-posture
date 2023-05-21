package com.licenta.data.datasources

import com.licenta.data.db.UserExercise
import com.licenta.data.models.response.WorkoutRes

interface WorkoutDataSource {

    suspend fun getWorkoutByUser(userId: Int) : List<WorkoutRes>
    suspend fun insertWorkout(userId: Int, workout: List<UserExercise>) : Boolean
    suspend fun updateWorkout(userId: Int) : Boolean

}