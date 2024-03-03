package com.villalta.udb.dsm.models

import com.villalta.udb.dsm.exceptions.UnidadesInsuficientesException

class CarritoItem (var id: Int, val product: Product, var cantidad: Int) {
    val subtotal: Double
        get() = product.precio * cantidad

    fun agregarUnidades(qty: Int): Boolean {
        if (qty < 0)
            throw IllegalArgumentException("Ingrese una cantidad valida para agregar al carrito")
        val cantidadTotal = qty + cantidad
        if (cantidadTotal > product.cantidadDisponible)
            throw UnidadesInsuficientesException();

        cantidad = cantidadTotal

        return true
    }

    fun restarUnidades(qty: Int): Boolean {
        if (qty < 0)
            throw IllegalArgumentException("La cantidad a restar debe ser mayor que 0")

        if (cantidad < qty)
            throw IllegalArgumentException("La cantidad a restar no debe ser mayor a la cantidad que esta en el carrito")

        cantidad -= qty

        return true
    }
}
