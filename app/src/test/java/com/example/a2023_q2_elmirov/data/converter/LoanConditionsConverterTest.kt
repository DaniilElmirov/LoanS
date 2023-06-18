package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.utils.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoanConditionsConverterTest {

    private val converter = LoanConditionsConverter()

    private val loanConditions = Data.loanConditions
    private val loanConditionsModel = Data.loanConditionsModel

    @Test
    fun `invoke loan conditions model EXPECT loan conditions`() {
        val expected = loanConditions
        val actual = converter(loanConditionsModel)

        assertEquals(expected, actual)
    }
}