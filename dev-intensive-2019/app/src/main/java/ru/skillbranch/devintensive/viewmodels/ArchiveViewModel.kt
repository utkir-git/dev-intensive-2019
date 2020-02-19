package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.repositories.ChatRepository

class ArchiveViewModel : ViewModel() {
    private val chatRepository = ChatRepository

    val chats = Transformations.map(chatRepository.loadChats()) { chats ->
        chats.orEmpty()
            .filter { it.isArchived }
            .map { it.toChatItem() }
            .sortedBy { it.id }
    }

    fun restoreFromArchive(id: String) {
        val chat = chatRepository.find(id)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun addToArchive(id: String) {
        val chat = chatRepository.find(id)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }
}