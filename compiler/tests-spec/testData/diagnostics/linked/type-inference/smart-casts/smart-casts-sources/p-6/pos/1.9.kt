// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// !WITH_CLASSES_WITH_PROJECTIONS
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 9
 * DESCRIPTION: Nullability condition, if, complex types with projections
 */

// TESTCASE NUMBER: 13, 14, 15, 16
class ClassWith6GenericParams<K, in L, out M, O, in P, out R>

// TESTCASE NUMBER: 1
fun case_1(x: List<<!REDUNDANT_PROJECTION!>out<!> Int?>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.Int?> & kotlin.collections.List<out kotlin.Int?>?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 2
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
fun case_2(a: List<<!REDUNDANT_PROJECTION!>out<!> List<<!REDUNDANT_PROJECTION!>out<!> List<<!REDUNDANT_PROJECTION!>out<!> List<<!REDUNDANT_PROJECTION!>out<!> List<<!REDUNDANT_PROJECTION!>out<!> List<<!REDUNDANT_PROJECTION!>out<!> Int?>?>?>?>?>?>?) {
    if (a != null) {
        val b = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.Int?>?>?>?>?>?> & kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.collections.List<out kotlin.Int?>?>?>?>?>?>?")!>a<!>[0]
        if (b != null) {
            val c = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>?>?>?>"), DEBUG_INFO_SMARTCAST!>b<!>[0]
            if (c != null) {
                val d = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>?>?>"), DEBUG_INFO_SMARTCAST!>c<!>[0]
                if (d != null) {
                    val e = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>?>"), DEBUG_INFO_SMARTCAST!>d<!>[0]
                    if (e != null) {
                        val f = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>? & kotlin.collections.List<kotlin.collections.List<kotlin.Any?>?>"), DEBUG_INFO_SMARTCAST!>e<!>[0]
                        if (f != null) {
                            val g = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Any?>? & kotlin.collections.List<kotlin.Any?>"), DEBUG_INFO_SMARTCAST!>f<!>[0]
                            if (g != null) {
                                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Any?")!>g<!>
                            }
                        }
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 3
fun case_3(a: MutableList<out Int>?) {
    if (a != null) {
        val b = a
        if (<!SENSELESS_COMPARISON!>a == null<!>)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.Int> & kotlin.collections.MutableList<out kotlin.Int>?")!>b<!>
    }
}

// TESTCASE NUMBER: 4
fun case_4(a: List<<!REDUNDANT_PROJECTION!>out<!> Int>?, b: List<<!REDUNDANT_PROJECTION!>out<!> Int> = if (a != null) <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.Int> & kotlin.collections.List<out kotlin.Int>?"), DEBUG_INFO_SMARTCAST!>a<!> else listOf()) {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.Int>?")!>a<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.Int>")!>b<!>
}

// TESTCASE NUMBER: 5
val x: List<<!REDUNDANT_PROJECTION!>out<!> Int>? = null

fun case_5() {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.Int> & kotlin.collections.List<out kotlin.Int>?")!>x<!>
    }
}

// TESTCASE NUMBER: 6
fun case_6() {
    val x: MutableList<out Int>? = null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.Int> & kotlin.collections.MutableList<out kotlin.Int>?")!>x<!>
    }
}

// TESTCASE NUMBER: 7
fun case_7() {
    var x: List<<!REDUNDANT_PROJECTION!>out<!> Int>? = null

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<out kotlin.Int> & kotlin.collections.List<out kotlin.Int>?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 8
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-25432
 */
class A8<T, K, L>(
    val x: T,
    val y: K,
    val z: L
)

fun case_8(x: A8<out Int?, out Short?, A8<out Int?, out Short?, out String?>?>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A8<out kotlin.Int?, out kotlin.Short?, A8<out kotlin.Int?, out kotlin.Short?, out kotlin.String?>?> & A8<out kotlin.Int?, out kotlin.Short?, A8<out kotlin.Int?, out kotlin.Short?, out kotlin.String?>?>?")!>x<!>
        if (x.x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>x.x<!>
        }
        if (x.y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Short?")!>x.y<!>
        }
        if (x.z != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A8<out kotlin.Int?, out kotlin.Short?, out kotlin.String?>?")!>x.z<!>
            if (x.z<!UNSAFE_CALL!>.<!>x != null) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>x.z<!UNSAFE_CALL!>.<!>x<!>
            }
            if (x.z<!UNSAFE_CALL!>.<!>y != null && x.z<!UNSAFE_CALL!>.<!>z != null) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Short?")!>x.z<!UNSAFE_CALL!>.<!>y<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.String?")!>x.z<!UNSAFE_CALL!>.<!>z<!>
            }
        }
    }
}

// TESTCASE NUMBER: 9
fun case_9(a: (MutableList<out Int>) -> MutableList<out Int>?, b: MutableList<out Int>?) {
    if (b != null) {
        val c = a(<!DEBUG_INFO_SMARTCAST!>b<!>)
        if (c != null)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.Int> & kotlin.collections.MutableList<out kotlin.Int>?")!>c<!>
    }
}

// TESTCASE NUMBER: 10
class A10<T>

fun case_9(a: A10<*>?) {
    if (a != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A10<*> & A10<*>?")!>a<!>
    }
}

// TESTCASE NUMBER: 11
class A11<out T>

val a10: A11<*>? = null

fun case_10() {
    if (a10 != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A11<*> & A11<*>?")!>a10<!>
    }
}

// TESTCASE NUMBER: 12
class A12<in T>

fun case_11() {
    val a: A12<*>? = null

    if (a != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A12<*> & A12<*>?")!>a<!>
    }
}

// TESTCASE NUMBER: 13
fun case_13(a: ClassWith6GenericParams<*, *, *, *, *, *>?) {
    if (a != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("ClassWith6GenericParams<*, *, *, *, *, *> & ClassWith6GenericParams<*, *, *, *, *, *>?")!>a<!>
    }
}

// TESTCASE NUMBER: 14
fun case_14(a: ClassWith6GenericParams<*, Int, *, List<*>, *, Map<Float, List<*>>>?) {
    if (a != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("ClassWith6GenericParams<*, kotlin.Int, *, kotlin.collections.List<*>, *, kotlin.collections.Map<kotlin.Float, kotlin.collections.List<*>>> & ClassWith6GenericParams<*, kotlin.Int, *, kotlin.collections.List<*>, *, kotlin.collections.Map<kotlin.Float, kotlin.collections.List<*>>>?")!>a<!>
    }
}

// TESTCASE NUMBER: 15
fun case_15(a: Any?) {
    if (a is ClassWith6GenericParams<*, *, *, *, *, *>?) {
        if (a != null) {
            a
        }
    }
}

// TESTCASE NUMBER: 16
fun case_16(a: Any?) {
    if (a === null) {
        } else {
        if (a !is ClassWith6GenericParams<*, *, *, *, *, *>?) {
        } else {
            <!DEBUG_INFO_EXPRESSION_TYPE("ClassWith6GenericParams<*, *, *, *, *, *> & kotlin.Any & kotlin.Any?"), DEBUG_INFO_SMARTCAST!>a<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 17
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
fun case_17(a: MutableList<out MutableList<MutableList<MutableList<MutableList<MutableList<Int?>?>?>?>?>?>?) {
    if (a != null) {
        val b = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.Int?>?>?>?>?>?> & kotlin.collections.MutableList<out kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.collections.MutableList<kotlin.Int?>?>?>?>?>?>?")!>a<!>[0]
        if (b != null) {
            val c = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?>?>?> & kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?>?>?>?")!>b<!>[0]
            if (c != null) {
                val d = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?>?> & kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?>?>?")!>c<!>[0]
                if (d != null) {
                    val e = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?> & kotlin.collections.MutableList<out kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?>?")!>d<!>[0]
                    if (e != null) {
                        val f = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?> & kotlin.collections.MutableList<out kotlin.collections.MutableList<in kotlin.Nothing?>?>?")!>e<!>[0]
                        if (f != null) {
                            val g = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<in kotlin.Nothing?> & kotlin.collections.MutableList<in kotlin.Nothing?>?")!>f<!>[0]
                            if (g != null) {
                                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Any?")!>g<!>
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 18
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
class A18<T>(val x: T)

fun case_18(a: A18<out Int?>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>a.x<!>
    }
}

/*
 * TESTCASE NUMBER: 19
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
class A19<T>(val x: T)

fun case_18(a: A19<out Nothing?>) {
    if (<!SENSELESS_COMPARISON!>a.x != null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>a.x<!>
    }
}

/*
 * TESTCASE NUMBER: 20
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
class A20<T>(val x: T)

fun case_20(a: A20<out Any?>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a.x<!>
    }
}

/*
 * TESTCASE NUMBER: 21
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
class A21<out T>(val x: T)

fun case_21(a: A21<<!REDUNDANT_PROJECTION!>out<!> Int?>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int?")!>a.x<!>
    }
}

/*
 * TESTCASE NUMBER: 22
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
class A22<out T>(val x: T)

fun case_22(a: A22<<!REDUNDANT_PROJECTION!>out<!> Nothing?>) {
    if (<!SENSELESS_COMPARISON!>a.x != null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>a.x<!>
    }
}

/*
 * TESTCASE NUMBER: 23
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28598
 */
class A23<out T>(val x: T)

fun case_23(a: A23<<!REDUNDANT_PROJECTION!>out<!> Any?>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 24
class A24<out T>(val x: T)

fun case_24(a: A24<Int?>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 25
class A25<out T>(val x: T)

fun case_25(a: A25<Nothing?>) {
    if (<!SENSELESS_COMPARISON!>a.x != null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 26
class A26<out T>(val x: T)

fun case_26(a: A26<Any?>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Any?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 27
class A27<T>(val x: T)

fun case_27(a: A27<in Any>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 28
class A28<T>(val x: T)

fun case_28(a: A28<in Float>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 29
class A29<T>(val x: T)

fun case_29(a: A29<in Nothing>) {
    if (a.x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a.x<!>
    }
}

// TESTCASE NUMBER: 30
class A30<in T>(val x: Int) {
    fun <K : T> t(): K {
        return 10 <!UNCHECKED_CAST!>as K<!>
    }
}

fun case_30() {
    val a = A30<Number?>(10)
    val b = a.t<Int?>()

    if (b != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>b<!>
    }
}

// TESTCASE NUMBER: 31
class A31<T>(val x: T) {
    fun f() = x
}

fun case_31(z: A31<Int?>) {
    val x = z.f()

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 32
class A32<T>(val x: T?) {
    fun f() = x
}

fun case_32(z: A32<Int>) {
    val x = z.f()

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 33
class A33<T>(val x: T) {
    fun f() = select(x, null)
}

fun case_33(z: A33<Int>) {
    val x = z.f()

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 34
class A34<T>(val x: T) {
    fun f(): T? = x
}

fun case_34(z: A34<Int>) {
    val x = z.f()

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}
