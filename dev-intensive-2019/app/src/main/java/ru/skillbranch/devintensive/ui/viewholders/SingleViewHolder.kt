package ru.skillbranch.devintensive.ui.viewholders

import android.graphics.Color
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.ItemTouchViewHolder

class SingleViewHolder(containerView: View) : ChatAdapter.ChatItemViewHolder(containerView),
    ItemTouchViewHolder {

    override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
        if (item.avatar == null) {
            Glide.with(itemView)
                .clear(iv_avatar_single)
            iv_avatar_single.setInitials(item.initials)
        } else {

            Glide.with(itemView)
                .load(item.avatar)
                .into(iv_avatar_single)
        }


        sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
        with(tv_date_single) {
            visibility = if (item.lastMessageDate == null) View.GONE else View.VISIBLE
            text = item.lastMessageDate
        }
        with(tv_counter_single) {
            visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
            text = item.messageCount.toString()
        }
        tv_title_single.text = item.title
        tv_message_single.text = item.shortDescription

        itemView.setOnClickListener { listener(item) }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemCleared() {
        itemView.setBackgroundColor(defaultBackgroundColor)
    }

}