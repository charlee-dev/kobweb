package com.varabyte.kobweb.gradle.core.project.frontend

import kotlinx.serialization.Serializable

/**
 * Metadata about code like `val Bounce = Keyframes("bounce")` or `val Bounce by keyframes`
 */
@Serializable
class KeyframesEntry(
    val fqcn: String,
)
