package com.example.demo.product.model

import com.example.demo.product.usecase.RegisterProductUseCase.ProductRegistered
import com.example.demo.product.usecase.UpdateProductUseCase.*
import com.example.demo.vo.Audit
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
data class Product(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    val name: String? = null,

    val description: String? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    val categories: List<Category>,

    @Embedded
    val audit: Audit = Audit()

) : AbstractAggregateRoot<Product>(), Serializable {

    constructor(builder: ProductBuilder) : this(
        id = requireNotNull(builder.id),
        name = requireNotNull(builder.name),
        description = requireNotNull(builder.description),
        categories = requireNotNull(builder.categories),
    ) {
        registerEvent(ProductRegistered(this))
    }

    constructor(builder: ProductBuilderUpdate) : this(
        id = requireNotNull(builder.id),
        name = requireNotNull(builder.name),
        description = requireNotNull(builder.description),
        categories = emptyList()
    ) {
        registerEvent(ProductUpdated(this))
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    class ProductExistsException : Exception("Product already exists")

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class ProductNotExistsException : Exception("Product not exists")
}
