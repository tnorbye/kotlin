// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 15
 * DESCRIPTION: Nullability condition, if, varargs
 * NOTE: lazy smartcasts
 */

// TESTCASE NUMBER: 1
interface A1<T> {
    fun test(): T? { return null }
}

open class B1 : A1<B1>
open class C1 : A1<C1>

fun case_1() {
    val a = if (true) B1() else if (true) C1() else null

    if (a != null) {
        val b = <!DEBUG_INFO_EXPRESSION_TYPE("A1<out A1<out A1<out A1<out A1<out A1<*>>>>>> & A1<out A1<out A1<out A1<out A1<out A1<*>>>>>>?")!>a<!>.test()
        if (b != null) {
            val c = <!DEBUG_INFO_EXPRESSION_TYPE("A1<out A1<out A1<out A1<out A1<*>>>>> & A1<out A1<out A1<out A1<out A1<*>>>>>?")!>b<!>.test()
            if (c != null) {
                val d = <!DEBUG_INFO_EXPRESSION_TYPE("A1<out A1<out A1<out A1<*>>>> & A1<out A1<out A1<out A1<*>>>>?")!>c<!>.test()
                if (d != null) {
                    val e = <!DEBUG_INFO_EXPRESSION_TYPE("A1<out A1<out A1<*>>> & A1<out A1<out A1<*>>>?")!>d<!>.test()
                    if (e != null) {
                        val f = <!DEBUG_INFO_EXPRESSION_TYPE("A1<out A1<*>> & A1<out A1<*>>?")!>e<!>.test()
                        if (f != null) {
                            val g = <!DEBUG_INFO_EXPRESSION_TYPE("A1<*> & A1<*>?")!>f<!>.test()
                            if (g != null) {
                                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & kotlin.Any"), DEBUG_INFO_SMARTCAST!>g<!>.equals(null)
                            }
                        }
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 2
interface A2

interface B2<T> {
    fun test(): T? { return null }
}

class C2 : A2, B2<C2>
class D2 : A2, B2<D2>

fun case_2() {
    val x = if (true) D2() else if (true) C2() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A2? & B2<out {A2 & B2<out {A2 & B2<out {A2 & B2<out {A2 & B2<out Any?>}>}>}>}>?}")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("{A2? & B2<out {A2 & B2<out {A2 & B2<out {A2 & B2<out {A2 & B2<out Any?>}>}>}>}>?}")!>x<!><!UNSAFE_CALL!>.<!>test()
    }
}

// TESTCASE NUMBER: 3
interface A3

interface B3<T> {
    fun test1(): T? { return null }
}

interface E3<T> {
    fun test2(): T? { return null }
}

class C3 : A3, B3<C3>, E3<C3>
class D3 : A3, B3<D3>, E3<D3>

fun case_3() {
    val x = if (true) D3() else if (true) C3() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A3? & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>? & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>?}")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("{A3? & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>? & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>?}")!>x<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("{A3? & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>? & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>?}")!>x<!><!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("{A3? & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>? & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}> & E3<out {A3 & B3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}> & E3<out {A3 & B3<out {A3 & B3<out Any?> & E3<out Any?>}> & E3<out {A3 & B3<out Any?> & E3<out Any?>}>}>}>}>?}")!>x<!>.<!UNRESOLVED_REFERENCE!>test3<!>()
    }
}

// TESTCASE NUMBER: 4
interface A4<T> {
    fun test1(): T? { return null }
}

interface B4<T> {
    fun test2(): T? { return null }
}

class C4 : A4<D4>, B4<C4>
class D4 : A4<C4>, B4<D4>

fun case_4() {
    val x = if (true) D4() else if (true) C4() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A4<out {A4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}> & B4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}>}>? & B4<out {A4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}> & B4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}>}>?}")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("{A4<out {A4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}> & B4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}>}>? & B4<out {A4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}> & B4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}>}>?}")!>x<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("{A4<out {A4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}> & B4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}>}>? & B4<out {A4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}> & B4<out {A4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}> & B4<out {A4<out {A4<out Any?> & B4<out Any?>}> & B4<out {A4<out Any?> & B4<out Any?>}>}>}>}>?}")!>x<!><!UNSAFE_CALL!>.<!>test2()
    }
}

// TESTCASE NUMBER: 5
interface A5<T> {
    fun test1(): T? { return null }
}

interface B5<T> {
    fun test2(): T? { return null }
}

class C5 : A5<B5<D5>>, B5<B5<C5>>
class D5 : B5<B5<C5>>, A5<B5<D5>>

fun case_5() {
    val x = if (true) D5() else if (true) C5() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A5<B5<D5>> & B5<B5<C5>>} & {A5<B5<D5>> & B5<B5<C5>>}?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("{A5<B5<D5>> & B5<B5<C5>>}? & {A5<B5<D5>> & B5<B5<C5>>}"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("{A5<B5<D5>> & B5<B5<C5>>}? & {A5<B5<D5>> & B5<B5<C5>>}"), DEBUG_INFO_SMARTCAST!>x<!>.test2()
    }
}

// TESTCASE NUMBER: 6
interface A6<T> {
    fun test1(): T? { return null }
}

interface B6<T> {
    fun test2(): T? { return null }
}

class C6<T> : A6<B6<T>>, B6<B6<C6<T>>>
class D6<T> : B6<B6<D6<T>>>, A6<B6<T>>

