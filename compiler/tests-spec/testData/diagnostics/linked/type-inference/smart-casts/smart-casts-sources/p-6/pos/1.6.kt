// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 6
 * DESCRIPTION: null from variables
 * UNEXPECTED BEHAVIOUR
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

// TESTCASE NUMBER: 65, 66, 67, 68, 69, 70, 73, 74
open class A2 {
    fun t1() {}
}
open class B2: A2() {
    fun t2() {}
}
open class C2: B2() {}
open class D2: C2() {}
open class E2: D2() {}

// TESTCASE NUMBER: 71, 72, 73, 74
interface L1 {
    fun g() {}
    fun g1() {}
}
interface L2 {
    fun g() {}
    fun g2() {}
}
interface L3 {
    fun g() {}
    fun g3() {}
}

// TESTCASE NUMBER: 1
fun case_1(x: Any?) {
    val y = null
    if (x != <!DEBUG_INFO_CONSTANT!>y<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Any?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 2
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28159
 */
fun case_2(x: Nothing?) {
    val y = null
    if (<!DEBUG_INFO_CONSTANT!>x<!> !== <!DEBUG_INFO_CONSTANT!>y<!>) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>x<!>
    }
}

// TESTCASE NUMBER: 3
object A3 {
    val x: Number? = null
}

fun case_3() {
    val y = null

    if (A3.x == <!DEBUG_INFO_CONSTANT!>y<!>)
    else {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number & kotlin.Number?")!>A3.x<!>
    }
}

// TESTCASE NUMBER: 4
fun case_4(x: Char?, y: Nothing?) {
    if (x != <!DEBUG_INFO_CONSTANT!>y<!> && true) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Char & kotlin.Char?")!>x<!>
    }
}

// TESTCASE NUMBER: 5
fun case_5() {
    val x: Unit? = null
    val y: Nothing? = null

    if (x !== <!DEBUG_INFO_CONSTANT!>y<!>) <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Unit & kotlin.Unit?")!>x<!>
}

// TESTCASE NUMBER: 6
fun case_6(x: A?, z: Nothing?) {
    val y = true

    if (x != <!DEBUG_INFO_CONSTANT!>z<!> && !y) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A & A?")!>x<!>
    }
}

// TESTCASE NUMBER: 7
val x7: B? = null

fun case_7() {
    val y = null

    if (x7 != <!DEBUG_INFO_CONSTANT!>y<!> || <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & B?")!>x7<!> != <!DEBUG_INFO_CONSTANT!>y<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("B & B?")!>x7<!>
    }
}

// TESTCASE NUMBER: 8
fun case_8(x: C) {
    val y = null

    if (x !== <!DEBUG_INFO_CONSTANT!>y<!> && <!DEBUG_INFO_EXPRESSION_TYPE("C /* = kotlin.String */ & C /* = kotlin.String? */")!>x<!> != <!DEBUG_INFO_CONSTANT!>y<!>) <!DEBUG_INFO_EXPRESSION_TYPE("C /* = kotlin.String */ & C /* = kotlin.String? */")!>x<!>
}

// TESTCASE NUMBER: 9
fun case_9(x: C<!REDUNDANT_NULLABLE!>?<!>, y: Nothing?) {
    if (x === <!DEBUG_INFO_CONSTANT!>y<!>) {

    } else if (false) {
        <!DEBUG_INFO_EXPRESSION_TYPE("C /* = kotlin.String */ & C? /* = kotlin.String? */")!>x<!>
    }
}

// TESTCASE NUMBER: 10
class A10 {
    val x: D? = null
}

fun case_10() {
    val a = A10()
    val b = null

    if (a.x === <!DEBUG_INFO_CONSTANT!>b<!> || true) {
        if (a.x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("D /* = kotlin.Float */ & D? /* = kotlin.Float? */")!>a.x<!>
        }
    }
}

// TESTCASE NUMBER: 11
val x11: E = null
fun case_11(x: E<!REDUNDANT_NULLABLE!>?<!>, y: E) {
    val t: E = null
    val z = null
    val g = null

    if (x == <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_CONSTANT!>x<!> == <!DEBUG_INFO_CONSTANT!>g<!>) {

    } else {
        if (y != <!DEBUG_INFO_CONSTANT!>z<!>) {
            if (x11 == <!DEBUG_INFO_CONSTANT!>z<!>) {
                if (t != <!DEBUG_INFO_CONSTANT!>z<!> || <!DEBUG_INFO_CONSTANT!>t<!> != <!DEBUG_INFO_CONSTANT!>g<!>) {
                    <!DEBUG_INFO_EXPRESSION_TYPE("E /* = kotlin.String */ & E? /* = kotlin.String? */")!>x<!>
                }
            }
        }
    }
}

