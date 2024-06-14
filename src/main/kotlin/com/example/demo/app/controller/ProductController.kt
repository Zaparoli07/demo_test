package com.example.demo.app.controller

import com.example.demo.product.model.Product
import com.example.demo.product.usecase.RegisterProductUseCase
import com.example.demo.product.usecase.RegisterProductUseCase.RegisterProduct
import com.example.demo.product.usecase.UpdateProductUseCase
import com.example.demo.product.usecase.UpdateProductUseCase.UpdateProduct
import io.swagger.annotations.Api
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@RestController
@Api(tags = ["Product API"])
@RequestMapping(
    "/product",
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class ProductController(
    private val registerProductUseCase: RegisterProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) {

    @PostMapping
    fun save(@Valid @RequestBody cmd: RegisterProduct): Product {
        return registerProductUseCase.handle(cmd)
    }

    @PutMapping
    fun update(
        @RequestParam id: UUID,
        @RequestBody cmd: UpdateProduct
    ): Product {
        return updateProductUseCase.handle(id, cmd)
    }
}
