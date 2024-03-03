package com.villalta.udb.dsm.utils

import java.text.NumberFormat
import java.util.Currency

object Utils {
    fun generateString(input: String, char: Char = '-', length: Int = 32): String {
        val originalLength = input.length;
        val paddingLength = length - originalLength;

        if (paddingLength <= 0) return  input

        val leftPadding = char.toString().repeat(paddingLength / 2 + paddingLength % 2)
        val rightPadding = char.toString().repeat(paddingLength / 2)

        return "$leftPadding$input$rightPadding"
    }

    fun limpiarConsola() {
        for (i in 1..50) println()
    }

    fun formatCurrency(qty: Double): String {
        var format: NumberFormat = NumberFormat.getCurrencyInstance()

        format.maximumFractionDigits = 2
        format.currency = Currency.getInstance("USD")

        return format.format(qty)
    }
}