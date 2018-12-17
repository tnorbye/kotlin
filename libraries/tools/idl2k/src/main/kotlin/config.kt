/*
 * Copyright 2010-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.idl2k

val urls = listOf(
        "https://raw.githubusercontent.com/whatwg/html-mirror/master/source" to "org.w3c.dom",
        "http://html.spec.whatwg.org/" to "org.w3c.dom",
        "https://raw.githubusercontent.com/whatwg/dom/master/dom.html" to "org.w3c.dom",
        "https://dvcs.w3.org/hg/editing/raw-file/tip/editing.html" to "org.w3c.dom",
        "http://www.w3.org/TR/animation-timing/" to "org.w3c.dom",
        "http://www.w3.org/TR/geometry-1/" to "org.w3c.dom",
        "http://www.w3.org/TR/cssom-view/" to "org.w3c.dom",
        "http://www.w3.org/TR/uievents/" to "org.w3c.dom.events",
        "http://www.w3.org/TR/pointerevents/" to "org.w3c.dom.pointerevents",

        "https://drafts.csswg.org/cssom/" to "org.w3c.dom.css",
        "https://w3c.github.io/mediacapture-main/" to "org.w3c.dom.mediacapture",
        "http://www.w3.org/TR/DOM-Parsing/" to "org.w3c.dom.parsing",
        "https://raw.githubusercontent.com/whatwg/url/master/url.html" to "org.w3c.dom.url",

        "https://www.w3.org/TR/SVG2/single-page.html" to "org.w3c.dom.svg",
        "https://www.khronos.org/registry/webgl/specs/latest/1.0/webgl.idl" to "org.khronos.webgl",
        "https://www.khronos.org/registry/typedarray/specs/latest/typedarray.idl" to "org.khronos.webgl",

        "https://raw.githubusercontent.com/whatwg/xhr/master/Overview.src.html" to "org.w3c.xhr",
        "https://raw.githubusercontent.com/whatwg/fetch/master/Overview.src.html" to "org.w3c.fetch",
        "https://raw.githubusercontent.com/w3c/FileAPI/gh-pages/index.html" to "org.w3c.files",

        "https://raw.githubusercontent.com/whatwg/notifications/master/notifications.html" to "org.w3c.notifications",
        "https://raw.githubusercontent.com/whatwg/fullscreen/master/fullscreen.html" to "org.w3c.fullscreen",
        "http://www.w3.org/TR/vibration/" to "org.w3c.vibration",

        "http://www.w3.org/TR/hr-time/" to "org.w3c.performance",
        "http://www.w3.org/TR/2012/REC-navigation-timing-20121217/" to "org.w3c.performance",

        "https://w3c.github.io/ServiceWorker/" to "org.w3c.workers"
)

val relocations = mapOf(
        "Event" to "org.w3c.dom.events",
        "EventTarget" to "org.w3c.dom.events",
        "EventListener" to "org.w3c.dom.events"
)

val commentOutDeclarations = setOf(
        "MouseEvent.screenX: Double", "MouseEvent.screenY: Double",
        "MouseEvent.clientX: Double", "MouseEvent.clientY: Double",
        "MouseEvent.x: Double", "MouseEvent.y: Double",

        "HTMLAllCollection.namedItem",
        "HTMLAllCollection.get",

        "HTMLFormControlsCollection.namedItem",
        "HTMLFormControlsCollection.get",

        "HTMLPropertiesCollection.namedItem",
        "HTMLPropertiesCollection.get",

        "SVGElement.id"
)

val requiredArguments = setOf(
        "DOMPoint.constructor.point",
        "DOMQuad.constructor.rect"
)

val inheritanceExclude = mapOf(
        "SVGAElement" to setOf("HTMLHyperlinkElementUtils")
)

val kotlinBuiltinInterfaces = mapOf(
        "ItemArrayLike" to GenerateTraitOrClass("ItemArrayLike", "org.w3c.dom", GenerateDefinitionKind.INTERFACE, emptyList(),
                memberAttributes = mutableListOf(GenerateAttribute("length", SimpleType("Int", false), null, false, AttributeKind.VAL, false, false, false, false)),
                memberFunctions = mutableListOf(GenerateFunction("item", DynamicType, listOf(
                        GenerateAttribute("index", SimpleType("Int", false), null, false, AttributeKind.ARGUMENT, false, false, false, false)
                ), NativeGetterOrSetter.NONE, false, false)),
                constants = emptyList(),
                generateBuilderFunction = false,
                primaryConstructor = null,
                secondaryConstructors = emptyList()
                )
)

data class EventMapKey(val name: String, val context: String? = null)
private fun String.asEventMapKey(context: String? = null) = EventMapKey(this, context)

val specifyEventMapper = mapOf<EventMapKey, String>(
    "onbeforeunload".asEventMapKey() to "BeforeUnloadEvent",

    "ondrag".asEventMapKey() to "DragEvent",
    "ondragend".asEventMapKey() to "DragEvent",
    "ondragenter".asEventMapKey() to "DragEvent",
    "ondragexit".asEventMapKey() to "DragEvent",
    "ondragleave".asEventMapKey() to "DragEvent",
    "ondragover".asEventMapKey() to "DragEvent",
    "ondragstart".asEventMapKey() to "DragEvent",
    "ondrop".asEventMapKey() to "DragEvent",

    "onfetch".asEventMapKey() to "FetchEvent",

    "onblur".asEventMapKey() to "FocusEvent",
    "onfocus".asEventMapKey() to "FocusEvent",

    "onhashchange".asEventMapKey() to "HashChangeEvent",

    "oninput".asEventMapKey() to "InputEvent",

    "onkeydown".asEventMapKey() to "KeyboardEvent",
    "onkeypress".asEventMapKey() to "KeyboardEvent",
    "onkeyup".asEventMapKey() to "KeyboardEvent",

    "onaddtrack".asEventMapKey("MediaStream") to "MediaStreamTrackEvent",
    "onremovetrack".asEventMapKey("MediaStream") to "MediaStreamTrackEvent",

    "onmessage".asEventMapKey() to "MessageEvent",

    "onclick".asEventMapKey() to "MouseEvent",
    "oncontextmenu".asEventMapKey() to "MouseEvent",
    "ondblclick".asEventMapKey() to "MouseEvent",
    "onmousedown".asEventMapKey() to "MouseEvent",
    "onmouseenter".asEventMapKey() to "MouseEvent",
    "onmouseleave".asEventMapKey() to "MouseEvent",
    "onmousemove".asEventMapKey() to "MouseEvent",
    "onmouseout".asEventMapKey() to "MouseEvent",
    "onmouseover".asEventMapKey() to "MouseEvent",
    "onmouseup".asEventMapKey() to "MouseEvent",

    "onnotificationclick".asEventMapKey() to "NotificationEvent",
    "onnotificationclose".asEventMapKey() to "NotificationEvent",

    "onpagehide".asEventMapKey() to "PageTransitionEvent",
    "onpageshow".asEventMapKey() to "PageTransitionEvent",

    "ongotpointercapture".asEventMapKey() to "PointerEvent",
    "onlostpointercapture".asEventMapKey() to "PointerEvent",
    "onpointercancel".asEventMapKey() to "PointerEvent",
    "onpointerdown".asEventMapKey() to "PointerEvent",
    "onpointerenter".asEventMapKey() to "PointerEvent",
    "onpointerleave".asEventMapKey() to "PointerEvent",
    "onpointermove".asEventMapKey() to "PointerEvent",
    "onpointerout".asEventMapKey() to "PointerEvent",
    "onpointerover".asEventMapKey() to "PointerEvent",
    "onpointerup".asEventMapKey() to "PointerEvent",

    "onpopstate".asEventMapKey() to "PopStateEvent",

    "onloadstart".asEventMapKey() to "ProgressEvent",
    "onprogress".asEventMapKey() to "ProgressEvent",

    "onunhandledrejection".asEventMapKey() to "PromiseRejectionEvent",

    "onstorage".asEventMapKey() to "StorageEvent",

    "onaddtrack".asEventMapKey("AudioTrackList") to "TrackEvent",
    "onaddtrack".asEventMapKey("TextTrackList") to "TrackEvent",
    "onaddtrack".asEventMapKey("VideoTrackList") to "TrackEvent",
    "onremovetrack".asEventMapKey("AudioTrackList") to "TrackEvent",
    "onremovetrack".asEventMapKey("TextTrackList") to "TrackEvent",
    "onremovetrack".asEventMapKey("VideoTrackList") to "TrackEvent",

    "onwheel".asEventMapKey() to "WheelEvent"
)
