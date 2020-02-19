package ru.skillbranch.devintensive.ui.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

private const val TAG = "ChatItemTouchHelper"

class ArchiveChatItemTouchHelperCallback(
    adapter: ArchiveAdapter,
    context: Context,
    swipeListener: (ChatItem) -> Unit
) : ChatItemTouchHelperCallback(adapter, context, swipeListener) {

    override fun getIcon(itemView: View): Drawable {
        return itemView.resources.getDrawable(
            R.drawable.ic_unarchive_white_24dp,
            itemView.context.theme
        )
    }
}