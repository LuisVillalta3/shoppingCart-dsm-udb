package com.villalta.udb.dsm.controllers

import com.villalta.udb.dsm.models.Carrito
import com.villalta.udb.dsm.models.Product
import com.villalta.udb.dsm.utils.Menu
import com.villalta.udb.dsm.utils.ProductosData
import com.villalta.udb.dsm.utils.Utils
import java.util.InputMismatchException
import java.util.Scanner

object AppController {
    private val scanner = Scanner(System.`in`)

    fun start() {
        println(Utils.generateString("-"))
        println("-  Bienvenido a la tienda DSM  -")
        println(Utils.generateString("-"))

        ejecutarMenu()
    }

    private fun ejecutarMenu() {
        var option: Int = 0
        do {
            Menu.mostrarMenu()

            try {
                option = scanner.nextInt()
            } catch (ex: InputMismatchException) {
                scanner.nextLine()
                Utils.limpiarConsola()
            }

            when (option) {
                1 -> CarritoController.agregarAlCarritoMenu()
                2 -> CarritoController.verCarrito()
                3 -> println("Hasta luego")
                else -> {
                    println(Utils.generateString(" Opción invalida ", '!'))
                }
            }
        } while (option != 3)
    }
}