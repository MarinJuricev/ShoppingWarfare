package com.marinj.shoppingwarfare.core.result

import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right

sealed interface Either<out E, out V> {
    data class Right<out V>(val value: V) : Either<Nothing, V>
    data class Left<out E>(val error: E) : Either<E, Nothing>
}

fun <E> E.buildLeft() = Left(this)

fun <V> V.buildRight() = Right(this)

fun Throwable.toLeft() = message?.let { message ->
    Failure.ErrorMessage(message).buildLeft()
} ?: Failure.Unknown.buildLeft()

fun <E, V> Either<E, V>.isLeft() = this is Left<E>

fun <E, V> Either<E, V>.isRight() = this is Right<V>

fun <V> Either<Failure, V>.takeRightOrNull(): V? =
    when (this) {
        is Right -> value
        is Left -> null
    }

inline fun <T, V> Either<Failure, T>.map(
    transform: (T) -> Either<Failure, V>,
): Either<Failure, V> = when (this) {
    is Right -> transform(value)
    is Left -> this
}
