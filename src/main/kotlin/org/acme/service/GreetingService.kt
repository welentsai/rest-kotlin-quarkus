package org.acme.service

import arrow.core.*
import arrow.core.computations.either
import org.acme.to.ApError
import org.acme.to.IdError
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class GreetingService {

    //    val right: Either<String, Int> = Either.Right(5)
    suspend fun greeting(name: String): String {
        return "Hi $name, hello from greeting service !"
    }

    fun arrowGreeting(name: String): Either<ApError, String> =
        Either.Right("Hi $name, hello from arrow greeting service !")

    suspend fun arrowIdCheck(id: String): Either<IdError, String> =
        either {
            val validId = isValidLength(id).bind()
            isvalidCountyCode(validId).bind()
            isValidGenderCode(validId).bind()
            isValidSerialCode(validId).bind()
            isValidCheckSum(validId).bind()
        }

    private fun isValidCheckSum(id: String): Either<IdError, String> {
        val conver = "ABCDEFGHJKLMNPQRSTUVXYWZIO"
        val weights = intArrayOf(1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1)
        val newId = String.format("%d", conver.indexOf(id[0]) + 10) + id.substring(1)
        println("   new Id = $newId")
        var checkSum = 0
        for (i in weights.indices) {
            val code = newId[i].digitToInt()
            val weight = weights[i]
            checkSum += code * weight
        }
        println("   checksum = $checkSum")

        if (checkSum % 10 != 0) return Either.Left(IdError.CheckSumError)
        return Either.Right("PASS!! ID is valid !!")
    }

    private fun isValidSerialCode(id: String): Either<IdError, String> {
        val subId = id.substring(2)
        subId.forEach {
            val code = it.code
            if (code < 48 || code > 57) return Either.Left(IdError.SerialCodeError)
        }
        return Either.Right(id)
    }

    private fun isValidGenderCode(id: String): Either<IdError, String> {
        val genderCode = id[1].code
        if (genderCode != 49 && genderCode != 50) return Either.Left(IdError.GenderCodeError)
        return Either.Right(id)
    }

    private fun isvalidCountyCode(id: String): Either<IdError, String> {
        val countyCode = id[0].code
        if (countyCode < 65 || countyCode > 90) return Either.Left(IdError.CountyCodeError)
        return Either.Right(id)
    }

    private fun isValidLength(id: String): Either<IdError, String> {
        if (id.length != 10) return Either.Left(IdError.IdLengthError)
        return Either.Right(id)
    }
}