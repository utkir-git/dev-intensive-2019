package ru.skillbranch.devintensive.utils

import android.annotation.SuppressLint

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var firstName: String?
        var lastName: String?
        val parts: List<String>? = fullName?.trim()?.split(" ")
        if (fullName?.trim()?.length == 0 || parts == null) {
            firstName = null
            lastName = null
        } else if (parts?.size == 1) {
            firstName = parts.get(0)
            lastName = null
        } else {
            firstName = parts?.getOrNull(0)
            lastName = parts?.getOrNull(1)
        }
        //return Pair(firstName,lastName)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var array = payload?.trim()?.split(" ")
        if (array.size == 1 && payload.length != 0)
            return toLatin(array.get(0))
        else if (array.size == 2)
            return toLatin(array.get(0)) + divider + toLatin(array.get(1))
        return ""
    }

    @SuppressLint("DefaultLocale")
    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName != null && firstName.trim().isNotEmpty() && lastName != null && lastName.trim().isNotEmpty()) {
            return toLatin(firstName.trim().get(0).toString()).toUpperCase().get(0).toString() +
                    toLatin(lastName.trim().get(0).toString()).toUpperCase().get(0)
        } else if (firstName != null && firstName.trim().isNotEmpty() && (lastName == null || lastName.trim().isNotEmpty()))
            return toLatin(firstName.get(0).toString().toUpperCase())
        return null
    }

    fun toLatin(text: String?): String {
        var name: String = ""
        if (text != null) {
            for (i in text) {
                name += when (i) {
                    'a', 'а' -> 'a'
                    'A', 'А' -> 'A'
                    'b', 'б' -> 'b'
                    'B', 'Б' -> 'B'
                    'v', 'в' -> 'v'
                    'V', 'В' -> 'V'
                    'g', 'г' -> 'g'
                    'G', 'Г' -> 'G'
                    'd', 'д' -> 'd'
                    'D', 'Д' -> 'D'
                    'e', 'э', 'ё', 'е' -> 'e'
                    'E', 'Э', 'Ё', 'Е' -> 'E'
                    'ж' -> "zh"
                    'Ж' -> "Zh"
                    'j' -> 'j'
                    'J' -> 'J'
                    'z', 'з' -> 'z'
                    'Z', 'З' -> 'Z'
                    'i', 'ы', 'и' -> 'i'
                    'I', 'Ы', 'И' -> 'I'
                    'y', 'й' -> 'y'
                    'Y', 'Й' -> 'Y'
                    'k', 'к' -> 'k'
                    'K', 'К' -> 'K'
                    'l', 'л' -> 'l'
                    'L', 'Л' -> 'L'
                    'm', 'м' -> 'm'
                    'M', 'М' -> 'M'
                    'n', 'н' -> 'n'
                    'N', 'Н' -> 'N'
                    'o', 'о' -> 'o'
                    'O', 'О' -> 'O'
                    'p', 'п' -> 'p'
                    'P', 'П' -> 'P'
                    'r', 'р' -> 'r'
                    'R', 'Р' -> 'R'
                    's', 'с' -> 's'
                    'S', 'С' -> 'S'
                    't', 'т' -> 't'
                    'T', 'Т' -> 'T'
                    'u', 'у' -> 'u'
                    'U', 'У' -> 'U'
                    'f', 'ф' -> 'f'
                    'F', 'Ф' -> 'F'
                    'h', 'х' -> 'h'
                    'H', 'Х' -> 'H'
                    'x' -> 'x'
                    'X' -> 'X'
                    'ц' -> "c"
                    'Ц' -> "C"
                    'ч' -> "ch"
                    'Ч' -> "Ch"
                    'щ', 'ш' -> "sh"
                    'Щ', 'Ш' -> "Sh"
                    'Ь', 'ь', 'Ъ', 'ъ' -> ""
                    'ю' -> "yu"
                    'Ю' -> "Yu"
                    'я' -> "ya"
                    'Я' -> "Ya"
                    'w' -> 'w'
                    'W' -> 'W'
                    'q' -> 'q'
                    'Q' -> 'Q'
                    else -> ""
                }
            }
        }
        return name
    }
}