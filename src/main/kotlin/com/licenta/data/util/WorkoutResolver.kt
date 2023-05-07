package com.licenta.data.util

import com.licenta.data.datasources.ExerciseMuscleDataSource
import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.UserExercise
import com.licenta.data.db.mappers.exerciseToExerciseEntity
import com.licenta.data.models.Exercise
import com.licenta.data.models.request.CaptureReq
import kotlin.math.abs

suspend fun recommendWorkout(
    userCapture: CaptureReq,
    exerciseMuscleDataSource: ExerciseMuscleDataSource
) : List<UserExercise> {

    val workout = mutableListOf<UserExercise>()
    val exerciseList = mutableListOf<ExerciseEntity>()

    val hf = HEAD_FORWARD_NORMAL - userCapture.headForward
    val rs = ROUNDED_SHOULDERS_NORMAL - userCapture.roundedShoulders
    val apt = LORDOSIS_NORMAL - userCapture.lordosis

    val problems = mapOf(
        LORDOSIS to apt,
        HEAD_FORWARD to hf,
        ROUNDED_SHOULDERS to rs
    ).toList().filter { it.second > CAPTURE_BUFFER }.sortedByDescending { abs(it.second) }

    val problemsCount = problems.count()
    var exPerProblem = NUMBER_OF_EXERCISES / problemsCount

    problems.forEachIndexed { index, (problem, value) ->
        val absDiff = abs(value)
        exPerProblem = if (index == 0) exPerProblem + NUMBER_OF_EXERCISES % problemsCount else exPerProblem

        if(absDiff < LOW_RISK) {
           val selection = exerciseSelection(problem, exPerProblem, REPS_LOW_RISK, exerciseMuscleDataSource)
           workout.addAll(selection)

        }
        else if (absDiff < MEDIUM_RISK) {
            val selection = exerciseSelection(problem, exPerProblem, REPS_MEDIUM_RISK, exerciseMuscleDataSource)
            workout.addAll(selection)
        }

        else if(absDiff < HIGH_RISK) {
            val selection = exerciseSelection(problem, exPerProblem, REPS_HIGH_RISK, exerciseMuscleDataSource)
            workout.addAll(selection)
        }
        else {
            //TODO bros dead af
        }

    }

    return workout
}


suspend fun exerciseSelection(
    problem: Int,
    numberOfExercises: Int,
    reps: Int,
    exerciseMuscle: ExerciseMuscleDataSource
): List<UserExercise> {
    val exSet = mutableListOf<ExerciseEntity>()
    val workout = mutableListOf<UserExercise>()

    exSet.addAll(exerciseByProblem(problem, numberOfExercises, exerciseMuscle))

    exSet.forEach {
        workout.add(UserExercise {
            exercise = it
            this@UserExercise.reps = reps
        })
    }

    return workout
}


suspend fun exerciseByProblem(
    problem: Int,
    numberOfExercises: Int,
    exerciseMuscle: ExerciseMuscleDataSource) : List<ExerciseEntity>
{
    var strengthMuscles = listOf<String>()
    var stretchMuscles = listOf<String>()
    when(problem) {
        LORDOSIS -> {
            strengthMuscles = APT_STRENGTH
            stretchMuscles = APT_STRETCH
        }
        HEAD_FORWARD -> {
            strengthMuscles = HF_STRENGTH
            stretchMuscles = HF_STRETCH
        }
        ROUNDED_SHOULDERS -> {
            strengthMuscles = RS_STRENGTH
            stretchMuscles = RS_STRETCH
        }
    }
    val ex = mutableListOf<ExerciseEntity>()
    strengthMuscles.forEach { muscle ->
        ex.addAll(
            exerciseMuscle.getExercisesByMuscleNameAndType(muscle, "strength")
                .shuffled()
        )
    }
    stretchMuscles.forEach {muscle ->
        ex.addAll(
            exerciseMuscle.getExercisesByMuscleNameAndType(muscle, "stretch")
                .shuffled()
        )
    }

    return ex.shuffled().take(numberOfExercises)
}