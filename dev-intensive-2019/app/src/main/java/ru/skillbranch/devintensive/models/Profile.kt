package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils

data class Profile(
    val firstName: String,
    val lastName: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {

    companion object {
        private val githubExceptionsAddresses = listOf(
            "enterprise",
            "features",
            "topics",
            "collections",
            "trending",
            "events",
            "marketplace",
            "pricing",
            "nonprofit",
            "customer-stories",
            "security",
            "login",
            "join"
        )

        fun validateRepository(repository: String): Boolean {
            if (repository.isEmpty()) return true
            val regex = Regex(
                "^(https:\\/\\/)?(www\\.)?(github\\.com\\/)(?!(${githubExceptionsAddresses.joinToString(
                    "|"
                )})(?=\\/|\$))[a-zA-Z\\d](?:[a-zA-Z\\d]|-(?=[a-zA-Z\\d])){0,38}(\\/)?$"
            )
            return repository.contains(regex)
        }
    }

    val rank: String = "Junior Android Developer"
    val nickname: String
        get() = Utils.transliteration("${firstName} $lastName", "_").trim()

    fun toMap(): Map<String, Any> /*Не одобряю возвращаемый тип. И вобще.*/ = mapOf(
        "nickname" to nickname,
        "rank" to rank,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository,
        "rating" to rating,
        "respect" to respect
    )

}