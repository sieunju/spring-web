package com.hmju.til.features.auth_jwt.model.dto

import com.hmju.til.features.auth_jwt.model.entity.AuthEntity
import java.util.*

/**
 * Description :
 *
 * Created by juhongmin on 1/21/24
 */
data class AuthDTO(
    val token: String = "",
    val expiredDate: Date,
    val refreshToken: String = ""
) {

    constructor(
        entity: AuthEntity
    ) : this(
        token = entity.token,
        expiredDate = entity.expiredDate,
        refreshToken = entity.refreshToken
    )
}
