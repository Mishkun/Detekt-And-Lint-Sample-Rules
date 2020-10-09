package io.github.mishkun.lintrules.rules

import com.android.tools.lint.detector.api.*
import com.android.tools.lint.detector.api.Category.Companion.INTEROPERABILITY_KOTLIN
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import org.w3c.dom.Attr

val ViewIdNamingIssue = Issue.create(
    id = "ViewIdNaming",
    briefDescription = "View ids should start with abbreviated prefixes",
    explanation = """
        |To simplify work with kotlin synthetics, we use prefixes. They need to be
        |abbreviation of any of superclasses of the view
    """.trimMargin(),
    category = INTEROPERABILITY_KOTLIN,
    priority = 5,
    severity = Severity.WARNING,
    implementation = Implementation(ViewIdNamingIssueDetector::class.java, RESOURCE_FILE_SCOPE)
)

class ViewIdNamingIssueDetector : LayoutDetector() {

    private val validIdPrefixes = setOf("v", "vg")

    override fun getApplicableAttributes(): Collection<String>? = listOf("id")

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        if (getIdPrefix(attribute.value) !in validIdPrefixes) {
            context.report(
                issue = ViewIdNamingIssue,
                location = context.getLocation(attribute),
                message = "Incorrect view id prefix",
                quickfixData = buildFix(attribute)
            )
        }
    }

    private fun buildFix(attribute: Attr): LintFix {
        val id = getId(attribute.value)
        val prefixed = "v${id.capitalize()}"
        return fix().name("Add v prefix").replace().text(id).with(prefixed).build()
    }

    private fun getId(string: String) =string.takeLastWhile { it != '/' }

    private fun getIdPrefix(string: String) = getId(string)
        .takeWhile { it.isLowerCase() }
}
