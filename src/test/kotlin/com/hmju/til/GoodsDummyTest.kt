package com.hmju.til

import com.fasterxml.jackson.annotation.JsonProperty
import com.hmju.til.features.file.FileService
import com.hmju.til.features.goods.GoodsService
import com.hmju.til.features.goods.model.dto.GoodsDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.function.client.WebClient

/**
 * Description : Goods Dummy 넣는 Test Code
 *
 * Created by juhongmin on 1/14/24
 */
@SpringBootTest
@ActiveProfiles("prod")
class GoodsDummyTest {

    @Autowired
    lateinit var fileService: FileService

    @Autowired
    lateinit var goodsService: GoodsService

    @Value("\${goods_dummy_url_1}")
    private lateinit var dummyUrl1: String

    @Value("\${goods_dummy_url_2}")
    private lateinit var dummyUrl2: String

    @Value("\${goods_dummy_url_3}")
    private lateinit var dummyUrl3: String

    internal data class DummyResponse(
        val products: List<GoodsDummy> = listOf()
    ) {
        internal data class GoodsDummy(
            @JsonProperty("onlineProdName")
            val title: String? = null,
            @JsonProperty("promotionalDesc")
            val message: String? = null
        )
    }

    @Test
    fun 상품_더미데이터_추가(){
        상품_더미_데이터_추가_1()
        상품_더미_데이터_추가_2()
        상품_더미_데이터_추가_3()
    }

    @Test
    fun 상품_더미_데이터_추가_1() {
        val images = fileService
            .fetch(1, 300)
            .map { it.path }
            .filter { it.endsWith(".jpg") || it.endsWith(".png") }
            .map { "https://til.qtzz.synology.me${it}" }
        val imageSize = images.size

        val voList = reqDummyGoods().mapIndexed { idx, dummy ->
            GoodsDTO(
                title = dummy.title ?: "Empty Title",
                message = dummy.message ?: "Empty Message",
                imagePath = images[idx % imageSize]
            )
        }
        val result = goodsService.postAll(voList)

        // 1차 저장됐는지 확인
        assert(result.isNotEmpty())
        println("1차 확인 완료")
        // 2차 데이터 확인
        val newList = goodsService.fetch(1, 100)
        assert(result == newList)
        println("2차 확인 완료")
    }

    private fun reqDummyGoods(): List<DummyResponse.GoodsDummy> {
        return try {
            val res = WebClient.builder()
                .build()
                .get()
                .uri(dummyUrl1)
                .retrieve()
                .bodyToMono(DummyResponse::class.java)
                .block()
            res?.products ?: listOf()
        } catch (ex: Exception) {
            println("ERROR $ex")
            listOf()
        }
    }

    internal data class DummyResponse2(
        val data: List<Data> = listOf()
    ) {
        internal data class Data(
            @JsonProperty("blockList")
            val list: List<BlockData> = listOf()
        )

        internal data class BlockData(
            @JsonProperty("list")
            val list: List<OriginData> = listOf()
        )

        internal data class OriginData(
            @JsonProperty("title1")
            val title: String? = null,
            @JsonProperty("prdNm")
            val message: String? = null
        )
    }

    @Test
    fun 상품_더미_데이터_추가_2() {
        val images = fileService
            .fetch(1, 300)
            .map { it.path }
            .filter { it.endsWith(".jpg") || it.endsWith(".png") }
            .map { "https://til.qtzz.synology.me${it}" }
        val imageSize = images.size

        val voList = reqDummyGoods_2().mapIndexed { idx, dummy ->
            GoodsDTO(
                title = dummy.title ?: "Empty Title",
                message = dummy.message ?: "Empty Message",
                imagePath = images[idx % imageSize]
            )
        }
        val result = goodsService.postAll(voList)

        // 1차 저장됐는지 확인
        assert(result.isNotEmpty())
    }

    private fun reqDummyGoods_2(): List<DummyResponse2.OriginData> {
        return try {
            val res = WebClient.builder()
                .build()
                .get()
                .uri(dummyUrl2)
                .retrieve()
                .bodyToMono(DummyResponse2::class.java)
                .block()
            res?.data?.get(0)?.list?.get(0)?.list ?: listOf()
        } catch (ex: Exception) {
            listOf()
        }
    }

    @Test
    fun 상품_더미_데이터_추가_3() {
        val images = fileService
            .fetch(1, 300)
            .map { it.path }
            .filter { it.endsWith(".jpg") || it.endsWith(".png") }
            .map { "https://til.qtzz.synology.me${it}" }
        val imageSize = images.size

        val voList = reqDummyGoods3().mapIndexed { idx, dummy ->
            GoodsDTO(
                title = dummy.title ?: "Empty Title",
                message = dummy.message ?: "Empty Message",
                imagePath = images[idx % imageSize]
            )
        }
        val result = goodsService.postAll(voList)

        // 저장됐는지 확인
        assert(result.isNotEmpty())
    }

    private fun reqDummyGoods3(): List<DummyResponse.GoodsDummy> {
        return try {
            val res = WebClient.builder()
                .build()
                .get()
                .uri(dummyUrl3)
                .retrieve()
                .bodyToMono(DummyResponse::class.java)
                .block()
            res?.products ?: listOf()
        } catch (ex: Exception) {
            listOf()
        }
    }
}
