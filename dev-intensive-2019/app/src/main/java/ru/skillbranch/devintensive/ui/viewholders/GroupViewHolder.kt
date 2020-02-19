package ru.skillbranch.devintensive.ui.viewholders

import android.graphics.Color
import android.view.View
import kotlinx.android.synthetic.main.item_chat_group.*
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ItemTouchViewHolder

class GroupViewHolder(containerView: View) : ChatAdapter.ChatItemViewHolder(containerView),
    ItemTouchViewHolder {

    override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {

        iv_avatar_group.setInitials(item.initials)

        with(tv_date_group) {
            visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
            text = item.lastMessageDate
        }
        with(tv_counter_group) {
            visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
            text = item.messageCount.toString()
        }
        with(tv_message_author) {
            visibility = if (item.author != null) View.VISIBLE else View.GONE
            text = "@${item.author}"
        }
        tv_title_group.text = item.title
        tv_message_group.text = item.shortDescription

        itemView.setOnClickListener { listener(item) }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemCleared() {
        itemView.setBackgroundColor(defaultBackgroundColor)
    }

}