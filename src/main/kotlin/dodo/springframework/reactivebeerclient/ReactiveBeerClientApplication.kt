package dodo.springframework.reactivebeerclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveBeerClientApplication

fun main(args: Array<String>) {
    runApplication<ReactiveBeerClientApplication>(*args)
}
