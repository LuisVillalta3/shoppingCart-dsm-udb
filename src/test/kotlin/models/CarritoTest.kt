package models

import com.villalta.udb.dsm.exceptions.ProductoNotFoundException
import com.villalta.udb.dsm.exceptions.UnidadesInsuficientesException
import com.villalta.udb.dsm.models.Carrito
import com.villalta.udb.dsm.models.CarritoItem
import com.villalta.udb.dsm.models.Product
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeTypeOf

class CarritoTest : DescribeSpec({

    describe("Un carrito") {
        describe("Busca items por CarritoItemId") {
            it ("Si el item id es igual a 0") {
                shouldThrow<IllegalArgumentException> {
                    Carrito(mutableListOf<CarritoItem>()).findByCarritoItemId(0)
                }
            }

            it ("Si el item id es menor a 0") {
                shouldThrow<IllegalArgumentException> {
                    Carrito(mutableListOf<CarritoItem>()).findByCarritoItemId(-1)
                }
            }

            it ("Si el carrito esta vacio") {
                Carrito(mutableListOf<CarritoItem>()).findByCarritoItemId(1).shouldBeNull()
            }

            it ("Si el item no existe dentro del carrito") {
                Carrito(mutableListOf<CarritoItem>(
                    CarritoItem(1,
                        Product(1, "Producto", 10.0, 100),
                        10
                    )
                )).findByCarritoItemId(2).shouldBeNull()
            }

            it ("Si el item existe dentro del carrito") {
                Carrito(mutableListOf<CarritoItem>(
                    CarritoItem(1,
                        Product(1, "Producto", 10.0, 100),
                        10
                    )
                )).findByCarritoItemId(1).shouldBeTypeOf<CarritoItem>()
            }
        }

        describe("Busca items por ProductId") {
            it ("Si el item id es igual a 0") {
                shouldThrow<IllegalArgumentException> {
                    Carrito(mutableListOf<CarritoItem>()).findCarritoItemByProductId(0)
                }
            }

            it ("Si el item id es menor a 0") {
                shouldThrow<IllegalArgumentException> {
                    Carrito(mutableListOf<CarritoItem>()).findCarritoItemByProductId(-1)
                }
            }

            it ("Si el carrito esta vacio") {
                Carrito(mutableListOf<CarritoItem>()).findCarritoItemByProductId(1).shouldBeNull()
            }

            it ("Si el item no existe dentro del carrito") {
                Carrito(mutableListOf<CarritoItem>(
                    CarritoItem(1,
                        Product(1, "Producto", 10.0, 100),
                        10
                    )
                )).findCarritoItemByProductId(2).shouldBeNull()
            }

            it ("Si el item existe dentro del carrito") {
                Carrito(mutableListOf<CarritoItem>(
                    CarritoItem(1,
                        Product(1, "Producto", 10.0, 100),
                        10
                    )
                )).findCarritoItemByProductId(1).shouldBeTypeOf<CarritoItem>()
            }
        }

        describe("Añade elementos al carrito") {
            it ("Si ya esta el elemento dentro del carrito") {
                val carrito = Carrito(
                    mutableListOf<CarritoItem>(
                        CarritoItem(1,
                            Product(1, "Producto", 10.0, 100),
                            10
                        )
                    )
                )

                carrito.agregarItem(Product(1, "Producto", 10.0, 100), 5).shouldBeTrue()
                carrito.findCarritoItemByProductId(1)!!.cantidad.shouldBeEqual(15)
            }

            it ("Si el elemento no esta dentro del carrito") {
                val carrito = Carrito(
                    mutableListOf<CarritoItem>()
                )

                carrito.agregarItem(Product(1, "Producto", 10.0, 100), 5).shouldBeTrue()
                carrito.findCarritoItemByProductId(1)!!.cantidad.shouldBeEqual(5)
            }

            it ("Si las unidades a agregar son mayores a las disponibles") {
                shouldThrow<UnidadesInsuficientesException> {
                    val carrito = Carrito(
                        mutableListOf<CarritoItem>(
                            CarritoItem(1,
                                Product(1, "Producto", 10.0, 100),
                                10
                            )
                        )
                    )

                    carrito.agregarItem(Product(1, "Producto", 10.0, 100), 500)
                    carrito.findCarritoItemByProductId(1)!!.cantidad.shouldBeEqual(10)
                }
            }
        }

        describe("Elmina elementos del carrito") {
            it ("El producto no esta en el carrito") {
                shouldThrow<ProductoNotFoundException> {
                    Carrito(
                        mutableListOf<CarritoItem>()
                    ).eliminarElementos(1, 1)
                }
            }

            it ("Si la cantidad a restar es menor que 0") {
                shouldThrow<IllegalArgumentException> {
                    Carrito(
                        mutableListOf<CarritoItem>(
                            CarritoItem(1, Product(1, "Producto", 10.0, 10), 10)
                        )
                    ).eliminarElementos(1, -1)
                }
            }

            it ("Si la cantidad a restar es mayor a la cantidad dentro del carrito") {
                shouldThrow<IllegalArgumentException> {
                    Carrito(
                        mutableListOf<CarritoItem>(
                            CarritoItem(1, Product(1, "Producto", 10.0, 10), 10)
                        )
                    ).eliminarElementos(1, 11)
                }
            }

            it ("Si la cantidad a restar es igual o menor a la cantidad disponible") {
                var carrito = Carrito(
                    mutableListOf<CarritoItem>(
                        CarritoItem(1, Product(1, "Producto", 10.0, 10), 10)
                    )
                )

                carrito.eliminarElementos(1, 5)
                carrito.findCarritoItemByProductId(1)!!.cantidad.shouldBeEqual(5)

                carrito.eliminarElementos(1, 5)
                carrito.findCarritoItemByProductId(1).shouldBeNull()
            }
        }
    }

})