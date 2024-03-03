package com.villalta.udb.dsm.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.villalta.udb.dsm.models.Product
import java.io.File
import java.lang.reflect.Type

object ProductosData {
    fun readProductos(): List<Product> {
        val gson = Gson()

        val jsonString = File("src/main/resources/productos.json").readText()

        val listType = object : TypeToken<List<Product>>() {}.type

        val productos: List<Product> = gson.fromJson(jsonString, listType)

        return productos
    }

    fun listarProductos(listProductos: MutableList<Product>) {
        //Utils.limpiarConsola()

        println("|-----|-----------------------------------------|----------|----------------------|")
        println("| ID  | Nombre                                  | Precio   | Cantidad Disponible  |")
        println("|-----|-----------------------------------------|----------|----------------------|")

        for (producto in listProductos) {
            println(
                "| ${producto.id.toString().padEnd(3)} | ${producto.nombre.padEnd(39)} | ${"$" + producto.precio.toString().padEnd(7)} | ${producto.cantidadDisponible.toString().padEnd(20)} |"
            )
        }

        println("|-----|-----------------------------------------|----------|----------------------|")
    }
}