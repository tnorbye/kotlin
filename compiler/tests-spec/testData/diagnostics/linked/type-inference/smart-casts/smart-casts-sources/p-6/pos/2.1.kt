// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 2
 * NUMBER: 1
 * DESCRIPTION: Empty 'when' with bound value.
 * UNEXPECTED BEHAVIOUR
 */

// TESTCASE NUMBER: 1
interface A<out T>
interface Foo

open class B : Foo, A<B>
open class C : Foo, A<C>

fun foo() = run {
    val mm = object: B() {}
    val nn = object: C() {}

    val cc = if (true) mm else nn

    <!DEBUG_INFO_EXPRESSION_TYPE("{A<{A<{A<{A<{A<Any?> & Foo}> & Foo}> & Foo}> & Foo}> & Foo}")!>cc<!>
}
