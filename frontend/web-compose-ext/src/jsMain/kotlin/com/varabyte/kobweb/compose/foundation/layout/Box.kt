package com.varabyte.kobweb.compose.foundation.layout

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.*
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.alignSelf
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.gridTemplateRows
import org.jetbrains.compose.web.css.justifyItems
import org.jetbrains.compose.web.css.justifySelf
import org.jetbrains.compose.web.css.value
import org.jetbrains.compose.web.dom.Div

class BoxScope {
    fun Modifier.align(alignment: Alignment) = styleModifier {
        when (alignment) {
            // justify in grid means "row" while align means "col"
            Alignment.TopStart -> {
                alignSelf(AlignSelf.Start)
                justifySelf(AlignSelf.Start.value)
            }
            Alignment.TopCenter -> {
                alignSelf(AlignSelf.Start)
                justifySelf(AlignSelf.Center.value)
            }
            Alignment.TopEnd -> {
                alignSelf(AlignSelf.Start)
                justifySelf(AlignSelf.End.value)
            }
            Alignment.CenterStart -> {
                alignSelf(AlignSelf.Center)
                justifySelf(AlignSelf.Start.value)
            }
            Alignment.Center -> {
                alignSelf(AlignSelf.Center)
                justifySelf(AlignSelf.Center.value)
            }
            Alignment.CenterEnd -> {
                justifySelf(AlignSelf.End.value)
                alignSelf(AlignSelf.Center)
            }
            Alignment.BottomStart -> {
                justifySelf(AlignSelf.Start.value)
                alignSelf(AlignSelf.End)
            }
            Alignment.BottomCenter -> {
                justifySelf(AlignSelf.Center.value)
                alignSelf(AlignSelf.End)
            }
            Alignment.BottomEnd -> {
                justifySelf(AlignSelf.End.value)
                alignSelf(AlignSelf.End)
            }
        }
    }
}

@Composable
fun Box(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Div(attrs = modifier.asAttributeBuilder {
        style {
            // The Compose "Box" concept means: all children should be stacked one of top of the other. We do this by
            // setting the current element to grid but then jam all of its children into its top-left (and only) cell.
            display(DisplayStyle.Grid)
            gridTemplateColumns("1fr")
            gridTemplateRows("1fr")

            when (contentAlignment) {
                // justify in grid means "row" while align means "col"
                Alignment.TopStart -> {
                    alignItems(AlignItems.Start)
                    justifyItems(AlignItems.Start.value)
                }
                Alignment.TopCenter -> {
                    alignItems(AlignItems.Start)
                    justifyItems(AlignItems.Center.value)
                }
                Alignment.TopEnd -> {
                    alignItems(AlignItems.Start)
                    justifyItems(AlignItems.End.value)
                }
                Alignment.CenterStart -> {
                    alignItems(AlignItems.Center)
                    justifyItems(AlignItems.Start.value)
                }
                Alignment.Center -> {
                    alignItems(AlignItems.Center)
                    justifyItems(AlignItems.Center.value)
                }
                Alignment.CenterEnd -> {
                    justifyItems(AlignItems.End.value)
                    alignItems(AlignItems.Center)
                }
                Alignment.BottomStart -> {
                    justifyItems(AlignItems.Start.value)
                    alignItems(AlignItems.End)
                }
                Alignment.BottomCenter -> {
                    justifyItems(AlignItems.Center.value)
                    alignItems(AlignItems.End)
                }
                Alignment.BottomEnd -> {
                    justifyItems(AlignItems.End.value)
                    alignItems(AlignItems.End)
                }
            }
        }
    }) {
        BoxScope().content()
    }
}