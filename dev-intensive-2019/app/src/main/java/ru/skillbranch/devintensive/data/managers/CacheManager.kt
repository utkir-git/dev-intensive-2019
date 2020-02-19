package ru.skillbranch.devintensive.data.managers

import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.DataGenerator

object CacheManager {
    private val chats = mutableLiveData(DataGenerator.stabChats)
    private val users = mutableLiveData(DataGenerator.stabUsers)
    fun loadChats() = chats

    fun findUsersById(ids: List<String>): List<User> {
        // Оп, квадратичненькая сложность (впрочем ids всегда маленький по идее)
        return users.value.orEmpty().filter { ids.contains(it.id) }
    }

    fun nextChatId(): String {
        return chats.value.orEmpty().size.toString()
    }

    fun insertChat(chat: Chat) {
        val copy = chats.value.orEmpty().toMutableList()
        copy.add(chat)
        chats.postValue(copy)
    }

}