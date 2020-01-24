import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView(): UserView {
val nickName= Utils.transliteration("$firstName $lastName")
val initials=Utils.toInitials(firstName,lastName)
val status=if (lastVisit==null) "Еще ни разу не был" else if (isOnline)
    "online" else "Последный раз был ${lastVisit?.humanizeDiff(lastVisit)}"
    return UserView(
        id,
        fullName = "$firstName $lastName",
        avatar = avatar,
        nickName = nickName,
        initials = initials,
        status = status
    )
}



