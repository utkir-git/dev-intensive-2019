fun String.truncate(limit: Int = 16): String? {
    val text = this.trim()
    if (text.length > limit) {
        return text.substring(0, limit).trim() + "..."
    }
    return text
}
fun String.stripHtml():String? {
    val text=this.trim()
    val patternSpace="\\s+".toRegex()
    val patternTag="<[^>]*>".toRegex()
    val patternOut="[&'\"]".toRegex()
    return text.replace(patternTag,"").replace(patternOut,"").replace(patternSpace," ")
}