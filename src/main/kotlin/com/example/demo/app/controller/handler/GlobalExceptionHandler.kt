package com.example.demo.app.controller.handler

import com.example.demo.product.model.Product.ProductExistsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionHandler {

    @ExceptionHandler(ProductExistsException::class)
    fun handlerProductExistsException(ex: ProductExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                ErrorResponse(
                    HttpStatus.CONFLICT.value().toString(),
                    ex.message.toString()
                )
            )
    }
}
