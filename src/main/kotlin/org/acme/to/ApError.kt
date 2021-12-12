package org.acme.to

import javax.ws.rs.core.Response

sealed class ApError(val message: String = "") {
    object  FormatError: ApError("Format Error")
    object  NotANumber: ApError("Not a Number")

    fun toResponse() = Response.ok(message).status(200).build()
}