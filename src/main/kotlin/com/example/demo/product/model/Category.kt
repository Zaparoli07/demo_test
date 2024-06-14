package com.example.demo.product.model

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "category")
data class Category(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    val description: String? = null,
)
