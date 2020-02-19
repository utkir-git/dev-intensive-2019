package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView(): UserView {

    val nickname = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = when {
        lastVisit == null -> "Еще ни разу не был"
        isOnline -> "online"
        else -> "Последний раз был ${lastVisit!!.humanizeDiff()}"
    }

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickname = nickname,
        initials = initials,
        status = status,
        avatar = avatar
    )
}

