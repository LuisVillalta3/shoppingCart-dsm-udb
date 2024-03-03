package models

import com.villalta.udb.dsm.exceptions.UnidadesInsuficientesException
import com.villalta.udb.dsm.models.CarritoItem
import com.villalta.udb.dsm.models.Product
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class CarritoItemTest : DescribeSpec({

    describe("Un item del carrito") {
        describe ("Muestra el subtotal") {
            it ("Si hay 0 unidades en el carrito") {
                CarritoItem(1, Product(1, "Producto", 10.0, 1), 0).subtotal.shouldBeEqual(0.0)
            }

            it ("Si hay 1 o más unidades") {
                CarritoItem(1, Product(1, "Producto", 10.0, 1), 1).subtotal.shouldBeEqual(10.0)
                CarritoItem(1, Product(1, "Producto", 10.0, 1), 5).subtotal.shouldBeEqual(50.0)
            }
        }

        describe("Añade unidades al carrito") {
            it ("Si las unidades a agregar superan a la cantidad de producto disponible") {
                shouldThrow<UnidadesInsuficientesException> {
                    CarritoItem(1, Product(1, "Producto", 10.0, 1), 1).agregarUnidades(5)
                }
            }

            it ("Si las unidades a agregar es igual a la cantidad disponible") {
                val ci = CarritoItem(1, Product(1, "Producto", 10.0, 1), 0)
                ci.agregarUnidades(1).shouldBeTrue()
                ci.cantidad.shouldBeEqual(1)
            }
        }

        describe("Elimina unidades del carrito") {
            it ("Si la cantidad a restar es menor que 0") {
                shouldThrow<IllegalArgumentException> {
                    CarritoItem(1, Product(1, "Producto", 10.0, 1), 1).restarUnidades(-5)
                }
            }

            it ("Si la cantidad a restar es mayor a la cantidad dentro del carrito") {
                shouldThrow<IllegalArgumentException> {
                    CarritoItem(1, Product(1, "Producto", 10.0, 1), 1).restarUnidades(5)
                }
            }

            it ("Si la cantidad a restar es igual o menor a la cantidad disponible") {
                var ci = CarritoItem(1, Product(1, "Producto", 10.0, 1), 1)
                ci.restarUnidades(1).shouldBeTrue()
                ci.cantidad.shouldBeEqual(0)

                ci = CarritoItem(1, Product(1, "Producto", 10.0, 1), 2)
                ci.restarUnidades(1).shouldBeTrue()
                ci.cantidad.shouldBeEqual(1)
            }
        }
    }
})