package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.utils.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoanRequestConverterTest {

    private val converter = LoanRequestConverter()

    private val loanRequest = Data.loanRequest
    private val loanRequestModel = Data.loanRequestModel

    @Test
    fun `invoke loan request EXPECT loan request model`() {
        val expected = loanRequestModel
        val actual = converter(loanRequest)

        assertEquals(expected, actual)
    }
}