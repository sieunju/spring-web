package com.hmju.til.component

import org.springframework.stereotype.Component

/**
 * Description : 데이터 베이스 Component 구현체
 *
 * Created by juhongmin on 12/31/23
 */
@Component
@Suppress("unused")
internal class DataSourceComponentImpl : DataSourceComponent {

    override fun getPropertiesMap(): Map<String, Any> {
        val map = hashMapOf<String, Any>()
        map["hibernate.use-new-id-generator-mappings"] = false
        map["hibernate.ddl-auto"] = "none"
        map["hibernate.format_sql"] = true
        map["hibernate.show_sql"] = true
        map["hibernate.physical_naming_strategy"] = "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
        return map
    }

}
