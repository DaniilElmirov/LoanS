package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.utils.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserConverterTest {

    private val converter = UserConverter()

    private val userModel = Data.userModel
    private val user = Data.user

    @Test
    fun `convert user model EXPECT get user`() {
        val expected = user
        val actual = converter(userModel)

        assertEquals(expected, actual)
    }
}