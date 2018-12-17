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
        "https://w3c.github.io/webrtc-pc/" to "org.w3c.dom.webrtc",

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

sealed class EventMapKey
data class SimpleEventMapKey(val name: String) : EventMapKey()
data class ContextAwareEventMapKey(val name: String, val context: String) : EventMapKey()

private fun String.toSimpleEventMapKey() = SimpleEventMapKey(this)
private fun String.toContextAwareEventMapKey(context: String) = ContextAwareEventMapKey(this, context)

val specifyEventMapper = mapOf<EventMapKey, String>(
    "onbeforeunload".toSimpleEventMapKey() to "BeforeUnloadEvent",

    "ondrag".toSimpleEventMapKey() to "DragEvent",
    "ondragend".toSimpleEventMapKey() to "DragEvent",
    "ondragenter".toSimpleEventMapKey() to "DragEvent",
    "ondragexit".toSimpleEventMapKey() to "DragEvent",
    "ondragleave".toSimpleEventMapKey() to "DragEvent",
    "ondragover".toSimpleEventMapKey() to "DragEvent",
    "ondragstart".toSimpleEventMapKey() to "DragEvent",
    "ondrop".toSimpleEventMapKey() to "DragEvent",

    "onfetch".toSimpleEventMapKey() to "FetchEvent",

    "onblur".toSimpleEventMapKey() to "FocusEvent",
    "onfocus".toSimpleEventMapKey() to "FocusEvent",

    "onhashchange".toSimpleEventMapKey() to "HashChangeEvent",

    "oninput".toSimpleEventMapKey() to "InputEvent",

    "onkeydown".toSimpleEventMapKey() to "KeyboardEvent",
    "onkeypress".toSimpleEventMapKey() to "KeyboardEvent",
    "onkeyup".toSimpleEventMapKey() to "KeyboardEvent",

    "onaddtrack".toContextAwareEventMapKey("MediaStream") to "MediaStreamTrackEvent",
    "onremovetrack".toContextAwareEventMapKey("MediaStream") to "MediaStreamTrackEvent",

    "onmessage".toSimpleEventMapKey() to "MessageEvent",

    "onclick".toSimpleEventMapKey() to "MouseEvent",
    "oncontextmenu".toSimpleEventMapKey() to "MouseEvent",
    "ondblclick".toSimpleEventMapKey() to "MouseEvent",
    "onmousedown".toSimpleEventMapKey() to "MouseEvent",
    "onmouseenter".toSimpleEventMapKey() to "MouseEvent",
    "onmouseleave".toSimpleEventMapKey() to "MouseEvent",
    "onmousemove".toSimpleEventMapKey() to "MouseEvent",
    "onmouseout".toSimpleEventMapKey() to "MouseEvent",
    "onmouseover".toSimpleEventMapKey() to "MouseEvent",
    "onmouseup".toSimpleEventMapKey() to "MouseEvent",

    "onnotificationclick".toSimpleEventMapKey() to "NotificationEvent",
    "onnotificationclose".toSimpleEventMapKey() to "NotificationEvent",

    "onpagehide".toSimpleEventMapKey() to "PageTransitionEvent",
    "onpageshow".toSimpleEventMapKey() to "PageTransitionEvent",

    "ongotpointercapture".toSimpleEventMapKey() to "PointerEvent",
    "onlostpointercapture".toSimpleEventMapKey() to "PointerEvent",
    "onpointercancel".toSimpleEventMapKey() to "PointerEvent",
    "onpointerdown".toSimpleEventMapKey() to "PointerEvent",
    "onpointerenter".toSimpleEventMapKey() to "PointerEvent",
    "onpointerleave".toSimpleEventMapKey() to "PointerEvent",
    "onpointermove".toSimpleEventMapKey() to "PointerEvent",
    "onpointerout".toSimpleEventMapKey() to "PointerEvent",
    "onpointerover".toSimpleEventMapKey() to "PointerEvent",
    "onpointerup".toSimpleEventMapKey() to "PointerEvent",

    "onpopstate".toSimpleEventMapKey() to "PopStateEvent",

    "onloadstart".toSimpleEventMapKey() to "ProgressEvent",
    "onprogress".toSimpleEventMapKey() to "ProgressEvent",

    "onunhandledrejection".toSimpleEventMapKey() to "PromiseRejectionEvent",

    "ontrack".toContextAwareEventMapKey("RTCPeerConnection") to "RTCTrackEvent",

    "onstorage".toSimpleEventMapKey() to "StorageEvent",

    "onaddtrack".toContextAwareEventMapKey("AudioTrackList") to "TrackEvent",
    "onaddtrack".toContextAwareEventMapKey("TextTrackList") to "TrackEvent",
    "onaddtrack".toContextAwareEventMapKey("VideoTrackList") to "TrackEvent",
    "onremovetrack".toContextAwareEventMapKey("AudioTrackList") to "TrackEvent",
    "onremovetrack".toContextAwareEventMapKey("TextTrackList") to "TrackEvent",
    "onremovetrack".toContextAwareEventMapKey("VideoTrackList") to "TrackEvent",

    "onwheel".toSimpleEventMapKey() to "WheelEvent"
)
