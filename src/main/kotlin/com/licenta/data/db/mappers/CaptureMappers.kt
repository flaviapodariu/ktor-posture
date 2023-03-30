package com.licenta.data.db.mappers

import com.licenta.data.db.Capture
import com.licenta.data.models.request.CaptureReq
import com.licenta.data.models.response.CaptureRes

fun postureCaptureReqToCapture(postureReq: CaptureReq) : Capture {
    return Capture {
        headForward = postureReq.headForward
        lordosis = postureReq.lordosis
        roundedShoulders = postureReq.roundedShoulders
    }
}

fun captureToCaptureDto(capture: Capture) : CaptureRes {
    return CaptureRes(
        id = capture.id,
        userId = capture.user.id,
        headForward = capture.headForward,
        lordosis = capture.lordosis,
        roundedShoulders = capture.roundedShoulders,
        date = capture.date
    )
}