package com.villalta.udb.dsm.utils

import com.villalta.udb.dsm.controllers.AppController
import com.villalta.udb.dsm.models.Carrito

object Menu {
    fun mostrarMenu() {
        println(Utils.generateString(" Menú "))
        println("- 1. Seleccionar productos ")
        println("- 2. Ver mi carrito ")
        println("- 3. Salir ")
        println(Utils.generateString("-"))
        println(Utils.generateString(" Ingrese una opción "))
    }

    fun verCarrito(carrito: Carrito) {
        if (carrito.isEmpty) {
            println("|--------------------------------------------------------------------------------------|")
            println("|                         ¡NO HAY PRODUCTOS EN EL CARRITO!                             |")
            println("|--------------------------------------------------------------------------------------|")
            return
        }


        println("|-----|-----------------------------------------|----------|------------|--------------|")
        println("| ID  | Nombre                                  | Precio   | Cantidad   | Subtotal     |")
        println("|-----|-----------------------------------------|----------|------------|--------------|")

        for (cartItem in carrito.cartItems) {
            println(
                "| ${cartItem.product.id.toString().padEnd(3)} | ${cartItem.product.nombre.padEnd(39)} | ${Utils.formatCurrency(cartItem.product.precio).padEnd(8)} | ${cartItem.cantidad.toString().padEnd(10)} | ${Utils.formatCurrency(cartItem.subtotal).padEnd(12)} |"
            )
        }

        println("|-----|-----------------------------------------|----------|------------|--------------|")
        println("|     | Total                                   |          |            | ${Utils.formatCurrency(
            carrito.total).padEnd(10)} |")
        println("|-----|-----------------------------------------|----------|------------|--------------|")

        println()
    }

    fun carritoMenu() {
        println("------- Menú -------")
        println("1. Proceder a pagar")
        println("2. Añadir más unidades de un producto")
        println("3. Eliminar un producto del carrito")
        println("4. Vaciar carrito")
        println("5. Regresar al menú principal")
    }
}