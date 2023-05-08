package com.licenta.data.datasources

import com.licenta.data.db.UserExercise
import com.licenta.data.db.UsersExercises
import com.licenta.data.db.exercisesMuscles
import com.licenta.data.db.mappers.exerciseMuscleToDtoAddOn
import com.licenta.data.db.mappers.usersExerciseToWorkoutRes
import com.licenta.data.db.userExercises
import com.licenta.data.models.response.WorkoutRes
import kotlinx.coroutines.yield
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.filter
import org.ktorm.entity.forEach
import org.ktorm.entity.map
import org.ktorm.support.mysql.bulkInsert
import org.ktorm.support.mysql.bulkInsertOrUpdate

class WorkoutDataSourceImpl(
     val db: Database
) : WorkoutDataSource {

    private val usersExercise = db.userExercises
    override suspend fun getWorkoutByUser(userId: Int) : List<WorkoutRes> {

         return usersExercise.filter { it.userId eq userId }
            .map {
            usersExerciseToWorkoutRes(it, it.exercise)
        }

    }

    override suspend fun insertWorkout(userId: Int, workout: Set<UserExercise>) : Boolean {
        println(workout)

        try {
            db.bulkInsertOrUpdate(UsersExercises) {
                workout.forEach { exercise ->
                    item {
                        set(it.userId, userId)
                        set(it.exerciseId, exercise.exercise.id)
                        set(it.reps, exercise.reps)
                    }
                    onDuplicateKey {
                        set(it.reps, exercise.reps)
                    }
                }
            }
        }
        catch(e: Exception){
            e.printStackTrace()
            return false
        }

        return true
    }

    override suspend fun updateWorkout(userId: Int) : Boolean {
        TODO("Not yet implemented")
    }
}