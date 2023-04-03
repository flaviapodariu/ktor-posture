package com.licenta.data.db.mappers

import com.licenta.data.db.CaptureEntity
import com.licenta.data.models.request.CaptureReq
import com.licenta.data.models.response.CaptureRes

fun captureReqToCaptureEntity(captureReq: CaptureReq) : CaptureEntity {
    return CaptureEntity {
        headForward = captureReq.headForward
        lordosis = captureReq.lordosis
        roundedShoulders = captureReq.roundedShoulders
    }
}

fun captureEntityToCaptureDto(captureEntity: CaptureEntity) : CaptureRes {
    return CaptureRes(
        id = captureEntity.id,
        userId = captureEntity.user.id,
        headForward = captureEntity.headForward,
        lordosis = captureEntity.lordosis,
        roundedShoulders = captureEntity.roundedShoulders,
        date = captureEntity.date
    )
}