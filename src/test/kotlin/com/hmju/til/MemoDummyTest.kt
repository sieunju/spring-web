package com.hmju.til

import com.hmju.til.memo.MemoService
import com.hmju.til.memo.model.dto.MemoDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import kotlin.random.Random

/**
 * Description : Local DB 에 더미 데이터 넣기 위한 테스트 코드 클래스
 *
 * Created by juhongmin on 12/24/23
 */
@SpringBootTest
class MemoDummyTest {

    @Autowired
    lateinit var service: MemoService

    @Test
    fun 메모_더미_데이터_추가() {
        val list = mutableListOf<MemoDTO>()
        for (idx in 0 until 300) {
            list.add(
                MemoDTO(
                    userId = "dummy",
                    tag = Random.nextInt(6),
                    title = "Dummy...Random Title $idx",
                    contents = "Dummy Random Contents ${System.nanoTime()}",
                    registerDate = LocalDateTime.now()
                )
            )
        }
        val result = service.postAll(list)
        assert(result.size == 300)
        println("Done $result")
    }

    @Test
    fun 메모_데이터_추가_후_삭제() {
        val result = service.fetch(1, 300)
            .map { it.id }
            .flatMap { service.deleteAll(listOf(it)) }
        println(result)
    }
}
