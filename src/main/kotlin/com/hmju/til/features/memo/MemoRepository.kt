package com.hmju.til.features.memo

import com.hmju.til.features.memo.model.entity.Memo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Description : Memo Repository
 *
 * Created by juhongmin on 12/22/23
 */
@Suppress("unused")
interface MemoRepository : JpaRepository<Memo, Long> {
    /**
     * 범위로 메모 리스트 가져오는 함수
     * @param offset 가져올 인덱스 0부터 시작
     * @param limit 페이지 사이즈
     */
    @Query(
        value =
            "SELECT \n" +
                "\t\tmt.memo_id,\n" +
                "\t\tmt.contents,\n" +
                "\t\tmt.register_date,\n" +
                "\t\tmt.num,\n" +
                "\t\tmt.tag,\n" +
                "\t\tmt.title,\n" +
                "\t\tmt.user_id\n" +
                "\tfrom\n" +
                "\t\tMEMO_TB mt\n" +
                "\torder by mt.MEMO_ID  asc\n" +
                "\tlimit :limit  OFFSET :offset",
        nativeQuery = true,
    )
    fun findRange(
        offset: Int,
        limit: Int,
    ): List<Memo>
}