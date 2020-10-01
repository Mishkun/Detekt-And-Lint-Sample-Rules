package io.github.mishkun.lintrules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue
import io.github.mishkun.lintrules.rules.ViewIdNamingIssue

class LintRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(ViewIdNamingIssue)
    override val api: Int = com.android.tools.lint.detector.api.CURRENT_API
}
