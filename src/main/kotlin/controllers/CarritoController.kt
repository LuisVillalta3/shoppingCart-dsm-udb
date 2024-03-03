package com.villalta.udb.dsm.controllers

import com.villalta.udb.dsm.models.Carrito
import com.villalta.udb.dsm.models.Product
import com.villalta.udb.dsm.utils.GeneratePdf
import com.villalta.udb.dsm.utils.Menu
import com.villalta.udb.dsm.utils.ProductosData
import com.villalta.udb.dsm.utils.Utils
import java.util.*

object CarritoController {
    private val scanner = Scanner(System.`in`)
    private val carrito = Carrito()

    private val listProductos: MutableList<Product> = mutableListOf()

    init {
        ProductosData.readProductos().forEach {
            listProductos.add(it)
        }
    }

    fun agregarAlCarritoMenu() {
        var option: Int = -1
        do {
            ProductosData.listarProductos(listProductos)
            println(Utils.generateString("Escriba el id de un producto para comprarlo o escriba 0 para regresar al menú"))

            try {
                option = scanner.nextInt()
            } catch (ex: InputMismatchException) {
                scanner.nextLine()
                Utils.limpiarConsola()
            }

            if (option == 0) Utils.limpiarConsola()
            else if (option > 0) addToCarrito(option)
            else println(Utils.generateString(" Opción invalida ", '!'))
        } while (option != 0)
    }

    fun verCarrito() {
        Utils.limpiarConsola()

        var option: Int = 0

        do {
            Menu.verCarrito(carrito)

            Menu.carritoMenu()

            try {
                option = scanner.nextInt()
            } catch (ex: InputMismatchException) {
                scanner.nextLine()
            }

            when (option) {
                1 -> procederPago()
                2 -> addMasProducto()
                3 -> removerProductos()
                4 -> vaciarCarrito()
                5 -> println()
                else -> {
                    println(Utils.generateString(" Opción invalida ", '!'))
                }
            }
        } while (option != 5)
    }

    private fun removerProductos() {
        println()
        println("Ingrese el id del producto o ingrese 0 para regresar al menú")

        try {
            var productId = -1

            productId = scanner.nextInt()

            removeToCarrito(productId)
        } catch (ex: Exception) {
            println(Utils.generateString(" ${ex.message} ", '!'))
        }
    }

    private fun addMasProducto() {
        println()
        println("Ingrese el id del producto o ingrese 0 para regresar al menú")

        try {
            var productId = -1

            productId = scanner.nextInt()

            addToCarrito(productId)
        } catch (ex: Exception) {
            println(Utils.generateString(" ${ex.message} ", '!'))
        }
    }

    private fun addToCarrito(productId: Int) {
        var qty: Int = 0
        val product: Product? = listProductos.find { p: Product -> p.id == productId }

        try {
            if (product == null) {
                println(Utils.generateString(" EL PRODUCTO QUE SELECCIONÓ NO EXISTE ", '!'))
                return
            }

            if (product.cantidadDisponible == 0) {
                println("No hay unidades suficientes")
                return
            }

            println("Seleccione la cantidad de producto que quiere agregar al carrito")
            qty = scanner.nextInt()

            if (carrito.agregarItem(product, qty)) {
                println(Utils.generateString("Producto agregado al carrito", '!'))

                val index = listProductos.indexOf(product)
                product.cantidadDisponible -= qty

                listProductos[index] = product
            }
        } catch (ex: Exception) {
            println(Utils.generateString(" ${ex.message} ", '!'))
        }
    }

    private fun removeToCarrito(productId: Int) {
        var qty: Int = 0
        val product: Product? = listProductos.find { p: Product -> p.id == productId }

        try {
            if (product == null) {
                println(Utils.generateString(" EL PRODUCTO QUE SELECCIONÓ NO EXISTE ", '!'))
                return
            }

            println("Seleccione la cantidad de producto que quiere remover del carrito")
            qty = scanner.nextInt()

            if (carrito.eliminarElementos(productId, qty)) {
                println(Utils.generateString("Producto agregado al carrito", '!'))

                val index = listProductos.indexOf(product)
                product.cantidadDisponible += qty

                listProductos[index] = product
            }
        } catch (ex: Exception) {
            println(Utils.generateString(" ${ex.message} ", '!'))
        }
    }

    private fun procederPago() {
        Utils.limpiarConsola()

        Menu.verCarrito(carrito)

        var option = -1
        println("¿Desea proceder con el pago?")
        println("Presione 1 para continuar con el pago, presione cualquier otra tecla para regresar")

        try {
            option = scanner.nextInt()

            if (option != 1) return

            var string = "Procesando pago"

            for (time in 0..10) {
                Utils.limpiarConsola()
                println("$string${".".repeat(time)}")
                Thread.sleep(500)
            }

            string = "Generando factura"

            for (time in 0..10) {
                Utils.limpiarConsola()
                println("Su pago fue procesado exitosamente!")
                println("$string${".".repeat(time)}")
                Thread.sleep(500)
            }

            GeneratePdf.generate(carrito)
            vaciarCarrito()

            println()
            println("Su pago fue realizado, su factura fue generada")
            println("Gracias por su compra, presione cualquier tecla para continuar")

            scanner.nextLine()
        } catch (exception: Exception) {
            println(exception.message)
        }
    }

    private fun vaciarCarrito() {
        for (item in carrito.cartItems) {
            try {
                val product: Product? = listProductos.find { p: Product -> p.id == item.product.id }

                if (product == null) continue

                if (carrito.eliminarElementos(item.product.id, item.cantidad)) {
                    println(Utils.generateString("Producto agregado al carrito", '!'))

                    val index = listProductos.indexOf(product)
                    product.cantidadDisponible += item.cantidad

                    listProductos[index] = product
                }
            } catch (ex: Exception) {
                continue
            }
        }
    }
}