// TESTCASE NUMBER: 12
fun case_12(x: E, y: E, z1: Nothing?, z2: Nothing?) = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>if (x == <!DEBUG_INFO_CONSTANT!>z1<!> || x == <!DEBUG_INFO_CONSTANT!>z2<!>) "1"
    else if (y === <!DEBUG_INFO_CONSTANT!>z1<!> && <!DEBUG_INFO_CONSTANT!>y<!> == <!DEBUG_INFO_CONSTANT!>z2<!>) <!DEBUG_INFO_EXPRESSION_TYPE("E /* = kotlin.String */ & E /* = kotlin.String? */"), DEBUG_INFO_SMARTCAST!>x<!>
    else "-1"<!>

// TESTCASE NUMBER: 13
fun case_13(x: othertypes.A?, z: Nothing?) =
<!DEBUG_INFO_EXPRESSION_TYPE("othertypes.A & othertypes.A")!>if (x == <!DEBUG_INFO_CONSTANT!>z<!> || x === <!DEBUG_INFO_CONSTANT!>z<!> && x == <!DEBUG_INFO_CONSTANT!>z<!>) {
    throw Exception()
} else <!DEBUG_INFO_EXPRESSION_TYPE("othertypes.A & othertypes.A?"), DEBUG_INFO_SMARTCAST!>x<!><!>

// TESTCASE NUMBER: 14
class A14 {
    val x: othertypes.B<!REDUNDANT_NULLABLE!>?<!>
    init {
        x = othertypes.B()
    }
}

fun case_14() {
    val a = A14()
    val z1 = null
    val z2 = <!DEBUG_INFO_CONSTANT!>z1<!>

    if (a.x != <!DEBUG_INFO_CONSTANT!>z1<!> && a.x != <!DEBUG_INFO_CONSTANT!>z2<!> || a.x != <!DEBUG_INFO_CONSTANT!>z2<!> && <!SENSELESS_COMPARISON!>a.x !== null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("othertypes.B /* = kotlin.String */ & othertypes.B? /* = kotlin.String? */")!>a.x<!>
    }
}

// TESTCASE NUMBER: 15
fun case_15(x: F) {
    val z = null
    val <!UNUSED_VARIABLE!>t<!> = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String")!>if (x === null || <!DEBUG_INFO_CONSTANT!>z<!> == x && x === <!DEBUG_INFO_CONSTANT!>z<!> || <!SENSELESS_COMPARISON!>null === x<!>) "" else {
        <!DEBUG_INFO_EXPRESSION_TYPE("F /* = kotlin.String */ & F /* = kotlin.String? */"), DEBUG_INFO_SMARTCAST!>x<!>
    }<!>
}

// TESTCASE NUMBER: 16
fun case_16() {
    val x: G = null
    val z: Nothing? = null

    if (<!DEBUG_INFO_CONSTANT!>x<!> !== <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("G /* = kotlin.Nothing? */")!>x<!>
    }
}

// TESTCASE NUMBER: 17
val x17: Int? = 1
val z17 = null

val case_17 = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int")!>if (x17 === <!DEBUG_INFO_CONSTANT!>z17<!>) 0 else {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?"), DEBUG_INFO_SMARTCAST!>x17<!>
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

fun case_18(a: A18.B18.C18.D18.E18.F18.G18.H18.I18?, b: Boolean) {
    val z1 = null
    val z2 = null

    if (a != (if (b) <!DEBUG_INFO_CONSTANT!>z1<!> else <!DEBUG_INFO_CONSTANT!>z2<!>) || <!DEBUG_INFO_CONSTANT!>z1<!> !== <!DEBUG_INFO_CONSTANT!>a<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A18.B18.C18.D18.E18.F18.G18.H18.I18 & A18.B18.C18.D18.E18.F18.G18.H18.I18?")!>a<!>
    }
}

// TESTCASE NUMBER: 19
fun case_19(b: Boolean) {
    val z = null
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
                            } else <!DEBUG_INFO_CONSTANT!>z<!>
                        }
                    } else <!DEBUG_INFO_CONSTANT!>z<!>
                }
            } else <!DEBUG_INFO_CONSTANT!>z<!>
        }
    } else <!DEBUG_INFO_CONSTANT!>z<!>

    if (a != <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_SMARTCAST!>a<!>.B19 !== <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19 != <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19<!>.D19 != <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19<!>.D19<!>.x !== <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number & kotlin.Number?")!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!><!DEBUG_INFO_SMARTCAST!>a<!>.B19<!>.C19<!>.D19<!>.x<!>
    }
}

// TESTCASE NUMBER: 20
fun case_20(b: Boolean, z: Nothing?) {
    val a = object {
        val B19 = object {
            val C19 = object {
                val D19 =  if (b) {
                    object {}
                } else <!DEBUG_INFO_CONSTANT!>z<!>
            }
        }
    }

    if (a.B19.C19.D19 !== <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("case_20.<no name provided>.B19.<no name provided>.C19.<no name provided>.D19.<no name provided> & case_20.<no name provided>.B19.<no name provided>.C19.<no name provided>.D19.<no name provided>?")!>a.B19.C19.D19<!>
    }
}

