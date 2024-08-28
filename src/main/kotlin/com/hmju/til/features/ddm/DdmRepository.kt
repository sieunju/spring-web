package com.hmju.til.features.ddm

import com.hmju.til.features.ddm.model.entity.DdmEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DdmRepository : JpaRepository<DdmEntity, Int>
