package org.acme.to

import javax.ws.rs.core.Response

sealed class ApError(val message: String = "") {
    object  FormatError: ApError("Format Error")
    object  NotANumber: ApError("Not a Number")

    fun toResponse() = Response.ok(message).status(200).build()
}

sealed class IdError(val message: String = "") {
    object  IdLengthError: IdError("ID length is not correct!")
    object  CountyCodeError: IdError("County code is not correct!")
    object  GenderCodeError: IdError("Gender code is not correct!")
    object  SerialCodeError: IdError("Serial code is not correct!")
    object  CheckSumError: IdError("FAIL!! ID is invalid!!")

    override fun toString() = message
}