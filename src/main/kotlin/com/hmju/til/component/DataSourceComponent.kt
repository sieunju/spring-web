package com.hmju.til.component

/**
 * Description : 데이터 베이스 Component
 *
 * Created by juhongmin on 12/31/23
 */
interface DataSourceComponent {
    /**
     * Getter DataSource
     */
    fun getPropertiesMap(): Map<String, Any>
}