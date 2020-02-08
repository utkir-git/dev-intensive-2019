package ru.skillbranch.devintensive

import Chat
import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.format
import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.utils.Utils
import stripHtml
import truncate
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun test_factory() {
        val user = User.makeUser("Jhone Wick")
        println(user)
    }

    @Test
    fun test_baseMessage(){
        val user = User.makeUser("Василий")
        val textMessage=
            BaseMessage.makeMessage(user, Chat("0"), Date(), "any text message", "text") //Василий отправил сообщение "any text message" только что
        val imagetMessage= BaseMessage.makeMessage(user, Chat("0"), Date().add(-2, TimeUnits.HOUR), "https://anyurl.com" , "image",true) //Василий получил изображение "https://anyurl.com" 2 часа назад
        println(textMessage.formatMessage())
        println(imagetMessage.formatMessage())
    }
    @Test
    fun test_parsFullName(){
        val fio1=  Utils.parseFullName(null) //null null
        val fio2 =   Utils.parseFullName("") //null null
        val fio3 = Utils.parseFullName(" ") //null null
        val fio4= Utils.parseFullName("John") //John null
        println("$fio1")
        println("$fio2")
        println("$fio3")
        println("$fio4")
    }
    @Test
    fun test_date_format() {
        val d1=  Date().format() //14:00:00 27.06.19
        val d2=  Date().format("HH:mm") //14:00
        println("$d1\n$d2")
    }
    @Test
    fun test_date_add() {
        println(Date().add(2, TimeUnits.SECOND)) //Thu Jun 27 14:00:02 GST 2019
        println(Date().add(-4, TimeUnits.DAY))  //Thu Jun 23 14:00:00 GST 2019
    }
    @Test
    fun test_toInitials() {
        val name1 = Utils.toInitials("john", "doe") //JD
        val name2 = Utils.toInitials("John", null) //J
        val name3 = Utils.toInitials(null, null) //null
        val name4 = Utils.toInitials(" ", "") //null
        println("$name1\n$name2\n$name3\n$name4")
    }
    @Test
    fun test_transliteration() {
        val name1 = Utils.transliteration("Женя Стереотипов", " ")   //Zhenya Stereotipov
        val name2 = Utils.transliteration("Amazing Петр", "_")      //Amazing_Petr
        println("$name1\n$name2")
    }
    @Test
    fun test_humanizeDiff() {
        val d1 = Date().add(-2, TimeUnits.HOUR).humanizeDiff() //2 часа назад
        val d2 = Date().add(-5, TimeUnits.DAY).humanizeDiff() //5 дней назад
        val d3 = Date().add(2, TimeUnits.MINUTE).humanizeDiff() //через 2 минуты
        val d4 = Date().add(7, TimeUnits.DAY).humanizeDiff() //через 7 дней
        val d5 = Date().add(-400, TimeUnits.DAY).humanizeDiff() //более года назад
        val d6 = Date().add(400, TimeUnits.DAY).humanizeDiff() //более чем через год
        println(d1)
        println(d2)
        println(d3)
        println(d4)
        println(d5)
        println(d6)
    }
    @Test
    fun test_plural() {
        val d1 = TimeUnits.SECOND.plural(1) //1 секунду
        val d2 = TimeUnits.MINUTE.plural(4) //4 минуты
        val d3 = TimeUnits.HOUR.plural(19) //19 часов
        val d4 = TimeUnits.DAY.plural(222) //222 дня
        println(d1)
        println(d2)
        println(d3)
        println(d4)
    }
    @Test
    fun test_truncate() {
        val d1 =
            "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate() //Bender Bending R...
        val d2 =
            "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15) //Bender Bending...
        val d3 = "A     ".truncate(3) //A
        println(d1)
        println(d2)
        println(d3)
    }
    @Test
    fun test_stripHtml() {
        val d1 =
            """<p class="title">Образовательное IT-сообщество Skill Branch</p>""".stripHtml() //Образовательное IT-сообщество Skill Branch
        val d2 =
            "<p>Образовательное       IT-сообщество Skill Branch</p>".stripHtml() //Образовательное IT-сообщество Skill Branch
        println("$d1\n$d2")
    }
}
