package ru.skillbranch.devintensive.models

/*
* Валидация
Question.NAME -> "Имя должно начинаться с заглавной буквы"
Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
Question.MATERIAL -> "Материал не должен содержать цифр"
Question.BDAY -> "Год моего рождения должен содержать только цифры"
Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
Question.IDLE -> //игнорировать валидацию
* */
class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    var count = 0
    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        return if (question.answers.contains(answer)) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else {
            var isValidationCorrect = true
            var variation = "Это неправильный ответ"
            when (question) {
                Question.NAME -> if (answer.get(0).isLowerCase()) {
                    isValidationCorrect = false
                    variation = "Имя должно начинаться с заглавной буквы"
                }
                Question.PROFESSION -> if (!answer.get(0).isLetter()) {
                    isValidationCorrect = false
                    variation = "Профессия должна начинаться со строчной буквы"
                }
                Question.MATERIAL -> if (answer.contains("[0-9]".toRegex())) {
                    isValidationCorrect = false
                    variation = "Материал не должен содержать цифр"
                }
                Question.BDAY -> if (answer.contains("[^0-9]".toRegex())) {
                    isValidationCorrect = false
                    variation = "Год моего рождения должен содержать только цифры"
                }
                Question.SERIAL -> if (answer.contains("[^0-9]".toRegex()) || answer.length != 7) {
                    isValidationCorrect = false
                    variation = "Серийный номер содержит только цифры, и их 7"
                }
                Question.IDLE -> variation = "Это неправильный ответ"
            }
            if (isValidationCorrect) {
                count++
                if (count <= 3) {
                    status = status.nextStatus()
                } else {
                    status = Status.NORMAL
                    question = Question.NAME
                    variation = "Это неправильный ответ. Давай все по новой"
                }
            }
            variation + "\n${question.question}" to status.color
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}