package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType
import ru.skillbranch.devintensive.ui.viewholders.SingleViewHolder

class ArchiveAdapter(private val listener: (ChatItem) -> Unit) :
    RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>(), AdapterWithChatItems {

    private var _items: List<ChatItem> = emptyList()

    override var items: List<ChatItem>
        get() = _items
        set(value) {

            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return _items[oldItemPosition].id == value[newItemPosition].id
                }

                override fun getOldListSize(): Int = _items.size

                override fun getNewListSize(): Int = value.size

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return _items[oldItemPosition] == value[newItemPosition]
                }

            })
            _items = value
            diffResult.dispatchUpdatesTo(this)
        }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatAdapter.ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_chat_single -> SingleViewHolder(inflater.inflate(viewType, parent, false))
            R.layout.item_chat_group -> SingleViewHolder(inflater.inflate(viewType, parent, false))
            else -> error("not valid item type")
        }
    }

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ChatAdapter.ChatItemViewHolder, position: Int) {
        holder.bind(_items[position], listener)
    }

    override fun getItemViewType(position: Int): Int {
        return when (_items[position].chatType) {
            ChatType.GROUP -> R.layout.item_chat_group
            ChatType.SINGLE -> R.layout.item_chat_single
            else -> error("not valid item type")
        }
    }
}