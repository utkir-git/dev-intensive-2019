package ru.skillbranch.devintensive.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user_list.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.show
import ru.skillbranch.devintensive.models.data.UserItem

class UserAdapter(val listener: (UserItem) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var items: List<UserItem> = listOf()

    fun updateData(newData: List<UserItem>) {

        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == newData[newItemPosition].id
            }

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newData.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == newData[newItemPosition]
            }

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newData
        diffResult.dispatchUpdatesTo(this)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(items[position], listener)


    class UserViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(item: UserItem, listener: (UserItem) -> Unit) {

            if (item.avatar != null) {
                Glide.with(containerView)
                    .load(item.avatar)
                    .into(iv_avatar_user)
            } else {
                Glide.with(containerView)
                    .clear(iv_avatar_user)
                iv_avatar_user.setInitials(item.initials ?: "??")
            }

            sv_indicator.show { item.isOnline }
            tv_user_name.text = item.fullName
            tv_last_activity.text = item.lastActivity
            iv_selected.visibility = if (item.isSelected) View.VISIBLE else View.GONE
            containerView.setOnClickListener { listener(item) }

        }
    }
}