// TESTCASE NUMBER: 21
val z21 = null

enum class A21(val c: Int?) {
    A(1),
    B(5),
    D(<!DEBUG_INFO_CONSTANT!>z21<!>)
}

fun case_21() {
    if (A21.B.c !== <!DEBUG_INFO_CONSTANT!>z21<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>A21.B.c<!>
    }
}

var z22 = null

// TESTCASE NUMBER: 22
fun case_22(a: (() -> Unit)?) {
    if (a != <!DEBUG_INFO_CONSTANT!>z22<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Unit")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(\) -> kotlin.Unit\)? & \(\) -> kotlin.Unit"), DEBUG_INFO_SMARTCAST!>a<!>()<!>
    }
}

// TESTCASE NUMBER: 23
var z23 = null

fun case_23(a: ((Float) -> Int?)?, b: Float?, z: Nothing?) {
    if (a != <!DEBUG_INFO_CONSTANT!>z<!> && b !== <!DEBUG_INFO_CONSTANT!>z<!> && b !== <!DEBUG_INFO_CONSTANT!>z<!>) {
        val x = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(kotlin.Float\) -> kotlin.Int?\)? & \(kotlin.Float\) -> kotlin.Int?"), DEBUG_INFO_SMARTCAST!>a<!>(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Float & kotlin.Float?"), DEBUG_INFO_SMARTCAST!>b<!>)<!>
        if (x != <!DEBUG_INFO_CONSTANT!>z<!> || <!DEBUG_INFO_CONSTANT!>x<!> !== <!DEBUG_INFO_CONSTANT!>z23<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 24
fun case_24(a: ((() -> Unit) -> Unit)?, b: (() -> Unit)?, z: Nothing?) =
    if (a !== <!DEBUG_INFO_CONSTANT!>z<!> && b !== <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("\(\(\(\) -> kotlin.Unit\) -> kotlin.Unit\)? & \(\(\) -> kotlin.Unit\) -> kotlin.Unit"), DEBUG_INFO_SMARTCAST!>a<!>(<!DEBUG_INFO_EXPRESSION_TYPE("\(\) -> kotlin.Unit & \(\(\) -> kotlin.Unit\)?"), DEBUG_INFO_SMARTCAST!>b<!>)
    } else <!DEBUG_INFO_CONSTANT!>z<!>

// TESTCASE NUMBER: 25
var z25 = null

fun case_25(b: Boolean, z: Nothing?) {
    val x = {
        if (b) object {
            val a = 10
        } else <!DEBUG_INFO_CONSTANT!>z<!>
    }

    val y = if (b) x else <!DEBUG_INFO_CONSTANT!>z<!>

    if (y !== <!DEBUG_INFO_CONSTANT!>z<!> || <!DEBUG_INFO_CONSTANT!>y<!> != <!DEBUG_INFO_CONSTANT!>z25<!>) {
        val z1 = <!DEBUG_INFO_EXPRESSION_TYPE("case_25.<anonymous>.<no name provided>?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(\) -> case_25.<anonymous>.<no name provided>?\)? & \(\) -> case_25.<anonymous>.<no name provided>?"), DEBUG_INFO_SMARTCAST!>y<!>()<!>

        if (z1 != <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_CONSTANT!>z25<!> !== z1) {
            <!DEBUG_INFO_EXPRESSION_TYPE("case_25.<anonymous>.<no name provided>? & case_25.<anonymous>.<no name provided>"), DEBUG_INFO_SMARTCAST!>z1<!>.a
        }
    }
}

// TESTCASE NUMBER: 26
var z26 = null

fun case_26(a: ((Float) -> Int?)?, b: Float?) {
    var z = null

    if (a != <!DEBUG_INFO_CONSTANT!>z<!> == true && b != <!DEBUG_INFO_CONSTANT!>z26<!> == true) {
        val x = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(kotlin.Float\) -> kotlin.Int?\)? & \(kotlin.Float\) -> kotlin.Int?"), DEBUG_INFO_SMARTCAST!>a<!>(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Float & kotlin.Float?"), DEBUG_INFO_SMARTCAST!>b<!>)<!>
        if (x != <!DEBUG_INFO_CONSTANT!>z26<!> == true || <!DEBUG_INFO_CONSTANT!>z<!> !== <!DEBUG_INFO_CONSTANT!>x<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 27
fun case_27(z: Nothing?) {
    if (A3.x == <!DEBUG_INFO_CONSTANT!>z<!> == true == true == true == true == true == true == true == true == true == true == true == true == true == true)
    else {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Number & kotlin.Number?")!>A3.x<!>
    }
}

// TESTCASE NUMBER: 28
object A28 {
    object B28 {
        object C28 {
            object D28 {
                object E28 {
                    object F28 {
                        object G28 {
                            object H28 {
                                object I28 {
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

var z28 = null

fun case_28(a: A28.B28.C28.D28.E28.F28.G28.H28.I28?) =
    if (a != <!DEBUG_INFO_CONSTANT!>z28<!> == true == false == false == false == true == false == true == false == false == true == true) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A28.B28.C28.D28.E28.F28.G28.H28.I28? & A28.B28.C28.D28.E28.F28.G28.H28.I28"), DEBUG_INFO_SMARTCAST!>a<!>.x
    } else -1

/*
 * TESTCASE NUMBER: 29
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28328, KT-28329
 */
fun case_29(b: Boolean) {
    val z = null
    val x = {
        if (b) object {
            val a = 10
        } else null
    }

    val y = if (b) x else null

    if (false || false || false || false || y !== <!DEBUG_INFO_CONSTANT!>z<!>) {
        val z1 = <!DEBUG_INFO_EXPRESSION_TYPE("case_29.<anonymous>.<no name provided>?")!><!UNSAFE_CALL!>y<!>()<!>

        if (<!DEBUG_INFO_CONSTANT!>z<!> !== z1 || false) {
            <!DEBUG_INFO_EXPRESSION_TYPE("case_29.<anonymous>.<no name provided>?")!>z1<!><!UNSAFE_CALL!>.<!>a
        }
    }
}

// TESTCASE NUMBER: 30
var z30 = null

fun case_30(a: ((Float) -> Int?)?, b: Float?) {
    if (<!DEBUG_INFO_CONSTANT!>z30<!> != a == true && b != <!DEBUG_INFO_CONSTANT!>z30<!> == true || false || false || false || false || false || false || false || false || false) {
        val x = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(kotlin.Float\) -> kotlin.Int?\)? & \(kotlin.Float\) -> kotlin.Int?"), DEBUG_INFO_SMARTCAST!>a<!>(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Float & kotlin.Float?"), DEBUG_INFO_SMARTCAST!>b<!>)<!>
        if (false || <!DEBUG_INFO_CONSTANT!>z30<!> != x == true) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 31
enum class A31(val c: Int?) {
    A(1),
    B(5),
    D(null)
}

fun case_31(z1: Boolean?, z: Nothing?) {
    if (false || A31.B.c != <!DEBUG_INFO_CONSTANT!>z<!> && z1 !== <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_SMARTCAST!>z1<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>A31.B.c<!>
    }
}

// TESTCASE NUMBER: 32
object A32 {
    object B32 {
        object C32 {
            object D32 {
                object E32 {
                    object F32 {
                        object G32 {
                            object H32 {
                                object I32 {
                                    val x: Int? = 32
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val z32 = null

fun case_32(a: A32.B32.C32.D32.E32.F32.G32.H32.I32?) =
    if (a == <!DEBUG_INFO_CONSTANT!>z32<!> == true == false == false == false == true == false == true == false == false == true == true && true) {
        -1
    } else {
        <!DEBUG_INFO_EXPRESSION_TYPE("A32.B32.C32.D32.E32.F32.G32.H32.I32? & A32.B32.C32.D32.E32.F32.G32.H32.I32"), DEBUG_INFO_SMARTCAST!>a<!>.x
    }

// TESTCASE NUMBER: 33
fun case_33(a: ((Float) -> Int?)?, b: Float?, c: Boolean?) {
    var z = null

    if (true && a == <!DEBUG_INFO_CONSTANT!>z<!> == true || b == null == true) {

    } else {
        val x = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(kotlin.Float\) -> kotlin.Int?\)? & \(kotlin.Float\) -> kotlin.Int?"), DEBUG_INFO_SMARTCAST!>a<!>(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Float & kotlin.Float?"), DEBUG_INFO_SMARTCAST!>b<!>)<!>
        if (x == <!DEBUG_INFO_CONSTANT!>z<!> == true && <!DEBUG_INFO_CONSTANT!>x<!> === <!DEBUG_INFO_CONSTANT!>z<!> || (c != <!DEBUG_INFO_CONSTANT!>z<!> && !<!DEBUG_INFO_SMARTCAST!>c<!>)) {

        } else {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?"), DEBUG_INFO_SMARTCAST!>x<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 34
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28329
 */
enum class A34(val c: Int?) {
    A(1),
    B(5),
    D(null)
}

var z34 = null

fun case_34(z1: Boolean?) {
    var z = null

    if (true && true && true && true && A34.B.c != <!DEBUG_INFO_CONSTANT!>z34<!> && <!SENSELESS_COMPARISON!>A34.B.c !== null<!> && A34.B.c !== <!DEBUG_INFO_CONSTANT!>z<!> || z1 != <!DEBUG_INFO_CONSTANT!>z34<!> || <!ALWAYS_NULL!>z1<!>!! && true && true) {

    } else {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>A34.B.c<!>
    }
}

// TESTCASE NUMBER: 35
object A35 {
    object B35 {
        object C35 {
            object D35 {
                object E35 {
                    object F35 {
                        object G35 {
                            object H35 {
                                object I35 {
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

val z35 = null

fun case_35(a: A35.B35.C35.D35.E35.F35.G35.H35.I35?) {
    val g = false

    if (true && a != <!DEBUG_INFO_CONSTANT!>z35<!> && a !== <!DEBUG_INFO_CONSTANT!>z35<!> || g || !g || true || !true) {

    } else {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & A35.B35.C35.D35.E35.F35.G35.H35.I35?")!>a<!>
    }
}

// TESTCASE NUMBER: 36
fun case_36(x: Any) {
    var z = null

    if (x == <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing & kotlin.Any")!>x<!>
    }
}

// TESTCASE NUMBER: 37
fun case_37(x: Nothing?, y: Nothing?) {
    if (<!DEBUG_INFO_CONSTANT!>x<!> == <!DEBUG_INFO_CONSTANT!>y<!>) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>x<!>
    }
}

// TESTCASE NUMBER: 38
object A38 {
    val x: Number = 11
}

fun case_38() {
    val z = null

    if (A38.x != <!DEBUG_INFO_CONSTANT!>z<!>)
    else {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing & kotlin.Number")!>A38.x<!>
    }
}

// TESTCASE NUMBER: 39
var z39 = null

fun case_39(x: Char?) {
    if (x == <!DEBUG_INFO_CONSTANT!>z39<!> && true) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Char?")!>x<!>
    }
}

// TESTCASE NUMBER: 40
val z40 = null

fun case_40() {
    val x: Unit? = null
    var z = null

    if (x == <!DEBUG_INFO_CONSTANT!>z40<!> || <!DEBUG_INFO_CONSTANT!>z<!> === x) <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Unit?")!>x<!>
}

// TESTCASE NUMBER: 41
fun case_41(x: A?, z: Nothing?) {
    val y = true

    if (x === <!DEBUG_INFO_CONSTANT!>z<!> && !y) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & A?")!>x<!>
    }
}

// TESTCASE NUMBER: 42
val x42: B = B
var z42: Nothing? = null

fun case_42() {
    if (x42 == <!DEBUG_INFO_CONSTANT!>z42<!> || <!DEBUG_INFO_EXPRESSION_TYPE("B")!>x42<!> === <!DEBUG_INFO_CONSTANT!>z42<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing & B")!>x42<!>
    }
}

// TESTCASE NUMBER: 43
fun case_43(x: C) {
    val z = null

    if (x == <!DEBUG_INFO_CONSTANT!>z<!> && <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & C /* = kotlin.String? */")!>x<!> == <!DEBUG_INFO_CONSTANT!>z<!>) <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & C /* = kotlin.String? */")!>x<!>
}

/*
 * TESTCASE NUMBER: 44
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28329
 */
fun case_44(x: C<!REDUNDANT_NULLABLE!>?<!>, z1: Nothing?) {
    if (true && true && true && true && x !== <!DEBUG_INFO_CONSTANT!>z1<!>) {

    } else if (false) {
        <!DEBUG_INFO_EXPRESSION_TYPE("C? /* = kotlin.String? */")!>x<!>
    }
}

// TESTCASE NUMBER: 45
class A45 {
    val x: D? = null
}

fun case_45() {
    val a = A45()
    var z: Nothing? = null

    if (a.x != <!DEBUG_INFO_CONSTANT!>z<!> || true) {
        if (a.x == null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & D? /* = kotlin.Float? */")!>a.x<!>
        }
    }
}

// TESTCASE NUMBER: 46
var z46: Nothing? = null
val x46: E = null

fun case_46(x: E<!REDUNDANT_NULLABLE!>?<!>, y: E) {
    val t: E = null
    var z: Nothing? = null

    if (x != <!DEBUG_INFO_CONSTANT!>z46<!>) {

    } else {
        if (y === <!DEBUG_INFO_CONSTANT!>z46<!>) {
            if (<!DEBUG_INFO_CONSTANT!>z<!> != x46) {
                if (<!DEBUG_INFO_CONSTANT!>z<!> === t || t == <!DEBUG_INFO_CONSTANT!>z46<!>) {
                    <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & E? /* = kotlin.String? */")!>x<!>
                }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 47
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28328
 */
fun case_47(x: E, y: E, z: Nothing?) = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String?")!>if (x !== <!DEBUG_INFO_CONSTANT!>z<!> && true && true && true) "1"
    else if (y != <!DEBUG_INFO_CONSTANT!>z<!>) <!DEBUG_INFO_EXPRESSION_TYPE("E /* = kotlin.String? */")!>x<!>
    else "-1"<!>

// TESTCASE NUMBER: 48
fun case_48(x: othertypes.A?, z: Nothing?) =
<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>if (x != <!DEBUG_INFO_CONSTANT!>z<!> && true) {
    throw Exception()
} else <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & othertypes.A?")!>x<!><!>

// TESTCASE NUMBER: 49
class A49 {
    val x: othertypes.B<!REDUNDANT_NULLABLE!>?<!>
    init {
        x = othertypes.B()
    }
}

fun case_49() {
    val a = A49()
    var z = null

    if (a.x === <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & othertypes.B? /* = kotlin.String? */")!>a.x<!>
    }
}

// TESTCASE NUMBER: 50
fun case_50(x: F) {
    val z1 = null
    val z2 = null
    val <!UNUSED_VARIABLE!>t<!> = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String?")!>if (x != <!DEBUG_INFO_CONSTANT!>z1<!> && <!DEBUG_INFO_CONSTANT!>z2<!> !== x) "" else {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & F /* = kotlin.String? */")!>x<!>
    }<!>
}

// TESTCASE NUMBER: 51
fun case_51() {
    val x: G = null
    val z: Nothing? = null

    if (<!DEBUG_INFO_CONSTANT!>x<!> === <!DEBUG_INFO_CONSTANT!>z<!> || <!DEBUG_INFO_CONSTANT!>z<!> == <!DEBUG_INFO_CONSTANT!>x<!> && <!DEBUG_INFO_CONSTANT!>x<!> == <!DEBUG_INFO_CONSTANT!>z<!> || false || false || false) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("G /* = kotlin.Nothing? */")!>x<!>
    }
}

// TESTCASE NUMBER: 52
val x52: Int? = 1
val z52: Nothing? = null

val case_52 = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>if (x52 !== <!DEBUG_INFO_CONSTANT!>z52<!> && <!DEBUG_INFO_CONSTANT!>z52<!> != x52) 0 else {
    <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Int?")!>x52<!>
}<!>

//TESTCASE NUMBER: 53
object A53 {
    var z53 = null
    object B53 {
        object C53 {
            object D53 {
                object E53 {
                    object F53 {
                        object G53 {
                            object H53 {
                                object I53 {
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

fun case_53(a: A53.B53.C53.D53.E53.F53.G53.H53.I53?) {
    if (a == A53.z53) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & A53.B53.C53.D53.E53.F53.G53.H53.I53?")!>a<!>
    }
}

// TESTCASE NUMBER: 54
fun case_54(b: Boolean) {
    val a = if (b) {
        object {
            var z = null
            val B54 = if (b) {
                object {
                    val C54 = if (b) {
                        object {
                            val D54 = if (b) {
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

    val z = null

    if (a != <!DEBUG_INFO_CONSTANT!>z<!> && <!NI;DEBUG_INFO_SMARTCAST!>a<!>.B54 !== <!DEBUG_INFO_SMARTCAST!>a<!>.z && <!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B54<!>.C54 != <!DEBUG_INFO_SMARTCAST!>a<!>.z && <!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B54<!>.C54<!>.D54 != <!DEBUG_INFO_SMARTCAST!>a<!>.z && <!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B54<!>.C54<!>.D54<!>.x === <!DEBUG_INFO_SMARTCAST!>a<!>.z) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Number?")!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!><!NI;DEBUG_INFO_SMARTCAST!>a<!>.B54<!>.C54<!>.D54<!>.x<!>
    }
}

// TESTCASE NUMBER: 55
fun case_55(b: Boolean) {
    val a = object {
        val B19 = object {
            val C19 = object {
                var z = null
                val D19 =  if (b) {
                    object {}
                } else null
            }
        }
    }

    if (a.B19.C19.D19 === a.B19.C19.z) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & case_55.<no name provided>.B19.<no name provided>.C19.<no name provided>.D19.<no name provided>?")!>a.B19.C19.D19<!>
    }
}

// TESTCASE NUMBER: 56
enum class A56(val c: Int?) {
    A(1),
    B(5),
    D(null)
}

val z56 = null

fun case_56() {
    if (A56.B.c == <!DEBUG_INFO_CONSTANT!>z56<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Int?")!>A56.B.c<!>
    }
}

// TESTCASE NUMBER: 57
fun case_57(a: (() -> Unit)) {
    var z = null

    if (a == <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing & \(\) -> kotlin.Unit")!>a<!>
    }
}

// TESTCASE NUMBER: 58
fun case_58(a: ((Float) -> Int?)?, b: Float?, z: Nothing?) {
    if (a === <!DEBUG_INFO_CONSTANT!>z<!> && b == <!DEBUG_INFO_CONSTANT!>z<!> || <!DEBUG_INFO_CONSTANT!>z<!> == a && <!DEBUG_INFO_CONSTANT!>z<!> === b) {
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & \(\(kotlin.Float\) -> kotlin.Int?\)?")!>a<!>
        <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & kotlin.Float?")!>b<!>
        if (<!DEBUG_INFO_CONSTANT!>a<!> != <!DEBUG_INFO_CONSTANT!>z<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("\(kotlin.Float\) -> kotlin.Int? & kotlin.Nothing & \(\(kotlin.Float\) -> kotlin.Int?\)?")!>a<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 59
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28329
 */
fun case_59(a: ((() -> Unit) -> Unit)?, b: (() -> Unit)?, z: Nothing?) {
    if (false || false || a == <!DEBUG_INFO_CONSTANT!>z<!> && b === <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("\(\(\(\) -> kotlin.Unit\) -> kotlin.Unit\)?")!>a<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("\(\(\) -> kotlin.Unit\)?")!>b<!>
    }
}

// TESTCASE NUMBER: 60
var z60: Nothing? = null

fun case_60(b: Boolean) {
    val x = {
        if (b) object {
            val a = 10
        } else <!DEBUG_INFO_CONSTANT!>z60<!>
    }

    val y = if (b) x else <!DEBUG_INFO_CONSTANT!>z60<!>

    if (y != <!DEBUG_INFO_CONSTANT!>z60<!>) {
        val z = <!DEBUG_INFO_EXPRESSION_TYPE("case_60.<anonymous>.<no name provided>?")!><!DEBUG_INFO_EXPRESSION_TYPE("\(\(\) -> case_60.<anonymous>.<no name provided>?\)? & \(\) -> case_60.<anonymous>.<no name provided>?"), NI;DEBUG_INFO_SMARTCAST!>y<!>()<!>

        if (z == <!DEBUG_INFO_CONSTANT!>z60<!>) {
            <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing? & case_60.<anonymous>.<no name provided>?")!>z<!>
        }
    }
}

// TESTCASE NUMBER: 61
var z61 = null

fun case_61(x: Any?) {
    if (x is Number?) {
        if (x !== <!DEBUG_INFO_CONSTANT!>z61<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Number & kotlin.Any?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 62
fun case_62(x: Any?) {
    var z = null
    if (x is Number? && x is Int? && x != <!DEBUG_INFO_CONSTANT!>z<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 63
fun case_63(x: Any?, b: Boolean) {
    val z1 = null
    val z2 = null
    val z3 = null

    if (x is Number?) {
        if (x !== when (b) { true -> <!DEBUG_INFO_CONSTANT!>z1<!>; false -> <!DEBUG_INFO_CONSTANT!>z2<!>; <!REDUNDANT_ELSE_IN_WHEN!>else<!> -> <!DEBUG_INFO_CONSTANT!>z3<!> }) {
            if (x is Int?) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?")!>x<!>
            }
        }
    }
}

// TESTCASE NUMBER: 64
var z64 = null

fun case_64(x: Any?) {
    if (x != try {<!DEBUG_INFO_CONSTANT!>z64<!>} finally {}) {
        if (x is Number) {
            if (x is Int?) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?")!>x<!>
            }
        }
    }
}

// TESTCASE NUMBER: 65
fun case_65(x: Any?, z: Nothing?) {
    if (x is A2?) {
        if (x is B2?) {
            if (x is C2?) {
                if (x is D2?) {
                    if (x is E2?) {
                        if (x != <!DEBUG_INFO_CONSTANT!>z<!>) {
                            <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & C2 & D2 & E2 & kotlin.Any & kotlin.Any?")!>x<!>
                        }
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 66
fun case_66(x: Any?, z1: Nothing?, z2: Nothing?, b: Boolean) {
    if (x is A2?) {
        if (x is B2?) {
            if (x is C2?) {
                if (x != if (b) { <!DEBUG_INFO_CONSTANT!>z1<!> } else { <!DEBUG_INFO_CONSTANT!>z2<!> } && x is D2?) {
                    if (x is E2?) {
                        <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & C2 & D2 & E2 & kotlin.Any & kotlin.Any?")!>x<!>
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 67
fun case_67(x: Any?) {
    var z = null

    if (x is A2? && x is B2? && x is C2?) {
        if (x is D2? && x != (fun (): Nothing? { return <!DEBUG_INFO_CONSTANT!>z<!> })() && x is E2?) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & C2 & D2 & E2 & kotlin.Any & kotlin.Any?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 68
fun case_68(x: Any?, z: Nothing?) {
    if (x is A2? && x is B2? && x is C2?) {
        if (x is D2? && x != (fun (): Nothing? { return <!DEBUG_INFO_CONSTANT!>z<!> })() && x is E2?) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & C2 & D2 & E2 & kotlin.Any & kotlin.Any?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 69
fun case_69(x: Any?, z: Nothing?) {
    if (x is A2? && x is B2? && x is C2? && x is D2? && x != try { <!DEBUG_INFO_CONSTANT!>z<!> } catch (e: Exception) { <!DEBUG_INFO_CONSTANT!>z<!> } && x is E2?) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & C2 & D2 & E2 & kotlin.Any & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 70
val z70_1: Nothing? = null
var z70_2 = null

fun case_70(x: Any?) {
    if (x is A2? && x is B2? && x is C2?) {
        if (x is D2?) {

        } else if (x is E2? && x != <!DEBUG_INFO_CONSTANT!>z70_1<!> || x != <!DEBUG_INFO_CONSTANT!>z70_2<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & C2 & kotlin.Any & kotlin.Any?")!>x<!>
        }
    } else if (x is D2? && x !== <!DEBUG_INFO_CONSTANT!>z70_1<!> && x is E2?) {
        <!DEBUG_INFO_EXPRESSION_TYPE("D2 & E2 & kotlin.Any & kotlin.Any?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 71
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_71(t: Any?) {
    val z1 = null
    var z2 = <!DEBUG_INFO_CONSTANT!>z1<!>

    if (t is L1?) {
        if (t is L2?) {
            if (t != <!DEBUG_INFO_CONSTANT!>z2<!>) {
                <!DEBUG_INFO_EXPRESSION_TYPE("L1 & L2 & kotlin.Any & kotlin.Any?")!>t<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g()

                <!DEBUG_INFO_EXPRESSION_TYPE("L1 & L2 & kotlin.Any & kotlin.Any?")!>t<!>.let { <!DEBUG_INFO_EXPRESSION_TYPE("{Any & L1 & L2}")!>it<!>.g1(); <!DEBUG_INFO_EXPRESSION_TYPE("{Any & L1 & L2}")!>it<!>.g2() }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 72
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362, KT-27032
 */
fun case_72(t: Any?, z1: Nothing?) {
    var z2 = null

    if (t is L1? && t != <!DEBUG_INFO_CONSTANT!>z1<!> ?: <!DEBUG_INFO_CONSTANT!>z2<!> && t is L2?) {
        <!DEBUG_INFO_EXPRESSION_TYPE("L1 & L2 & kotlin.Any & kotlin.Any?")!>t<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g()

        <!DEBUG_INFO_EXPRESSION_TYPE("L1 & L2 & kotlin.Any & kotlin.Any?")!>t<!>.let { <!DEBUG_INFO_EXPRESSION_TYPE("{Any & L1 & L2}")!>it<!>.g1(); <!DEBUG_INFO_EXPRESSION_TYPE("{Any & L1 & L2}")!>it<!>.g2() }
    }
}

/*
 * TESTCASE NUMBER: 73
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_73(t: Any?) {
    val `null` = null

    if (t is L2?) {
        if (t is A2?) {
            if (t is B2? && t is L1?) {
                if (t !is L3?) {} else if (false) {
                    if (t != <!DEBUG_INFO_CONSTANT!>`null`<!>) {
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L3"), DEBUG_INFO_SMARTCAST!>t<!>.g()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & A2"), DEBUG_INFO_SMARTCAST!>t<!>.t1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B2"), DEBUG_INFO_SMARTCAST!>t<!>.t2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & L1 & L2 & L3 & kotlin.Any & kotlin.Any?")!>t<!>
                    }
                }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 74
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
val z74_1 = null
var z74_2 = null

fun case_74(t: Any?) {
    if (t is L2?) {
        if (t is A2?) {
            if (t == <!DEBUG_INFO_CONSTANT!>z74_1<!> || t === <!DEBUG_INFO_CONSTANT!>z74_2<!> || t !is L1?) else {
                if (t is B2?) {
                    if (t is L3?) {
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L3"), DEBUG_INFO_SMARTCAST!>t<!>.g()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & A2"), DEBUG_INFO_SMARTCAST!>t<!>.t1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B2"), DEBUG_INFO_SMARTCAST!>t<!>.t2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & L1 & L2 & L3 & kotlin.Any & kotlin.Any?")!>t<!>
                    }
                }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 75
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_75(t: Any?, z: Nothing?) {
    if (t !is B2? || <!USELESS_IS_CHECK!>t !is A2?<!>) else {
        if (t === ((((((<!DEBUG_INFO_CONSTANT!>z<!>)))))) || t !is L1?) else {
            if (t !is L2? || t !is L3?) {} else {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L3"), DEBUG_INFO_SMARTCAST!>t<!>.g()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B2"), DEBUG_INFO_SMARTCAST!>t<!>.t1()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B2"), DEBUG_INFO_SMARTCAST!>t<!>.t2()
                <!DEBUG_INFO_EXPRESSION_TYPE("A2 & B2 & L1 & L2 & L3 & kotlin.Any & kotlin.Any?"), DEBUG_INFO_SMARTCAST!>t<!>
            }
        }
    }
}

// TESTCASE NUMBER: 76
fun case_76(a: Any?, b: Int = if (<!DEPRECATED_IDENTITY_EQUALS!>a !is Number? === true<!> || a !is Int? == true || a != null == false == true) 0 else <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Any?"), DEBUG_INFO_SMARTCAST!>a<!>) {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int")!>b<!>
}
