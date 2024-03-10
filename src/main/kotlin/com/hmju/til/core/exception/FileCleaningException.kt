package com.hmju.til.core.exception

/**
 * Description :
 *
 * Created by juhongmin on 3/10/24
 */
class FileCleaningException private constructor(
    code: Code
) : RuntimeException(code.msg) {

    enum class Code(
        val msg: String
    ) {
        FILE_RESOURCE("resource file exception"),
        SAVE_ADD("DB 없는 파일들 추가에 에러 발생 "),
        DELETE("DB 데이터중 실제로 리소스에 없는 경우 에러 발생");

        operator fun invoke(): FileCleaningException {
            return FileCleaningException(this)
        }
    }
}
