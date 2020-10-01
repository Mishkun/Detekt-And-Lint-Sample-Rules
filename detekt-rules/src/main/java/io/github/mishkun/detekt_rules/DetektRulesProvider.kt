package io.github.mishkun.detekt_rules

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class DetektRulesProvider : RuleSetProvider {
    override val ruleSetId: String = "my_awesome_rules"

    override fun instance(config: Config) = RuleSet(
        ruleSetId,
        listOf()
    )
}
