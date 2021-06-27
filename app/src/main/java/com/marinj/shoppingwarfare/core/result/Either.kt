package com.marinj.shoppingwarfare.core.result

sealed class Either<out E, out V> {
    data class Right<out V>(val value: V) : Either<Nothing, V>()
    data class Left<out E>(val error: E) : Either<E, Nothing>()
}

fun <E> E.buildLeft() = Either.Left(this)

fun <T> T.buildRight() = Either.Right(this)