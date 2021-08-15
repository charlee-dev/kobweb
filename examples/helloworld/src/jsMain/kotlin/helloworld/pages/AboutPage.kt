package helloworld.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.Text
import helloworld.components.layouts.PageLayout
import org.jetbrains.compose.web.dom.P

class AboutPage : Page {
    @Composable
    override fun render() {
        PageLayout("ABOUT") {
            Text("This is a skeleton app used to showcase a basic site made using Kobweb")
            P()
            Link("/", "Go Home")
        }
    }
}