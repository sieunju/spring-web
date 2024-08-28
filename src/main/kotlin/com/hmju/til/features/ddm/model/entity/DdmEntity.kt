package com.hmju.til.features.ddm.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "DDM_TB")
data class DdmEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Long = 0,
    @Column(name = "NAME", nullable = false)
    val name: String = "",
    @Column(name = "DEVICE_TYPE", length = 30, nullable = false)
    val type: String = "",
    @Column(name = "VERSION", length = 30, nullable = false)
    val version: String = "",
    @Column(name = "PATH", length = 200, nullable = false)
    val path: String = "",
    @Column(name = "REG_DATE", nullable = false)
    val registerDate: LocalDateTime = LocalDateTime.now()
)
