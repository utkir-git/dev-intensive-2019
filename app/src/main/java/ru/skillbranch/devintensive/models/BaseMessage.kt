package ru.skillbranch.devintensive.models

import Chat
import User
import stripHtml
import truncate
import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any?,
            isIncoming: Boolean = false
        ): BaseMessage {
            lastId++
            val textLength = 15
            var textClear = payload.toString().stripHtml()
            var textTruncate = textClear?.truncate(textLength)
            return when (type) {
                "image" -> ImageMessage(
                    "$lastId",
                    from,
                    chat,
                    date = date,
                    image = textTruncate
                )
                else -> TextMessage("$lastId", from, chat, date = date, text = textTruncate)
            }
        }
    }
}



