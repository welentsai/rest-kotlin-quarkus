package org.acme.resource

import kotlinx.coroutines.runBlocking
import org.acme.service.GreetingService
import org.acme.to.Greeting
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/hello")
class GreetingResource {

    /**
     * Kotlin reflection annotation processing differs from Java.  You may experience an error when using CDI @Inject
     * adding @field: Default, to handle the lack of a @Target on the Kotlin reflection annotation definition.
     *
     * Kotlin requires a @field: xxx qualifier as it has no @Target on the annotation definition.
     * Add @field: xxx in this example. @Default is used as the qualifier, explicitly specifying the use of the default bean.
     */
    @Inject
    @field: Default
    lateinit var service: GreetingService

    // default route
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = "Hello RESTEasy"

    // /hello/greeting
    @GET
    @Path("/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    fun greeting() = Greeting("hello, Kotlin!")

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/greeting/{name}")
    fun greeting(@PathParam("name") name: String) = runBlocking { service.greeting(name) }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/arrow/{name}")
    fun arrowGreeting(@PathParam("name") name: String): Response =
        service.arrowGreeting(name).fold(
            ifLeft = { err -> err.toResponse() },
            ifRight = { str ->
                println(str)
                Response.ok(str).status(201).build()
            }
        )

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/arrow/check/{id}")
    fun arrowIdCheck(@PathParam("id") id: String): Response =
        service.arrowIdCheck(id).fold(
            ifLeft = { err -> err.toResponse() },
            ifRight = { str ->
                println(str)
                Response.ok(str).status(201).build()
            }
        )
}