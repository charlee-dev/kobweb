![version: 0.13.2](https://img.shields.io/badge/kobweb-0.13.2-blue)
![version: 0.9.12](https://img.shields.io/badge/kobweb_cli-0.9.12-blue)
<br>
![kotlin: 1.8.20](https://img.shields.io/badge/kotlin-1.8.20-blue?logo=kotlin)
![compose: 1.4.0](https://img.shields.io/badge/compose-1.4.0-blue?logo=jetpackcompose)
![ktor: 2.2.2](https://img.shields.io/badge/ktor-2.2.2-blue)
<br>
<a href="https://discord.gg/5NZ2GKV5Cs">
<img alt="Varabyte Discord" src="https://img.shields.io/discord/886036660767305799.svg?label=&logo=discord&logoColor=ffffff&color=7389D8&labelColor=6A7EC2" />
</a>
[![Mastodon Follow](https://img.shields.io/mastodon/follow/109382855401210782?domain=https%3A%2F%2Ffosstodon.org&style=social)](https://fosstodon.org/@bitspittle)

# K🕸️bweb

Kobweb is an opinionated Kotlin framework for creating websites and web apps, built on top of
[Compose HTML](https://github.com/JetBrains/compose-multiplatform#compose-html) and inspired by [Next.js](https://nextjs.org)
and [Chakra UI](https://chakra-ui.com).

```kotlin
@Page
@Composable
fun HomePage() {
  Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
    Row(Modifier.align(Alignment.End)) {
      var colorMode by rememberColorMode()
      Button(
        onClick = { colorMode = colorMode.opposite() },
        Modifier.borderRadius(50.percent).padding(0.px)
      ) {
        Box(Modifier.margin(7.px)) {
          // Includes support for Font Awesome icons
          if (colorMode.isLight()) FaSun() else FaMoon()
        }
      }
    }
    H1 {
      Text("Welcome to Kobweb!")
    }
    Row(Modifier.flexWrap(FlexWrap.Wrap)) {
      SpanText("Create rich, dynamic web apps with ease, leveraging ")
      Link("https://kotlinlang.org/", "Kotlin")
      SpanText(" and ")
      Link("https://github.com/JetBrains/compose-multiplatform#compose-html/", "Compose HTML")
    }
  }
}
```

<p align="center">
<img src="https://github.com/varabyte/media/raw/main/kobweb/screencasts/kobweb-welcome.gif" />
</p>

---

**Kobweb is still only publishing pre-release versions, but it's been usable for a while now, and it is getting close to
stabilization**. Please consider starring the project to indicate interest, so we know we're creating something the
community wants.
[How ready is it? ▼](#can-we-kobweb-yet)

Our goal is to provide:

* an intuitive structure for organizing your Kotlin website or web app
* automatic handling of routing between pages
* a collection of useful _batteries included_ widgets built on top of Compose HTML
* an environment built from the ground up around live reloading
* static site exports for improved SEO and potentially cheaper server setups
* support for responsive (i.e. mobile and desktop) design
* shared, rich types between client and server
* out-of-the-box Markdown support
* a way to easily define server API routes
* an open source foundation that the community can extend
* and much, much more!

| 📚🧑‍🎓 One of Kobweb's users, Stevdza-San, [is offering a course on Compose HTML using Kobweb](https://stevdza-san.com/p/build-a-complete-website-with-kotlin-and-jetpack-compose). Consider checking it out! |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

Here's a demo where we create a Compose HTML project from scratch with Markdown support and live reloading, in under
10 seconds:

https://user-images.githubusercontent.com/43705986/135570277-2d67033a-f647-4b04-aac0-88f8992145ef.mp4

# Trying it out yourself

The first step is to get the Kobweb binary. You can install it, download it, and/or build it, so we'll include
instructions for all these approaches.

## Install the Kobweb binary

*Major thanks to [aalmiray](https://github.com/aalmiray) and [helpermethod](https://github.com/helpermethod) to helping
me get these installation options working. Check out [JReleaser](https://github.com/jreleaser/jreleaser) if you ever
need to do this in your own project!*

### [Homebrew](https://brew.sh/)

*OS: Mac and Linux*

```bash
$ brew install varabyte/tap/kobweb
```

### [Scoop](https://scoop.sh/)

*OS: Windows*

```shell
# Note: Adding buckets only has to be done once.

# Feel free to skip java if you already have it
> scoop bucket add java
> scoop install java/openjdk

# Install kobweb
> scoop bucket add varabyte https://github.com/varabyte/scoop-varabyte.git
> scoop install varabyte/kobweb
```

### [SDKMAN!](https://sdkman.io/)

*OS: Windows, Mac, and \*nix*

```shell
$ sdk install kobweb
```

### Arch Linux

*Thanks a ton to [aksh1618](https://github.com/aksh1618) for adding support for this target!*

With an AUR helper:

```shell
$ trizen -S kobweb
```

Without an AUR helper:

```shell
$ git clone https://aur.archlinux.org/kobweb.git
$ cd kobweb
$ makepkg -si
```

### Don't see your favorite package manager?

Please see: https://github.com/varabyte/kobweb/issues/117 and consider leaving a comment!

## Download the Kobweb binary

Our binary artifact is hosted on GitHub. To download latest, you can either
[grab the zip or tar file from the GitHub](https://github.com/varabyte/kobweb-cli/releases/tag/v0.9.12) or you can fetch
it from your terminal:

```bash
$ cd /path/to/applications

# You can either pull down the zip file

$ wget https://github.com/varabyte/kobweb-cli/releases/download/v0.9.12/kobweb-0.9.12.zip
$ unzip kobweb-0.9.12.zip

# ... or the tar file

$ wget https://github.com/varabyte/kobweb-cli/releases/download/v0.9.12/kobweb-0.9.12.tar
$ tar -xvf kobweb-0.9.12.tar
```

and I recommend adding it to your path, either directly:

```bash
$ PATH=$PATH:/path/to/applications/kobweb-0.9.12/bin
$ kobweb version # to check it's working
```

or via symbolic link:

```bash
$ cd /path/to/bin # some folder you've created that's in your PATH
$ ln -s /path/to/applications/kobweb-0.9.12/bin/kobweb kobweb
```

## Build the Kobweb binary

Although we host Kobweb artifacts on GitHub, it's easy enough to build your own.

Building Kobweb requires JDK11 or newer. We'll first discuss how to add it.

### Download a JDK

If you want full control over your JDK install, manually downloading is a good option.

* [Download a JDK for your OS](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html)
* Unzip it somewhere
* Update your `JAVA_HOME` variable to point at it.

```bash
JAVA_HOME=/path/to/jdks/corretto-11.0.12
# ... or whatever version or path you chose
```

### Install a JDK with the IntelliJ IDE

For a more automated approach, you can request IntelliJ install a JDK for you.

Follow their instructions here: https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk

### Building the Kobweb CLI

The Kobweb CLI is actually maintained in a separate GitHub repo. Once you have the JDK set up, it should be easy to
clone and build it:

```bash
$ cd /path/to/src/root # some folder you've created for storing src code
$ git clone https://github.com/varabyte/kobweb-cli
$ cd kobweb-cli
$ ./gradlew :kobweb:installDist
```

Finally, update your PATH:

```bash
$ PATH=$PATH:/path/to/src/root/kobweb-cli/kobweb/build/install/kobweb/bin
$ kobweb version # to check it's working
```

## Update the Kobweb binary

If you previously installed Kobweb and are aware that a new version is available, the way you update it depends on how
you installed it.

| Method                    | Instructions                                                                                                                         |
|---------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| Homebrew                  | `brew upgrade kobweb`                                                                                                                |
| Scoop                     | `scoop update kobweb`                                                                                                                |
| SDKMAN!                   | `sdk upgrade kobweb`                                                                                                                 |
| Downloaded from<br>Github | Visit the [latest release](https://github.com/varabyte/kobweb-cli/releases/tag/v0.9.12). You can find both a zip and tar file there. |

## Create your Kobweb site

```bash
$ cd /path/to/projects/
$ kobweb create app
```

You'll be asked a few questions required for setting up your project.

You don't need to create a root folder for your project ahead of time - the setup process will prompt you for one to
create.

When finished, you'll have a basic project with three pages - a home page, an about page, and a markdown page - and some
components (which are collections of reusable, composable pieces). Your own directory structure should look something
like:

```
my-project
└── site/src/jsMain
    ├── kotlin.org.example.myproject
    │   ├── components
    │   │   ├── layouts
    │   │   │   └── PageLayout.kt
    │   │   ├── sections
    │   │   │   └── NavHeader.kt
    │   │   └── widgets
    │   │       └── GoHomeLink.kt
    │   ├── pages
    │   │   ├── About.kt
    │   │   └── Index.kt
    │   └── MyApp.kt
    └── resources/markdown
        └── Markdown.md
```

Note that there's no index.html or routing logic anywhere! We generate that for you automatically when you run Kobweb.
Which brings us to the next section...

## Run your Kobweb site

```bash
$ cd /path/to/projects/your-project/site
$ kobweb run
```

This command spins up a webserver at http://localhost:8080. If you want to configure the port, you can do so by editing
your project's `.kobweb/conf.yaml` file.

You can open your project in IntelliJ and start editing it. While Kobweb is running, it will detect changes, recompile,
and deploy updates to your site automatically.

### Using IntelliJ

If you don't want to keep a separate terminal window open beside your IDE window, you may prefer alternate solutions.

#### Terminal tool window

Use the [IntelliJ terminal tool window](https://www.jetbrains.com/help/idea/terminal-emulator.html).

You can run `kobweb` within it, and if you run into a compile error, the stack trace lines will get decorated with
links, making it easy to navigate to the relevant source.

#### Gradle commands

Run gradle commands directly. `kobweb` itself delegates to Gradle, but nothing is stopping you from calling the commands
yourself.

To start a Kobweb server, execute the `kobwebStart -t` command, and to stop it later, use the `kobwebStop` command. The
`-t` argument (or, `--continuous`) tells Gradle to watch for file changes, which gives you live loading behavior.

You can read all about [IntelliJ's Gradle integration here](https://www.jetbrains.com/help/idea/gradle.html), but in
general, you should create two run configurations: one for `kobwebStart -t` and one for `kobwebStop`. To do this, start
from [these instructions](https://www.jetbrains.com/help/idea/run-debug-gradle.html).

*ASIDE: If you ever want to export your site via Gradle, it's a bit more complicated but the command is<br>
`kobwebExport -PkobwebReuseServer=false -PkobwebEnv=DEV -PkobwebRunLayout=KOBWEB -PkobwebBuildTarget=RELEASE`. If you
want to export a static layout, also add `-PkobwebExportLayout=STATIC`.*

## Running examples

Kobweb will provide a growing collection of samples for you to learn from. To see what's available, run:

```bash
$ kobweb list

You can create the following Kobweb projects by typing `kobweb create ...`

• app: A template for a minimal site that demonstrates the basic features of Kobweb
• examples/jb/counter: A very minimal site with just a counter (based on the Jetbrains tutorial)
• examples/todo: An example TODO app, showcasing client / server interactions
```

For example, `kobweb create examples/todo` will instantiate a TODO app locally.

# Beginner topics

Kobweb, at its core, is a handful of classes responsible for trimming away much of the boilerplate around building a
Compose HTML app, such as routing and configuring basic CSS styles.

Kobweb is also a CLI binary of the same name which provides commands to handle the tedious parts of building and / or
running a Compose HTML app. We want to get that stuff out of the way, so you can enjoy focusing on the more
interesting work!

## Create a page

Creating a page is easy! It's just a normal `@Composable` method. To upgrade your composable to a page, all you need to
do is:

1. Define your composable in a file somewhere under the `pages` package in your `jsMain` source directory.
1. Annotate it with `@Page`

Just from that, Kobweb will create a site entry for you automatically.

For example, if I create the following file:

```kotlin
// jsMain/kotlin/com/example/mysite/pages/admin/Settings.kt

@Page
@Composable
fun SettingsPage() {
    /* ... */
}
```

this will create a page that I can then visit by going to `mysite.com/admin/settings`.

**Note:** The last part of a URL, here `settings`, is called a *slug*.

By default, the slug comes from the file name, but this behavior can be overridden (more on that shortly).

The file name `Index.kt` is special. If a page is defined inside such a file, it will be treated as the default page
under that URL. For example, a page defined in `.../pages/admin/Index.kt` will be visited if the user visits
`mysite.com/admin/`.

### Route Override

If you ever need to change the route generated for a page, you can set the `Page` annotation's `routeOverride` field:

```kotlin
// jsMain/kotlin/com/example/mysite/pages/admin/Settings.kt

@Page(routeOverride = "config")
@Composable
fun SettingsPage() {
    /* ... */
}
```

The above would create a page you could visit by going to `mysite.com/admin/config`.

`routeOverride` can additionally contain slashes, and if the value begins and/or ends with a slash, that has a special
meaning.

* Begins with a slash - represent the whole route from the root
* Ends with a slash - a slug will still be generated from the filename and appended to the route.

And if you set the override to "index", that behaves the same as setting the file to `Index.kt` as described above.

Some examples can clarify these rules (and how they behave when combined). Assuming we're defining a page for our site
`example.com` within the file `a/b/c/Slug.kt`:

| Annotation              | Resulting URL                   |
|-------------------------|---------------------------------|
| `@Page`                 | `example.com/a/b/c/slug`        |
| `@Page("other")`        | `example.com/a/b/c/other`       |
| `@Page("index")`        | `example.com/a/b/c/`            |
| `@Page("d/e/f/")`       | `example.com/a/b/c/d/e/f/slug`  |
| `@Page("d/e/f/other")`  | `example.com/a/b/c/d/e/f/other` |
| `@Page("/d/e/f/")`      | `example.com/d/e/f/slug`        |
| `@Page("/d/e/f/other")` | `example.com/d/e/f/other`       |
| `@Page("/")`            | `example.com/slug`              |
| `@Page("/other")`       | `example.com/other`             |

⚠️ We close this section with a warning - despite the flexibility allowed here, you should not be using this feature
frequently, if at all. A Kobweb project benefits from the fact that a user can easily associate a URL on your site with
a file in your codebase, but this feature allows you to break those assumptions. It is mainly provided to enable
dynamic routing (see the section below) or enabling a URL name that uses characters which aren't allowed in Kotlin
filenames.

### PackageMapping

If you don't want to change your slug but you *do* want to change a part of the route, you don't have to use a `Page`
annotation for this. You can instead register a package mapping with a `PackageMapping` file annotation. Doing so looks
like this:

```kotlin
// site/pages/blog/_2022/PackageMapping.kt
@file:PackageMapping("2022")

package site.pages.blog._2022

import com.varabyte.kobweb.core.PackageMapping
```

As with the `Page` route overrides, the main reason you'd want to do this is that Java / Kotlin package naming
requirements are much stricter than what you might want to allow in a URL part. `site.com/blog/2022/mypost` reads way
better than `site.com/blog/_2022/mypost`.

### Page context

Every page method provides access to its `PageContext` via the `rememberPageContext()` method.

A page's context provides it access to a router, allowing you to navigate to other pages, as well as other dynamic
information about the current page's URL (discussed in the following sections).

```kotlin
@Page
@Composable
fun ExamplePage() {
    val ctx = rememberPageContext()
    Button(onClick = { ctx.router.navigateTo("/other/page") }) {
        Text("Click me")
    }
}
```

### Query parameters

You can use the page context to check the values of any query parameters passed into the current page's URL.

So if you visit `site.com/posts?id=12345&mode=edit`, you can query those values like so:

```kotlin
@Page
@Composable
fun Posts() {
    val ctx = rememberPageContext()
    // Here, I'm assuming these params are always present, but you can
    // use `get` instead of `getValue` to handle the nullable case.
    val postId = ctx.route.params.getValue("id").toInt()
    val mode = EditMode.from(ctx.route.params.getValue("mode"))
    /* ... */
}
```

### Dynamic routes

In addition to query parameters, Kobweb supports embedding arguments directly in the URL itself. For example, you might
want to register the path `users/{user}/posts/{post}` which would be visited if the site visitor typed in a URL like
`users/bitspittle/posts/20211231103156`.

How do we set it up? Thankfully, it's fairly easy.

But first, notice that in the example dynamic route `users/{user}/posts/{post}` there are actually two different dynamic
parts, one in the middle and one at the tail end. These can be handled by the `PackageMapping` and `Page` annotations,
respectively.

#### PackageMapping

Pay attention to the use of the curly braces in the mapping name! That lets Kobweb know that this is a dynamic package.

```kotlin
// pages/users/user/PackageMapping.kt
@file:PackageMapping("{user}") // or @file:PackageMapping("{}")

package site.pages.users.user

import com.varabyte.kobweb.core.PackageMapping
```

If you pass an empty `"{}"` into the `PackageMapping` annotation, it directs Kobweb to use the name of the package
itself (i.e. `user` in this specific case).

#### Page

Like `PackageMapping`, the `Page` annotation can also take curly braces to indicate a dynamic value.

```kotlin
// pages/users/user/posts/Post.kt

@Page("{post}") // Or @Page("{}")
@Composable
fun PostPage() {
   /* ... */
}
```

An empty `"{}"` tells Kobweb to use the name of the current file.

Remember that the `Page` annotation allows you to rewrite the entire route. That value also accepts dynamic parts, so
you could even do something like:

```kotlin
// pages/users/user/posts/Post.kt

@Page("/users/{user}/posts/{post}") // Or @Page("/users/{user}/posts/{}")
@Composable
fun PostPage() {
    /* ... */
}
```

but with great power comes great responsibility. Tricks like this may be hard to find and/or update later, especially as
your project gets larger. While it works, you should only use this format in cases where you absolutely need to (perhaps
after a code refactor where you have to support legacy URL paths).

#### Querying dynamic route values

You query dynamic route values exactly the same as if you were requesting query parameters. That is, use `ctx.params`:

```kotlin
@Page("{}")
@Composable
fun PostPage() {
    val ctx = rememberPageContext()
    val postId = ctx.route.params.getValue("post")
    /* ... */
}
```

**Note:** You should avoid creating URL paths where the dynamic path and the query parameters have the same name, as in
`mysite.com/posts/{post}?post=...`, as there are no guarantees about which value will win out. Either way, one will be
overwritten by the other.

## Silk

Silk is a UI layer included with Kobweb and built upon Compose HTML. (To learn more about Compose HTML, please
visit [the official tutorials](https://github.com/JetBrains/compose-jb/tree/master/tutorials/HTML/Getting_Started)).

While Compose HTML requires you to understand underlying HTML / CSS concepts, Silk attempts to abstract some of that
away, providing an API more akin to what you might experience developing a Compose app on Android or Desktop. Less
"div, span, flexbox, attrs, styles, classes" and more "Rows, Columns, Boxes, and Modifiers".

We consider Silk a pretty important part of the Kobweb experience, but it's worth pointing out that it's designed as an
optional component. You can absolutely use Kobweb without Silk. (You can also use Silk without Kobweb!).

You can also interleave Silk and Compose HTML components easily (as Silk is just composing them itself).

### Inline vs StyleSheet

For those new to web dev, it's worth understanding that there are two ways to set styles on your HTML elements: inline
and stylesheet.

Inline styles are defined on the element tag itself. In raw HTML, this might look like:

```html
<div style="background-color:black">
```

Meanwhile, any given HTML page can reference a list of stylesheets which can define a bunch of styles, where each style
is tied to a selector (a rule which _selects_ what elements those styles apply to).

A concrete example of a very short stylesheet can help here:

```css
body {
  background-color: black;
  color: magenta
}
#title {
  color: yellow
}
```

And you could use that stylesheet to style the following document:

```html
<body>
  <!-- Div gets background-color from "body" and foreground color from "#title" -->
  <div id="title">
      Yellow on green
  </div>
</body>
```

There's no hard and fast rule, but in general, when writing HTML / CSS by hand, stylesheets are often preferred over
inline styles as it better maintains a separation of concerns. That is, the HTML should represent the content of your
site, while the CSS controls the look and feel.

However! We're not writing HTML / CSS by hand. We're using Compose HTML! So the distinctions discussed up until now
are less important here.

That said, there are times when you have to use stylesheets, because without them you can't define styles for advanced
behaviors (particularly [pseudo classes](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-classes),
[pseudo elements](https://developer.mozilla.org/en-US/docs/Web/CSS/Pseudo-elements), and
[media queries](https://developer.mozilla.org/en-US/docs/Web/CSS/Media_Queries/Using_media_queries), the discussion of
which are outside the scope of this README). For example, you can't override the color of visited links without using a
stylesheet approach. So it's worth realizing there are occasional differences.

---

In general, when you pass styles defined on the fly into a composable widget in Silk, those will result in inline
styles, whereas if you use a `ComponentStyle` to define the styles, those will get embedded into the site's stylesheet:

```kotlin
// Uses inline styles
Box(Modifier.color(Colors.Red)) { /* ... */ }

// Uses a stylesheet
val BoxStyle by ComponentStyle {
    base { Modifier.Color(Colors.Red) }
}
Box(BoxStyle.toModifier()) { /* ... */ }
```

We'll talk more about these approaches in the following sections.

One last note: debugging your page with browser tools may be easier if you lean on stylesheets over inline styles,
because it makes your DOM tree easier to look through without all that extra noise. As a result, I tend to use
`ComponentStyle` quite often.

### Modifier

Silk introduces the `Modifier` class, in order to provide an experience similar to what you find in Jetpack Compose.
(You can read [more about them here](https://developer.android.com/jetpack/compose/modifiers) if you're unfamiliar with
the concept).

In the world of Compose HTML, you can think of a `Modifier` as a wrapper on top of CSS styles and attributes. So
this:

```kotlin
Modifier.backgroundColor(Colors.Red).color(Colors.Green).padding(200.px)
```

when passed into a widget provided by Kobweb, like `Box`:

```kotlin
Box(Modifier.backgroundColor(Colors.Red).color(Colors.Green).padding(200.px)) {
    /* ... */
}
```

would generate an HTML tag with a style property like: `<div style="background:red;color:green;padding:200px">`

#### attrsModifier and styleModifier

There are a bunch of modifier extensions (and they're growing) provided by Kobweb, like `background`, `color`, and
`padding` above. But there are also two escape hatches anytime you run into a modifier that's missing:
`attrsModifier` and `styleModifier`.

Using them looks like this:

```kotlin
// Modify attributes of an element tag
// e.g. the "a", "b", and "c" in <tag a="..." b="..." c="..." />
Modifier.attrsModifier {
    onMouseDown { /* ... */ }
}

// Modify styles of an element tag
// e.g. the "a", "b", and "c" in `<tag style="a:...;b:...;c:..." />
Modifier.styleModifier {
    width(100.percent)
    height(50.percent)
}

// Note: Because "style" itself is an attribute, you can define styles in an attrsModifier:
Modifier.attrsModifier {
    style {
        width(100.percent)
        height(50.percent)
    }
}
// ... but in the above case, you should use a styleModifier for simplicity
```

### ComponentStyle

With Silk, you can define a style like so, using the `base` block:

```kotlin
val CustomStyle by ComponentStyle {
    base {
        Modifier.backgroud(Colors.Red)
    }
}
```

and convert it to a modifier by using `CustomStyle.toModifier()`. At this point, you can pass it into any composable
which takes a `Modifier` parameter:

```kotlin
// Approach #1 (uses inline styles)
Box(Modifier.backgroundColor(Colors.Red)) { /* ... */ }

// Appraoch #2 (uses stylesheets)
Box(CustomStyle.toModifier()) { /* ... */}
```

#### ComponentStyle name

Note above we used the `by` keyword here to create a component style. This automatically generates a name
for your style under the hood using [Kebab Case](https://www.freecodecamp.org/news/snake-case-vs-camel-case-vs-pascal-case-vs-kebab-case-whats-the-difference/#kebab-case).
So, here, the style's name will be "custom", and if you wrote `val TitleTextStyle by ComponentStyle`, its name would be
"title-text". You usually won't need to care about this name, but there are niche cases where it can be useful to
understand that is what's going on.

If you need to set a name manually, there's an alternate constructor version you can use for that, where you use
assignment instead of `by` delegation:

```kotlin
val CustomStyle = ComponentStyle("my-custom-name") {
    base {
        Modifier.backgroud(Colors.Red)
    }
}
```

#### Additional states

So, what's up with the `base` block?

True, it looks a bit verbose on its own. However, you can define additional styles that take effect conditionally. The
base style will always apply first, but then additional styles can be applied based on what state the element is in. (If
multiple states are applicable at the same time, they will be applied in the order specified.)

Here, we create a style which is red by default, but green when the mouse hovers over it:

```kotlin
val CustomStyle by ComponentStyle {
    base {
        Modifier.color(Colors.Red)
    }

    hover {
        Modifier.color(Colors.Green)
    }
}
```

Kobweb provides a bunch of these state blocks for you for convenience, but for those who are CSS-savvy, you can always
define the CSS rule directly to enable more complex combinations or reference states that Kobweb hasn't added yet.

For example, this is identical to the above style definition:

```kotlin
val CustomStyle by ComponentStyle {
    base {
        Modifier.color(Colors.Red)
    }

    cssRule(":hover") {
        Modifier.color(Colors.Green)
    }
}
```

#### Breakpoints

There's a feature in the world of responsive HTML / CSS design called breakpoints, which confusingly have nothing to do
with debugging breakpoints. Rather, they specify size boundaries for your site when styles change. This is how sites
present content differently on mobile vs. tablet vs. desktop.

Kobweb provides four breakpoint sizes you can use for your project, which, including using no breakpoint size at all,
gives you five buckets you can work with when designing your site:

* no breakpoint - mobile (and larger)
* sm - tablets (and larger)
* md - desktops (and larger)
* lg - widescreen (and larger)
* xl - ultra widescreen (and larger)

You can change the default values of breakpoints for your site by adding an "@InitSilk" block to your code:

```kotlin
@InitSilk
fun initializeBreakpoints(ctx: InitSilkContext) {
    ctx.theme.breakpoints = BreakpointSizes(
        sm = 30.cssRem,
        md = 48.cssRem,
        lg = 62.cssRem,
        xl = 80.cssRem,
    )
}
```

To reference a breakpoint in a `ComponentStyle`, just invoke it:

```kotlin
val CustomStyle by ComponentStyle {
    base {
        Modifier.fontSize(24.px)
    }

    Breakpoint.MD {
        Modifier.fontSize(32.px)
    }
}
```

#### Color-mode aware

When you define a `ComponentStyle`, an optional field is available for you to use called `colorMode`:

```kotlin
val CustomStyle by ComponentStyle {
    base {
        Modifier.color(if (colorMode.isLight()) Colors.Red else Colors.Pink)
    }
}
```

Note that Silk provides a `SilkTheme` object you can reference in styles. For example, if you want to set your element's
color to match the color that we use for links, you can reference the `SilkTheme.palettes[colorMode]` object to do so:

```kotlin
val CustomStyle by ComponentStyle {
    base {
        Modifier.color(SilkTheme.palettes[colorMode].link.default)
    }
}
```

`SilkTheme` contains very simple (e.g. black and white) defaults, but you can override them in an `@InitSilk` method,
perhaps to something that is more brand aware:

```kotlin
// Assume a bunch of color constants (e.g. BRAND_LIGHT_COLOR) are defined somewhere

@InitSilk
fun overrideSilkTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = BRAND_LIGHT_BACKGROUND
    ctx.theme.palettes.light.color = BRAND_LIGHT_COLOR
    ctx.theme.palettes.dark.background = BRAND_DARK_BACKGROUND
    ctx.theme.palettes.dark.color = BRAND_DARK_COLOR
}
```

#### ComponentVariant

With a style, you can also create a variant of that style (that is, additional modifications that are always applied
_on top of_ the style).

You define one using the `ComponentStyle.addVariant` method, but otherwise the declaration looks the same as defining a
`ComponentStyle`:

```kotlin
val HighlightedCustomVariant by CustomStyle.addVariant {
    base {
        Modifier.backgroundColor(Colors.Green)
    }
}
```

*Note: A common naming convention for variants is to take their associated style and use its name as a suffix plus the
word "Variant", e.g. "ButtonStyle" -> "GhostButtonVariant" and "TextStyle" -> "OutlinedTextVariant".*

Variants can be particularly useful if you're defining a custom widget that has default styles, but you want to give
callers an easy way to deviate from it in special cases.

For example, maybe you define a button widget (perhaps you're not happy with the one provided by Silk):

```kotlin
val ButtonStyle by ComponentStyle { /* ... */ }

// Note: Creates a style called "button-outline"
val OutlineButtonVariant by ButtonStyle.addVariant { /* ... */ }

// Note: Creates a style called "button-inverted"
val InvertedButtonVariant by ButtonStyle.addVariant { /* ... */ }
```

The `ComponentStyle.toModifier(...)` method, mentioned earlier, optionally takes a variant parameter. When passed in,
both styles will be applied -- the base style followed by the variant style.

For example, `MyButtonStyle.toModifier(OutlineButtonVariant)` applies the main button style first followed by additional
outline styling.

***Note:** Using a variant that was created from a different style will have no effect. In other words,
`LinkStyle.toModifier(OutlineButtonVariant)` will ignore the button variant in that case.*

##### ComponentVariantName

Like component styles created using the `by` keyword, variants have their name autogenerated for you. If you need to
control this name for any reason, you can use assignment instead and pass a name into `addVariant`, e.g.
`val InvertedButtonVariant = ButtonStyle.addVariant("custom-name") { /* ... */ }`

#### Writing custom widgets

While Silk methods are all written to support component styles and variants, if you ever want to write your own custom
widget that mimics Silk, your code should look something like:

```kotlin
val CustomWidgetStyle by ComponentStyle { /* ... */ }

@Composable
fun CustomWidget(
    modifier: Modifier = Modifier,
    variant: ComponentVariant? = null,
    @Composable content: () -> Unit
) {
    val finalModifier = CustomWidgetStyle.toModifier(variant).then(modifier)
    Box(finalModifier, content)
}
```

In other words, you should take in an optional `ComponentVariant` parameter, and then you should apply the modifiers in
order of: base style, then variant, then finally user overrides.

A caller might call your widget one of several ways:

```kotlin
// Approach #1: Use default styling
CustomWidget { /* ... */ }

// Approach #2: Tweak default styling with a variant
CustomWidget(variant = TransparentWidgetVariant) { /* ... */ }

// Approach #3: Tweak default styling with user overrides
CustomWidget(Modifier.backgroundColor(Colors.Blue)) { /* ... */ }

// Approach #4: Tweak default styling with a variant and then user overrides
CustomWidget(Modifier.backgroundColor(Colors.Blue), variant = TransparentWidgetVariant) { /* ... */ }
```

### Animations

In CSS, animations work by letting you define keyframes in a stylesheet which you then reference, by name, in an
animation style. You can read more about them
[on Mozilla's documentation site](https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Animations/Using_CSS_animations).

For example, here's the CSS for an animation of a sliding rectangle
([from this tutorial](https://www.w3schools.com/cssref/tryit.php?filename=trycss3_animation)):

```css
div {
  width: 100px;
  height: 100px;
  background: red;
  position: relative;
  animation: mymove 5s infinite;
}

@keyframes mymove {
  from {left: 0px;}
  to {left: 200px;}
}
```

Kobweb lets you define your keyframes in code by using the `by keyframes` pattern:

```kotlin
val ShiftRight by Keyframes {
    from { Modifier.left(0.px) }
    to { Modifier.left(200.px) }
}

// Later
Div(
    Modifier
        .size(100.px).backgroundColor(Colors.Red).position(Position.Relative)
        .animation(ShiftRight.toAnimation(
            duration = 5.s,
            iterationCount = AnimationIterationCount.Infinite
        ))
)
```

The name of the keyframes block is automatically derived from the property name (here, `ShiftRight` is converted into
`"shift-right"`). You can then use the `toAnimation` method to convert your collection of keyframes into an animation that
uses them, which you can pass into the `Modifier.animation` modifier.

### ElementRefScope and raw HTML elements

Occasionally, you may need access to the raw element backing the Silk widget you've just created. All Silk widgets
provide an optional `ref` parameter which take a listener that provide this information.

```kotlin
Box(
    ref = /* ... */
) {
    /* ... */
}
```

All `ref` callbacks (discussed more below) will receive an `org.w3c.dom.Element` subclass. You can check out the
[Element](https://kotlinlang.org/api/latest/jvm/stdlib/org.w3c.dom/-element/) class (and its often more
relevant [HTMLElement](https://kotlinlang.org/api/latest/jvm/stdlib/org.w3c.dom/-h-t-m-l-element/) inheritor) to see the
methods and properties that are available on it.

Raw HTML elements expose a lot of functionality not available through the higher level Compose HTML APIs.

#### `ref`

For a trivial but common example, we can use the raw element to capture focus:

```kotlin
Box(
    ref = ref { element ->
        // Triggered when this Box is first added into the DOM
        element.focus()
    }
)
```

The `ref { ... }` method can actually take one or more optional keys of any value. If any of these keys change on a
subsequent recomposition, the callback will be rerun:

```kotlin
val colorMode by rememberColorMode()
Box(
    // Callback will get triggered each time the color mode changes
    ref = ref(colorMode) { element -> /* ... */}
)
```

#### `disposableRef`

If you need to know both when the element enters AND exits the DOM, you can use `disposableRef` instead. With
`disposableRef`, the very last line in your block must be a call to `onDispose`:

```kotlin
val activeElements: MutableSet<HTMLElement> = /* ... */

/* ... later ... */

Box(
    ref = disposableRef { element ->
        activeElements.put(element)
        onDispose { activeElements.remove(element) }
    }
)
```

The `disposableRef` method can also take keys which rerun the listener if any of them change. The `onDispose` callback
will also be triggered in that case, as the old effect gets discarded.

#### `refScope`

And, finally, you may want to have multiple listeners that are recreated independently of one another based on different
keys. You can use `refScope` as a way to combine two or more `ref` and/or `disposableRef` calls in any combination:

```kotlin
val isFeature1Enabled: Boolean = /* ... */
val isFeature2Enabled: Boolean = /* ... */

Box(
    ref = refScope {
        ref(isFeature1Enabled) { element -> /* ... */ }
        dispoasbleRef(isFeature2Enabled) { element -> /* ... */; onDispose { /* ... */ } }
    }
)
```

#### Compose HTML refs

You may occasionally want the backing element of a normal Compose HTML widget, such as a `Div` or `Span`. However, these
widgets don't have a `ref` callback, as that's a convenience feature provided by Silk.

You still have a few options in this case.

The official way to retrieve a reference is by using a `ref` block inside an `attrs` block. This version of `ref` is
actually more similar to Silk's `disposableRef` concept than its `ref` one, as it requires an `onDispose` block:

```kotlin
Div(attrs = {
    ref { element -> /* ... */; onDispose { /* ... */ } }
})
```

*The above snippet was adapted from [the official tutorials](https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/HTML/Using_Effects#ref-in-attrsbuilder).*

You could put that exact same logic inside the `Modifier.toAttrs` block, if you're terminating some modifier chain:

```kotlin
Div(attrs = Modifier.toAttrs {
  ref { element -> /* ... */; onDispose { /* ... */ } }
})
```

Unlike Silk's version of `ref`, Compose HTML's version does not accept keys. If you need this behavior and if the
Compose HTML widget accepts a content block (many of them do), you can call Silk's `registerRefScope` method directly
within it:

```kotlin
Div {
  registerRefScope(
    disposableRef { element -> /* ... */; onDispose { /* ... */ } }
    // or ref { element -> /* ... */ }
  )
}
```

### CSS Variables

Kobweb supports CSS variables (also called CSS custom properties), which is a feature where you can store and retrieve
property values from variables declared within your CSS styles. It does this through a class called `StyleVariable`.

***Note:** You can find [official documentation for CSS custom properties here](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties).*

Using variables is fairly simple. You first declare one without a value (but lock it down to a type) and later you can
initialize it within a style using `Modifier.setVariable(...)`:

```kotlin
val dialogWidth by StyleVariable<CSSLengthValue>()

// This style will be applied to a div that wraps the whole page
val RootStyle by ComponentStyle {
  base { Modifier.setVariable(dialogWidth, 600.px) }
}
```

You can later use variables that were previously set, using the `value()` method to extract their current value:

```kotlin
val DialogStyle by ComponentStyle {
  base { Modifier.width(dialogWidth.value()) }
}
```

You can also provide a fallback value, which, if present, would be used in the case that a variable hadn't already been
set previously:

```kotlin
val DialogStyle by ComponentStyle {
  base { Modifier.width(dialogWidth.value(500.px)) }
}
```

Additionally, you can also provide a default fallback value when declaring the variable:

```kotlin
val dialogWidth by StyleVariable<CSSLengthValue>(100.px)

// This style will be applied to a div that wraps the whole page
val DialogStyle100 by ComponentStyle {
  // Uses default fallback. width = 100px
  base { Modifier.width(dialogWidth.value()) }
}
val DialogStyle200 by ComponentStyle {
  // Uses specific fallback. width = 200px
  base { Modifier.width(dialogWidth.value(200.px)) } // Uses fallback = 200.px
}
val DialogStyle300 by ComponentStyle {
  // Fallback ignored because variable is set explicitly. width = 300px
  base { Modifier.setVariable(dialogWidth, 300.px).width(dialogWidth.value(400.px)) }
}
```
***NOTE:** In the above example, we have one line where set a variable and query it in the same style, which we did
purely for demonstration purposes. In practice, you would probably never do this -- the variable should have been set
separately earlier.*

To demonstrate these concepts all together, below we declare a background color variable, create a root container scope
which sets it, a child style that uses it, and, finally, a child style variant that overrides it:

```kotlin
// Default to a debug color, so if we see it, it indicates we forgot to set it later
val bgColor by StyleVariable<CSSColorValue>(Colors.Magenta)

val ContainerStyle by ComponentStyle {
    base { Modifier.setVariable(bgColor, Colors.Blue) }
}
val SquareStyle by ComponentStyle {
    base { Modifier.size(100.px).backgroundColor(bgColor.value()) }
}
val RedSquareVariant by SquareStyle.addVariant {
    base { Modifier.setVariable(bgColor, Colors.Red) }
}
```

The following code brings the above styles together (and in some cases uses inline styles to override the background
color further):

```kotlin
@Composable
fun ColoredSquares() {
    Box(ContainerStyle.toModifier()) {
        Column {
            Row {
                // 1: Read color from ancestor's component style
                Box(SquareStyle.toModifier())
                // 2: Override color via variant
                Box(SquareStyle.toModifier(RedSquareVariant))
            }
            Row {
                // 3: Override color via inline styles
                Box(SquareStyle.toModifier().setVariable(bgColor, Colors.Green))
                Span(Modifier.setVariable(bgColor, Colors.Yellow).toAttrs()) {
                    // 4: Read color from parent's inline style
                    Box(SquareStyle.toModifier())
                }
            }
        }
    }
}
```

The above renders the following output:

![Kobweb CSS Variables, Squares example](https://github.com/varabyte/media/raw/main/kobweb/images/kobweb-variable-squares-example.png)

---

You can also set CSS variables directly from code, if you have access to the backing HTML element. Below, we use the
`ref` callback to get the backing element for a fullscreen `Box` and then use a `Button` to set it to a random color
from the colors of the rainbow:

```kotlin
val bgColor by StyleVariable<CSSColorValue>()

val ScreenStyle by ComponentStyle {
    base {
        Modifier.fillMaxSize().backgroundColor(
            // We specify `Red` as a fallback here, since the variable
            // won't otherwise be set when the UI first renders.
            bgColor.value(Colors.Red)
        )
    }
}

@Page
@Composable
fun RainbowBackground() {
    val roygbiv = listOf(Colors.Red, /*...*/ Colors.Violet)

    var screenElement: HTMLElement? by remember { mutableStateOf(null) }
    Box(ScreenStyle.toModifier(), ref = ref { screenElement = it }) {
        Button(onClick = {
            // We have the backing HTML element, so use setProperty to set the variable value directly
            screenElement!!.style.setProperty("--bgColor", roygbiv.random().toString())
        }) {
            Text("Click me")
        }
    }
}
```

The above results in the following UI:

![Kobweb CSS Variables, Rainbow example](https://github.com/varabyte/media/raw/main/kobweb/screencasts/kobweb-variable-roygbiv-example.gif)

#### In most cases, don't use CSS Variables

Most of the time, you can actually get away with not using CSS Variables! Your Kotlin code is often a more natural place
to describe dynamic behavior than HTML / CSS is.

Let's revisit the "colored squares" example from above. Note it's much easier to read if we don't try to use variables
at all.

```kotlin
val SquareStyle by ComponentStyle {
    base { Modifier.size(100.px) }
}

@Composable
fun ColoredSquares() {
    Column {
        Row {
            Box(SquareStyle.toModifier().backgroundColor(Colors.Blue))
            Box(SquareStyle.toModifier().backgroundColor(Colors.Red))
        }
        Row {
            Box(SquareStyle.toModifier().backgroundColor(Colors.Green))
            Box(SquareStyle.toModifier().backgroundColor(Colors.Yellow))
        }
    }
}
```

And the "rainbow background" example is similarly easier to read by using Kotlin variables
(i.e. `var someValue by remember { mutableStateOf(...) }`) instead of CSS variables:

```kotlin
val ScreenStyle by ComponentStyle {
    base { Modifier.fillMaxSize() }
}

@Page
@Composable
fun RainbowBackground() {
    val roygbiv = listOf(Colors.Red, /*...*/ Colors.Violet)

    var currColor by remember { mutableStateOf(Colors.Red) }
    Box(ScreenStyle.toModifier().backgroundColor(currColor)) {
        Button(onClick = { currColor = roygbiv.random() }) {
            Text("Click me")
        }
    }
}
```

Even though you should rarely need CSS variables, there may be occasions where they can be a useful tool in your
toolbox. The above examples were artificial examples used as a way to show off CSS variables in relatively isolated
environments. But here are some situations that might benefit from CSS variables:

* You have a site which allows users to choose from a list of several themes (e.g. primary and secondary colors). It
  would be trivial enough to add CSS variables for `themePrimary` and `themeSecondary` (applied at the site's root)
  which you can then reference throughout your styles.
* You need more control for colors in your theming than can be provided for by the simple light / dark color mode. For
  example, Wordle has light / dark + normal / contrast modes.
* You want to create a widget which dynamically changes its behavior based on the context it is added within. For
  example, maybe your site has a dark area and a light area, and the widget should use white outlines in the dark area
  and black outlines in the light. This can be accomplished by exposing an outline color variable, which each area of
  your site is responsible for setting.
* You are using a component style for a pseudo-class selector already (e.g. hover, focus, active) and you want that
  behavior to be dynamic.
* You have a widget that you ended up creating a bunch of variants for, but instead you realize you could replace them
  all with one or two CSS variables.

When in doubt, however, lean on Kotlin for handling dynamic behavior. If you want to share common style settings across
multiple component styles, just declare a `Modifier` instance somewhere and share *that* in each style. If you
want behavior to change based on some event, prefer using inline styles instead of hiding the logic inside component
styles.

### Font Awesome

Kobweb provides the `kobweb-silk-icons-fa` artifact which you can use in your project if you want access to all the free
Font Awesome (v6) icons.

Using it is easy! Search the [Font Awesome gallery](https://fontawesome.com/search?o=r&m=free), choose an
icon, and then call it using the associated Font Awesome icon composable.

For example, if I wanted to add the Kobweb-themed
[spider icon](https://fontawesome.com/icons/spider?s=solid&f=classic), I could call this in my Kobweb code:

```kotlin
FaSpider()
```

That's it!

Some icons have a choice between solid and outline versions, such as "Square"
([outline](https://fontawesome.com/icons/square?s=solid&f=classic) and
[filled](https://fontawesome.com/icons/square?s=regular&f=classic)). In that case, the default choice will be outline
mode, but you can pass in a style enum to control this:

```kotlin
FaSquare(style = IconStyle.FILLED)
```

All Font Awesome composables accept a modifier parameter, so you can tweak it further:

```kotlin
FaSpider(Modifier.color(Colors.Red))
```

***Note**: When you create a project using our `app` template, Font Awesome icons are included.*

### Material Design Icons

Kobweb provides the `kobweb-silk-icons-mdi` artifact which you can use in your project if you want access to all the
free Material Design icons.

Using it is easy! Search the [Material Icons gallery](https://fonts.google.com/icons?icon.set=Material+Icons), choose an
icon, and then call it using the associated Material Design Icon composable.

For example, let's say after a search I found and wanted to use their
[bug report icon](https://fonts.google.com/icons?icon.set=Material+Icons&icon.query=bug+report), I could call this in my
Kobweb code by converting the name to camel case:

```kotlin
MdiBugReport()
```

That's it!

Most material design icons support multiple styles: outlined, filled, rounded, sharp, and two-tone. Check the gallery
search link above to verify what styles are supported by your icon. You can identify the one you want to use by passing
it into the method's `style` parameter:

```kotlin
MdiLightMode(style = IconStyle.TWO_TONE)
```

All Material Design Icon composables accept a modifier parameter, so you can tweak it further:

```kotlin
MdiError(Modifier.color(Colors.Red))
```

## Intermediate topics

### Components: Layouts, Sections, and Widgets

Outside of pages, it is common to create reusable, composable parts. While Kobweb doesn't enforce any particular rule
here, we recommend a convention which, if followed, may make it easier to allow new readers of your codebase to get
around.

First, as a sibling to pages, create a folder called **components**. Within it, add:

* **layouts** - High-level composables that provide entire page layouts. Most (all?) of your `@Page` pages will start by
  calling a page layout function first. You may only have a single layout for your entire site.
* **sections** - Medium-level composables that represent compound areas inside your pages, organizing a collection of
  many children composables. If you have multiple layouts, it's likely sections would be shared across them. For
  example, nav headers and footers are great candidates for this subfolder.
* **widgets** - Low-level composables. Focused UI pieces that you may want to re-use all around your site. For example,
  a stylized visitor counter would be a good candidate for this subfolder.

### Specifying your application root

By default, Kobweb will automatically root every page to the [`KobwebApp` composable](https://github.com/varabyte/kobweb/blob/main/frontend/kobweb-core/src/jsMain/kotlin/com/varabyte/kobweb/core/App.kt)
(or, if using Silk, to a [`SilkApp` composable](https://github.com/varabyte/kobweb/blob/main/frontend/kobweb-silk/src/jsMain/kotlin/com/varabyte/kobweb/silk/SilkApp.kt)).
These perform some minimal common work (e.g. applying CSS styles) that should be present across your whole site.

This means if you register a page:

```kotlin
// jsMain/kotlin/com/example/mysite/pages/Index.kt

@Page
@Composable
fun HomePage() {
    /* ... */
}
```

then the final result that actually runs on your site will be:

```kotlin
// In a generated main.kt somewhere...

KobwebApp {
  HomePage()
}
```

It is likely you'll want to configure this further for your own application. Perhaps you have additional styles you'd
like to globally define (e.g. the default font used by your site). Perhaps you have some initialization logic that you'd
like to run before any page gets run (like logic for updating saved settings into local storage).

In this case, you can create your own root composable and annotate it with `@App`. If present, Kobweb will use that
instead of its own default. You should, of course, delegate to `KobwebApp` (or `SilkApp` if using Silk), as the
initialization logic from those methods should still be run.

Here's an example application composable override that I use in one of my own projects:

```kotlin
@App
@Composable
fun MyApp(content: @Composable () -> Unit) {
    SilkApp {
        val colorMode = getColorMode()
        LaunchedEffect(colorMode) { // Relaunched every time the color mode changes
            localStorage.setItem(COLOR_MODE_KEY, colorMode.name)
        }

        // A full screen Silk surface. Sets the background based on Silk's palette and animates color changes.
        Surface(Modifier.minHeight(100.vh)) {
            content()
        }
    }
}
```

You can only define *at most* a single `@App` on your site, or else the Kobweb Application plugin will complain at build
time.

### Define API routes

You can define and annotate methods which will generate server endpoints you can interact with. To add one:

1. Define your method (optionally `suspend`able) in a file somewhere under the `api` package your `jvmMain` source
   directory.
1. The method should take exactly one argument, an `ApiContext`.
1. Annotate it with `@Api`

For example, here's a simple method that echoes back an argument passed into it:

```kotlin
// jvmMain/kotlin/com/example/mysite/api/Echo.kt

@Api
fun echo(ctx: ApiContext) {
    // ctx.req is for the incoming request, ctx.res for responding back to the client

    // Params are parsed from the URL, e.g. here "/api/echo?message=..."
    val msg = ctx.req.params["message"] ?: ""
    ctx.res.setBodyText(msg)
}
```

After running your project, you can test the endpoint by visiting `mysite.com/api/echo?message=hello`

You can also trigger the endpoint in your frontend code by using the extension `api` property added to the
`kotlinx.browser.window` class:

```kotlin
@Page
@Composable
fun ApiDemoPage() {
  val coroutineScope = rememberCoroutineScope()

  Button(onClick = {
    coroutineScope.launch {
      println("Echoed: " + window.api.get("echo?message=hello").decodeToString())
    }
  })
}
```

All the HTTP methods are supported (`post`, `put`, etc.).

These methods will throw an exception if the request fails for any reason. Note that for every HTTP method, there's a
corresponding "try" version that will return null instead (`tryPost`, `tryPut`, etc.).

If you know what you're doing, you can of course always use [`window.fetch(...)`](https://developer.mozilla.org/en-US/docs/Web/API/fetch)
directly.

### Markdown

If you create a markdown file under the `jsMain/resources/markdown` folder, a corresponding page will be created for you
at build time, using the filename as its path.

For example, if I create the following file:

```markdown
// jsMain/resources/markdown/docs/tutorial/Kobweb.kt

# Kobweb Tutorial

...
```

this will create a page that I can then visit by going to `mysite.com/docs/tutorial/kobweb`

#### Front Matter

Front Matter is metadata that you can specify at the beginning of your document, like so:

```markdown
---
title: Tutorial
author: bitspittle
---

...
```

In a following section, we'll discuss how to embed code in your markdown, but for now, know that these key / value pairs
can be queried in code using the page's context:

```kotlin
@Composable
fun AuthorWidget() {
    val ctx = rememberPageContext()
    // Note: You can use `markdown!!` only if you're sure that
    // this composable is called while inside a page generated
    // from Markdown.
    val author = ctx.markdown!!.frontMatter.getValue("author").single()
    Text("Article by $author")
}
```

***Note:** If you're not seeing `ctx.markdown` autocomplete, you need to make sure you depend on the
`com.varabyte.kobwebx:kobwebx-markdown` artifact in your project's `build.gradle`*.

##### Root

Within your front matter, there's a special value which, if set, will be used to render a root `@Composable` that wraps
the code your markdown file would otherwise create. This is useful for specifying a layout for example:

```markdown
---
root: .components.layout.DocsLayout
---

# Kobweb Tutorial
```

The above will generate code like the following:

```kotlin
import com.mysite.components.layout.DocsLayout

@Composable
@Page
fun KobwebPage() {
    DocsLayout {
        H1 {
            Text("Kobweb Tutorial")
        }
    }
}
```

#### Kobweb Call

The power of Kotlin + Compose HTML is interactive components, not static text! Therefore, Kobweb Markdown support
enables special syntax that can be used to insert Kotlin code.

##### Block syntax

Usually, you will define widgets that belong in their own section. Just use three triple-curly braces to insert a
function that lives in its own block:

```markdown
# Kobweb Tutorial

...

{{{ .components.widgets.VisitorCounter }}}
```

which will generate code for you like the following:

```kotlin
@Composable
@Page
fun KobwebPage() {
    /* ... */
    com.mysite.components.widgets.VisitorCounter()
}
```

You may have noticed that the code path in the markdown file is prefixed with a `.`. When you do that, the final path
will automatically be prepended with your site's full package.

##### Inline syntax

Occasionally, you may want to insert a smaller widget into the flow of a single sentence. For this case, use the
`${...}` inline syntax:

```markdown
Press ${.components.widgets.ColorButton} to toggle the site's current color.
```

**Warning:** Spaces are not allowed within the curly braces! If you have them there, Markdown skips over the whole
thing and leaves it as text.

### Version Catalogs

The project templates created by Kobweb all embrace Gradle version catalogs, which are (at the time of writing this
README) a relatively new feature, so users may not be aware of it.

There is a file called `libs.versions.toml` that exists inside your project's root `gradle` folder. If you find yourself
wanting to tweak or add new versions to projects you originally created via `kobweb create`, that's where you'll find
them.

For example, here's the
[libs.versions.toml](https://github.com/varabyte/kobweb-site/blob/main/gradle/libs.versions.toml) we use for our own
landing site.

To read more about the feature, please check out the
[official docs](https://docs.gradle.org/current/userguide/platforms.html#sub:conventional-dependencies-toml).

# Advanced topics

## Multimodule

For simplicity, new projects can choose to put all their pages and widgets inside a single application module.

However, Kobweb is capable of splitting code up across modules. You can define components and/or pages in separate
modules and apply the `com.varabyte.kobweb.library` plugin on them (in contrast to your main module which applies the
`com.varabyte.kobweb.application` plugin.)

In other words, you can lay out your project like this:

```
my-project
├── sitelib
│   ├── build.gradle.kts # apply "com.varabyte.kobweb.library"
│   └── src/jsMain
│       └── kotlin.org.example.myproject.sitelib
│           ├── components
│           └── pages
└── site
    ├── build.gradle.kts # apply "com.varabyte.kobweb.application"
    └── src/jsMain
        └── kotlin.org.example.myproject.site
            ├── components
            └── pages
```

If you'd like to explore a multimodule project example, you can do so by running:

```bash
$ kobweb create examples/chat
```

which demonstrates a chat application with its auth and chat functionality each managed in their own separate modules.

## Adding Kobweb to an existing project

Currently, Kobweb is still under active development, and due to our limited resources, we are focusing on improving the
path to creating a new project from scratch. However, some users have shown interest in Kobweb but already have an
existing project and aren't sure how to add Kobweb into it.

As long as you understand that this path isn't officially supported yet, we'll provide steps below to take which may
help people accomplish this manually for now. Honestly, the hardest part is creating a correct `.kobweb/conf.yaml`,
which the following steps help you work around:

1. Be sure to check the Kobweb compatibility matrix [(see: COMPATIBILITY.md)](https://github.com/varabyte/kobweb/blob/main/COMPATIBILITY.md)
   to make sure you can match the versions it expects.
2. Create a dummy app project somewhere. Pay attention to the questions it asks you, as you may want to choose a
   package name that matches your project.
   ```bash
   # In some tmp directory somewhere
   kobweb create app
   # or `kobweb create app/empty`, if you are already
   # experienced with Kobweb and know what you're doing
   ```
3. When finished, copy the `site` subfolder out into your own project. (Once done, you can delete the dummy project, as
   it has served its usefulness.)
   ```bash
   cp -r app/site /path/to/your/project
   # delete app
   ```
4. In your own project's root `settings.gradle.kts` file, include the new module *and* add our custom artifact
   repository link so your project can find the Kobweb Gradle plugins.
   ```kotlin
   // settings.gradle.kts
   pluginManagement {
     repositories {
       // ... other repositories you already declared ...
       maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
     }
   }
   // ... other includes you already declared
   include(":site")
   ```
5. In your project's root `build.gradle.kts` file, add our custom artifact repository there as well (so your project can
   find Kobweb libraries)
   ```kotlin
   // build.gradle.kts
   subprojects {
     repositories {
       // ... other repositories you already declared ...
       maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
     }
   }

   // If you prefer, you can just declare this directly inside the
   // repositories block in site's `build.gradle.kts` file, but I
   // like declaring my maven repositories globally.
   ```
6. Kobweb uses version catalogs for its dependencies. Add or update your version catalog under
   `gradle/libs.versions.toml`
   ```toml
   [versions]
   jetbrains-compose = "..." # replace with actual version, see COMPATIBILITY.md!
   kobweb = "..." # replace with actual version
   kotlin = "..." # replace with actual version

   [libraries]
   kobweb-api = { module = "com.varabyte.kobweb:kobweb-api", version.ref = "kobweb" }
   kobweb-core = { module = "com.varabyte.kobweb:kobweb-core ", version.ref = "kobweb" }
   kobweb-silk-core = { module = "com.varabyte.kobweb:kobweb-silk", version.ref = "kobweb" }
   kobweb-silk-icons-fa = { module = "com.varabyte.kobweb:kobweb-silk-icons-fa", version.ref = "kobweb" }
   kobwebx-markdown = { module = "com.varabyte.kobwebx:kobwebx-markdown", version.ref = "kobweb" }

   [plugins]
   jetbrains-compose = { id = "org.jetbrains.compose", version.ref = "jetbrains-compose" }
   kobweb-application = { id = "com.varabyte.kobweb.application", version.ref = "kobweb" }
   kobwebx-markdown = { id = "com.varabyte.kobwebx.markdown", version.ref = "kobweb" }
   kotlin-multiplatform = { id = "org.jetbrains.kotlin.multiplatform", version.ref = "kotlin" }
   ```

If everything is working as expected, you should be able to run Kobweb within your project now:

```bash
# In /path/to/your/project
cd site
kobweb run
```

If you're still having issues, you may want to [connect with us ▼](#connecting-with-us)
for support (but understand that getting Kobweb added to complex existing projects may not be something we can currently
prioritize).

## Kobweb server logs

When you run `kobweb run`, the spun up web server will, by default, log to the `.kobweb/server/logs` directory.

You can configure logging behavior by editing the `.kobweb/conf.yaml` file. Below we show setting all parameters to
their default values:

```yaml
server:
  logging:
    level: DEBUG # ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF
    logRoot: ".kobweb/server/logs"
    clearLogsOnStart: true # Warning - if true, wipes ALL files in logRoot, so don't put other files in there!
    logFileBaseName: "kobweb-server" # e.g. "kobweb-server.log", "kobweb-server.2023-04-13.log"
    maxFileCount: null # null = unbound. One log file is created per day, so 30 = 1 month of logs
    totalSizeCap: 10MiB # null = unbound. Accepted units: B, K, M, G, KB, MB, GB, KiB, MiB, GiB
    compressHistory: true # If true, old log files are compressed with gzip
```

The above defaults were chosen to be reasonable for most users running their projects on their local machines in
developer mode. However, for production servers, you may want to set `clearLogsOnStart` to false, bump up the
`totalSizeCap` after reviewing disk limitations of your web server host, and maybe set `maxFileCount` to a reasonable
limit.

Note that most config files assume "10MB" is 10 * 1024 * 1024 bytes, but here it will actually result in
10 * 1000 * 1000 bytes. You probably want to use "KiB", "MiB", or "GiB" when you configure this value.

## Exporting your site in a GitHub workflow

While you can always export your site manually on your machine, you may want to automate this process. A common
solution for this is a [GitHub workflow](https://docs.github.com/en/actions/using-workflows).

For your convenience, we include a sample workflow below that exports your site and then uploads the results (which can
be downloaded from a link shown in the workflow summary page):

```yaml
# .github/workflows/export-site.yml

name: Export Kobweb site

on:
  workflow_dispatch:

jobs:
  export_and_upload:
    runs-on: ubuntu-latest
    defaults:
      run:
        shell: bash

    env:
      KOBWEB_CLI_VERSION: 0.9.12

    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          distribution: temurin
          java-version: 11

      # When projects are created on Windows, the executable bit is sometimes lost. So set it back just in case.
      - name: Ensure Gradle is executable
        run: chmod +x gradlew

      - name: Setup Gradle
        uses: gradle/gradle-build-action@v2

      - name: Query Browser Cache ID
        id: browser-cache-id
        run: echo "value=$(./gradlew -q :site:kobwebBrowserCacheId)" >> $GITHUB_OUTPUT

      - name: Cache Browser Dependencies
        uses: actions/cache@v3
        id: playwright-cache
        with:
          path: ~/.cache/ms-playwright
          key: ${{ runner.os }}-playwright-${{ steps.browser-cache-id.outputs.value }}

      - name: Fetch kobweb
        uses: robinraju/release-downloader@v1.7
        with:
          repository: "varabyte/kobweb-cli"
          tag: "v${{ env.KOBWEB_CLI_VERSION }}"
          fileName: "kobweb-${{ env.KOBWEB_CLI_VERSION }}.zip"
          tarBall: false
          zipBall: false

      - name: Unzip kobweb
        run: unzip kobweb-${{ env.KOBWEB_CLI_VERSION }}.zip

      - name: Run export
        run: |
          cd site
          ../kobweb-${{ env.KOBWEB_CLI_VERSION }}/bin/kobweb export --notty --layout static

      - name: Upload site
        uses: actions/upload-artifact@v3
        with:
          name: site
          path: site/.kobweb/site/
          if-no-files-found: error
          retention-days: 1
```

You can copy this workflow (or parts of it) into your own GitHub project and then modify it to your needs.

Some notes...

* ***workflow_dispatch***: This means that you can manually trigger this workflow from the GitHub UI, which I
  suggested here to prevent running an expensive export operation more than you need to. Of course, you can also
  configure your workflow to run on a schedule, or on push to a branch, etc.
* ***Setup Gradle***: This action is optional but I recommend it because it configures a bunch of caching for you.
* ***Caching the browser***: `kobweb export` needs to download a browser the first time it is run. This workflow sets up
  a cache that saves it across runs. The cache is tagged with a unique ID so that future Kobweb releases, which may
  change the version of browser downloaded, will use a new cache bucket (allowing GitHub to eventually clean up the old
  one).
* ***Upload site***: This action uploads the exported site as an artifact. You can then download the artifact from the
  workflow summary page. Your own workflow will likely delete this action and do something else here, like upload to a
  web server (or some location accessible by your webserver) or copy files over into a `gh_pages` repository. I've
  included this here (and set the retention days very low) just so you can verify that the workflow is working for your
  project.

For a simple site, the above workflow should take about 2 minutes to run.

## Kobweb Server Plugins

Many users who create a full stack application generally expect to completely own both the client- and server-side code.

However, being an opinionated framework, Kobweb provides a custom Ktor server in order to deliver some of its features.
For example, it implements logic for handling [server API routes▲](#define-api-routes) as well as some live reloading
functionality.

It would not be trivial to refactor this behavior into some library that users could import into their own backend
server. As a compromise, some server configuration is exposed by the `.kobweb/conf.yaml` file, and this has been the
main way users could affect the server's behavior.

That said, there will always be some use-cases that Kobweb won't anticipate. So as an escape hatch, Kobweb allows users
who know what they're doing to write their own plugins to extend the server.

***NOTE:** The Kobweb Server plugin feature is still fairly new. If you use it, please consider
[filing issues](https://github.com/varabyte/kobweb/issues/new?assignees=&labels=enhancement&projects=&template=feature_request.md&title=)
for any missing features and [connecting with us▼](#connecting-with-us) to share any feedback you have about your
experience.*

Creating a Kobweb server plugin is relatively straightforward. You'll need to:

* Create a new module in your project that produces a JAR file which bundles an implementation of
  the `KobwebServerPlugin` interface.
* Move a copy of that jar under your project's `.kobweb/server/plugins` directory.

### Create a Kobweb Server Plugin

The following instructions are based on a Kobweb multimodule setup, like the one created by `kobweb create app`.

* Create a new module in your project.
  * For example, name it "demo-server-plugin".
  * Be sure to update your `settings.gradle.kts` file to include the new project.
* Add a new entry for the `kobweb-server-project` library in `.gradle/libs.versions.toml`:
  ```toml
  [libraries]
  kobweb-server-plugin = { module = "com.varabyte.kobweb:kobweb-server-plugin", version.ref = "kobweb" }
  ```
* **For all remaining steps, create all files / directories under your new module's directory (e.g. `demo-server-plugin/`).**
* Create `build.gradle.kts`:
  ```kotlin
    plugins {
      kotlin("jvm")
    }
    group = "org.example.app" // update to your own project's group
    version = "1.0-SNAPSHOT"

    tasks.jar {
      // Remove the version number
      archiveFileName.set("${project.name}.jar")
    }

    dependencies {
      compileOnly(libs.kobweb.server.plugin)
    }
  ```
  * We omit the version number to prevent the accumulation of multiple versioned copies of the same plugin ended up in
    the Kobweb server. Instead, each new version should replace the previous one.
* Create `src/main/kotlin/DemoKobwebServerPlugin.kt`:
  ```kotlin
  import com.varabyte.kobweb.server.plugin.KobwebServerPlugin
  import io.ktor.server.application.Application
  import io.ktor.server.application.log

  class DemoKobwebServerPlugin : KobwebServerPlugin {
    override fun configure(application: Application) {
      application.log.info("REPLACE ME WITH REAL CONFIGURATION")
    }
  }
  ```
  * As the Kobweb server is written in Ktor, you should familiarize yourself
    with [Ktor's documentation](https://ktor.io/docs/plugins.html).
* Create `src/main/resources/META-INF/services/com.varabyte.kobweb.server.plugin.KobwebServerPlugin`:
  ```text
  org.example.app.DemoKobwebServerPlugin
  ```
  * This helps the JDK discover service implementations bundled within a JAR. You can
    read [this helpful article](https://www.baeldung.com/java-spi) to learn more about this useful Java feature.

### Copy your plugin jar manually

After building your JAR (`./gradlew :demo-server-plugin:jar`), manually copy it from `build/libs/` to your Kobweb
project's `.kobweb/server/plugins` directory.

Upon the next Kobweb server run (e.g. via `kobweb run`), if you check the logs, you should see something like this:

```text
[main] INFO  ktor.application - Autoreload is disabled because the development mode is off.
[main] INFO  ktor.application - REPLACE ME WITH REAL CONFIGURATION
[main] INFO  ktor.application - Application started in 0.112 seconds.
[main] INFO  ktor.application - Responding at http://0.0.0.0:8080
```

### Copy your plugin jar automatically

For convenience, the Kobweb Gradle Application plugin provides a way to notify it about your JAR task, and it will build
and copy it over for you automatically.

In your Kobweb's build script, include the following `notify...` line:

```kotlin
// site/build.gradle.kts

kobweb { /* ... */ }

notifyKobwebAboutServerPluginTask(project(":demo-server-plugin").tasks.named("jar", Jar::class))

kotlin { /* ... */ }
```

Once this is set up, you can modify your Kobweb server plugin, quit the server if one is running, and then rerun
`kobweb run` to have it pick up your changes automatically.

<!-- Some sites link to this section before I changed its name, so adding a span here so they can still find it. -->
## <span id="what-about-multiplatform-widgets"><span id="what-about-compose-for-web-canvas">What about Compose Multiplatform for Web?</span></span>

Jetbrains is working on a project called "Compose Multiplatform", which will allow developers to use the same Compose
API across Android, iOS, Desktop, and the Web. And it may seem like the Kobweb + Silk approach will be obsoleted by it.

It's first worth understanding the core difference between the two approaches. With Multiplatform Compose, the framework
owns its own rendering pipeline, drawing to a buffer, while Compose HTML modifies an HTML / CSS DOM tree and leaves it
up to the browser to do the final rendering.

This has major implications on how similar the two APIs can get. For example, in Desktop / Android, the order you apply
modifiers matters, while in HTML, this action simply sets html style properties under the hood, where order does not
matter.

Ditching HTML / CSS entirely at first can seem like a total win, but this approach has several limits:

* robots would lose the ability to crawl and index your site, hurting SEO.
* your initial render may take longer.
* your site will need to allocate a large canvas buffer, which could be *very* expensive on high res, wide-screen
  desktops.
* your UI will be opaque to the powerful suite of devtools that come bundled with browsers.
* you won't have the ability to style unvisited vs visited links differently (this information is hidden from you by
  the browser for security reasons and can only be set through HTML / CSS).
* you won't have the ability to turn elements on / off when printing the page.
* accessibility tools for browsers might not work.

It would also prevent a developer from making use of the rich ecosystem of Javascript libraries out there.

For now, I am making a bet that there will always be value in embracing the web, providing a framework that sticks to
HTML / CSS but offers a growing suite of UI widgets that hopefully makes it relatively rare for the developer to need to
worry about it.

For example, [flexbox](https://css-tricks.com/snippets/css/a-guide-to-flexbox/) is a very powerful CSS concept, but
you'll find it's much easier to compose `Row`s and `Column`s together than trying to remember if you should be
justifying your items or aligning your content, even if `Row`s and `Column`s are just creating the correct HTML / CSS
for you behind the scenes.

Ultimately, I believe there is room for both approaches. If you want to make an app experience that feels the same on
Android, iOS, Desktop, and Web, then "Multiplatform Compose" could be great for you. However, if you just want to make a
traditional website but want to use Kotlin instead of TypeScript, Kobweb can provide an excellent development experience
for that case.

# Can We Kobweb Yet

Current state: **Foundations are in place! You may encounter API gaps.**

Kobweb is becoming quite functional. We are already using it to build https://kobweb.varabyte.com and
https://bitspittle.dev (depending on when you're reading this, they may still be fairly barebones, but these sites will
get more of our full attention as Kobweb is finally stabilizing).

At this point:

* It is easy to set up a new project and get things running quickly.
* The live reloading flow is pretty nice, and you'll miss it when you switch to projects that don't have it.
* It supports generating pages from Markdown that can reference your Composable code.
* While it's not quite server-side rendering, you can export static pages which will get hydrated on load.
* You can use the `Modifier` builder for a growing number of css properties.
* Silk components are color mode aware and support responsive behavior.

However, there's always more to do.

* I'm trying to add support for every stabilized CSS property, but some are still missing, especially less
  common ones. (You can use a fallback for such cases in the meanwhile).
* A lot of detailed documentation is planned to go into the Kobweb site (linked just above) but it isn't done yet.

I think there's enough there now to let you do almost anything you'd want to do, as either Kobweb supports it or you can
escape hatch to underlying Compose HTML / Kotlin/JS approaches, but there might be some areas where it's still a bit
DIY. It would be great to get real world experience to hear what issues users are actually running into.

So, should you use Kobweb at this point? If you are...

* playing around with Compose HTML for the first time and want to get up and running quickly on a toy project:
    * **YES!!!** Please see the [connecting with us ▼](#connecting-with-us) section
      below, we'd definitely love to hear from you. It's still a good time if you'd want to have a voice in the
      direction of this project.
* a Kotlin developer who wants to write a small web app or create a new blog from scratch:
    * **Worth a shot!** I think if you evaluate Kobweb at this point, you'll find a lot to like. You can get in touch
      with us at our Discord if you try it and have questions or run into missing features.
* someone who already has an existing project in progress and wants to integrate Kobweb into it:
    * **Maybe not?** Depending how much work you've done, it may not be a trivial refactor. You can review
      [this earlier section ▲](#adding-kobweb-to-an-existing-project) if you want to try anyway.
* a company:
    * **Probably not?** I'm assuming a company is more risk-averse even to Compose HTML, which Kobweb is
      building on top of. If you *were* considering Compose HTML, however, Kobweb may be worth a look.

On the fence but not sure? Connect with us, and I'd be happy to help you assess your situation.

# Connecting with us

* [Join my Discord!](https://discord.gg/5NZ2GKV5Cs)
* Follow me on Mastodon: [@bitspittle@fosstodon.org](https://fosstodon.org/@bitspittle)
* You can send direct queries to [my email](mailto:bitspittle@gmail.com)

If you're comfortable with it, using Discord is recommended, because there's a growing community of users in there who
can offer help even when I'm not around.

# Filing issues and leaving feedback

It is still early days, and while we believe we've proven the feasibility of this approach at this point, there's still
plenty of work to do to get to a 1.0 launch! We are hungry for the community's feedback, so please don't hesitate to:

* [Open an issue](https://github.com/varabyte/kobweb/issues/new/choose)
* Contact us (using any of the ways mentioned above) telling us what features you want
* Ask us for guidance, especially as there are no tutorials yet (your questions can help us know what to write first!)

Thank you for your support and interest in Kobweb!