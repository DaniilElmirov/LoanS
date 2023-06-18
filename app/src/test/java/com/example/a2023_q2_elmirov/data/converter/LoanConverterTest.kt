package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.utils.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoanConverterTest {

    private val converter = LoanConverter()

    private val loan = Data.loan
    private val loanModel = Data.loanModel

    @Test
    fun `invoke loan model EXPECT loan`() {
        val expected = loan
        val actual = converter(loanModel)

        assertEquals(expected, actual)
    }
}