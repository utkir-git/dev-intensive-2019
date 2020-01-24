fun String.truncate(limit: Int = 16): String? {
    val text = this.trim()
    if (text.length > limit + 2)
        return text.substring(0, limit) + "..."
    return text
}
fun String.stripHtml():String? {
    var text=this.trim()
    val patternSpace="\\s+".toRegex()
    val patternTag="<[^>]*>".toRegex()
    return text.replace(patternTag," ").replace(patternSpace," ").toString()
}