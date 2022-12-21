package com.marinj.shoppingwarfare.core.ext

fun Map<String, Any>.getOrEmpty(
    key: String,
): String = getOrElse(key) { "" }.toString()

fun Map<String, Any>.getOrZero(
    key: String,
): Int = getOrElse(key) { 0 }.toString().toInt()

fun Map<String, Any>.getOrFalse(
    key: String,
): Boolean = getOrElse(key) { false }.toString().toBoolean()
