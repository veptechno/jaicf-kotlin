package com.justai.jaicf.builder.theme.demo

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.builder.ActivationRulesBuilder
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.generic.ActivatorTypeToken
import com.justai.jaicf.generic.ChannelTypeToken
import com.justai.jaicf.generic.ContextTypeToken
import com.justai.jaicf.hook.BotHook
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.model.state.StatePath
import com.justai.jaicf.model.transition.Transition
import com.justai.jaicf.reactions.Reactions

@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@DslMarker
annotation class ScenarioDsl

infix fun Theme.append(other: Theme): Theme = TODO()

fun <B: BotRequest, R: Reactions> Theme(
    name: String,
    channelToken: ChannelTypeToken<B, R>,
    body: ThemeBuilder<B, R>.() -> Unit
): Theme = TODO()

fun Theme(
    name: String,
    body: ThemeBuilder<BotRequest, Reactions>.() -> Unit
): Theme = TODO()

/*
 * Old Scenario class is now replaced by this Theme class
 * All scenario logic is now here
 * This class is supposed to be sent to BotEngine
 */
class Theme {
    val model: ScenarioModel by lazy<ScenarioModel> { TODO() }
}

@ScenarioDsl
open class NodeBuilder<B: BotRequest, R: Reactions>(
    private val channelToken: ChannelTypeToken<B, R>
) {

    @ScenarioDsl
    fun theme(
        name: String,
        modal: Boolean = false,
        propagateHooks: Boolean = true,
        body: ThemeBuilder<B, R>.() -> Unit
    ) {}

    @ScenarioDsl
    fun state(
        name: String,
        noContext: Boolean = false,
        modal: Boolean = false,
        body: StateBuilder<B, R>.() -> Unit
    ) {}

    @ScenarioDsl
    fun append(
        theme: Theme,
        modal: Boolean = false,
        propagateHooks: Boolean = true
    ) {}

    @ScenarioDsl
    fun fallback(
        name: String = "fallback",
        body: ActionContext<ActivatorContext, B, R>.() -> Unit
    ) {}

    @ScenarioDsl
    fun <B1 : B, R1 : R> fallback(
        channelToken: ChannelTypeToken<B1, R1>,
        name: String = "fallback",
        body: ActionContext<ActivatorContext, B1, R1>.() -> Unit
    ) {}
}

class ThemeBuilder<B: BotRequest, R: Reactions>(
    private val channelToken: ChannelTypeToken<B, R>
) : NodeBuilder<B, R>(channelToken) {

    @ScenarioDsl
    inline fun <reified T: BotHook> handle(listener: @ScenarioDsl T.() -> Unit) {}
}

class StateBuilder<B: BotRequest, R: Reactions>(
    private val channelToken: ChannelTypeToken<B, R>
) : NodeBuilder<B, R>(channelToken) {

    fun activators(from: String = "%parent%", body: @ScenarioDsl ActivationRulesBuilder.() -> Unit) {}

    fun globalActivators(body: @ScenarioDsl ActivationRulesBuilder.() -> Unit) {}

    fun action(body: @ScenarioDsl ActionContext<ActivatorContext, B, R>.() -> Unit) {}

    fun <A1 : ActivatorContext> action(
        activatorToken: ActivatorTypeToken<A1>,
        body: @ScenarioDsl ActionContext<A1, B, R>.() -> Unit
    ) {}

    fun <B1 : B, R1 : R> action(
        channelToken: ChannelTypeToken<B1, R1>,
        body: @ScenarioDsl ActionContext<ActivatorContext, B1, R1>.() -> Unit
    ) {}

    fun <A1 : ActivatorContext, B1 : B, R1 : R> action(
        contextToken: ContextTypeToken<A1, B1, R1>,
        body: @ScenarioDsl ActionContext<A1, B1, R1>.() -> Unit
    ) {}
}
