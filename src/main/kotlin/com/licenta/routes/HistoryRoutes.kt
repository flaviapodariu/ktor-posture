package com.licenta.routes

import com.licenta.data.models.datasources.HistoryDataSource
import com.licenta.data.models.db.Capture
import com.licenta.data.models.request.PostureCaptureReq
import com.licenta.data.models.response.PostureHistory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.Identity.decode
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.getUserHistory(
    historyDataSource: HistoryDataSource
) {
    authenticate {
        get("dashboard") {
            val id = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()

            //what is user does not exist?
            val userHistory = PostureHistory(historyDataSource.getHistory(id.toInt()))

            call.respond(
                status = HttpStatusCode.OK,
                message = userHistory
            )
            return@get

        }
    }

}

fun Route.sendPosture(
    historyDataSource: HistoryDataSource
) {
    authenticate{
        post("dashboard") {
           val req = call.receiveNullable<PostureCaptureReq>() ?: run {
                call.respond(HttpStatusCode.BadRequest) // req json could not be mapped to register request model
                return@post
            }
            val id = call.principal<JWTPrincipal>()!!
                .payload.getClaim("id").asString()

            if (!historyDataSource.addNewCapture(id.toInt(), req)) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message="Could not update daily posture. Please try again later!")
                return@post
            }
            // evaluate posture score
            // create workout and send it with the res
            call.respond(
                status = HttpStatusCode.OK,
                message = "workout :)"
            )
            return@post
        }

    }
}