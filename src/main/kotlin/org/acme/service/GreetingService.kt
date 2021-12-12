package org.acme.service

import arrow.core.*
import org.acme.to.ApError
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GreetingService {

//    val right: Either<String, Int> = Either.Right(5)

    fun greeting(name: String): String {
        return "Hi $name, hello from greeting service !"
    }

    fun arrowGreeting(name: String): Either<ApError, String> =
        Either.Right("Hi $name, hello from arrow greeting service !")


}