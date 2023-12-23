package com.hmju.til.model.base

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Description : JSend Format 규격에 맞는 데이터 모델
 *
 * Created by juhongmin on 12/22/23
 */
class JSendResponse<T, M : JSendMeta> private constructor(
    builder: Builder<T, M>
) : ResponseEntity<Any>(
    builder.getBody(),
    builder.status
) {

    @Suppress("unused")
    class Builder<T, M : JSendMeta> {
        var status: HttpStatus = HttpStatus.OK
            private set

        private var payloadObj: T? = null
        private var payloadList: List<T>? = null
        private var message: String? = null
        private var meta: M? = null

        /**
         * Setter HTTP Status
         * @see HttpStatus
         * @param HTTP Status 코드
         */
        fun setStatus(status: HttpStatus): Builder<T, M> {
            this.status = status
            return this
        }

        /**
         * Setter Payload
         * @param payload List 형태의 데이터
         */
        fun setPayload(payload: List<T>): Builder<T, M> {
            payloadList = payload
            return this
        }

        /**
         * Setter Payload
         * @param payload Object 형태의 데이터
         */
        fun setPayload(payload: T): Builder<T, M> {
            payloadObj = payload
            return this
        }

        /**
         * Setter Message
         * @param msg 메시지
         */
        fun setMessage(msg: String): Builder<T, M> {
            message = msg
            return this
        }

        /**
         * Setter Meta
         * @param meta 페이로드에 넣을 메타 데이터
         */
        fun setMeta(meta: M): Builder<T, M> {
            this.meta = meta
            return this
        }

        /**
         * Getter Response Body
         *
         * @see JSendObj
         * @see JSendList
         * @see JSendEmpty
         */
        fun getBody(): Any {
            return if (payloadObj != null) {
                JSendObj(
                    status = this.status == HttpStatus.OK,
                    message = this.message,
                    data = PayloadObj(this.payloadObj!!, this.meta)
                )
            } else if (payloadList != null) {
                JSendList(
                    status = this.status == HttpStatus.OK,
                    message = this.message,
                    data = PayloadList(this.payloadList!!, this.meta)
                )
            } else {
                JSendEmpty(
                    status = this.status == HttpStatus.OK,
                    message = this.message
                )
            }
        }

        data class JSendList<T, M : JSendMeta>(
            val status: Boolean = true,
            val message: String? = null,
            val data: PayloadList<T, M>
        )

        data class JSendObj<T, M : JSendMeta>(
            val status: Boolean = true,
            val message: String? = null,
            val data: PayloadObj<T, M>
        )

        data class PayloadList<T, M : JSendMeta>(
            val payload: List<T>,
            val meta: M? = null
        )

        data class PayloadObj<T, M : JSendMeta>(
            val payload: T,
            val meta: M? = null
        )

        data class JSendEmpty(
            val status: Boolean = true,
            val message: String? = null
        )

        /**
         * JSendResponse Build
         */
        fun build(): JSendResponse<T, M> {
            return JSendResponse(this)
        }
    }
}