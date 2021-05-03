package com.amwa.koky.data.mapper

interface DomainMapper<E, D> {
    fun mapToDomain(type: E): D
}

interface EntityMapper<E, D> {
    fun mapToEntity(type: E): D
}