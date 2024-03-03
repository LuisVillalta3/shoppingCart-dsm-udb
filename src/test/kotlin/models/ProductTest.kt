package models

import com.villalta.udb.dsm.models.Product
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe

class ProductTest : DescribeSpec({
    describe("Un producto") {
        describe("Muestra el valor total ($)") {
            it ("Si hay 0 unidades disponibles") {
                Product(1, "Producto", 10.0, 0).valorTotal.shouldBeEqual(0.0)
            }

            it ("Si hay 1 o más unidades") {
                Product(1, "Producto", 10.0, 1).valorTotal.shouldBeEqual(10.0)
                Product(1, "Producto", 10.0, 2).valorTotal.shouldBeEqual(20.0)
            }
        }

        describe("Esta disponible") {
            it ("Si hay 0 unidades") {
                Product(1, "Producto", 10.0, 0).disponible.shouldBeFalse()
            }

            it ("Si hay 1 o más unidades") {
                Product(1, "Producto", 10.0, 1).disponible.shouldBeTrue()
                Product(1, "Producto", 10.0, 2).disponible.shouldBeTrue()
            }
        }
    }
})