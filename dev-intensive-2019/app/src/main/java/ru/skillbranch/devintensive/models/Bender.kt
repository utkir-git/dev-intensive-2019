package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {

    private var validationError: String? = null

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        validationError = question.validate(answer)

        return if (validationError == null && (question == Question.IDLE || question.answers.contains(
                answer.trim().toLowerCase()
            ))
        ) {
            val prevQuestion = question
            question = question.nextQuestion()
            if (question == Question.IDLE && question == prevQuestion) {
                question.question
            } else {
                "Отлично - ты справился\n${question.question}"
            } to status.color
        } else {

            if (validationError == null) {
                status = status.nextStatus()
                if (status == Status.NORMAL) {
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}"
                } else {
                    "Это неправильный ответ\n${question.question}"
                }
            } else {
                "$validationError\n${question.question}"
            } to status.color
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (ordinal < values().lastIndex) {
                values()[ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("bender", "бендер")) {
            override fun validate(answer: String) =
                if (answer.isEmpty() || !answer[0].isUpperCase()) {
                    "Имя должно начинаться с заглавной буквы"
                } else {
                    null
                }

            override fun nextQuestion(): Question {
                return PROFESSION
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun validate(answer: String): String? {
                return if (answer.isEmpty() || !answer[0].isLowerCase()) {
                    "Профессия должна начинаться со строчной буквы"
                } else null
            }

            override fun nextQuestion(): Question {
                return MATERIAL
            }
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun validate(answer: String): String? {
                return if (answer.contains(Regex("[0-9]"))) {
                    "Материал не должен содержать цифр"
                } else null
            }

            override fun nextQuestion(): Question {
                return BDAY
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun validate(answer: String): String? {
                return if (answer.any { !it.isDigit() }) {
                    "Год моего рождения должен содержать только цифры"
                } else null
            }

            override fun nextQuestion(): Question {
                return SERIAL
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun validate(answer: String): String? {
                return if (answer.length != 7 || answer.any { !it.isDigit() }) {
                    "Серийный номер содержит только цифры, и их 7"
                } else null
            }

            override fun nextQuestion(): Question {
                return IDLE
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun validate(answer: String): String? = null

            override fun nextQuestion(): Question {
                return IDLE
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validate(answer: String): String?
    }
}