package com.varabyte.kobweb.compose.css

import com.varabyte.kobweb.compose.css.functions.CSSUrl
import com.varabyte.kobweb.compose.css.functions.Gradient
import org.jetbrains.compose.web.css.StylePropertyValue
import org.jetbrains.compose.web.css.StyleScope

// CSS text content should always be surrounded by quotes, but this is a pretty subtle requirement that's easy to miss
// and causes silent failures. The person is passing in a String so their intention is clear. Let's just quote it
// for them if they don't have it!
private fun String.wrapQuotesIfNecessary() = if (this.length >= 2 && this.first() == '"' && this.last() == '"') {
    this
} else {
    "\"${this.replace("\"", "\\\"")}\""
}

// See: https://developer.mozilla.org/en-US/docs/Web/CSS/content
sealed class Content(private val value: String): StylePropertyValue {
    override fun toString() = value

    /** Content keywords that cannot be used in combination with any others. */
    sealed class Restricted(value: String): Content(value)

    /** Content keywords that can be used in combination with others. */
    sealed class Unrestricted(value: String): Content(value)

    private class Keyword(value: String): Unrestricted(value)
    private class RestrictedKeyword(value: String): Restricted(value)
    private class Text(value: String): Unrestricted(value.wrapQuotesIfNecessary())

    private class Url(url: CSSUrl) : Unrestricted(url.toString())
    private class Gradient(gradient: com.varabyte.kobweb.compose.css.functions.Gradient) :
        Unrestricted(gradient.toString())

    companion object {
        fun of(url: CSSUrl): Content.Unrestricted = Url(url)
        fun of(gradient: com.varabyte.kobweb.compose.css.functions.Gradient): Content.Unrestricted = Gradient(gradient)
        fun of(text: String): Content.Unrestricted = Text(text)

        // Non-combinable keywords
        val None get(): Content.Restricted = RestrictedKeyword("none")
        val Normal get(): Content.Restricted = RestrictedKeyword("normal")

        // Language / position-dependent keywords
        val CloseQuote get(): Content = Keyword("close-quote")
        val NoCloseQuote get(): Content = Keyword("no-close-quote")
        val NoOpenQuote get(): Content = Keyword("no-open-quote")
        val OpenQuote get(): Content = Keyword("open-quote")

        // Global
        val Inherit get(): Content = Keyword("inherit")
        val Initial get(): Content = Keyword("initial")
        val Revert get(): Content = Keyword("revert")
        val Unset get(): Content = Keyword("unset")
    }
}

fun StyleScope.content(content: Content.Restricted) {
    property("content", content)
}

fun StyleScope.content(vararg contents: Content.Unrestricted) {
    property("content", contents.joinToString(" "))
}

fun StyleScope.content(altText: String, vararg contents: Content.Unrestricted) {
    property("content", "${contents.joinToString(" ")} / ${altText.wrapQuotesIfNecessary()}")
}

/** Convenience function for an extremely common case, setting content to text. */
fun StyleScope.content(value: String) {
    content(Content.of(value))
}

fun CSSUrl.toContent() = Content.of(this)
fun Gradient.toContent() = Content.of(this)