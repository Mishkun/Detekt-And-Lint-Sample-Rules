package io.github.mishkun.detektrules.rules

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtIfExpression

private const val DESCRIPTION = "Use when instead of multiline if dum dum"

class WhenInsteadOfIfRule(config: Config = Config.empty) : Rule(config) {
    override val issue: Issue = Issue(
        id = "WhenInsteadOfIf",
        severity = Severity.Style,
        description = DESCRIPTION,
        debt = Debt.FIVE_MINS
    )

    override fun visitIfExpression(expression: KtIfExpression) {
        super.visitIfExpression(expression)
        if (expression.`else` != null && expression.isMultiline()) {
            report(CodeSmell(issue, Entity.from(expression), DESCRIPTION))
        }
    }

    private fun KtIfExpression.isMultiline() = textContains('\n')
}
