package com.villalta.udb.dsm.models

import com.villalta.udb.dsm.exceptions.ProductoNotFoundException

class Carrito (private val items: MutableList<CarritoItem> = mutableListOf<CarritoItem>()){
    val isEmpty: Boolean
        get() = cartItems.isEmpty()

    val cartItems: MutableList<CarritoItem>
        get() = items.filter { item: CarritoItem -> item.cantidad > 0 }.toMutableList()

    val total: Double
        get() = cartItems.sumOf { item: CarritoItem -> item.cantidad * item.product.precio }

    fun findByCarritoItemId(carritoItemId: Int): CarritoItem? {
        if (carritoItemId <= 0)
            throw IllegalArgumentException("El item id debe ser mayor a 0")

        if (isEmpty) return null

        return items.find { item: CarritoItem -> item.id == carritoItemId }
    }

    fun findCarritoItemByProductId(productId: Int): CarritoItem? {
        if (productId <= 0)
            throw IllegalArgumentException("El item id debe ser mayor a 0")

        if (isEmpty) return null

        return items.find { item: CarritoItem -> item.product.id == productId }
    }

    fun agregarItem(product: Product, qty: Int): Boolean {
        val carritoItem = findCarritoItemByProductId(product.id) ?: CarritoItem(-1, product, 0)

        carritoItem.agregarUnidades(qty)

        if (carritoItem.id == -1) {
            if (items.size > 0)
                carritoItem.id = items[items.size - 1].id + 1
            else carritoItem.id = 1
            items.add(carritoItem)
        } else {
            val index = items.indexOf(carritoItem);
            items[index] = carritoItem
        }

        return true
    }

    fun eliminarElementos(productId: Int, qty: Int): Boolean {
        val carritoItem = findCarritoItemByProductId(productId)
        val index = items.indexOf(carritoItem);

        if (carritoItem == null || index < 0)
            throw ProductoNotFoundException()

        carritoItem.restarUnidades(qty)

        if (carritoItem.cantidad == 0) {
            items.removeAt(index)
        } else {
            items[index] = carritoItem
        }

        return true
    }
}