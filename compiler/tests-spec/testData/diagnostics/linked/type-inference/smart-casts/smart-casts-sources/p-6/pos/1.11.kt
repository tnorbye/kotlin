// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// !WITH_CLASSES_WITH_PROJECTIONS
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 11
 * DESCRIPTION: Nullability condition, if, intersection types
 */

// TESTCASE NUMBER: 1
fun <A, B : Inv<A>, C: Out<A?>>case_1_1(a: C, b: B) = select(a.x, b.x)

fun case_1() {
    val x = case_1_1(Out(10), Inv(0.1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Byte & Double & Int & Long & Short}> & Number} & {Comparable<{Byte & Double & Int & Long & Short}> & Number}?")!>x<!>
    }
}

// TESTCASE NUMBER: 2
fun <A, B : Inv<A>, C: Out<A?>>case_2_1(a: C, b: B) = select(a.x, b.x)

fun case_2(y: Int) {
    val x = case_2_1(Out(y), Inv(0.1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Double & Int}> & Number} & {Comparable<{Double & Int}> & Number}?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 3
 * ISSUES: KT-28670
 */
fun case_3(a: Int?, b: Float?, c: Double?, d: Boolean?) {
    when (d) {
        true -> a
        false -> b
        null -> c
    }.apply {
        <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Double & Float & Int}>? & Number?}")!>this<!>
        if (this != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Double & Float & Int}>? & Number?}")!>this<!>
        }
    }.let {
        <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Double & Float & Int}>? & Number?}")!>it<!>
        if (it != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{Comparable<{Double & Float & Int}>? & Number?}")!>it<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 4
 * ISSUES: KT-28670
 */
interface A4
interface B4

fun case_4(a: A4?, b: B4?, d: Boolean) {
    a as B4?
    b as A4?
    val x = when (d) {
        true -> a
        false -> b
    }

    x.apply {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A4? & B4?}")!>this<!>
        if (this != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{A4? & B4?}")!>this<!>
        }
    }
    x.let {
        <!DEBUG_INFO_EXPRESSION_TYPE("{A4? & B4?}")!>it<!>
        if (it != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{A4? & B4?}")!>it<!>
        }
    }
    }

/*
 * TESTCASE NUMBER: 5
 * ISSUES: KT-28670
 */
interface A5
interface B5

fun case_5(a: A5?, b: B5?, d: Boolean) {
    a as B5?
    b as A5
    val x = when (d) {
        true -> a
        false -> b
    }

    x.apply {
        if (this != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{A5? & B5?}")!>this<!>
        }
    }
    x.let {
        if (it != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{A5? & B5?}")!>it<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 6
 * ISSUES: KT-28670
 */
interface A6
interface B6
interface C6

fun case_6(a: A6?, b: B6, d: Boolean) {
    a as B6?
    b as A6
    val x = when (d) {
        true -> a
        false -> b
    }

    x.apply {
        this as C6
        <!DEBUG_INFO_EXPRESSION_TYPE("{A6? & B6?}")!>this<!>
        if (<!SENSELESS_COMPARISON!>this != null<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{A6? & B6?}")!>this<!>
        }
    }
    x.let {
        it as C6
        <!DEBUG_INFO_EXPRESSION_TYPE("C6 & {A6? & B6?}")!>it<!>
        if (<!SENSELESS_COMPARISON!>it != null<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("C6 & {A6? & B6?}")!>it<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 7
 * ISSUES: KT-28670
 */
interface A7
interface B7
interface C7

fun case_6(a: A7?, b: B7?, d: Boolean) {
    a as B7?
    b as A7?
    val x = when (d) {
        true -> a
        false -> b
    }

    x.apply {
        this as C7?
        <!DEBUG_INFO_EXPRESSION_TYPE("{A7? & B7?}")!>this<!>
        if (this != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("{A7? & B7?}")!>this<!>
        }
    }
    x.let {
        it as C7?
        <!DEBUG_INFO_EXPRESSION_TYPE("C7? & {A7? & B7?}")!>it<!>
        if (it != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("C7 & {A7? & B7?}")!>it<!>
        }
    }
}
