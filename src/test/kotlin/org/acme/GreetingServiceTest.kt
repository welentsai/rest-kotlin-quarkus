package org.acme

import io.quarkus.test.junit.QuarkusTest
import org.acme.service.GreetingService
import org.acme.to.IdError
import org.junit.jupiter.api.Test
import javax.enterprise.inject.Default
import javax.inject.Inject

@QuarkusTest
class GreetingServiceTest {

    @Inject
    @field: Default
    lateinit var service: GreetingService

    @Test
    fun testArrowIdCheck() {
        arrowCheck("A123456789")
        arrowCheck("A12345678")
        arrowCheck("0123456789")
        arrowCheck("A323456789")
        arrowCheck("A123456T89")
        arrowCheck("A123456788")
    }

    fun arrowCheck(id: String) {
        println("ID = $id")
        val result = service.arrowIdCheck(id.trim()).fold(
            ifLeft = {err -> err.toString()},
            ifRight = {str -> str}
        )
        println("   result = $result")

    }
}