package com.licenta.data.util

import com.licenta.data.datasources.ExerciseDataSource
import com.licenta.data.db.ExerciseEntity
import com.licenta.data.db.UsersExercise
import com.licenta.data.models.request.CaptureReq
import kotlin.math.abs

suspend fun recommendWorkout(
    userCapture: CaptureReq,
    exerciseDataSource: ExerciseDataSource
) : List<UsersExercise> {

    val workout = mutableListOf<UsersExercise>()
    val exerciseList = mutableListOf<ExerciseEntity>()

    val lordosisDiff = userCapture.lordosis - LORDOSIS_NORMAL
    val headDiff = userCapture.headForward - HEAD_FORWARD_NORMAL
    val shouldersDiff = userCapture.roundedShoulders - ROUNDED_SHOULDERS_NORMAL

    val problems = mapOf(
        LORDOSIS to lordosisDiff,
        HEAD_FORWARD to headDiff,
        ROUNDED_SHOULDERS to shouldersDiff
    ).toList().filter { it.second > CAPTURE_BUFFER }.sortedByDescending { abs(it.second) }

    val problemsCount = problems.count()
    var exPerProblem = NUMBER_OF_EXERCISES / problemsCount

    problems.forEachIndexed { index, (problem, value) ->
        val absDiff = abs(value)
        exPerProblem = if(index == 0) exPerProblem + NUMBER_OF_EXERCISES % problemsCount else exPerProblem

        if(absDiff < LOW_RISK) {
           val selection = exerciseSelection(problem, exPerProblem, MIN_SCORE_LOW_RISK, exerciseDataSource)
            exerciseList.addAll(selection)
            selection.forEach{
                workout.add(UsersExercise {
                    exercise = it
                    reps = REPS_LOW_RISK
                })
            }

        }
        else if (absDiff < MEDIUM_RISK) {
            val selection = exerciseSelection(problem, exPerProblem, MIN_SCORE_MEDIUM_RISK, exerciseDataSource)
            exerciseList.addAll(selection)
            selection.forEach{
                workout.add(UsersExercise {
                    exercise = it
                    reps = REPS_MEDIUM_RISK
                })
            }

        }
        else if(absDiff < HIGH_RISK) {
            val selection = exerciseSelection(problem, exPerProblem, MIN_SCORE_HIGH_RISK, exerciseDataSource)
            exerciseList.addAll(selection)
            selection.forEach{
                workout.add(UsersExercise {
                    exercise = it
                    reps = REPS_HIGH_RISK
                })
            }
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
    minScore: Int,
    exerciseDataSource: ExerciseDataSource) : List<ExerciseEntity> {
    val exSet = mutableListOf<ExerciseEntity>()
    when(problem) {
        LORDOSIS -> {
            exSet.addAll(
                exerciseDataSource.getLordosisWhereScoreAbove(minScore)
                    .shuffled().take(numberOfExercises)
            )
        }
        HEAD_FORWARD -> {
            exSet.addAll(
                exerciseDataSource.getHeadForwardWhereScoreAbove(minScore)
                    .shuffled().take(numberOfExercises)
            )
        }
        ROUNDED_SHOULDERS -> {
            exSet.addAll(
                exerciseDataSource.getRoundedShouldersWhereScoreAbove(minScore)
                    .shuffled().take(numberOfExercises)
            )
        }
    }

    return exSet
}
