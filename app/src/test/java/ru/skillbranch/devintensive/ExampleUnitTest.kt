package ru.skillbranch.devintensive

import Chat
import User
import ru.skillbranch.devintensive.models.*
import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.*
import ru.skillbranch.devintensive.utils.Utils
import stripHtml
import toUserView
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
        //  val user=User.makeUser(null)
        val user2 = User.makeUser("Jhone Wick")
        println(user2)
        // val user3=user2.copy()
        val user3 = User.makeUser("Jhone Doe")
        println(user3)
    }

    @Test
    fun test_decomposition() {
        val user = User.makeUser("Jhone Wick")
        fun getUserInfo() = user
        val (userId, firstName, lastName) = getUserInfo()
        println("$userId $firstName $lastName")
        println("${user.component1()} ${user.component2()} ${user.component3()}")
    }

    @Test
    fun test_copy() {
        val user = User.makeUser("Jhone Wick")
        val user2 = user.copy(lastVisit = Date().add(-4, TimeUnits.SECUND))
        val user3 = user.copy(lastName = "Cena", lastVisit = Date())
        println(
            """
            ${user.lastVisit?.add(10, TimeUnits.SECUND)?.format()} 
            ${user2.lastVisit?.add(5, TimeUnits.DAY)?.format()} 
            ${user3.lastVisit?.format()} 
        """.trimIndent()
        )
//        user2.firstName = "Bob"
//        if (user.equals(user2))
//        // if (user==user2)  println("equels data and hash:\n${user.hashCode()} $user \n${user2.hashCode()} $user2")
//        else  println("not equels data and hash:\n${user.hashCode()} $user \n${user2.hashCode()} $user2")
//        println("----------------------------------------------------------------")
//        user2 = user
//        user2.firstName = "Alex"
//        if (user === user2) println("equels adresses:\n${System.identityHashCode(user)} \n${System.identityHashCode(user2)}")
//        else println("not equels adresses:\n${System.identityHashCode(user)} \n${System.identityHashCode(user2)}")
    }

    @Test
    fun test_date_format() {
      val d1=  Date().format() //14:00:00 27.06.19
      val d2=  Date().format("HH:mm") //14:00
        println("$d1\n$d2")
//        val user = User.makeUser("aaa ddd")
//        user.lastVisit = Date()
//        val user2 = user.copy(lastVisit = Date().add(10, TimeUnits.MINUTE))
//        val user3 = user.copy(firstName = "Akram", lastVisit = Date().add(-5, TimeUnits.SECOND))
//        println(
//            """
//    ${user.lastVisit?.format("HH:mm")}
//    ${user2.lastVisit?.format()}
//    ${user3.lastVisit?.format()}
//""".trimIndent()
//        )
    }

    @Test
    fun test_data_mapping() {
        val user = User.makeUser("Донёр Зайцев")
        user.lastVisit = Date().add(-2000005, TimeUnits.MINUTE)
        println(user)
        val userView = user.toUserView()
        userView.printMe()
    }

    @Test
    fun test_abstract_factory() {
        val user = User.makeUser("Артём Зайцев")
        val textMessage =
            BaseMessage.makeMessage(
                user,
                Chat("0"),
                payload = "1   2   3<456789/> 10 11 12 13",
                type = "text"
            )
        val imageMessage =
            BaseMessage.makeMessage(
                user,
                Chat("0"),
                payload = "abc   de<fjhgk/>lmnoprst",
                type = "image"
            )
        when (textMessage) {
            is TextMessage -> println("this is text message")
            is ImageMessage -> println("this is image message")
        }
        println(textMessage.formatMessage())
        println(imageMessage.formatMessage())
    }

    @Test
    fun test_baseMessage(){
        val user = User.makeUser("Василий")
        val textMessage=
        BaseMessage.makeMessage(user, Chat("0"), Date(), "any text message", "text") //Василий отправил сообщение "any text message" только что
        val imagetMessage= BaseMessage.makeMessage(user, Chat("0"), Date().add(-2,TimeUnits.HOUR), "https://anyurl.com" , "image",true) //Василий получил изображение "https://anyurl.com" 2 часа назад
   println(textMessage.formatMessage())
   println(imagetMessage.formatMessage())
    }

    @Test
    fun test_transliteration() {
        val name = Utils.transliteration("Женя Стереотипов", " ")   //Zhenya Stereotipov
        val name2 = Utils.transliteration("Amazing Петр", "_")      //Amazing_Petr
        println("$name   $name2")
    }

    @Test
    fun test_toInitials() {
        val name1 = Utils.toInitials("john", "doe") //JD
        val name2 = Utils.toInitials("John", null) //J
        val name3 = Utils.toInitials(null, null) //null
        val name4 = Utils.toInitials(" ", "") //null
        println("$name1 $name2 $name3 $name4")
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
        val d1 = TimeUnits.SECUND.plural(1) //1 секунду
        val d2 = TimeUnits.MINUTE.plural(4) //4 минуты
        val d3 = TimeUnits.HOUR.plural(19) //19 часов
        val d4 = TimeUnits.DAY.plural(222) //222 дня
        println(d1)
        println(d2)
        println(d3)
        println(d4)
    }

    @Test
    fun test_date_add() {
        println(Date().add(2, TimeUnits.SECUND)) //Thu Jun 27 14:00:02 GST 2019
        println(Date().add(-4, TimeUnits.DAY))  //Thu Jun 23 14:00:00 GST 2019
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








