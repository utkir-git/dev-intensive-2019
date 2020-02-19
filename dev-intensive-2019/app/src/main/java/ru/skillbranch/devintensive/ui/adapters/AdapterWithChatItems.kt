package ru.skillbranch.devintensive.ui.adapters

import ru.skillbranch.devintensive.models.data.ChatItem

interface AdapterWithChatItems {
    val items: List<ChatItem>
}