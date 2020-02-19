package ru.skillbranch.devintensive.ui.viewholders

import android.view.View
import kotlinx.android.synthetic.main.item_chat_archive.*
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter

class ArchiveViewHolder(containerView: View) : ChatAdapter.ChatItemViewHolder(containerView) {

    override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {

        with(tv_date_archive) {
            visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
            text = item.lastMessageDate
        }
        with(tv_counter_archive) {
            visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
            text = item.messageCount.toString()
        }
        with(tv_message_author_archive) {
            visibility = if (item.author != null) View.VISIBLE else View.GONE
            text = "@${item.author}"
        }
        tv_message_archive.text = item.shortDescription

        itemView.setOnClickListener { listener(item) }
    }

}