package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String? {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time
    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date? = Date()): String? {
    var time = this.time
    var timeDiff = System.currentTimeMillis() - time
    if (timeDiff >= 0) {
        if (0 <= timeDiff && timeDiff <= SECOND) return "только что"                            //   0с - 1с "только что"
        else if (SECOND < timeDiff && timeDiff <= 45 * SECOND) return "несколько секунд назад"    //   1с - 45с "несколько секунд назад"
        else if (45 * SECOND < timeDiff && timeDiff <= 75 * SECOND) return "минуту назад"          //   45с - 75с "минуту назад"
        else if (75 * SECOND < timeDiff && timeDiff <= 45 * MINUTE) return "${timeDiff / MINUTE} минут назад" //   75с - 45мин "N минут назад"
        else if (45 * MINUTE < timeDiff && timeDiff <= 75 * MINUTE) return "час назад"            //   45мин - 75мин "час назад"
        else if (75 * MINUTE < timeDiff && timeDiff <= 22 * HOUR) return "${timeDiff / HOUR} часов назад"  //   75мин 22ч "N часов назад"
        else if (22 * HOUR < timeDiff && timeDiff <= 26 * HOUR) return "день назад"               //   22ч - 26ч "день назад"
        else if (26 * HOUR < timeDiff && timeDiff <= 360 * DAY) return "${timeDiff / DAY} дней назад" //   26ч - 360д "N дней назад"
        else if (timeDiff > 360 * DAY) return "более года назад"                       //   >360д "более года назад"

    } else {
        timeDiff *= -1
        if (0 <= timeDiff && timeDiff <= SECOND) return "только что"                            //   0с - 1с "только что"
        else if (SECOND < timeDiff && timeDiff <= 45 * SECOND) return "через несколько секунд"    //   1с - 45с "через несколько секунд"
        else if (45 * SECOND < timeDiff && timeDiff <= 75 * SECOND) return "через минуту"          //   45с - 75с "через минуту"
        else if (75 * SECOND < timeDiff && timeDiff <= 45 * MINUTE) return "через ${timeDiff / MINUTE} минуты" //   75с - 45мин "через N минуты"
        else if (45 * MINUTE < timeDiff && timeDiff <= 75 * MINUTE) return "через час"            //   45мин - 75мин "через час"
        else if (75 * MINUTE < timeDiff && timeDiff <= 22 * HOUR) return "через ${timeDiff / HOUR} часов"  //   75мин 22ч "через N часов"
        else if (22 * HOUR < timeDiff && timeDiff <= 26 * HOUR) return "через день"               //   22ч - 26ч "через день"
        else if (26 * HOUR < timeDiff && timeDiff <= 360 * DAY) return "через ${timeDiff / DAY} дней" //   26ч - 360д "через N"
        else if (timeDiff > 360 * DAY) return "более чем через год"                       //   >360д "более чем через год"
    }
    return null
}

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY;
    fun plural(i: Int): String? {
        when (this.name) {
            TimeUnits.SECOND.name -> {
                return "$i секунду"
            }
            TimeUnits.MINUTE.name -> {
                return "$i минуты"
            }
            TimeUnits.HOUR.name -> {
                return "$i часов"
            }
            TimeUnits.DAY.name -> {
                return "$i дня"
            }
        }
        return null
    }
}




