package com.licenta.data.models.response

import com.licenta.data.models.Exercise
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutRes(
    val exercise: Exercise,
    val reps: Int
)
