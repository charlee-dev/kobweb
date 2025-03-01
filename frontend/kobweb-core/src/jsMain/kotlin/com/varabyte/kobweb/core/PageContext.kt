package com.varabyte.kobweb.core

import androidx.compose.runtime.*
import com.varabyte.kobweb.navigation.Route
import com.varabyte.kobweb.navigation.Router

/**
 * Various contextual information useful for a page.
 *
 * Access it using [rememberPageContext] either in the page itself or within any composable nested inside of it.
 *
 * ```
 * @Page
 * @Composable
 * fun SettingsPage() {
 *    val ctx = rememberPageContext()
 *    val userName = ctx.route.params["username"] ?: "Unknown user"
 *    ...
 * }
 */
class PageContext internal constructor(val router: Router) {
    internal val routeState: MutableState<RouteInfo?> = mutableStateOf(null)
    var route get() = routeState.value ?: error("PageContext route info is only valid within a @Page composable")
        internal set(value) { routeState.value = value }

    class RouteInfo internal constructor(private val route: Route, private val dynamicParams: Map<String, String>) {
        /**
         * The slug for the current page.
         *
         * In the URL: "https://example.com/a/b/c/slug?x=1&y=2#id", the slug is "slug"
         */
        val slug: String = route.path.substringAfterLast('/')

        /**
         * The current route path.
         *
         * This property is equivalent to `window.location.pathname` but provided here as a convenience property.
         */
        val path: String = route.path

        /**
         * Params extracted either from the URL's query parameters OR from a dynamic route
         *
         * For example:
         *
         * ```
         * /users/posts?user=123&post=11
         * ```
         *
         * and/or
         *
         * ```
         * /users/123/posts/11
         *
         * # for a URL registered as "/users/{user}/posts/{post}"
         * ```
         *
         * will generate a mapping of "user" to 123 and "post" to 11
         */
        val params: Map<String, String> = dynamicParams + route.queryParams

        /**
         * The post-hash fragment of a URL, if specified.
         *
         * For example, `/a/b/c/#fragment-id` will be the value `fragment-id`
         */
        val fragment: String? = route.fragment

        override fun toString() = route.toString()

        override fun equals(other: Any?): Boolean {
            return (other is RouteInfo
                    && other.path == path
                    && other.params == params
                    && other.fragment == fragment
                    )
        }

        override fun hashCode(): Int {
            var result = path.hashCode()
            result = 31 * result + params.hashCode()
            result = 31 * result + fragment.hashCode()
            return result
        }

        fun copy(path: String = route.path, queryParams: Map<String, String> = route.queryParams, fragment: String? = route.fragment, dynamicParams: Map<String, String> = this.dynamicParams) =
            RouteInfo(Route(path, queryParams, fragment), dynamicParams)
    }

    companion object {
        internal lateinit var instance: PageContext
        internal fun init(router: Router) { instance = PageContext(router) }
    }

    // region deprecated route properties

    // We moved the following properties under the `route` property to limit unnecessary recompositions for users who
    // just want to access the router property.

    @Deprecated("Use `route.slug` instead", ReplaceWith("route.slug"))
    val slug: String get() = route.slug

    @Deprecated("Use `route.params` instead", ReplaceWith("route.params"))
    val params: Map<String, String> get() = route.params

    @Deprecated("Use `route.fragment` instead", ReplaceWith("route.params"))
    val fragment: String? get() = route.fragment

    // endregion
}

/**
 * A property which indicates if this current page is being rendered as part of a Kobweb export.
 *
 * While it should be rare that you'll need to use it, it can be useful to check if you want to avoid doing some
 * side-effect that shouldn't happen at export time, like sending page visit analytics to a server for example.
 */
val PageContext.isExporting: Boolean get() = route.params.containsKey("_kobwebIsExporting")

// Note: PageContext is technically a global, but we wrap it in a `PageContextLocal` as a way to ensure it is only
// accessible when under a `@Page` composable.
internal val PageContextLocal = staticCompositionLocalOf<PageContext?> { null }

/**
 * Returns the active page's context.
 *
 * This will throw an exception if not called within the scope of a `@Page` annotated composable.
 */
@Composable
// Note: Technically this isn't a real "remember", as the page context is really just a composition local, but we leave
// the API like this because user's mental model should think of it like a normal remember call. After all, they
// shouldn't wrap the return value in a remember themselves. It's possible we may revisit this approach in the future,
// as well.
fun rememberPageContext() = PageContextLocal.current ?: error("PageContext is only valid within a @Page composable")