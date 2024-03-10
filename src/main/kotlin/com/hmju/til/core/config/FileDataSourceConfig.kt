package com.hmju.til.core.config

import com.hmju.til.component.DataSourceComponent
import jakarta.persistence.EntityManagerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

/**
 * Description : 데이터 베이스 설정
 * db_file 를 사용하는 Repository 가 생성될때마다 아래 basePackages 추가할것
 * Created by juhongmin on 12/31/23
 */
@Configuration
@EnableJpaRepositories(
    basePackages = [
        "com.hmju.til.features.file",
    ],
    entityManagerFactoryRef = "fileEntityManagerFactory",
    transactionManagerRef = "fileTransactionManagerFactory",
)
@Suppress("unused")
class FileDataSourceConfig
    @Autowired
    constructor(
        private val dbComponent: DataSourceComponent,
    ) {
        private val logger: Logger by lazy { LoggerFactory.getLogger(this.javaClass) }

        @Value("\${spring.datasource.file.driverClassName}")
        private lateinit var driverClassName: String

        @Value("\${spring.datasource.file.url}")
        private lateinit var url: String

        @Value("\${spring.datasource.file.username}")
        private lateinit var username: String

        @Value("\${spring.datasource.file.password}")
        private lateinit var password: String

        @Bean("fileDataSource")
        fun getFileDataSource(): DataSource {
            return DataSourceBuilder.create()
                .url(url)
                .driverClassName(driverClassName)
                .username(username)
                .password(password)
                .build()
        }

        @Bean("fileEntityManagerFactory")
        fun getFileEntityManager(
            @Qualifier("fileDataSource") dataSource: DataSource,
        ): LocalContainerEntityManagerFactoryBean {
            val em = LocalContainerEntityManagerFactoryBean()
            em.dataSource = dataSource
            em.setPackagesToScan(
                "com.hmju.til.features.file",
            )
            em.persistenceUnitName = "fileEntityManager"
            val adapter = HibernateJpaVendorAdapter()
            adapter.setGenerateDdl(true)
            em.jpaVendorAdapter = adapter
            em.setJpaPropertyMap(dbComponent.getPropertiesMap())
            return em
        }

        @Bean("fileTransactionManagerFactory")
        fun getFileTransactionManager(
            @Qualifier("fileEntityManagerFactory") factory: EntityManagerFactory,
        ): PlatformTransactionManager {
            return JpaTransactionManager(factory)
        }
    }