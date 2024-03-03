package com.villalta.udb.dsm.models

class Product (val id: Int, val nombre: String, val precio: Double, var cantidadDisponible: Int) {
    val valorTotal: Double
        get() = cantidadDisponible * precio

    val disponible: Boolean
        get() = cantidadDisponible > 0
}