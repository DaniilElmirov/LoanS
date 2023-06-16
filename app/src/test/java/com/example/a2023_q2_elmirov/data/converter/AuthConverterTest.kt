package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthConverterTest {

    private val converter = AuthConverter()

    private val auth = Data.auth
    private val authModel = Data.authModel

    @Test
    fun `convert auth EXPECT get auth model`() = runTest {
        val expected = authModel
        val actual = converter(auth)

        assertEquals(expected, actual)
    }
}