package com.marinj.shoppingwarfare.core.mapper

interface Mapper<R, O> {
    suspend fun map(origin: O): R
}
