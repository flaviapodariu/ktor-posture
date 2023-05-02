package com.licenta.data.db.mappers

import com.licenta.data.db.ExerciseMuscle
import com.licenta.data.models.response.ExerciseMuscleType


// when returning list of exercises to GET workout route, we need
// info about muscle worked and exercise type (stretch/strength)
fun exerciseMuscleToDtoAddOn(entity: ExerciseMuscle) : ExerciseMuscleType {
    return ExerciseMuscleType(
        muscle = entity.muscle.name,
        type = entity.type
    )
}