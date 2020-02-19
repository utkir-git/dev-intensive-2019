package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupViewModel : ViewModel() {
    private val query = mutableLiveData("")
    private val groupRepository = GroupRepository
    private val userItems = mutableLiveData(loadUsers())
    private val selectedItems = Transformations.map(userItems) {
        it.filter { it.isSelected }
    }

    fun getUserData(): LiveData<List<UserItem>> {
        val result = MediatorLiveData<List<UserItem>>()

        val filter = {
            val users = userItems.value.orEmpty()
            val queryStr = query.value ?: ""

            result.value = if (queryStr.isEmpty()) users else users.filter {
                it.fullName.contains(
                    queryStr,
                    true
                )
            }
        }

        result.addSource(userItems) { filter() }
        result.addSource(query) { filter() }
        return result
    }

    fun getSelectedData(): LiveData<List<UserItem>> {
        return selectedItems
    }

    fun handleSelectedItem(userId: String) {
        userItems.value = userItems.value.orEmpty().map {
            if (it.id == userId) {
                it.copy(isSelected = !it.isSelected)
            } else it
        }
    }

    fun handleRemoveChip(userId: String) {
        userItems.value = userItems.value.orEmpty().map {
            if (it.id == userId) {
                it.copy(isSelected = false)
            } else it
        }
    }

    fun handleSearchQuery(text: String?) {
        query.value = text
    }

    fun handleCreateGroup() {
        val items = selectedItems.value.orEmpty()
        if (items.isNotEmpty()) groupRepository.createChat(items)
    }

    private fun loadUsers(): List<UserItem> = groupRepository.loadUsers().map { it.toUserItem() }
}