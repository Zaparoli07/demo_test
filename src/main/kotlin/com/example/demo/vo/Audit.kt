package com.example.demo.vo

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.PreUpdate

@Embeddable
data class Audit(

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "row_created_at")
    val rowCreatedAt: LocalDateTime = LocalDateTime.now(),

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "row_updated_at")
    var rowUpdatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "row_index")
    var rowIndex: Int = 0,
) {

    @PreUpdate
    fun prePersist() {
        this.rowUpdatedAt = LocalDateTime.now()
        this.rowIndex = ++rowIndex
    }
}
