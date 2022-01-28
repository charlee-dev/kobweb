package com.varabyte.kobweb.silk.components.forms

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.UserSelect
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.style.*
import com.varabyte.kobweb.silk.theme.shapes.Rect
import com.varabyte.kobweb.silk.theme.shapes.clip
import com.varabyte.kobweb.silk.theme.toSilkPalette
import org.jetbrains.compose.web.css.px

enum class Button {
    LEFT,
    MIDDLE,
    RIGHT;

    companion object {
        fun fromValue(value: Short): Button {
            return when (value) {
                Buttons.LEFT -> LEFT
                Buttons.MIDDLE -> MIDDLE
                Buttons.RIGHT -> RIGHT
                else -> LEFT
            }
        }
    }
}

class ButtonEvent(val button: Button)

object Buttons {
    const val LEFT = 0.toShort()
    const val MIDDLE = 1.toShort()
    const val RIGHT = 2.toShort()
}

val ButtonStyle = ComponentStyle("silk-button") {
    val buttonColors = colorMode.toSilkPalette().button

    base {
        Modifier
            .backgroundColor(buttonColors.default)
            .clip(Rect(4.px))
            // No selecting text within buttons
            .userSelect(UserSelect.None)
            .role("button")
    }

    hover {
        Modifier
            .backgroundColor(buttonColors.hover)
            .cursor(Cursor.Pointer)
    }

    active {
        Modifier.backgroundColor(buttonColors.pressed)
    }
    focus {
        Modifier.backgroundColor(buttonColors.hover)
    }
}

/**
 * An area which provides a SilkTheme-aware background color.
 */
@Composable
fun Button(
    onClick: (ButtonEvent) -> Unit,
    modifier: Modifier = Modifier,
    variant: ComponentVariant? = null,
    content: @Composable () -> Unit
) {
    Box(
        ButtonStyle.toModifier(variant)
            .then(modifier)
            .onClick { evt ->
                onClick(ButtonEvent(Button.fromValue(evt.button)))
                // Blur is a bad name - it means, here, remove focus
                // https://developer.mozilla.org/en-US/docs/Web/API/HTMLElement/blur
                js("document.activeElement.blur()")
                evt.preventDefault()
            }
            .tabIndex(0) // Allow button to be tabbed to
            .onKeyDown { evt ->
                if (evt.isComposing) return@onKeyDown
                if (evt.key == "Enter") {
                    onClick(ButtonEvent(Button.LEFT))
                    evt.preventDefault()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}