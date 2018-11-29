// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 3
 * DESCRIPTION: Nullability condition smartcast source using if expression and simple builtin and custom types.
 * UNSPECIFIED BEHAVIOR
 */

// FILE: other_types.kt

package othertypes

// TESTCASE NUMBER: 13
class A {}

// TESTCASE NUMBER: 14
typealias B = String?

// TESTCASE NUMBER: 16
typealias C = Nothing

// FILE: main.kt

import othertypes.*

// TESTCASE NUMBER: 6
class A {}

// TESTCASE NUMBER: 7
object B {}

// TESTCASE NUMBER: 8
typealias C = String?

// TESTCASE NUMBER: 10
typealias D = Float

// TESTCASE NUMBER: 11
typealias E = C

// TESTCASE NUMBER: 15
typealias F = othertypes.B

// TESTCASE NUMBER: 16
typealias G = othertypes.C?

// TESTCASE NUMBER: 1
fun case_1(x: Any?) {
    if (x == null) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 2
fun case_2(x: Nothing?) {
    if (<!SENSELESS_COMPARISON!><!DEBUG_INFO_CONSTANT!>x<!> == null<!>) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>x<!>
    }
}

// TESTCASE NUMBER: 3
object A3 {
    val x: Number? = null
}

fun case_3() {
    if (A3.x != null)
    else {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Number?")!>A3.x<!>
    }
}

// TESTCASE NUMBER: 4
fun case_4(x: Char?) {
    if (x == null && true) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Char?")!>x<!>
    }
}

// TESTCASE NUMBER: 5
fun case_5() {
    val x: Unit? = null

    if (x == null) <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Unit?")!>x<!>
}

// TESTCASE NUMBER: 6
fun case_6(x: A?) {
    val y = true

    if (x == null && !y) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & A?")!>x<!>
    }
}

// TESTCASE NUMBER: 7
val x7: B? = null

fun case_7() {
    if (x7 == null || <!SENSELESS_COMPARISON!><!DEBUG_INFO_EXPRESSION_TYPE("B & B?")!>x7<!> == null<!>) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & B?")!>x7<!>
    }
}

// TESTCASE NUMBER: 8
fun case_8(x: C) {
    if (x == null && <!SENSELESS_COMPARISON!><!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & C /* = kotlin.String? */")!>x<!> == null<!>) <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & C /* = kotlin.String? */")!>x<!>
}

/*
 * TESTCASE NUMBER: 9
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28329
 */
fun case_9(x: C<!REDUNDANT_NULLABLE!>?<!>) {
    if (true && true && true && true && x !== null) {

    } else if (false) {
        <!DEBUG_INFO_EXPRESSION_TYPE("C? /* = kotlin.String? */")!>x<!>
    }
}

// TESTCASE NUMBER: 10
class A10 {
    val x: D? = null
}

fun case_10() {
    val a = A10()

    if (a.x != null || true) {
        if (a.x == null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & D? /* = kotlin.Float? */")!>a.x<!>
        }
    }
}

// TESTCASE NUMBER: 11
val x11: E = null
fun case_11(x: E<!REDUNDANT_NULLABLE!>?<!>, y: E) {
    val t: E = null

    if (x != null) {

    } else {
        if (y == null) {
            if (x11 != null) {
                if (t == null) {
                    <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & E? /* = kotlin.String? */")!>x<!>
                }
            }
        }
    }
}

// TESTCASE NUMBER: 12
fun case_12(x: E, y: E) = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String?")!>if (x != null && true && true && true) "1"
    else if (y != null) <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & E /* = kotlin.String? */")!>x<!>
    else "-1"<!>

// TESTCASE NUMBER: 13
fun case_13(x: othertypes.A?) =
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>if (x != null && true) {
        throw Exception()
    } else <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & othertypes.A?")!>x<!><!>

// TESTCASE NUMBER: 14
class A14 {
    val x: othertypes.B<!REDUNDANT_NULLABLE!>?<!>
    init {
        x = othertypes.B()
    }
}

