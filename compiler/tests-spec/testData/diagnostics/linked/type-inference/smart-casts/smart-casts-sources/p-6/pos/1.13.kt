// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 13
 * DESCRIPTION: Nullability condition, if, type parameters
 * NOTE: lazy smartcasts
 */

// TESTCASE NUMBER: 1
fun <T> case_1(x: T) {
    var y = null

    if (<!DEBUG_INFO_CONSTANT!>y<!> != x) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.apply { equals(x); <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x) }
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.also { <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(x) }
    }
}

// TESTCASE NUMBER: 2
fun <T> case_2(x: T?, y: Nothing?) {
    if (<!DEBUG_INFO_CONSTANT!>y<!> != x) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.apply { equals(x); <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x) }
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.also { <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(x) }
    }
}

// TESTCASE NUMBER: 3
fun <T> case_3(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 4
fun <T> case_4(x: T?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
    }
}

// TESTCASE NUMBER: 5
interface A5 { fun test() }

fun <T> case_5(x: T?) {
    if (x is A5) {
        if (<!SENSELESS_COMPARISON!>x != null<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A5 & T!! & T?")!>x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A5"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A5"), DEBUG_INFO_SMARTCAST!>x<!>.test()

            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A5"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A5"), DEBUG_INFO_SMARTCAST!>x<!>.test()
            x.apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>.equals(x)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>.test()
            }
            x.also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 6
interface A6 { fun test() }

fun <T> case_6(x: T?) {
    if (x is A6?) {
        if (x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A6 & T!! & T?")!>x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A6"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A6"), DEBUG_INFO_SMARTCAST!>x<!>.test()

            <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_SMARTCAST!>x<!>.test()
            x.apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>this<!>.equals(x)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>this<!>.test()
            }
            x.also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 7
interface A7 { fun test() }

fun <T> case_7(y: T) {
    val x = y
    if (x is A7?) {
        if (x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A7 & T!! & T")!>x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T & A7"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T & A7"), DEBUG_INFO_SMARTCAST!>x<!>.test()

            x.apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>this<!>.equals(x)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>this<!>.test()
            }
            x.also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 8
interface A8 { fun test() }

fun <T> case_8(x: T) {
    if (x != null) {
        if (x is A8?) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A8 & T!! & T")!>x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T & A8"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T & A8"), DEBUG_INFO_SMARTCAST!>x<!>.test()

            <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_SMARTCAST!>x<!>.test()
            x.apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>this<!>.equals(x)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>this<!>.test()
            }
            x.also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 9
fun <T : Number> case_9(x: T) {
    if (<!SENSELESS_COMPARISON!>x != null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>.toByte()

        x.equals(x)
        x.toByte()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            equals(this)
            x.toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.toByte()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.toByte()
        }
    }
}

// TESTCASE NUMBER: 10
fun <T : Number?> case_10(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.toByte()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.toByte()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.toByte()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

// TESTCASE NUMBER: 11
interface A11 { fun test() }

fun <T : Number> case_11(x: T?) {
    if (x is A11?) {
        if (x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A11 & T!! & T?")!>x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A11"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T? & A11"), DEBUG_INFO_SMARTCAST!>x<!>.test()

            <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
            <!DEBUG_INFO_SMARTCAST!>x<!>.test()
            x.apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>this<!>.equals(x)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>this<!>.test()
            }
            x.also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 12
interface A12 { fun test() }

fun <T> case_12(x: T) where T : Number?, T: A12? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.toByte()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 13
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
fun <T> case_13(x: T) where T : List<*>?, T: Comparable<T?> {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!><!UNSAFE_CALL!>.<!>last()
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>.compareTo(null)

        x.equals(x)
        x<!UNSAFE_CALL!>.<!>last()
        x.compareTo(null)
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>last<!>()
            compareTo(null)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()
            this.compareTo(null)
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.equals(it)
            it.compareTo(null)
        }
    }
}

/*
 * TESTCASE NUMBER: 14
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
fun <T: List<*>?> case_14(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>last()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>last()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>last<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>last()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 15
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A15<T1, T2, T3, T4, T5, T6, T7> { fun test() }

fun <T: A15<*, *, *, *, *, *, *>?> case_15(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 16
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A16<T> { fun test() }

fun <T: A16<out T>?> case_16(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 17
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A17<T> { fun test() }

fun <T: A17<in T>?> case_17(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 18
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A18<T> { fun test() }

fun <T: A18<in T>?> case_18(x: T) {
    val y = x

    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>y<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>y<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(y)
        x<!UNSAFE_CALL!>.<!>test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(y)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 19
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A19<T> { fun test() }

fun <T: A19<out T>?> case_19(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 20
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A20_1<T> { fun test1() }
interface A20_2<T> { fun test2() }

fun <T> case_20(x: T) where T: A20_1<in T>?, T: A20_2<out T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test1<!>()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 21
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A21_1<T> { fun test1() }
interface A21_2<T> { fun test2() }
interface A21_3<T> { fun test3() }

fun <T> case_21(x: T) where T: A21_1<in T>?, T: A21_2<out T>?, T: A21_3<T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test3()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.test3()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test1<!>()
            <!UNSAFE_CALL!>test2<!>()
            test3()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test3()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test3()
        }
    }
}

/*
 * TESTCASE NUMBER: 22
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A22<T> { fun test() }

fun <T: A22<A22<out T>>?> case_22(x: T) {
    var y = x

    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>y<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>y<!>.test()

        x<!UNSAFE_CALL!>.<!>equals(y)
        x<!UNSAFE_CALL!>.<!>test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            <!UNSAFE_CALL!>equals<!>(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>test()
        }
    }
}

// TESTCASE NUMBER: 23
interface A23<T> { fun test() }

fun <T: A23<A23<out T>>?> case_23(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 24
interface A24<T> { fun test() }

fun <T : A24<in T>> case_24(x: A24<T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A24<T> & A24<T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>? & A24<T>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>? & A24<T>"), DEBUG_INFO_SMARTCAST!>x<!>.test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>this<!>.test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 25
interface A25<T> { fun test() }

fun <T : A25<out T>> case_25(x: A25<T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A25<T> & A25<T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>? & A25<T>"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>? & A25<T>"), DEBUG_INFO_SMARTCAST!>x<!>.test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>this<!>.test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 26
interface A26<T> { fun test() }

fun <T : A26<T>> case_26(x: A26<in T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T> & A26<in T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T> & A26<in T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T> & A26<in T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 27
interface A27<T> { fun test() }

fun <T : A27<T>> case_27(x: A27<out T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T> & A27<out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T> & A27<out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T> & A27<out T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 28
interface A28<T> { fun test() }

fun <T : A28<in T>> case_28(x: A28<out T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T> & A28<out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T> & A28<out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T> & A28<out T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 29
interface A29<T> { fun test() }

fun <T : A29<out T>> case_29(x: A29<in T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T> & A29<in T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T> & A29<in T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T> & A29<in T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 30
interface A30<T> { fun test() }

fun <T : A30<in T>> case_30(x: A30<in T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T> & A30<in T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T> & A30<in T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T> & A30<in T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 31
interface A31<T> { fun test() }

fun <T : A31<out T>> case_31(x: A31<out T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T> & A31<out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T> & A31<out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T> & A31<out T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 32
fun <T> case_32(x: Map<T, *>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *> & kotlin.collections.Map<T, *>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *> & kotlin.collections.Map<T, *>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *> & kotlin.collections.Map<T, *>?")!>x<!>.isEmpty()

        x.equals(x)
        x.isEmpty()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>this<!>.isEmpty()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 33
interface A33<T1, T2, T3, T4, T5, T6, T7> { fun test() }

fun <T> case_33(x: A33<T, *, T, *, T, *, T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T> & A33<T, *, T, *, T, *, T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T> & A33<T, *, T, *, T, *, T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T> & A33<T, *, T, *, T, *, T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 34
interface A34<T> { fun test() }

fun <T> case_34(x: A34<out T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T> & A34<out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T> & A34<out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T> & A34<out T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 35
interface A35<T> { fun test() }

fun <T> case_35(x: A35<in T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T> & A35<in T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T> & A35<in T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T> & A35<in T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 36
interface A36<T> { fun test() }

fun <T> case_36(x: A36<out T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T> & A36<out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T> & A36<out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T> & A36<out T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 37
fun <T> case_37(x: Map<in T, *>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *> & kotlin.collections.Map<in T, *>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *> & kotlin.collections.Map<in T, *>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *> & kotlin.collections.Map<in T, *>?")!>x<!>.isEmpty()

        x.equals(x)
        x.isEmpty()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>this<!>.isEmpty()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 38
fun <T> case_38(x: Map<*, <!REDUNDANT_PROJECTION!>out<!> T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T> & kotlin.collections.Map<*, out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T> & kotlin.collections.Map<*, out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T> & kotlin.collections.Map<*, out T>?")!>x<!>.isEmpty()

        x.equals(x)
        x.isEmpty()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>this<!>.isEmpty()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 39
interface A39<T1, T2> { fun test() }

fun <T> case_39(x: A39<in T, out T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T> & A39<in T, out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T> & A39<in T, out T>?")!>x<!>.equals(x)

        x.equals(x)
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>this<!>
            equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>this<!>.equals(x)
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>it<!>.equals(it)
        }
    }
}

// TESTCASE NUMBER: 40
interface A40<T1, T2> { fun test() }

fun <T> case_40(x: A40<in T, in T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T> & A40<in T, in T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T> & A40<in T, in T>?")!>x<!>.equals(x)

        x.equals(x)
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>this<!>
            equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>this<!>.equals(x)
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>it<!>.equals(it)
        }
    }
}

// TESTCASE NUMBER: 41
fun <T> case_41(x: Map<out T, <!REDUNDANT_PROJECTION!>out<!> T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T> & kotlin.collections.Map<out T, out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T> & kotlin.collections.Map<out T, out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T> & kotlin.collections.Map<out T, out T>?")!>x<!>.isEmpty()

        x.equals(x)
        x.isEmpty()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>this<!>.isEmpty()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 42
fun <T> case_42(x: Map<T, <!REDUNDANT_PROJECTION!>out<!> T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T> & kotlin.collections.Map<T, out T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T> & kotlin.collections.Map<T, out T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T> & kotlin.collections.Map<T, out T>?")!>x<!>.isEmpty()

        x.equals(x)
        x.isEmpty()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>this<!>.isEmpty()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 43
fun <T> case_43(x: Map<in T, T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T> & kotlin.collections.Map<in T, T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T> & kotlin.collections.Map<in T, T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T> & kotlin.collections.Map<in T, T>?")!>x<!>.isEmpty()

        x.equals(x)
        x.isEmpty()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>this<!>.isEmpty()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 44
interface A44<T1, T2, T3, T4, T5, T6, T7> { fun test() }

fun <T> case_44(x: A44<in T, *, out T, *, T, *, in T>?) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T> & A44<in T, *, out T, *, T, *, in T>?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T> & A44<in T, *, out T, *, T, *, in T>?")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T> & A44<in T, *, out T, *, T, *, in T>?")!>x<!>.test()

        x.equals(x)
        x.test()
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>this<!>.test()
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 45
fun <T> case_45(x: T) where T : Number?, T: Comparable<T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.toByte()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.compareTo(x)

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.toByte()
        <!DEBUG_INFO_SMARTCAST!>x<!>.compareTo(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            compareTo(this)
            <!DEBUG_INFO_SMARTCAST!>x<!>.toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.compareTo(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.toByte()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.compareTo(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.toByte()
        }
    }
}

// TESTCASE NUMBER: 46
fun <T> case_46(x: T) where T : CharSequence?, T: Comparable<T>?, T: Iterable<*>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.compareTo(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.get(0)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.iterator()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.compareTo(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.get(0)
        <!DEBUG_INFO_SMARTCAST!>x<!>.iterator()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            compareTo(this)
            get(0)
            iterator()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.compareTo(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.get(0)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.iterator()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.compareTo(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.get(0)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.iterator()
        }
    }
}

/*
 * TESTCASE NUMBER: 47
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A47_1<T> { fun test1() {} }
interface A47_2<T> { fun test2() }

fun <T> case_47(x: T?) where T : A47_1<T>, T: Comparable<*>?, T: A47_2<out T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }

        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!><!UNREACHABLE_CODE!>compareTo(<!>return<!UNREACHABLE_CODE!>)<!>
        <!UNREACHABLE_CODE!>x<!UNSAFE_CALL!>.<!>compareTo(return)<!>

        <!UNREACHABLE_CODE!><!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!UNREACHABLE_CODE!><!UNSAFE_CALL!>compareTo<!>(<!>return<!UNREACHABLE_CODE!>)<!>
            <!UNREACHABLE_CODE!><!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>compareTo(return)<!>
        }<!>

        <!UNREACHABLE_CODE!><!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!><!UNREACHABLE_CODE!>compareTo(<!>return<!UNREACHABLE_CODE!>)<!>
        }<!>
    }
}

/*
 * TESTCASE NUMBER: 48
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A48_1<T> { fun test1() {} }
interface A48_2<T> { fun test2() }

fun <T> case_48(x: T?) where T : A48_1<out T>, T: A48_2<in T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 49
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A49_1<T> { fun test1() {} }
interface A49_2<T> { fun test2() }

fun <T> case_49(x: T?) where T : A49_1<in T>, T: A49_2<in T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 50
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A50_1<T> { fun test1() {} }
interface A50_2<T> { fun test2() {} }

fun <T> case_50(x: T?) where T : A50_1<out T>, T: A50_2<out T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 51
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A51_1<T> { fun test1() {} }
interface A51_2<T> { fun test2() }

fun <T> case_51(x: T?) where T : A51_1<T>, T: A51_2<out T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

// TESTCASE NUMBER: 52
class A52_1<T> { fun test1() {} }
interface A52_2<T> { fun test2() }

fun <T> case_52(x: T?) where T : A52_1<in T>, T: A52_2<T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_SMARTCAST!>x<!>.test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 53
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A53_1<T> { fun test1() {} }
interface A53_2<T> { fun test2() }

fun <T> case_53(x: T?) where T : A53_1<in T>, T: A53_2<*>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 54
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
class A54_1<T> { fun test1() {} }
interface A54_2<T> { fun test2() }

fun <T> case_54(x: T?) where T : A54_1<*>, T: A54_2<out T?>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        x<!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

// TESTCASE NUMBER: 55
class A55_1<T> { fun test1() {} }
interface A55_2<T> { fun test2() }

fun <T> case_55(x: T?) where T : A55_1<*>, T: A55_2<T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T? & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test2()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_SMARTCAST!>x<!>.test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test2()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test2()
        }
    }
}

// TESTCASE NUMBER: 56
interface A56 { fun test() }

fun <T> case_56(x: T) where T : Number?, T: A56? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.toByte()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.toByte()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_SMARTCAST!>x<!>.toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.toByte()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.toByte()
        }
    }
}

/*
 * TESTCASE NUMBER: 57
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
fun <T> case_57(x: T) where T : List<*>?, T: Comparable<T?> {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!><!UNSAFE_CALL!>.<!>last()
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>x<!>.compareTo(null)

        x.equals(x)
        x<!UNSAFE_CALL!>.<!>last()
        x.compareTo(null)
        x.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>last<!>()
            compareTo(null)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.compareTo(null)
        }
        x.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.compareTo(null)
        }
    }
}

/*
 * TESTCASE NUMBER: 58
 * NOTE: lazy smartcasts
 */
interface A58<T> { fun test() }

fun <T : A58<A58<A58<A58<A58<A58<A58<A58<A58<A58<T>>>>>>>>>>?> case_59(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_SMARTCAST!>x<!>.test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
        }
    }
}

/*
 * TESTCASE NUMBER: 59
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A59_1<T1, T2, T3, T4, T5, T6, T7> { fun test1() }
interface A59_2<T1, T2, T3, T4, T5, T6, T7> { fun test2() }
interface A59_3<T1, T2, T3, T4, T5, T6, T7> { fun test3() }

fun <T> case_59(x: T) where T: A59_1<in T, *, out T?, Nothing?, T, T?, Any>?, T: A59_2<out T, in T?, T, *, Unit?, Int, T?>?, T: A59_3<out Nothing, in T, T, in Int?, Number, out T?, out T?>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test3()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test1()
        x<!UNSAFE_CALL!>.<!>test2()
        x<!UNSAFE_CALL!>.<!>test3()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test1<!>()
            <!UNSAFE_CALL!>test2<!>()
            <!UNSAFE_CALL!>test3<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test3()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test3()
        }
    }
}

/*
 * TESTCASE NUMBER: 60
 * NOTE: lazy smartcasts
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A60<T> { fun test() }

fun <T: A60<out T>?> case_60(x: T) {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_SMARTCAST!>x<!>.equals(x)
        x<!UNSAFE_CALL!>.<!>test()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(x)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
        }
    }
}

// TESTCASE NUMBER: 61
interface A61<T> { fun test1() }
interface B61<T>: A61<T>, C61<T> { fun test2() }
interface C61<T>: A61<T> { fun test3() }

class D61<T>: A61<T>, B61<T>, C61<T> {
    override fun test1() {}
    override fun test2() {}
    override fun test3() {}
    fun test4() {}
}

fun <T> case_61(x: T) where T : A61<T>?, T: D61<T>?, T: B61<T>?, T: C61<T>? {
    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test3()
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>x<!>.test4()

        <!DEBUG_INFO_SMARTCAST!>x<!>.test1()
        <!DEBUG_INFO_SMARTCAST!>x<!>.test2()
        <!DEBUG_INFO_SMARTCAST!>x<!>.test3()
        <!DEBUG_INFO_SMARTCAST!>x<!>.test4()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            test1()
            test2()
            test3()
            test4()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test3()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test4()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test3()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test4()
        }
    }
}

/*
 * TESTCASE NUMBER: 62
 * UNEXPECTED BEHAVIOUR
 */
fun <T : Nothing?> case_62(x: T) {
    if (x <!EQUALS_MISSING, UNRESOLVED_REFERENCE!>!=<!> null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>x<!>.hashCode()

        x.hashCode()
        <!DEBUG_INFO_SMARTCAST!>x<!>.apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            hashCode()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.hashCode()
        }
        <!DEBUG_INFO_SMARTCAST!>x<!>.also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.hashCode()
        }
    }
}