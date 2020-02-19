package ru.skillbranch.devintensive.extensions

fun String.truncate(limit: Int = 16): String {
    val trimmedString = this.trim()
    if (trimmedString.length <= limit - 1) return trimmedString
    val substr = trimmedString.substring(0 until limit).trim()
    return "$substr..."
}

fun String.stripHtml(): String =
    this.replace(Regex("(<(.|\\n)+?>)|(&quot;)|(&amp;)|(&gt;)|(&lt;)"), "").replace(
        Regex("\\s+"),
        " "
    )