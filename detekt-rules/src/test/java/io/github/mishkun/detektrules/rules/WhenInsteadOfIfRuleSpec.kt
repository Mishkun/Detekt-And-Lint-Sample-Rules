package io.github.mishkun.detektrules.rules

import io.gitlab.arturbosch.detekt.test.lint
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.intellij.lang.annotations.Language

class WhenInsteadOfIfRuleSpec : FreeSpec({
    val rule = WhenInsteadOfIfRule()
    "should not report when" {
        @Language("kt")
        val source = """
            fun main() {
                when(2+2) {
                  4 -> print("hey")
                  else -> print("meh")
                }
            }
        """.trimIndent()

        rule.lint(source).shouldBeEmpty()
    }

    "should not report single line if" {
        @Language("kt")
        val source = """
            fun main() {
                if (2+2 == 4)  print("hey") else print("meh")
            }
        """.trimIndent()

        rule.lint(source).shouldBeEmpty()
    }

    "should not report single line if with braces" {
        @Language("kt")
        val source = """
            fun main() {
                if (2+2 == 4) { print("hey")} else {print("meh")}
            }
        """.trimIndent()

        rule.lint(source).shouldBeEmpty()
    }

    "should report multiline if" {
        @Language("kt")
        val source = """
            fun main() {
                if (2+2 == 4) { 
                    print("hey")
                } else {
                    print("meh")
                }
            }
        """.trimIndent()

        val findings = rule.lint(source)
        findings.size shouldBe 1
    }


    "should not report multiline if without else clause" {
        @Language("kt")
        val source = """
            fun main() {
                if (2+2 == 4) { 
                    print("hey")
                }
            }
        """.trimIndent()

        rule.lint(source).shouldBeEmpty()
    }
})
