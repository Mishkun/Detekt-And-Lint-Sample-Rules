package io.github.mishkun.lintrules.rules

import com.android.tools.lint.detector.api.Category.Companion.INTEROPERABILITY_KOTLIN
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
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
                message = "Incorrect view id prefix"
            )
        }
    }

    private fun getIdPrefix(string: String) = string.takeLastWhile { it != '/' }
        .takeWhile { it.isLowerCase() }
}
