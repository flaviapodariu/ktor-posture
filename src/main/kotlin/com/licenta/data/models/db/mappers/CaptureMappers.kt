package com.licenta.data.models.db.mappers

import com.licenta.data.models.db.Capture
import com.licenta.data.models.request.PostureCaptureReq
import com.licenta.data.models.response.CaptureDto

fun postureCaptureReqToCapture(postureReq: PostureCaptureReq) : Capture {
    return Capture {
        headForward = postureReq.headForward
        lordosis = postureReq.lordosis
        roundedShoulders = postureReq.roundedShoulders
    }
}

fun captureToCaptureDto(capture: Capture) : CaptureDto {
    return CaptureDto(
        id = capture.id,
        userId = capture.user.id,
        headForward = capture.headForward,
        lordosis = capture.lordosis,
        roundedShoulders = capture.roundedShoulders,
        date = capture.date
    )
}