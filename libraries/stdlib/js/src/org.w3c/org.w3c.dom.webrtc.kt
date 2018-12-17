/*
 * Copyright 2010-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license 
 * that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See libraries/tools/idl2k for details

@file:Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
package org.w3c.dom.webrtc

import kotlin.js.*
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.css.*
import org.w3c.dom.events.*
import org.w3c.dom.mediacapture.*
import org.w3c.dom.parsing.*
import org.w3c.dom.pointerevents.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*

/**
 * Exposes the JavaScript [RTCConfiguration](https://developer.mozilla.org/en/docs/Web/API/RTCConfiguration) to Kotlin
 */
public external interface RTCConfiguration {
    var iceServers: Array<RTCIceServer>?
        get() = definedExternally
        set(value) = definedExternally
    var iceTransportPolicy: RTCIceTransportPolicy? /* = RTCIceTransportPolicy.ALL */
        get() = definedExternally
        set(value) = definedExternally
    var bundlePolicy: RTCBundlePolicy? /* = RTCBundlePolicy.BALANCED */
        get() = definedExternally
        set(value) = definedExternally
    var rtcpMuxPolicy: RTCRtcpMuxPolicy? /* = RTCRtcpMuxPolicy.REQUIRE */
        get() = definedExternally
        set(value) = definedExternally
    var peerIdentity: String?
        get() = definedExternally
        set(value) = definedExternally
    var certificates: Array<RTCCertificate>?
        get() = definedExternally
        set(value) = definedExternally
    var iceCandidatePoolSize: Byte? /* = 0 */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCConfiguration(iceServers: Array<RTCIceServer>? = undefined, iceTransportPolicy: RTCIceTransportPolicy? = RTCIceTransportPolicy.ALL, bundlePolicy: RTCBundlePolicy? = RTCBundlePolicy.BALANCED, rtcpMuxPolicy: RTCRtcpMuxPolicy? = RTCRtcpMuxPolicy.REQUIRE, peerIdentity: String? = undefined, certificates: Array<RTCCertificate>? = undefined, iceCandidatePoolSize: Byte? = 0): RTCConfiguration {
    val o = js("({})")

    o["iceServers"] = iceServers
    o["iceTransportPolicy"] = iceTransportPolicy
    o["bundlePolicy"] = bundlePolicy
    o["rtcpMuxPolicy"] = rtcpMuxPolicy
    o["peerIdentity"] = peerIdentity
    o["certificates"] = certificates
    o["iceCandidatePoolSize"] = iceCandidatePoolSize

    return o
}

public external interface RTCOAuthCredential {
    var macKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var accessToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCOAuthCredential(macKey: String?, accessToken: String?): RTCOAuthCredential {
    val o = js("({})")

    o["macKey"] = macKey
    o["accessToken"] = accessToken

    return o
}

/**
 * Exposes the JavaScript [RTCIceServer](https://developer.mozilla.org/en/docs/Web/API/RTCIceServer) to Kotlin
 */
public external interface RTCIceServer {
    var urls: dynamic
        get() = definedExternally
        set(value) = definedExternally
    var username: String?
        get() = definedExternally
        set(value) = definedExternally
    var credential: dynamic
        get() = definedExternally
        set(value) = definedExternally
    var credentialType: RTCIceCredentialType? /* = RTCIceCredentialType.PASSWORD */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCIceServer(urls: dynamic, username: String? = undefined, credential: dynamic = undefined, credentialType: RTCIceCredentialType? = RTCIceCredentialType.PASSWORD): RTCIceServer {
    val o = js("({})")

    o["urls"] = urls
    o["username"] = username
    o["credential"] = credential
    o["credentialType"] = credentialType

    return o
}

/**
 * Exposes the JavaScript [RTCOfferAnswerOptions](https://developer.mozilla.org/en/docs/Web/API/RTCOfferAnswerOptions) to Kotlin
 */
public external interface RTCOfferAnswerOptions {
    var voiceActivityDetection: Boolean? /* = true */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCOfferAnswerOptions(voiceActivityDetection: Boolean? = true): RTCOfferAnswerOptions {
    val o = js("({})")

    o["voiceActivityDetection"] = voiceActivityDetection

    return o
}

/**
 * Exposes the JavaScript [RTCOfferOptions](https://developer.mozilla.org/en/docs/Web/API/RTCOfferOptions) to Kotlin
 */
public external interface RTCOfferOptions : RTCOfferAnswerOptions {
    var iceRestart: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCOfferOptions(iceRestart: Boolean? = false, voiceActivityDetection: Boolean? = true): RTCOfferOptions {
    val o = js("({})")

    o["iceRestart"] = iceRestart
    o["voiceActivityDetection"] = voiceActivityDetection

    return o
}

/**
 * Exposes the JavaScript [RTCAnswerOptions](https://developer.mozilla.org/en/docs/Web/API/RTCAnswerOptions) to Kotlin
 */
public external interface RTCAnswerOptions : RTCOfferAnswerOptions {
}

@kotlin.internal.InlineOnly
public inline fun RTCAnswerOptions(voiceActivityDetection: Boolean? = true): RTCAnswerOptions {
    val o = js("({})")

    o["voiceActivityDetection"] = voiceActivityDetection

    return o
}

/**
 * Exposes the JavaScript [RTCPeerConnection](https://developer.mozilla.org/en/docs/Web/API/RTCPeerConnection) to Kotlin
 */
public external open class RTCPeerConnection(configuration: RTCConfiguration = definedExternally) : EventTarget {
    open val localDescription: RTCSessionDescription?
    open val currentLocalDescription: RTCSessionDescription?
    open val pendingLocalDescription: RTCSessionDescription?
    open val remoteDescription: RTCSessionDescription?
    open val currentRemoteDescription: RTCSessionDescription?
    open val pendingRemoteDescription: RTCSessionDescription?
    open val signalingState: RTCSignalingState
    open val iceGatheringState: RTCIceGatheringState
    open val iceConnectionState: RTCIceConnectionState
    open val connectionState: RTCPeerConnectionState
    open val canTrickleIceCandidates: Boolean?
    var onnegotiationneeded: ((Event) -> dynamic)?
    var onicecandidate: ((Event) -> dynamic)?
    var onicecandidateerror: ((Event) -> dynamic)?
    var onsignalingstatechange: ((Event) -> dynamic)?
    var oniceconnectionstatechange: ((Event) -> dynamic)?
    var onicegatheringstatechange: ((Event) -> dynamic)?
    var onconnectionstatechange: ((Event) -> dynamic)?
    var ontrack: ((Event) -> dynamic)?
    open val sctp: RTCSctpTransport?
    var ondatachannel: ((Event) -> dynamic)?
    var onstatsended: ((Event) -> dynamic)?
    fun createOffer(options: RTCOfferOptions = definedExternally): Promise<RTCSessionDescriptionInit>
    fun createAnswer(options: RTCAnswerOptions = definedExternally): Promise<RTCSessionDescriptionInit>
    fun setLocalDescription(description: RTCSessionDescriptionInit): Promise<Unit>
    fun setRemoteDescription(description: RTCSessionDescriptionInit): Promise<Unit>
    fun addIceCandidate(candidate: RTCIceCandidateInit): Promise<Unit>
    fun getConfiguration(): RTCConfiguration
    fun setConfiguration(configuration: RTCConfiguration): Unit
    fun close(): Unit
    fun createOffer(successCallback: (RTCSessionDescriptionInit) -> Unit, failureCallback: (dynamic) -> Unit, options: RTCOfferOptions = definedExternally): Promise<Unit>
    fun setLocalDescription(description: RTCSessionDescriptionInit, successCallback: () -> Unit, failureCallback: (dynamic) -> Unit): Promise<Unit>
    fun createAnswer(successCallback: (RTCSessionDescriptionInit) -> Unit, failureCallback: (dynamic) -> Unit): Promise<Unit>
    fun setRemoteDescription(description: RTCSessionDescriptionInit, successCallback: () -> Unit, failureCallback: (dynamic) -> Unit): Promise<Unit>
    fun addIceCandidate(candidate: RTCIceCandidateInit, successCallback: () -> Unit, failureCallback: (dynamic) -> Unit): Promise<Unit>
    fun getSenders(): Array<RTCRtpSender>
    fun getReceivers(): Array<RTCRtpReceiver>
    fun getTransceivers(): Array<RTCRtpTransceiver>
    fun addTrack(track: MediaStreamTrack, vararg streams: MediaStream): RTCRtpSender
    fun removeTrack(sender: RTCRtpSender): Unit
    fun addTransceiver(trackOrKind: dynamic, init: RTCRtpTransceiverInit = definedExternally): RTCRtpTransceiver
    fun createDataChannel(label: String, dataChannelDict: RTCDataChannelInit = definedExternally): RTCDataChannel
    fun getStats(selector: MediaStreamTrack? = definedExternally): Promise<RTCStatsReport>

    companion object {
        fun getDefaultIceServers(): Array<RTCIceServer>
        fun generateCertificate(keygenAlgorithm: AlgorithmIdentifier): Promise<RTCCertificate>
    }
}

/**
 * Exposes the JavaScript [RTCSessionDescription](https://developer.mozilla.org/en/docs/Web/API/RTCSessionDescription) to Kotlin
 */
public external open class RTCSessionDescription(descriptionInitDict: RTCSessionDescriptionInit) {
    open val type: RTCSdpType
    open val sdp: String
    fun toJSON(): dynamic
}

public external interface RTCSessionDescriptionInit {
    var type: RTCSdpType?
        get() = definedExternally
        set(value) = definedExternally
    var sdp: String? /* = "" */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCSessionDescriptionInit(type: RTCSdpType?, sdp: String? = ""): RTCSessionDescriptionInit {
    val o = js("({})")

    o["type"] = type
    o["sdp"] = sdp

    return o
}

/**
 * Exposes the JavaScript [RTCIceCandidate](https://developer.mozilla.org/en/docs/Web/API/RTCIceCandidate) to Kotlin
 */
public external open class RTCIceCandidate(candidateInitDict: RTCIceCandidateInit = definedExternally) {
    open val candidate: String
    open val sdpMid: String?
    open val sdpMLineIndex: Short?
    open val foundation: String?
    open val component: RTCIceComponent?
    open val priority: Int?
    open val address: String?
    open val protocol: RTCIceProtocol?
    open val port: Short?
    open val type: RTCIceCandidateType?
    open val tcpType: RTCIceTcpCandidateType?
    open val relatedAddress: String?
    open val relatedPort: Short?
    open val usernameFragment: String?
    fun toJSON(): RTCIceCandidateInit
}

/**
 * Exposes the JavaScript [RTCIceCandidateInit](https://developer.mozilla.org/en/docs/Web/API/RTCIceCandidateInit) to Kotlin
 */
public external interface RTCIceCandidateInit {
    var candidate: String? /* = "" */
        get() = definedExternally
        set(value) = definedExternally
    var sdpMid: String? /* = null */
        get() = definedExternally
        set(value) = definedExternally
    var sdpMLineIndex: Short? /* = null */
        get() = definedExternally
        set(value) = definedExternally
    var usernameFragment: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCIceCandidateInit(candidate: String? = "", sdpMid: String? = null, sdpMLineIndex: Short? = null, usernameFragment: String? = undefined): RTCIceCandidateInit {
    val o = js("({})")

    o["candidate"] = candidate
    o["sdpMid"] = sdpMid
    o["sdpMLineIndex"] = sdpMLineIndex
    o["usernameFragment"] = usernameFragment

    return o
}

/**
 * Exposes the JavaScript [RTCPeerConnectionIceEvent](https://developer.mozilla.org/en/docs/Web/API/RTCPeerConnectionIceEvent) to Kotlin
 */
public external open class RTCPeerConnectionIceEvent(type: String, eventInitDict: RTCPeerConnectionIceEventInit = definedExternally) : Event {
    open val candidate: RTCIceCandidate?
    open val url: String?
}

public external interface RTCPeerConnectionIceEventInit : EventInit {
    var candidate: RTCIceCandidate?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCPeerConnectionIceEventInit(candidate: RTCIceCandidate? = undefined, url: String? = undefined, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCPeerConnectionIceEventInit {
    val o = js("({})")

    o["candidate"] = candidate
    o["url"] = url
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external open class RTCPeerConnectionIceErrorEvent(type: String, eventInitDict: RTCPeerConnectionIceErrorEventInit) : Event {
    open val hostCandidate: String
    open val url: String
    open val errorCode: Short
    open val errorText: String
}

public external interface RTCPeerConnectionIceErrorEventInit : EventInit {
    var hostCandidate: String?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var errorCode: Short?
        get() = definedExternally
        set(value) = definedExternally
    var statusText: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCPeerConnectionIceErrorEventInit(hostCandidate: String? = undefined, url: String? = undefined, errorCode: Short?, statusText: String? = undefined, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCPeerConnectionIceErrorEventInit {
    val o = js("({})")

    o["hostCandidate"] = hostCandidate
    o["url"] = url
    o["errorCode"] = errorCode
    o["statusText"] = statusText
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external interface RTCCertificateExpiration {
    var expires: Number?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCCertificateExpiration(expires: Number? = undefined): RTCCertificateExpiration {
    val o = js("({})")

    o["expires"] = expires

    return o
}

/**
 * Exposes the JavaScript [RTCCertificate](https://developer.mozilla.org/en/docs/Web/API/RTCCertificate) to Kotlin
 */
public external abstract class RTCCertificate {
    open val expires: Number
    fun getFingerprints(): Array<RTCDtlsFingerprint>

    companion object {
        fun getSupportedAlgorithms(): Array<AlgorithmIdentifier>
    }
}

/**
 * Exposes the JavaScript [RTCRtpTransceiverInit](https://developer.mozilla.org/en/docs/Web/API/RTCRtpTransceiverInit) to Kotlin
 */
public external interface RTCRtpTransceiverInit {
    var direction: RTCRtpTransceiverDirection? /* = RTCRtpTransceiverDirection.SENDRECV */
        get() = definedExternally
        set(value) = definedExternally
    var streams: Array<MediaStream>? /* = arrayOf() */
        get() = definedExternally
        set(value) = definedExternally
    var sendEncodings: Array<RTCRtpEncodingParameters>? /* = arrayOf() */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpTransceiverInit(direction: RTCRtpTransceiverDirection? = RTCRtpTransceiverDirection.SENDRECV, streams: Array<MediaStream>? = arrayOf(), sendEncodings: Array<RTCRtpEncodingParameters>? = arrayOf()): RTCRtpTransceiverInit {
    val o = js("({})")

    o["direction"] = direction
    o["streams"] = streams
    o["sendEncodings"] = sendEncodings

    return o
}

/**
 * Exposes the JavaScript [RTCRtpSender](https://developer.mozilla.org/en/docs/Web/API/RTCRtpSender) to Kotlin
 */
public external abstract class RTCRtpSender {
    open val track: MediaStreamTrack?
    open val transport: RTCDtlsTransport?
    open val rtcpTransport: RTCDtlsTransport?
    open val dtmf: RTCDTMFSender?
    fun setParameters(parameters: RTCRtpSendParameters): Promise<Unit>
    fun getParameters(): RTCRtpSendParameters
    fun replaceTrack(withTrack: MediaStreamTrack?): Promise<Unit>
    fun setStreams(vararg streams: MediaStream): Unit
    fun getStats(): Promise<RTCStatsReport>

    companion object {
        fun getCapabilities(kind: String): RTCRtpCapabilities?
    }
}

public external interface RTCRtpParameters {
    var headerExtensions: Array<RTCRtpHeaderExtensionParameters>?
        get() = definedExternally
        set(value) = definedExternally
    var rtcp: RTCRtcpParameters?
        get() = definedExternally
        set(value) = definedExternally
    var codecs: Array<RTCRtpCodecParameters>?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpParameters(headerExtensions: Array<RTCRtpHeaderExtensionParameters>?, rtcp: RTCRtcpParameters?, codecs: Array<RTCRtpCodecParameters>?): RTCRtpParameters {
    val o = js("({})")

    o["headerExtensions"] = headerExtensions
    o["rtcp"] = rtcp
    o["codecs"] = codecs

    return o
}

/**
 * Exposes the JavaScript [RTCRtpSendParameters](https://developer.mozilla.org/en/docs/Web/API/RTCRtpSendParameters) to Kotlin
 */
public external interface RTCRtpSendParameters : RTCRtpParameters {
    var transactionId: String?
        get() = definedExternally
        set(value) = definedExternally
    var encodings: Array<RTCRtpEncodingParameters>?
        get() = definedExternally
        set(value) = definedExternally
    var degradationPreference: RTCDegradationPreference? /* = RTCDegradationPreference.BALANCED */
        get() = definedExternally
        set(value) = definedExternally
    var priority: RTCPriorityType? /* = RTCPriorityType.LOW */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpSendParameters(transactionId: String?, encodings: Array<RTCRtpEncodingParameters>?, degradationPreference: RTCDegradationPreference? = RTCDegradationPreference.BALANCED, priority: RTCPriorityType? = RTCPriorityType.LOW, headerExtensions: Array<RTCRtpHeaderExtensionParameters>?, rtcp: RTCRtcpParameters?, codecs: Array<RTCRtpCodecParameters>?): RTCRtpSendParameters {
    val o = js("({})")

    o["transactionId"] = transactionId
    o["encodings"] = encodings
    o["degradationPreference"] = degradationPreference
    o["priority"] = priority
    o["headerExtensions"] = headerExtensions
    o["rtcp"] = rtcp
    o["codecs"] = codecs

    return o
}

public external interface RTCRtpReceiveParameters : RTCRtpParameters {
    var encodings: Array<RTCRtpDecodingParameters>?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpReceiveParameters(encodings: Array<RTCRtpDecodingParameters>?, headerExtensions: Array<RTCRtpHeaderExtensionParameters>?, rtcp: RTCRtcpParameters?, codecs: Array<RTCRtpCodecParameters>?): RTCRtpReceiveParameters {
    val o = js("({})")

    o["encodings"] = encodings
    o["headerExtensions"] = headerExtensions
    o["rtcp"] = rtcp
    o["codecs"] = codecs

    return o
}

public external interface RTCRtpCodingParameters {
    var rid: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpCodingParameters(rid: String? = undefined): RTCRtpCodingParameters {
    val o = js("({})")

    o["rid"] = rid

    return o
}

public external interface RTCRtpDecodingParameters : RTCRtpCodingParameters {
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpDecodingParameters(rid: String? = undefined): RTCRtpDecodingParameters {
    val o = js("({})")

    o["rid"] = rid

    return o
}

/**
 * Exposes the JavaScript [RTCRtpEncodingParameters](https://developer.mozilla.org/en/docs/Web/API/RTCRtpEncodingParameters) to Kotlin
 */
public external interface RTCRtpEncodingParameters : RTCRtpCodingParameters {
    var codecPayloadType: Byte?
        get() = definedExternally
        set(value) = definedExternally
    var dtx: RTCDtxStatus?
        get() = definedExternally
        set(value) = definedExternally
    var active: Boolean? /* = true */
        get() = definedExternally
        set(value) = definedExternally
    var ptime: Int?
        get() = definedExternally
        set(value) = definedExternally
    var maxBitrate: Int?
        get() = definedExternally
        set(value) = definedExternally
    var maxFramerate: Double?
        get() = definedExternally
        set(value) = definedExternally
    var scaleResolutionDownBy: Double?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpEncodingParameters(codecPayloadType: Byte? = undefined, dtx: RTCDtxStatus? = undefined, active: Boolean? = true, ptime: Int? = undefined, maxBitrate: Int? = undefined, maxFramerate: Double? = undefined, scaleResolutionDownBy: Double? = undefined, rid: String? = undefined): RTCRtpEncodingParameters {
    val o = js("({})")

    o["codecPayloadType"] = codecPayloadType
    o["dtx"] = dtx
    o["active"] = active
    o["ptime"] = ptime
    o["maxBitrate"] = maxBitrate
    o["maxFramerate"] = maxFramerate
    o["scaleResolutionDownBy"] = scaleResolutionDownBy
    o["rid"] = rid

    return o
}

public external interface RTCRtcpParameters {
    var cname: String?
        get() = definedExternally
        set(value) = definedExternally
    var reducedSize: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtcpParameters(cname: String? = undefined, reducedSize: Boolean? = undefined): RTCRtcpParameters {
    val o = js("({})")

    o["cname"] = cname
    o["reducedSize"] = reducedSize

    return o
}

public external interface RTCRtpHeaderExtensionParameters {
    var uri: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: Short?
        get() = definedExternally
        set(value) = definedExternally
    var encrypted: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpHeaderExtensionParameters(uri: String?, id: Short?, encrypted: Boolean? = false): RTCRtpHeaderExtensionParameters {
    val o = js("({})")

    o["uri"] = uri
    o["id"] = id
    o["encrypted"] = encrypted

    return o
}

/**
 * Exposes the JavaScript [RTCRtpCodecParameters](https://developer.mozilla.org/en/docs/Web/API/RTCRtpCodecParameters) to Kotlin
 */
public external interface RTCRtpCodecParameters {
    var payloadType: Byte?
        get() = definedExternally
        set(value) = definedExternally
    var mimeType: String?
        get() = definedExternally
        set(value) = definedExternally
    var clockRate: Int?
        get() = definedExternally
        set(value) = definedExternally
    var channels: Short?
        get() = definedExternally
        set(value) = definedExternally
    var sdpFmtpLine: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpCodecParameters(payloadType: Byte?, mimeType: String?, clockRate: Int?, channels: Short? = undefined, sdpFmtpLine: String? = undefined): RTCRtpCodecParameters {
    val o = js("({})")

    o["payloadType"] = payloadType
    o["mimeType"] = mimeType
    o["clockRate"] = clockRate
    o["channels"] = channels
    o["sdpFmtpLine"] = sdpFmtpLine

    return o
}

public external interface RTCRtpCapabilities {
    var codecs: Array<RTCRtpCodecCapability>?
        get() = definedExternally
        set(value) = definedExternally
    var headerExtensions: Array<RTCRtpHeaderExtensionCapability>?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpCapabilities(codecs: Array<RTCRtpCodecCapability>?, headerExtensions: Array<RTCRtpHeaderExtensionCapability>?): RTCRtpCapabilities {
    val o = js("({})")

    o["codecs"] = codecs
    o["headerExtensions"] = headerExtensions

    return o
}

public external interface RTCRtpCodecCapability {
    var mimeType: String?
        get() = definedExternally
        set(value) = definedExternally
    var clockRate: Int?
        get() = definedExternally
        set(value) = definedExternally
    var channels: Short?
        get() = definedExternally
        set(value) = definedExternally
    var sdpFmtpLine: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpCodecCapability(mimeType: String?, clockRate: Int?, channels: Short? = undefined, sdpFmtpLine: String? = undefined): RTCRtpCodecCapability {
    val o = js("({})")

    o["mimeType"] = mimeType
    o["clockRate"] = clockRate
    o["channels"] = channels
    o["sdpFmtpLine"] = sdpFmtpLine

    return o
}

public external interface RTCRtpHeaderExtensionCapability {
    var uri: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpHeaderExtensionCapability(uri: String? = undefined): RTCRtpHeaderExtensionCapability {
    val o = js("({})")

    o["uri"] = uri

    return o
}

/**
 * Exposes the JavaScript [RTCRtpReceiver](https://developer.mozilla.org/en/docs/Web/API/RTCRtpReceiver) to Kotlin
 */
public external abstract class RTCRtpReceiver {
    open val track: MediaStreamTrack
    open val transport: RTCDtlsTransport?
    open val rtcpTransport: RTCDtlsTransport?
    fun getParameters(): RTCRtpReceiveParameters
    fun getContributingSources(): Array<RTCRtpContributingSource>
    fun getSynchronizationSources(): Array<RTCRtpSynchronizationSource>
    fun getStats(): Promise<RTCStatsReport>

    companion object {
        fun getCapabilities(kind: String): RTCRtpCapabilities?
    }
}

/**
 * Exposes the JavaScript [RTCRtpContributingSource](https://developer.mozilla.org/en/docs/Web/API/RTCRtpContributingSource) to Kotlin
 */
public external interface RTCRtpContributingSource {
    var timestamp: Double?
        get() = definedExternally
        set(value) = definedExternally
    var source: Int?
        get() = definedExternally
        set(value) = definedExternally
    var audioLevel: Double?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpContributingSource(timestamp: Double?, source: Int?, audioLevel: Double? = undefined): RTCRtpContributingSource {
    val o = js("({})")

    o["timestamp"] = timestamp
    o["source"] = source
    o["audioLevel"] = audioLevel

    return o
}

/**
 * Exposes the JavaScript [RTCRtpSynchronizationSource](https://developer.mozilla.org/en/docs/Web/API/RTCRtpSynchronizationSource) to Kotlin
 */
public external interface RTCRtpSynchronizationSource : RTCRtpContributingSource {
    var voiceActivityFlag: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCRtpSynchronizationSource(voiceActivityFlag: Boolean? = undefined, timestamp: Double?, source: Int?, audioLevel: Double? = undefined): RTCRtpSynchronizationSource {
    val o = js("({})")

    o["voiceActivityFlag"] = voiceActivityFlag
    o["timestamp"] = timestamp
    o["source"] = source
    o["audioLevel"] = audioLevel

    return o
}

/**
 * Exposes the JavaScript [RTCRtpTransceiver](https://developer.mozilla.org/en/docs/Web/API/RTCRtpTransceiver) to Kotlin
 */
public external abstract class RTCRtpTransceiver {
    open val mid: String?
    open val sender: RTCRtpSender
    open val receiver: RTCRtpReceiver
    open val stopped: Boolean
    open var direction: RTCRtpTransceiverDirection
    open val currentDirection: RTCRtpTransceiverDirection?
    fun stop(): Unit
    fun setCodecPreferences(codecs: Array<RTCRtpCodecCapability>): Unit
}

public external abstract class RTCDtlsTransport : EventTarget {
    open val iceTransport: RTCIceTransport
    open val state: RTCDtlsTransportState
    open var onstatechange: ((Event) -> dynamic)?
    open var onerror: ((Event) -> dynamic)?
    fun getRemoteCertificates(): Array<ArrayBuffer>
}

public external interface RTCDtlsFingerprint {
    var algorithm: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCDtlsFingerprint(algorithm: String? = undefined, value: String? = undefined): RTCDtlsFingerprint {
    val o = js("({})")

    o["algorithm"] = algorithm
    o["value"] = value

    return o
}

/**
 * Exposes the JavaScript [RTCIceTransport](https://developer.mozilla.org/en/docs/Web/API/RTCIceTransport) to Kotlin
 */
public external abstract class RTCIceTransport : EventTarget {
    open val role: RTCIceRole
    open val component: RTCIceComponent
    open val state: RTCIceTransportState
    open val gatheringState: RTCIceGathererState
    open var onstatechange: ((Event) -> dynamic)?
    open var ongatheringstatechange: ((Event) -> dynamic)?
    open var onselectedcandidatepairchange: ((Event) -> dynamic)?
    fun getLocalCandidates(): Array<RTCIceCandidate>
    fun getRemoteCandidates(): Array<RTCIceCandidate>
    fun getSelectedCandidatePair(): RTCIceCandidatePair?
    fun getLocalParameters(): RTCIceParameters?
    fun getRemoteParameters(): RTCIceParameters?
}

/**
 * Exposes the JavaScript [RTCIceParameters](https://developer.mozilla.org/en/docs/Web/API/RTCIceParameters) to Kotlin
 */
public external interface RTCIceParameters {
    var usernameFragment: String?
        get() = definedExternally
        set(value) = definedExternally
    var password: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCIceParameters(usernameFragment: String? = undefined, password: String? = undefined): RTCIceParameters {
    val o = js("({})")

    o["usernameFragment"] = usernameFragment
    o["password"] = password

    return o
}

/**
 * Exposes the JavaScript [RTCIceCandidatePair](https://developer.mozilla.org/en/docs/Web/API/RTCIceCandidatePair) to Kotlin
 */
public external interface RTCIceCandidatePair {
    var local: RTCIceCandidate?
        get() = definedExternally
        set(value) = definedExternally
    var remote: RTCIceCandidate?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCIceCandidatePair(local: RTCIceCandidate? = undefined, remote: RTCIceCandidate? = undefined): RTCIceCandidatePair {
    val o = js("({})")

    o["local"] = local
    o["remote"] = remote

    return o
}

/**
 * Exposes the JavaScript [RTCTrackEvent](https://developer.mozilla.org/en/docs/Web/API/RTCTrackEvent) to Kotlin
 */
public external open class RTCTrackEvent(type: String, eventInitDict: RTCTrackEventInit) : Event {
    open val receiver: RTCRtpReceiver
    open val track: MediaStreamTrack
    open val streams: Array<out MediaStream>
    open val transceiver: RTCRtpTransceiver
}

/**
 * Exposes the JavaScript [RTCTrackEventInit](https://developer.mozilla.org/en/docs/Web/API/RTCTrackEventInit) to Kotlin
 */
public external interface RTCTrackEventInit : EventInit {
    var receiver: RTCRtpReceiver?
        get() = definedExternally
        set(value) = definedExternally
    var track: MediaStreamTrack?
        get() = definedExternally
        set(value) = definedExternally
    var streams: Array<MediaStream>? /* = arrayOf() */
        get() = definedExternally
        set(value) = definedExternally
    var transceiver: RTCRtpTransceiver?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCTrackEventInit(receiver: RTCRtpReceiver?, track: MediaStreamTrack?, streams: Array<MediaStream>? = arrayOf(), transceiver: RTCRtpTransceiver?, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCTrackEventInit {
    val o = js("({})")

    o["receiver"] = receiver
    o["track"] = track
    o["streams"] = streams
    o["transceiver"] = transceiver
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external abstract class RTCSctpTransport {
    open val transport: RTCDtlsTransport
    open val state: RTCSctpTransportState
    open val maxMessageSize: Double
    open val maxChannels: Short?
    open var onstatechange: ((Event) -> dynamic)?
}

/**
 * Exposes the JavaScript [RTCDataChannel](https://developer.mozilla.org/en/docs/Web/API/RTCDataChannel) to Kotlin
 */
public external abstract class RTCDataChannel : EventTarget {
    open val label: String
    open val ordered: Boolean
    open val maxPacketLifeTime: Short?
    open val maxRetransmits: Short?
    open val protocol: String
    open val negotiated: Boolean
    open val id: Short?
    open val priority: RTCPriorityType
    open val readyState: RTCDataChannelState
    open val bufferedAmount: Int
    open var bufferedAmountLowThreshold: Int
    open var onopen: ((Event) -> dynamic)?
    open var onbufferedamountlow: ((Event) -> dynamic)?
    open var onerror: ((Event) -> dynamic)?
    open var onclose: ((Event) -> dynamic)?
    open var onmessage: ((MessageEvent) -> dynamic)?
    open var binaryType: String
    fun close(): Unit
    fun send(data: String): Unit
    fun send(data: Blob): Unit
    fun send(data: ArrayBuffer): Unit
    fun send(data: ArrayBufferView): Unit
}

public external interface RTCDataChannelInit {
    var ordered: Boolean? /* = true */
        get() = definedExternally
        set(value) = definedExternally
    var maxPacketLifeTime: Short?
        get() = definedExternally
        set(value) = definedExternally
    var maxRetransmits: Short?
        get() = definedExternally
        set(value) = definedExternally
    var protocol: String? /* = "" */
        get() = definedExternally
        set(value) = definedExternally
    var negotiated: Boolean? /* = false */
        get() = definedExternally
        set(value) = definedExternally
    var id: Short?
        get() = definedExternally
        set(value) = definedExternally
    var priority: RTCPriorityType? /* = RTCPriorityType.LOW */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCDataChannelInit(ordered: Boolean? = true, maxPacketLifeTime: Short? = undefined, maxRetransmits: Short? = undefined, protocol: String? = "", negotiated: Boolean? = false, id: Short? = undefined, priority: RTCPriorityType? = RTCPriorityType.LOW): RTCDataChannelInit {
    val o = js("({})")

    o["ordered"] = ordered
    o["maxPacketLifeTime"] = maxPacketLifeTime
    o["maxRetransmits"] = maxRetransmits
    o["protocol"] = protocol
    o["negotiated"] = negotiated
    o["id"] = id
    o["priority"] = priority

    return o
}

/**
 * Exposes the JavaScript [RTCDataChannelEvent](https://developer.mozilla.org/en/docs/Web/API/RTCDataChannelEvent) to Kotlin
 */
public external open class RTCDataChannelEvent(type: String, eventInitDict: RTCDataChannelEventInit) : Event {
    open val channel: RTCDataChannel
}

public external interface RTCDataChannelEventInit : EventInit {
    var channel: RTCDataChannel?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCDataChannelEventInit(channel: RTCDataChannel?, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCDataChannelEventInit {
    val o = js("({})")

    o["channel"] = channel
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

/**
 * Exposes the JavaScript [RTCDTMFSender](https://developer.mozilla.org/en/docs/Web/API/RTCDTMFSender) to Kotlin
 */
public external abstract class RTCDTMFSender : EventTarget {
    open var ontonechange: ((Event) -> dynamic)?
    open val canInsertDTMF: Boolean
    open val toneBuffer: String
    fun insertDTMF(tones: String, duration: Int = definedExternally, interToneGap: Int = definedExternally): Unit
}

/**
 * Exposes the JavaScript [RTCDTMFToneChangeEvent](https://developer.mozilla.org/en/docs/Web/API/RTCDTMFToneChangeEvent) to Kotlin
 */
public external open class RTCDTMFToneChangeEvent(type: String, eventInitDict: RTCDTMFToneChangeEventInit) : Event {
    open val tone: String
}

public external interface RTCDTMFToneChangeEventInit : EventInit {
    var tone: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCDTMFToneChangeEventInit(tone: String?, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCDTMFToneChangeEventInit {
    val o = js("({})")

    o["tone"] = tone
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

/**
 * Exposes the JavaScript [RTCStatsReport](https://developer.mozilla.org/en/docs/Web/API/RTCStatsReport) to Kotlin
 */
public external abstract class RTCStatsReport {
}

/**
 * Exposes the JavaScript [RTCStats](https://developer.mozilla.org/en/docs/Web/API/RTCStats) to Kotlin
 */
public external interface RTCStats {
    var timestamp: Double?
        get() = definedExternally
        set(value) = definedExternally
    var type: dynamic
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCStats(timestamp: Double?, type: dynamic, id: String?): RTCStats {
    val o = js("({})")

    o["timestamp"] = timestamp
    o["type"] = type
    o["id"] = id

    return o
}

public external open class RTCStatsEvent(type: String, eventInitDict: RTCStatsEventInit) : Event {
    open val report: RTCStatsReport
}

public external interface RTCStatsEventInit : EventInit {
    var report: RTCStatsReport?
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCStatsEventInit(report: RTCStatsReport?, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCStatsEventInit {
    val o = js("({})")

    o["report"] = report
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

public external open class RTCErrorEvent(type: String, eventInitDict: RTCErrorEventInit) : Event {
    open val error: dynamic
}

public external interface RTCErrorEventInit : EventInit {
    var error: dynamic /* = null */
        get() = definedExternally
        set(value) = definedExternally
}

@kotlin.internal.InlineOnly
public inline fun RTCErrorEventInit(error: dynamic = null, bubbles: Boolean? = false, cancelable: Boolean? = false, composed: Boolean? = false): RTCErrorEventInit {
    val o = js("({})")

    o["error"] = error
    o["bubbles"] = bubbles
    o["cancelable"] = cancelable
    o["composed"] = composed

    return o
}

/* please, don't implement this interface! */
public external interface RTCIceCredentialType {
    companion object
}
public inline val RTCIceCredentialType.Companion.PASSWORD: RTCIceCredentialType get() = "password".asDynamic().unsafeCast<RTCIceCredentialType>()
public inline val RTCIceCredentialType.Companion.OAUTH: RTCIceCredentialType get() = "oauth".asDynamic().unsafeCast<RTCIceCredentialType>()

/* please, don't implement this interface! */
public external interface RTCIceTransportPolicy {
    companion object
}
public inline val RTCIceTransportPolicy.Companion.RELAY: RTCIceTransportPolicy get() = "relay".asDynamic().unsafeCast<RTCIceTransportPolicy>()
public inline val RTCIceTransportPolicy.Companion.ALL: RTCIceTransportPolicy get() = "all".asDynamic().unsafeCast<RTCIceTransportPolicy>()

/* please, don't implement this interface! */
public external interface RTCBundlePolicy {
    companion object
}
public inline val RTCBundlePolicy.Companion.BALANCED: RTCBundlePolicy get() = "balanced".asDynamic().unsafeCast<RTCBundlePolicy>()
public inline val RTCBundlePolicy.Companion.MAX_COMPAT: RTCBundlePolicy get() = "max-compat".asDynamic().unsafeCast<RTCBundlePolicy>()
public inline val RTCBundlePolicy.Companion.MAX_BUNDLE: RTCBundlePolicy get() = "max-bundle".asDynamic().unsafeCast<RTCBundlePolicy>()

/* please, don't implement this interface! */
public external interface RTCRtcpMuxPolicy {
    companion object
}
public inline val RTCRtcpMuxPolicy.Companion.NEGOTIATE: RTCRtcpMuxPolicy get() = "negotiate".asDynamic().unsafeCast<RTCRtcpMuxPolicy>()
public inline val RTCRtcpMuxPolicy.Companion.REQUIRE: RTCRtcpMuxPolicy get() = "require".asDynamic().unsafeCast<RTCRtcpMuxPolicy>()

/* please, don't implement this interface! */
public external interface RTCSignalingState {
    companion object
}
public inline val RTCSignalingState.Companion.STABLE: RTCSignalingState get() = "stable".asDynamic().unsafeCast<RTCSignalingState>()
public inline val RTCSignalingState.Companion.HAVE_LOCAL_OFFER: RTCSignalingState get() = "have-local-offer".asDynamic().unsafeCast<RTCSignalingState>()
public inline val RTCSignalingState.Companion.HAVE_REMOTE_OFFER: RTCSignalingState get() = "have-remote-offer".asDynamic().unsafeCast<RTCSignalingState>()
public inline val RTCSignalingState.Companion.HAVE_LOCAL_PRANSWER: RTCSignalingState get() = "have-local-pranswer".asDynamic().unsafeCast<RTCSignalingState>()
public inline val RTCSignalingState.Companion.HAVE_REMOTE_PRANSWER: RTCSignalingState get() = "have-remote-pranswer".asDynamic().unsafeCast<RTCSignalingState>()
public inline val RTCSignalingState.Companion.CLOSED: RTCSignalingState get() = "closed".asDynamic().unsafeCast<RTCSignalingState>()

/* please, don't implement this interface! */
public external interface RTCIceGatheringState {
    companion object
}
public inline val RTCIceGatheringState.Companion.NEW: RTCIceGatheringState get() = "new".asDynamic().unsafeCast<RTCIceGatheringState>()
public inline val RTCIceGatheringState.Companion.GATHERING: RTCIceGatheringState get() = "gathering".asDynamic().unsafeCast<RTCIceGatheringState>()
public inline val RTCIceGatheringState.Companion.COMPLETE: RTCIceGatheringState get() = "complete".asDynamic().unsafeCast<RTCIceGatheringState>()

/* please, don't implement this interface! */
public external interface RTCPeerConnectionState {
    companion object
}
public inline val RTCPeerConnectionState.Companion.CLOSED: RTCPeerConnectionState get() = "closed".asDynamic().unsafeCast<RTCPeerConnectionState>()
public inline val RTCPeerConnectionState.Companion.FAILED: RTCPeerConnectionState get() = "failed".asDynamic().unsafeCast<RTCPeerConnectionState>()
public inline val RTCPeerConnectionState.Companion.DISCONNECTED: RTCPeerConnectionState get() = "disconnected".asDynamic().unsafeCast<RTCPeerConnectionState>()
public inline val RTCPeerConnectionState.Companion.NEW: RTCPeerConnectionState get() = "new".asDynamic().unsafeCast<RTCPeerConnectionState>()
public inline val RTCPeerConnectionState.Companion.CONNECTING: RTCPeerConnectionState get() = "connecting".asDynamic().unsafeCast<RTCPeerConnectionState>()
public inline val RTCPeerConnectionState.Companion.CONNECTED: RTCPeerConnectionState get() = "connected".asDynamic().unsafeCast<RTCPeerConnectionState>()

/* please, don't implement this interface! */
public external interface RTCIceConnectionState {
    companion object
}
public inline val RTCIceConnectionState.Companion.CLOSED: RTCIceConnectionState get() = "closed".asDynamic().unsafeCast<RTCIceConnectionState>()
public inline val RTCIceConnectionState.Companion.FAILED: RTCIceConnectionState get() = "failed".asDynamic().unsafeCast<RTCIceConnectionState>()
public inline val RTCIceConnectionState.Companion.DISCONNECTED: RTCIceConnectionState get() = "disconnected".asDynamic().unsafeCast<RTCIceConnectionState>()
public inline val RTCIceConnectionState.Companion.NEW: RTCIceConnectionState get() = "new".asDynamic().unsafeCast<RTCIceConnectionState>()
public inline val RTCIceConnectionState.Companion.CHECKING: RTCIceConnectionState get() = "checking".asDynamic().unsafeCast<RTCIceConnectionState>()
public inline val RTCIceConnectionState.Companion.COMPLETED: RTCIceConnectionState get() = "completed".asDynamic().unsafeCast<RTCIceConnectionState>()
public inline val RTCIceConnectionState.Companion.CONNECTED: RTCIceConnectionState get() = "connected".asDynamic().unsafeCast<RTCIceConnectionState>()

/* please, don't implement this interface! */
public external interface RTCSdpType {
    companion object
}
public inline val RTCSdpType.Companion.OFFER: RTCSdpType get() = "offer".asDynamic().unsafeCast<RTCSdpType>()
public inline val RTCSdpType.Companion.PRANSWER: RTCSdpType get() = "pranswer".asDynamic().unsafeCast<RTCSdpType>()
public inline val RTCSdpType.Companion.ANSWER: RTCSdpType get() = "answer".asDynamic().unsafeCast<RTCSdpType>()
public inline val RTCSdpType.Companion.ROLLBACK: RTCSdpType get() = "rollback".asDynamic().unsafeCast<RTCSdpType>()

/* please, don't implement this interface! */
public external interface RTCIceProtocol {
    companion object
}
public inline val RTCIceProtocol.Companion.UDP: RTCIceProtocol get() = "udp".asDynamic().unsafeCast<RTCIceProtocol>()
public inline val RTCIceProtocol.Companion.TCP: RTCIceProtocol get() = "tcp".asDynamic().unsafeCast<RTCIceProtocol>()

/* please, don't implement this interface! */
public external interface RTCIceTcpCandidateType {
    companion object
}
public inline val RTCIceTcpCandidateType.Companion.ACTIVE: RTCIceTcpCandidateType get() = "active".asDynamic().unsafeCast<RTCIceTcpCandidateType>()
public inline val RTCIceTcpCandidateType.Companion.PASSIVE: RTCIceTcpCandidateType get() = "passive".asDynamic().unsafeCast<RTCIceTcpCandidateType>()
public inline val RTCIceTcpCandidateType.Companion.SO: RTCIceTcpCandidateType get() = "so".asDynamic().unsafeCast<RTCIceTcpCandidateType>()

/* please, don't implement this interface! */
public external interface RTCIceCandidateType {
    companion object
}
public inline val RTCIceCandidateType.Companion.HOST: RTCIceCandidateType get() = "host".asDynamic().unsafeCast<RTCIceCandidateType>()
public inline val RTCIceCandidateType.Companion.SRFLX: RTCIceCandidateType get() = "srflx".asDynamic().unsafeCast<RTCIceCandidateType>()
public inline val RTCIceCandidateType.Companion.PRFLX: RTCIceCandidateType get() = "prflx".asDynamic().unsafeCast<RTCIceCandidateType>()
public inline val RTCIceCandidateType.Companion.RELAY: RTCIceCandidateType get() = "relay".asDynamic().unsafeCast<RTCIceCandidateType>()

/* please, don't implement this interface! */
public external interface RTCPriorityType {
    companion object
}
public inline val RTCPriorityType.Companion.VERY_LOW: RTCPriorityType get() = "very-low".asDynamic().unsafeCast<RTCPriorityType>()
public inline val RTCPriorityType.Companion.LOW: RTCPriorityType get() = "low".asDynamic().unsafeCast<RTCPriorityType>()
public inline val RTCPriorityType.Companion.MEDIUM: RTCPriorityType get() = "medium".asDynamic().unsafeCast<RTCPriorityType>()
public inline val RTCPriorityType.Companion.HIGH: RTCPriorityType get() = "high".asDynamic().unsafeCast<RTCPriorityType>()

/* please, don't implement this interface! */
public external interface RTCRtpTransceiverDirection {
    companion object
}
public inline val RTCRtpTransceiverDirection.Companion.SENDRECV: RTCRtpTransceiverDirection get() = "sendrecv".asDynamic().unsafeCast<RTCRtpTransceiverDirection>()
public inline val RTCRtpTransceiverDirection.Companion.SENDONLY: RTCRtpTransceiverDirection get() = "sendonly".asDynamic().unsafeCast<RTCRtpTransceiverDirection>()
public inline val RTCRtpTransceiverDirection.Companion.RECVONLY: RTCRtpTransceiverDirection get() = "recvonly".asDynamic().unsafeCast<RTCRtpTransceiverDirection>()
public inline val RTCRtpTransceiverDirection.Companion.INACTIVE: RTCRtpTransceiverDirection get() = "inactive".asDynamic().unsafeCast<RTCRtpTransceiverDirection>()

/* please, don't implement this interface! */
public external interface RTCDtxStatus {
    companion object
}
public inline val RTCDtxStatus.Companion.DISABLED: RTCDtxStatus get() = "disabled".asDynamic().unsafeCast<RTCDtxStatus>()
public inline val RTCDtxStatus.Companion.ENABLED: RTCDtxStatus get() = "enabled".asDynamic().unsafeCast<RTCDtxStatus>()

/* please, don't implement this interface! */
public external interface RTCDegradationPreference {
    companion object
}
public inline val RTCDegradationPreference.Companion.MAINTAIN_FRAMERATE: RTCDegradationPreference get() = "maintain-framerate".asDynamic().unsafeCast<RTCDegradationPreference>()
public inline val RTCDegradationPreference.Companion.MAINTAIN_RESOLUTION: RTCDegradationPreference get() = "maintain-resolution".asDynamic().unsafeCast<RTCDegradationPreference>()
public inline val RTCDegradationPreference.Companion.BALANCED: RTCDegradationPreference get() = "balanced".asDynamic().unsafeCast<RTCDegradationPreference>()

/* please, don't implement this interface! */
public external interface RTCDtlsTransportState {
    companion object
}
public inline val RTCDtlsTransportState.Companion.NEW: RTCDtlsTransportState get() = "new".asDynamic().unsafeCast<RTCDtlsTransportState>()
public inline val RTCDtlsTransportState.Companion.CONNECTING: RTCDtlsTransportState get() = "connecting".asDynamic().unsafeCast<RTCDtlsTransportState>()
public inline val RTCDtlsTransportState.Companion.CONNECTED: RTCDtlsTransportState get() = "connected".asDynamic().unsafeCast<RTCDtlsTransportState>()
public inline val RTCDtlsTransportState.Companion.CLOSED: RTCDtlsTransportState get() = "closed".asDynamic().unsafeCast<RTCDtlsTransportState>()
public inline val RTCDtlsTransportState.Companion.FAILED: RTCDtlsTransportState get() = "failed".asDynamic().unsafeCast<RTCDtlsTransportState>()

/* please, don't implement this interface! */
public external interface RTCIceGathererState {
    companion object
}
public inline val RTCIceGathererState.Companion.NEW: RTCIceGathererState get() = "new".asDynamic().unsafeCast<RTCIceGathererState>()
public inline val RTCIceGathererState.Companion.GATHERING: RTCIceGathererState get() = "gathering".asDynamic().unsafeCast<RTCIceGathererState>()
public inline val RTCIceGathererState.Companion.COMPLETE: RTCIceGathererState get() = "complete".asDynamic().unsafeCast<RTCIceGathererState>()

/* please, don't implement this interface! */
public external interface RTCIceTransportState {
    companion object
}
public inline val RTCIceTransportState.Companion.NEW: RTCIceTransportState get() = "new".asDynamic().unsafeCast<RTCIceTransportState>()
public inline val RTCIceTransportState.Companion.CHECKING: RTCIceTransportState get() = "checking".asDynamic().unsafeCast<RTCIceTransportState>()
public inline val RTCIceTransportState.Companion.CONNECTED: RTCIceTransportState get() = "connected".asDynamic().unsafeCast<RTCIceTransportState>()
public inline val RTCIceTransportState.Companion.COMPLETED: RTCIceTransportState get() = "completed".asDynamic().unsafeCast<RTCIceTransportState>()
public inline val RTCIceTransportState.Companion.DISCONNECTED: RTCIceTransportState get() = "disconnected".asDynamic().unsafeCast<RTCIceTransportState>()
public inline val RTCIceTransportState.Companion.FAILED: RTCIceTransportState get() = "failed".asDynamic().unsafeCast<RTCIceTransportState>()
public inline val RTCIceTransportState.Companion.CLOSED: RTCIceTransportState get() = "closed".asDynamic().unsafeCast<RTCIceTransportState>()

/* please, don't implement this interface! */
public external interface RTCIceRole {
    companion object
}
public inline val RTCIceRole.Companion.CONTROLLING: RTCIceRole get() = "controlling".asDynamic().unsafeCast<RTCIceRole>()
public inline val RTCIceRole.Companion.CONTROLLED: RTCIceRole get() = "controlled".asDynamic().unsafeCast<RTCIceRole>()

/* please, don't implement this interface! */
public external interface RTCIceComponent {
    companion object
}
public inline val RTCIceComponent.Companion.RTP: RTCIceComponent get() = "rtp".asDynamic().unsafeCast<RTCIceComponent>()
public inline val RTCIceComponent.Companion.RTCP: RTCIceComponent get() = "rtcp".asDynamic().unsafeCast<RTCIceComponent>()

/* please, don't implement this interface! */
public external interface RTCSctpTransportState {
    companion object
}
public inline val RTCSctpTransportState.Companion.CONNECTING: RTCSctpTransportState get() = "connecting".asDynamic().unsafeCast<RTCSctpTransportState>()
public inline val RTCSctpTransportState.Companion.CONNECTED: RTCSctpTransportState get() = "connected".asDynamic().unsafeCast<RTCSctpTransportState>()
public inline val RTCSctpTransportState.Companion.CLOSED: RTCSctpTransportState get() = "closed".asDynamic().unsafeCast<RTCSctpTransportState>()

/* please, don't implement this interface! */
public external interface RTCDataChannelState {
    companion object
}
public inline val RTCDataChannelState.Companion.CONNECTING: RTCDataChannelState get() = "connecting".asDynamic().unsafeCast<RTCDataChannelState>()
public inline val RTCDataChannelState.Companion.OPEN: RTCDataChannelState get() = "open".asDynamic().unsafeCast<RTCDataChannelState>()
public inline val RTCDataChannelState.Companion.CLOSING: RTCDataChannelState get() = "closing".asDynamic().unsafeCast<RTCDataChannelState>()
public inline val RTCDataChannelState.Companion.CLOSED: RTCDataChannelState get() = "closed".asDynamic().unsafeCast<RTCDataChannelState>()

/* please, don't implement this interface! */
public external interface RTCErrorDetailType {
    companion object
}
public inline val RTCErrorDetailType.Companion.DATA_CHANNEL_FAILURE: RTCErrorDetailType get() = "data-channel-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.DTLS_FAILURE: RTCErrorDetailType get() = "dtls-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.FINGERPRINT_FAILURE: RTCErrorDetailType get() = "fingerprint-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_BAD_SCRIPT_FAILURE: RTCErrorDetailType get() = "idp-bad-script-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_EXECUTION_FAILURE: RTCErrorDetailType get() = "idp-execution-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_LOAD_FAILURE: RTCErrorDetailType get() = "idp-load-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_NEED_LOGIN: RTCErrorDetailType get() = "idp-need-login".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_TIMEOUT: RTCErrorDetailType get() = "idp-timeout".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_TLS_FAILURE: RTCErrorDetailType get() = "idp-tls-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_TOKEN_EXPIRED: RTCErrorDetailType get() = "idp-token-expired".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.IDP_TOKEN_INVALID: RTCErrorDetailType get() = "idp-token-invalid".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.SCTP_FAILURE: RTCErrorDetailType get() = "sctp-failure".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.SDP_SYNTAX_ERROR: RTCErrorDetailType get() = "sdp-syntax-error".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.HARDWARE_ENCODER_NOT_AVAILABLE: RTCErrorDetailType get() = "hardware-encoder-not-available".asDynamic().unsafeCast<RTCErrorDetailType>()
public inline val RTCErrorDetailType.Companion.HARDWARE_ENCODER_ERROR: RTCErrorDetailType get() = "hardware-encoder-error".asDynamic().unsafeCast<RTCErrorDetailType>()

