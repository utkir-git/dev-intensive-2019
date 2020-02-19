package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.utils.DataGenerator

object GroupRepository {
    fun loadUsers(): List<User> {
        return DataGenerator.stabUsers
    }

    fun createChat(items: List<UserItem>) {
        val ids = items.map { it.id }
        val users = CacheManager.findUsersById(ids)
        val chat = Chat(
            CacheManager.nextChatId(),
            users.map { it.firstName }.joinToString(", "),
            users
        )
        CacheManager.insertChat(chat)
    }

}