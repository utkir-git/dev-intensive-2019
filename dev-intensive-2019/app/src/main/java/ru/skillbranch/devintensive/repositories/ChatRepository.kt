package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository {
    private val chats = CacheManager.loadChats()

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }

    fun update(chat: Chat) {
        val copy = chats.value?.toMutableList() ?: return
        val chatId = chats.value?.indexOfFirst { it.id == chat.id } ?: return
        if (chatId == -1) return

        copy[chatId] = chat
        chats.value = copy
    }

    fun find(id: String): Chat? {
        return chats.value?.firstOrNull { it.id == id }
    }
}