package com.licenta.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val name: String,
    val description: String,
    val treats: BackProblem,
    val reps: Int
)

@Serializable
data class BackProblem(val code: Int, val name: String) {
    companion object {
        val Lordosis = BackProblem(code=1, name="Lordosis")
        val HeadForward = BackProblem(code=2, name="Head forward")
    }
}