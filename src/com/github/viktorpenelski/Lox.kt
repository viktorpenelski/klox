package com.github.viktorpenelski

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

var hadError = false

fun main(args: Array<String>) {
    when {
        args.size > 1 -> {
            println("Usage: klox [script path]")
            System.exit(64)
        }
        args.size == 1 -> runFile(args[0])
        else -> runPrompt()
    }
}

private fun runPrompt() {
    val input = InputStreamReader(System.`in`)
    val reader = BufferedReader(input)
    while (true) {
        println("> ")
        run(reader.readLine())
        hadError = false
    }
}

private fun runFile(path: String) {
    val bytes = Files.readAllBytes(Paths.get(path))
    run(String(bytes, Charset.defaultCharset()))
    if (hadError) System.exit(65)
}

private fun run(source: String) {
    val scanner = Scanner(source)
    val tokens = scanner.scanTokens()

    for (token in tokens) {
        println(token)
    }
}

fun error(line: Int, msg: String) {
    report(line, "", msg)
}

fun report(line: Int, where: String, msg: String) {
    System.err.println("[line $line] Error $where: $msg")
    hadError = true
}
