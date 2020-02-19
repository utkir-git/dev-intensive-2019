package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User,
    val chat: Chat,
    val isIncoming: Boolean = true,
    val date: Date = Date(),
    var isReaded: Boolean = false
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory { // По моему это все таки не абстрактная фабрика, а фабричный метод
        var lastId = -1
        fun makeMessage(
            from: User,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any?,
            isIncoming: Boolean = false
        ): BaseMessage {
            lastId++
            return when (type) {
                "text" -> TextMessage(
                    lastId.toString(),
                    from,
                    chat,
                    date = date,
                    text = payload as String,
                    isIncoming = isIncoming
                )
                "image" -> ImageMessage(
                    lastId.toString(),
                    from,
                    chat,
                    date = date,
                    image = payload as String,
                    isIncoming = isIncoming
                )
                else -> throw IllegalStateException()
            }
        }

    }
}