fun case_6() {
    val x = if (true) D6<Int>() else if (true) C6<Float>() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A6<out B6<out {Comparable<{Float & Int}> & Number}>>? & B6<out B6<out {A6<out B6<out {Comparable<Nothing> & Number}>> & B6<out B6<out {A6<out Any?> & B6<out Any?>}>>}>>?}")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("{A6<out B6<out {Comparable<{Float & Int}> & Number}>>? & B6<out B6<out {A6<out B6<out {Comparable<Nothing> & Number}>> & B6<out B6<out {A6<out Any?> & B6<out Any?>}>>}>>?}")!>x<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("{A6<out B6<out {Comparable<{Float & Int}> & Number}>>? & B6<out B6<out {A6<out B6<out {Comparable<Nothing> & Number}>> & B6<out B6<out {A6<out Any?> & B6<out Any?>}>>}>>?}")!>x<!><!UNSAFE_CALL!>.<!>test2()
    }
}

// TESTCASE NUMBER: 7
interface B7<T, K> {
    fun test1(): T? { return null }
}

open class F7<T>
open class C7<T, K> : B7<F7<T>, F7<K>>
open class D7<T, K> : B7<F7<K>, F7<T>>

fun case_7() {
    val x = if (true) D7<Int, Float>() else if (true) C7<Char, String>() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("B7<out F7<out {Comparable<{Char & Float}> & java.io.Serializable}>, out F7<out {Comparable<{Int & String}> & java.io.Serializable}>> & B7<out F7<out {Comparable<{Char & Float}> & java.io.Serializable}>, out F7<out {Comparable<{Int & String}> & java.io.Serializable}>>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("B7<out F7<out {Comparable<{Char & Float}> & java.io.Serializable}>, out F7<out {Comparable<{Int & String}> & java.io.Serializable}>> & B7<out F7<out {Comparable<{Char & Float}> & java.io.Serializable}>, out F7<out {Comparable<{Int & String}> & java.io.Serializable}>>?")!>x<!>.test1()
    }
}

// TESTCASE NUMBER: 8
open class F8<K, T> {
    fun test1(): T? { return null }
    fun test2(): K? { return null }
}
open class C8<T, K> : F8<F8<T, K>, K>()
open class D8<T, K> : F8<F8<K, T>, T>()

fun case_8() {
    val x = if (true) D8<Int, Float>() else if (true) C8<Char, String>() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("F8<out F8<out {Comparable<{Char & Float}> & java.io.Serializable}, out {Comparable<{Int & String}> & java.io.Serializable}>, out {Comparable<{Int & String}> & java.io.Serializable}> & F8<out F8<out {Comparable<{Char & Float}> & java.io.Serializable}, out {Comparable<{Int & String}> & java.io.Serializable}>, out {Comparable<{Int & String}> & java.io.Serializable}>?")!>x<!>.test1()
        val y = <!DEBUG_INFO_EXPRESSION_TYPE("F8<out F8<out {Comparable<{Char & Float}> & java.io.Serializable}, out {Comparable<{Int & String}> & java.io.Serializable}>, out {Comparable<{Int & String}> & java.io.Serializable}> & F8<out F8<out {Comparable<{Char & Float}> & java.io.Serializable}, out {Comparable<{Int & String}> & java.io.Serializable}>, out {Comparable<{Int & String}> & java.io.Serializable}>?")!>x<!>.test2()
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Int & String}> & java.io.Serializable}?")!>y.test1()<!>
            val z = <!DEBUG_INFO_EXPRESSION_TYPE("F8<out {Comparable<{Char & Float}> & java.io.Serializable}, out {Comparable<{Int & String}> & java.io.Serializable}> & F8<out {Comparable<{Char & Float}> & java.io.Serializable}, out {Comparable<{Int & String}> & java.io.Serializable}>?")!>y<!>.test2()
            if (z != null) {
                <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Char & Float}> & java.io.Serializable}? & {Comparable<{Char & Float}> & java.io.Serializable}"), DEBUG_INFO_SMARTCAST!>z<!>.equals(z)
            }
        }
    }
}

// TESTCASE NUMBER: 9
open class F9<K, T> {
    fun test1(): T? { return null }
    fun test2(): K? { return null }
}
open class C9<T, K> : F9<C9<T, K>, D9<K, T>>()
open class D9<T, K> : F9<D9<K, T>, C9<T, K>>()

fun case_9() {
    val x = if (true) D9<Int, String>() else if (true) C9<Float, Char>() else null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("F9<out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>, out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>> & F9<out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>, out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("F9<out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>, out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>> & F9<out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>, out F9<out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>, out F9<out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>, out F9<out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>, out F9<out F9<out F9<*, *>, out F9<*, *>>, out F9<out F9<*, *>, out F9<*, *>>>>>>>?")!>x<!>.test1()
        val y = x.test2()
        if (y != null) {
            val z = <!DEBUG_INFO_EXPRESSION_TYPE("F9<out F9<out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>, out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>>, out F9<out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>, out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>>> & F9<out F9<out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>, out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>>, out F9<out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>, out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>>>?")!>y<!>.test2()
            if (z != null) {
                <!DEBUG_INFO_EXPRESSION_TYPE("F9<out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>, out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>> & F9<out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>, out F9<out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>, out F9<out F9<out kotlin.Any?, out kotlin.Any?>, out F9<out kotlin.Any?, out kotlin.Any?>>>>?")!>z<!>.equals(z)
            }
        }
    }
}
