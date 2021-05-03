package com.amwa.koky.data.mapper

import com.amwa.koky.data.local.db.model.SearchHistoryEntity
import com.amwa.koky.domain.model.SearchHistory

object SearchHistoryEntityMapper : DomainMapper<SearchHistoryEntity, SearchHistory> {
    override fun mapToDomain(type: SearchHistoryEntity) = with(type) {
        SearchHistory(query, id)
    }
}

object SearchHistoryDomainMapper : EntityMapper<SearchHistory, SearchHistoryEntity> {
    override fun mapToEntity(type: SearchHistory) = with(type) {
        SearchHistoryEntity(query, id)
    }
}