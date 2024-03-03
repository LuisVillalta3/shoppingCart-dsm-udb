package com.villalta.udb.dsm.utils

import com.villalta.udb.dsm.models.Carrito
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.io.File
import java.time.LocalDateTime

object GeneratePdf {
    fun generate(carrito: Carrito) {
        val document = PDDocument()
        val page = PDPage(PDRectangle(PDRectangle.A4.height, PDRectangle.A4.width))
        document.addPage(page)

        // Configurar el contenido de la página
        val contentStream = PDPageContentStream(document, page)
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12f)

        // Configurar los encabezados de la tabla
        val startX = 50f
        val startY = page.mediaBox.upperRightY - 50f
        val margin = 10f
        val yStart = startY - 20f
        val rowHeight = 20f
        val tableWidth = page.mediaBox.width - 2 * margin
        val cellMargin = 5f
        val cols = 5
        val colWidths = listOf(60f, 300f, 85f, 85f, 0f)

        // Dibujar encabezados de columna
        contentStream.beginText()
        contentStream.showText("Factura digital - Tienda DSM UDB")
        contentStream.newLineAtOffset(startX, yStart)
        contentStream.showText("ID")
        contentStream.newLineAtOffset(60f, 0f)
        contentStream.showText("Nombre")
        contentStream.newLineAtOffset(300f, 0f)
        contentStream.showText("Precio")
        contentStream.newLineAtOffset(85f, 0f)
        contentStream.showText("Cantidad")
        contentStream.newLineAtOffset(85f, 0f)
        contentStream.showText("Subtotal")
        contentStream.endText()

        var nextY = yStart
        for (item in carrito.cartItems) {
            nextY -= rowHeight
            contentStream.setLineWidth(1f)
            contentStream.moveTo(startX, nextY)
            contentStream.lineTo(startX + tableWidth, nextY)
            contentStream.stroke()

            var nextX = startX + cellMargin
            val productFields = listOf(item.id, item.product.nombre, Utils.formatCurrency(item.product.precio), item.cantidad, Utils.formatCurrency(item.subtotal))
            for ((index, field) in productFields.withIndex()) {
                contentStream.beginText()
                contentStream.newLineAtOffset(nextX, nextY - 15)
                contentStream.showText(field.toString())
                contentStream.endText()
                nextX += colWidths[index]
            }
        }

        nextY -= rowHeight
        contentStream.setLineWidth(1f)
        contentStream.moveTo(startX, nextY)
        contentStream.lineTo(startX + tableWidth, nextY)
        contentStream.stroke()

        var nextX = startX + cellMargin
        val productFields = listOf("", "Total", "", "", Utils.formatCurrency(carrito.total))
        for ((index, field) in productFields.withIndex()) {
            contentStream.beginText()
            contentStream.newLineAtOffset(nextX, nextY - 15)
            contentStream.showText(field.toString())
            contentStream.endText()
            nextX += colWidths[index]
        }

        // Cerrar el contenido de la página
        contentStream.close()

        // Guardar el documento en un archivo
        val datetime = LocalDateTime.now()
        document.save("factura-${datetime.year.toString()}-${datetime.month.toString()}-${datetime.dayOfMonth.toString()}.pdf")

        // Cerrar el documento
        document.close()
    }
}