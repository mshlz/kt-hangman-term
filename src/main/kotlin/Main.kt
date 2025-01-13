package dev.mshlz

import kotlin.random.Random

fun hangman() {
    val words = listOf(
        "amor", "feliz", "cachorro", "livro", "escola",
        "computador", "familia", "amizade", "trabalho", "carro",
        "praia", "mundo", "cidade", "sol", "estrela",
        "flor", "chuva", "tempo", "jogo", "tarde",
        "manha"
    )

    val fruits = listOf(
        "maca", "banana", "laranja", "manga", "uva",
        "morango", "abacaxi", "pera", "melancia", "kiwi",
        "abacate", "coco", "goiaba", "caqui", "amora",
        "cabeludinha", "graviola", "maracuja", "romã", "carambola"
    )

    val animals = listOf(
        "cachorro", "gato", "elefante", "leao", "tigre",
        "urso", "girafa", "zebra", "coelho", "cavalo",
        "macaco", "pato", "papagaio", "camelo", "pinguim",
        "tartaruga", "lobo", "boi", "raposa", "onca"
    )

    val gallows = """
        +----+
        |    |
        |    %s
        |   %s%s%s
        |   %s %s
        |
    """.trimIndent()

    var statsWon = 0
    var statsLose = 0

    while (true) {
        val word = animals.random().uppercase()
        val letters = word.toSet()
        val discovered = mutableSetOf<Char>()

        var lives = 6

        fun draw() {
            println("Hangman  [%s]".format((1..lives).joinToString(" ") { "♥" }))
            print(
                gallows.format(
                    if (lives < 6) "O" else " ",
                    if (lives < 4) "/" else " ",
                    if (lives < 5) "|" else " ",
                    if (lives < 3) "\\" else " ",
                    if (lives < 2) "/" else " ",
                    if (lives < 1) "\\" else " ",
                )
            )
            println(
                "        [ %s ]".format(
                    word.asSequence()
                        .map { letter -> if (discovered.contains(letter)) letter else "_" }
                        .joinToString(" ")
                )
            )
        }

        fun drawAnimation(iteration: Int) {
            val options = "/|\\"
            fun String.next() = run {
                val random = Random.nextInt(0, this.length)
                val index = (iteration + random) % this.length
                this[index]
            }
            println(
                gallows.format(
                    "O",
                    options.next(),
                    "|",
                    options.next(),
                    options.next(),
                    options.next(),
                )
            )
        }

        fun readLetter() = run {
            var character: Char?
            print("Digite uma letra (. para fechar): ")
            do {
                character = readln().toCharArray().firstOrNull()
            } while (character == null)
            character.uppercaseChar()
        }

        // ----------------------------------------------------------
        var running = true

        do {
            draw()
            val letter = readLetter()

            if (letter == '.') {
                running = false
                break
            } else if (letters.contains(letter)) {
                discovered.add(letter)
            } else {
                lives--
            }
        } while (lives > 0 && discovered.size != letters.size)

        if (!running) {
            println("Fechando jogo")
            println("Ganhou: $statsWon")
            println("Perdeu: $statsLose")
            break
        } else {
            draw()
            if (lives < 1) {
                repeat(50) {
                    drawAnimation(it)
                    Thread.sleep(100)
                }
                println("Você perdeu! A palavra era $word")
                statsLose++
                Thread.sleep(1000)
            } else {
                println("Você ganhou!")
                statsWon++
            }
        }
    }
}

fun main() {
    hangman()
}
