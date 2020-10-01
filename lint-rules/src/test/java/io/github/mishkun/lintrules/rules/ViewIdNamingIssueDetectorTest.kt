package io.github.mishkun.lintrules.rules

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.detector.api.Issue
import io.kotest.core.spec.style.FreeSpec
import org.intellij.lang.annotations.Language


class ViewIdNamingIssueDetectorTest : FreeSpec({
    "should report nothing as v is correct naming" {
        @Language("XML")
        val fileContent = """
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/vLayout" >
        </LinearLayout>        
        """.trimIndent()

        fileContent.lintAsLayoutFile(ViewIdNamingIssue)
            .expectClean()
    }

    "should report nothing as vg is correct naming" {
        @Language("XML")
        val fileContent = """
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/vgLayout" >
        </LinearLayout>        
        """.trimIndent()

        fileContent.lintAsLayoutFile(ViewIdNamingIssue)
            .expectClean()
    }

    "should report incorrect id"{
        @Language("XML")
        val fileContent = """
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/layout" >
        </LinearLayout>        
        """.trimIndent()

        fileContent.lintAsLayoutFile(ViewIdNamingIssue)
            .expectWarningCount(1)
    }


    "should report incorrect id if names differ" {
        @Language("XML")
        val fileContent = """
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/flLayout" >
        </LinearLayout>        
        """.trimIndent()

        fileContent.lintAsLayoutFile(ViewIdNamingIssue)
            .expectWarningCount(1)
    }
})

fun String.lintAsLayoutFile(vararg issues: Issue): TestLintResult =
    lint().files(xml("res/layout/dummy_layout.xml", this))
        .allowDuplicates()
        .issues(*issues)
        .allowMissingSdk()
        .run()
