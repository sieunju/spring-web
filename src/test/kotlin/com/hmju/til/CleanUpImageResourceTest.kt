package com.hmju.til

import com.hmju.til.features.file.FileRepository
import com.hmju.til.features.file.FileService
import com.hmju.til.features.file.model.entity.FileEntity
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.io.path.toPath


/**
 * Description :
 *
 * Created by juhongmin on 3/9/24
 */
@SpringBootTest
// @ActiveProfiles("prod")
@ActiveProfiles("local")
class CleanUpImageResourceTest {

    @Autowired
    lateinit var fileService: FileService

    @Autowired
    lateinit var repository: FileRepository

    @Test
    fun cleaning_file_resource() {
        // src/main/resources/files/img
        val findAllFiles = getResourceFiles()
        val findAllDb = repository.findAll()
        val addEntityList = mutableListOf<FileEntity>()
        val removeEntityList = mutableListOf<FileEntity>()
        // 전체 파일등중 DB에 없는 리소스 파일 추가
        findAllFiles.forEach { file ->
            val newPath = file.path.replace("src/main/resources/files","")
            val findDb = findAllDb.find { it.path.contains(newPath) }
            if (findDb == null) {
                addEntityList.add(FileEntity(
                    originalName = file.name,
                    path = file.path.replace("src/main/resources/files","/resources"),
                    mimeType = Files.probeContentType(file.toURI().toPath())
                ))
            }
        }

        // repository.saveAll(addEntityList)
         addEntityList.forEach { println("추가할 파일들 $it") }
        // DB에서 실제 리스소 파일에 없는 것들 제거
        findAllDb.forEach { entity ->
            val findFile = findAllFiles.find {
                val newPath = it.path.replace("src/main/resources/files","")
                entity.path.contains(newPath)
            }
            if (findFile == null) {
                removeEntityList.add(entity)
            }
        }
        repository.deleteAll(removeEntityList)
    }

    private fun getResourceFiles(): List<File> {
        return try {
            val path = Paths.get("./src/main/resources/files")
            Files.walk(path)
                .map { it.normalize() }
                .filter { Files.isRegularFile(it) && !it.endsWith(".DS_Store") }
                .map { it.toFile() }
                .toList()
        } catch (e: Exception) {
            // ignore
            listOf()
        }
    }
}