fun case_14() {
    val a = A14()

    if (a.x == null) {
        if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
            if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                    if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                        if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                            if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                    if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                        if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                            if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                                if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                                    if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                                        if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                                            if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                                                if (<!SENSELESS_COMPARISON!>a.x == null<!>) {
                                                                    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & othertypes.B? /* = kotlin.String? */")!>a.x<!>
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 15
fun case_15(x: F) {
    val <!UNUSED_VARIABLE!>t<!> = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String?")!>if (x != null) "" else {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & F /* = kotlin.String? */")!>x<!>
    }<!>
}

// TESTCASE NUMBER: 16
fun case_16() {
    val x: G = null

    if (<!SENSELESS_COMPARISON!><!DEBUG_INFO_CONSTANT!>x<!> == null<!> || false || false || false) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("G /* = kotlin.Nothing? */")!>x<!>
    }
}

// TESTCASE NUMBER: 17
val x17: Int? = 1

val t = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>if (x17 !== null) 0 else {
    <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Int?")!>x17<!>
}<!>

//TESTCASE NUMBER: 18
object A18 {
    object B18 {
        object C18 {
            object D18 {
                object E18 {
                    object F18 {
                        object G18 {
                            object H18 {
                                object I18 {
                                    val x: Int? = 10
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun case_18(a: A18.B18.C18.D18.E18.F18.G18.H18.I18?) {
    if (a == null) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & A18.B18.C18.D18.E18.F18.G18.H18.I18?")!>a<!>
    }
}

// TESTCASE NUMBER: 19
fun case_19(b: Boolean) {
    val a = if (b) {
        object {
            val B19 = if (b) {
                object {
                    val C19 = if (b) {
                        object {
                            val D19 = if (b) {
                                object {
                                    val x: Number? = 10
                                }
                            } else null
                        }
                    } else null
                }
            } else null
        }
    } else null

    if (a != null && <!NI;DEBUG_INFO_SMARTCAST!>a<!>.B19 != null && <!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19 != null && <!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19<!>.D19 != null && <!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19<!>.D19<!>.x == null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Number?")!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19<!>.D19<!>.x<!>
    }
}

// TESTCASE NUMBER: 20
fun case_20(b: Boolean) {
    val a = object {
        val B19 = object {
            val C19 = object {
                val D19 =  if (b) {
                    object {}
                } else null
            }
        }
    }

    if (a.B19.C19.D19 == null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & case_20.<no name provided>.B19.<no name provided>.C19.<no name provided>.D19.<no name provided>?")!>a.B19.C19.D19<!>
    }
}

// TESTCASE NUMBER: 21
enum class A21(val c: Int?) {
    A(1),
    B(5),
    D(null)
}

fun case_21() {
    if (A21.B.c == null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Int?")!>A21.B.c<!>
    }
}

// TESTCASE NUMBER: 22
fun case_22(a: (() -> Unit)?) {
    if (a == null) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & \(\(\) -> kotlin.Unit\)?")!>a<!>
    }
}

// TESTCASE NUMBER: 23
fun case_23(a: ((Float) -> Int?)?, b: Float?) {
    if (a == null && b == null) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & \(\(kotlin.Float\) -> kotlin.Int?\)?")!>a<!>
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Float?")!>b<!>
        if (<!SENSELESS_COMPARISON!><!DEBUG_INFO_CONSTANT!>a<!> != null<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("\(kotlin.Float\) -> kotlin.Int? & kotlin.Nothing & \(\(kotlin.Float\) -> kotlin.Int?\)?")!>a<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 24
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28329
 */
fun case_24(a: ((() -> Unit) -> Unit)?, b: (() -> Unit)?) {
    if (false || false || a == null && b === null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("\(\(\(\) -> kotlin.Unit\) -> kotlin.Unit\)?")!>a<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("\(\(\) -> kotlin.Unit\)?")!>b<!>
    }
}

// TESTCASE NUMBER: 25
fun case_25(b: Boolean) {
    val x = {
        if (b) object {
            val a = 10
        } else null
    }

    val y = if (b) x else null

    if (y != null) {
        val z = <!DEBUG_INFO_EXPRESSION_TYPE("case_25.<anonymous>.<no name provided>?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(\) -> case_25.<anonymous>.<no name provided>?\)? & \(\) -> case_25.<anonymous>.<no name provided>?"), NI;DEBUG_INFO_SMARTCAST!>y<!>()<!>

        if (z == null) {
            <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & case_25.<anonymous>.<no name provided>?")!>z<!>
        }
    }
}

// TESTCASE NUMBER: 26
fun case_26(a: Int?, b: Int? = if (a !== null) 0 else <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Int?")!>a<!>) {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>a<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>b<!>
}
