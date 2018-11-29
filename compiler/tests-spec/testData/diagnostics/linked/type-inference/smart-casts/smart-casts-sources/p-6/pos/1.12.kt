// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 12
 * DESCRIPTION: Nullability condition, if, receivers
 * NOTE: lazy smartcasts
 */

// TESTCASE NUMBER: 1
fun <T> T.case_1() {
    if (this != null) {
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> { equals(this); <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this) }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> { <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(this) }
    }
}

// TESTCASE NUMBER: 2
fun <T> T?.case_2() {
    if (this != null) {
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> { equals(this); <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this) }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> { <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(this) }
    }
}

// TESTCASE NUMBER: 3
fun <T> T.case_3() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
    }
}

// TESTCASE NUMBER: 4
fun <T> T?.case_4() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
    }
}

// TESTCASE NUMBER: 5
interface A5 { fun test() }

fun <T> T?.case_5() {
    if (this is A5) {
        if (<!SENSELESS_COMPARISON!>this != null<!>) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A5 & T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A5"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A5"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test()

            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
            apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>.equals(this)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A5 & T?!!}")!>this<!>.test()
            }
            also {
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

fun <T> T?.case_6() {
    if (this is A6?) {
        if (this != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A6 & T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A6"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A6"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test()

            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
            apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>this<!>.equals(this)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>this<!>.test()
            }
            also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A6 & T?!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 7
interface A7 { fun test() }

fun <T> T.case_7() {
    val x = this
    if (x is A7?) {
        if (x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A7 & T!! & T")!>x<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T & A7"), DEBUG_INFO_SMARTCAST!>x<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T & A7"), DEBUG_INFO_SMARTCAST!>x<!>.test()

            x.apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A7 & T!!}")!>this<!>.equals(this)
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

fun <T> T.case_8() {
    if (this != null) {
        if (this is A8?) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A8 & T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A8"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A8"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test()

            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
            apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>this<!>.equals(this)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>this<!>.test()
            }
            also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A8 & T!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 9
fun <T : Number> T.case_9() {
    if (<!SENSELESS_COMPARISON!>this != null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.toByte()

        equals(this)
        toByte()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            equals(this)
            toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.toByte()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.toByte()
        }
    }
}

// TESTCASE NUMBER: 10
fun <T : Number?> T.case_10() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.toByte()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>toByte<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.toByte()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

// TESTCASE NUMBER: 11
interface A11 { fun test() }

fun <T : Number> T?.case_11() {
    if (this is A11?) {
        if (this != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A11 & T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A11"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A11"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test()

            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
            <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
            apply {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>this<!>
                equals(this)
                test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>this<!>.equals(this)
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>this<!>.test()
            }
            also {
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>it<!>
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>it<!>.test()
                <!DEBUG_INFO_EXPRESSION_TYPE("{A11 & T?!!}")!>it<!>.equals(it)
            }
        }
    }
}

// TESTCASE NUMBER: 12
interface A12 { fun test() }

fun <T> T.case_12() where T : Number?, T: A12? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.toByte()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 13
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
fun <T> T.case_13() where T : List<*>?, T: Comparable<T?> {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.compareTo(null)

        equals(this)
        <!UNSAFE_CALL!>last<!>()
        compareTo(null)
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>last<!>()
            compareTo(null)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()
            this.compareTo(null)
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.equals(it)
            it.compareTo(null)
        }
    }
}

/*
 * TESTCASE NUMBER: 14
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
fun <T: List<*>?> T.case_14() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>last<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>last<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>last()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 15
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A15<T1, T2, T3, T4, T5, T6, T7> { fun test() }

fun <T: A15<*, *, *, *, *, *, *>?> T.case_15() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 16
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A16<T> { fun test() }

fun <T: A16<out T>?> T.case_16() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 17
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A17<T> { fun test() }

fun <T: A17<in T>?> T.case_17() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 18
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A18<T> { fun test() }

fun <T: A18<in T>?> T.case_18() {
    val y = this

    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>y<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>y<!><!UNSAFE_CALL!>.<!>test()

        <!UNSAFE_CALL!>equals<!>(y)
        <!UNSAFE_CALL!>test<!>()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            <!UNSAFE_CALL!>equals<!>(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>equals(y)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 19
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A19<T> { fun test() }

fun <T: A19<out T>?> T.case_19() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
        }
    }
}

/*
 * TESTCASE NUMBER: 20
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A20_1<T> { fun test1() }
interface A20_2<T> { fun test2() }

fun <T> T.case_20() where T: A20_1<in T>?, T: A20_2<out T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test1<!>()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }
    }
}

/*
 * TESTCASE NUMBER: 21
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A21_1<T> { fun test1() }
interface A21_2<T> { fun test2() }
interface A21_3<T> { fun test3() }

fun <T> T.case_21() where T: A21_1<in T>?, T: A21_2<out T>?, T: A21_3<T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test3()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test3<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test1<!>()
            <!UNSAFE_CALL!>test2<!>()
            test3()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test3()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T: A22<A22<out T>>?> T.case_22() {
    var y = this

    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T")!>y<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>y<!>.equals(y)
        <!DEBUG_INFO_EXPRESSION_TYPE("T & T!!"), DEBUG_INFO_SMARTCAST!>y<!>.test()

        <!UNSAFE_CALL!>equals<!>(y)
        <!UNSAFE_CALL!>test<!>()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            <!UNSAFE_CALL!>equals<!>(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>test()
        }
    }
}

// TESTCASE NUMBER: 23
interface A23<T> { fun test() }

fun <T: A23<A23<out T>>?> T.case_23() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 24
interface A24<T> { fun test() }

fun <T : A24<in T>> A24<T>?.case_24() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A24<T> & A24<T>?"), DEBUG_INFO_EXPRESSION_TYPE("A24<T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>"), DEBUG_INFO_EXPRESSION_TYPE("A24<T>?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>"), DEBUG_INFO_EXPRESSION_TYPE("A24<T>?"), DEBUG_INFO_SMARTCAST!>this<!>.test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>this<!>.test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A24<T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 25
interface A25<T> { fun test() }

fun <T : A25<out T>> A25<T>?.case_25() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A25<T> & A25<T>?"), DEBUG_INFO_EXPRESSION_TYPE("A25<T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>"), DEBUG_INFO_EXPRESSION_TYPE("A25<T>?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>"), DEBUG_INFO_EXPRESSION_TYPE("A25<T>?"), DEBUG_INFO_SMARTCAST!>this<!>.test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>this<!>.test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A25<T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 26
interface A26<T> { fun test() }

fun <T : A26<T>> A26<in T>?.case_26() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T> & A26<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A26<in T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T> & A26<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A26<in T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T> & A26<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A26<in T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A26<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 27
interface A27<T> { fun test() }

fun <T : A27<T>> A27<out T>?.case_27() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T> & A27<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A27<out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T> & A27<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A27<out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T> & A27<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A27<out T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A27<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 28
interface A28<T> { fun test() }

fun <T : A28<in T>> A28<out T>?.case_28() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T> & A28<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A28<out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T> & A28<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A28<out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T> & A28<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A28<out T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A28<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 29
interface A29<T> { fun test() }

fun <T : A29<out T>> A29<in T>?.case_29() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T> & A29<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A29<in T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T> & A29<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A29<in T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T> & A29<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A29<in T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A29<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 30
interface A30<T> { fun test() }

fun <T : A30<in T>> A30<in T>?.case_30() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T> & A30<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A30<in T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T> & A30<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A30<in T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T> & A30<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A30<in T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A30<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 31
interface A31<T> { fun test() }

fun <T : A31<out T>> A31<out T>?.case_31() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T> & A31<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A31<out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T> & A31<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A31<out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T> & A31<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A31<out T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A31<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 32
fun <T> Map<T, *>?.case_32() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *> & kotlin.collections.Map<T, *>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *> & kotlin.collections.Map<T, *>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *> & kotlin.collections.Map<T, *>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, *>?")!>this<!>.isEmpty()

        equals(this)
        isEmpty()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>this<!>.isEmpty()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, kotlin.Any?>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 33
interface A33<T1, T2, T3, T4, T5, T6, T7> { fun test() }

fun <T> A33<T, *, T, *, T, *, T>?.case_33() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T> & A33<T, *, T, *, T, *, T>?"), DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T> & A33<T, *, T, *, T, *, T>?"), DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T> & A33<T, *, T, *, T, *, T>?"), DEBUG_INFO_EXPRESSION_TYPE("A33<T, *, T, *, T, *, T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A33<T, out kotlin.Any?, T, out kotlin.Any?, T, out kotlin.Any?, T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 34
interface A34<T> { fun test() }

fun <T> A34<out T>?.case_34() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T> & A34<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A34<out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T> & A34<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A34<out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T> & A34<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A34<out T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A34<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 35
interface A35<T> { fun test() }

fun <T> A35<in T>?.case_35() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T> & A35<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A35<in T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T> & A35<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A35<in T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T> & A35<in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A35<in T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A35<in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 36
interface A36<T> { fun test() }

fun <T> A36<out T>?.case_36() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T> & A36<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A36<out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T> & A36<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A36<out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T> & A36<out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A36<out T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A36<out T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 37
fun <T> Map<in T, *>?.case_37() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *> & kotlin.collections.Map<in T, *>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *> & kotlin.collections.Map<in T, *>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *> & kotlin.collections.Map<in T, *>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, *>?")!>this<!>.isEmpty()

        equals(this)
        isEmpty()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>this<!>.isEmpty()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, kotlin.Any?>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 38
fun <T> Map<*, <!REDUNDANT_PROJECTION!>out<!> T>?.case_38() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T> & kotlin.collections.Map<*, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T> & kotlin.collections.Map<*, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T> & kotlin.collections.Map<*, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<*, out T>?")!>this<!>.isEmpty()

        equals(this)
        isEmpty()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>this<!>.isEmpty()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out kotlin.Any?, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 39
interface A39<T1, T2> { fun test() }

fun <T> A39<in T, out T>?.case_39() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T> & A39<in T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T> & A39<in T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>?")!>this<!>.equals(this)

        equals(this)
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>this<!>
            equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>this<!>.equals(this)
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A39<in T, out T>")!>it<!>.equals(it)
        }
    }
}

// TESTCASE NUMBER: 40
interface A40<T1, T2> { fun test() }

fun <T> A40<in T, in T>?.case_40() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T> & A40<in T, in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T> & A40<in T, in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>?")!>this<!>.equals(this)

        equals(this)
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>this<!>
            equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>this<!>.equals(this)
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A40<in T, in T>")!>it<!>.equals(it)
        }
    }
}

// TESTCASE NUMBER: 41
fun <T> Map<out T, <!REDUNDANT_PROJECTION!>out<!> T>?.case_41() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T> & kotlin.collections.Map<out T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T> & kotlin.collections.Map<out T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T> & kotlin.collections.Map<out T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, out T>?")!>this<!>.isEmpty()

        equals(this)
        isEmpty()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>this<!>.isEmpty()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<out T, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 42
fun <T> Map<T, <!REDUNDANT_PROJECTION!>out<!> T>?.case_42() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T> & kotlin.collections.Map<T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T> & kotlin.collections.Map<T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T> & kotlin.collections.Map<T, out T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, out T>?")!>this<!>.isEmpty()

        equals(this)
        isEmpty()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>this<!>.isEmpty()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<T, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 43
fun <T> Map<in T, T>?.case_43() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T> & kotlin.collections.Map<in T, T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T> & kotlin.collections.Map<in T, T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T> & kotlin.collections.Map<in T, T>?"), DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>?")!>this<!>.isEmpty()

        equals(this)
        isEmpty()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>this<!>
            equals(this)
            isEmpty()
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>this<!>.isEmpty()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.Map<in T, T>")!>it<!>.isEmpty()
        }
    }
}

// TESTCASE NUMBER: 44
interface A44<T1, T2, T3, T4, T5, T6, T7> { fun test() }

fun <T> A44<in T, *, out T, *, T, *, in T>?.case_44() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T> & A44<in T, *, out T, *, T, *, in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T>?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T> & A44<in T, *, out T, *, T, *, in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T>?")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T> & A44<in T, *, out T, *, T, *, in T>?"), DEBUG_INFO_EXPRESSION_TYPE("A44<in T, *, out T, *, T, *, in T>?")!>this<!>.test()

        equals(this)
        test()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>this<!>.test()
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("A44<in T, out kotlin.Any?, out T, out kotlin.Any?, T, out kotlin.Any?, in T>")!>it<!>.test()
        }
    }
}

// TESTCASE NUMBER: 45
fun <T> T.case_45() where T : Number?, T: Comparable<T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.toByte()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.compareTo(this)

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>toByte<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>compareTo<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            compareTo(this)
            toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.compareTo(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.toByte()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.compareTo(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.toByte()
        }
    }
}

// TESTCASE NUMBER: 46
fun <T> T.case_46() where T : CharSequence?, T: Comparable<T>?, T: Iterable<*>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.compareTo(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.get(0)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.iterator()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>compareTo<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>get<!>(0)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>iterator<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            compareTo(this)
            get(0)
            iterator()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.compareTo(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.get(0)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.iterator()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_47() where T : A47_1<T>, T: Comparable<*>?, T: A47_2<out T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!><!UNSAFE_CALL!>.<!>test2()
        }

        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!><!UNREACHABLE_CODE!>compareTo(<!>return<!UNREACHABLE_CODE!>)<!>
        <!UNREACHABLE_CODE!><!UNSAFE_CALL!>compareTo<!>(return)<!>

        <!UNREACHABLE_CODE!><!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!UNREACHABLE_CODE!><!UNSAFE_CALL!>compareTo<!>(<!>return<!UNREACHABLE_CODE!>)<!>
            <!UNREACHABLE_CODE!><!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>compareTo(return)<!>
        }<!>

        <!UNREACHABLE_CODE!><!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_48() where T : A48_1<out T>, T: A48_2<in T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_49() where T : A49_1<in T>, T: A49_2<in T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_50() where T : A50_1<out T>, T: A50_2<out T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_51() where T : A51_1<T>, T: A51_2<out T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_52() where T : A52_1<in T>, T: A52_2<T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_53() where T : A53_1<in T>, T: A53_2<*>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_54() where T : A54_1<*>, T: A54_2<out T?>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!><!UNSAFE_CALL!>.<!>test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            <!UNSAFE_CALL!>test2<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T?.case_55() where T : A55_1<*>, T: A55_2<T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T?"), DEBUG_INFO_EXPRESSION_TYPE("T?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T?"), DEBUG_INFO_SMARTCAST!>this<!>.test2()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test1()
            test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test2()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test2()
        }
    }
}

// TESTCASE NUMBER: 56
interface A56 { fun test() }

fun <T> T.case_56() where T : Number?, T: A56? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.toByte()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>toByte<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            toByte()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.toByte()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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
fun <T> T.case_57() where T : List<*>?, T: Comparable<T?> {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()
        <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.compareTo(null)

        equals(this)
        <!UNSAFE_CALL!>last<!>()
        compareTo(null)
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>last<!>()
            compareTo(null)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.compareTo(null)
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!><!UNSAFE_CALL!>.<!>last()
            <!DEBUG_INFO_EXPRESSION_TYPE("T")!>it<!>.compareTo(null)
        }
    }
}

// TESTCASE NUMBER: 58
interface A58<T> { fun test() }

fun <T : A58<A58<A58<A58<A58<A58<A58<A58<A58<A58<T>>>>>>>>>>?> T.case_59() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            test()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.equals(it)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test()
        }
    }
}

/*
 * TESTCASE NUMBER: 59
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A59_1<T1, T2, T3, T4, T5, T6, T7> { fun test1() }
interface A59_2<T1, T2, T3, T4, T5, T6, T7> { fun test2() }
interface A59_3<T1, T2, T3, T4, T5, T6, T7> { fun test3() }

fun <T> T.case_59() where T: A59_1<in T, *, out T?, Nothing?, T, T?, Any>?, T: A59_2<out T, in T?, T, *, Unit?, Int, T?>?, T: A59_3<out Nothing, in T, T, in Int?, Number, out T?, out T?>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test3()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test1<!>()
        <!UNSAFE_CALL!>test2<!>()
        <!UNSAFE_CALL!>test3<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test1<!>()
            <!UNSAFE_CALL!>test2<!>()
            <!UNSAFE_CALL!>test3<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test3()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-28785
 */
interface A60<T> { fun test() }

fun <T: A60<out T>?> T.case_60() {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.equals(this)
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!><!UNSAFE_CALL!>.<!>test()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>equals<!>(this)
        <!UNSAFE_CALL!>test<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            equals(this)
            <!UNSAFE_CALL!>test<!>()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!><!UNSAFE_CALL!>.<!>test()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
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

fun <T> T.case_61() where T : A61<T>?, T: D61<T>?, T: B61<T>?, T: C61<T>? {
    if (this != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test1()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test2()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test3()
        <!DEBUG_INFO_EXPRESSION_TYPE("T!!"), DEBUG_INFO_EXPRESSION_TYPE("T"), DEBUG_INFO_SMARTCAST!>this<!>.test4()

        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test1<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test2<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test3<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>test4<!>()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
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
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test1()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test2()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test3()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.test4()
        }
    }
}

// TESTCASE NUMBER: 62
fun Nothing?.case_62() {
    if (<!SENSELESS_COMPARISON!>this != null<!>) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>this<!>.equals(this)

        equals(this)
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>this<!>
            equals(this)
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>this<!>.equals(this)
        }
        also {
            <!DEBUG_INFO_CONSTANT, DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing?")!>it<!>.equals(this)
        }
    }
}

// TESTCASE NUMBER: 63
fun Nothing.case_63() {
    if (<!SENSELESS_COMPARISON!>this <!UNREACHABLE_CODE!>!= null<!><!>) <!UNREACHABLE_CODE!>{
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing")!>this<!>.hashCode()

        hashCode()
        apply {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing")!>this<!>
            <!UNREACHABLE_CODE!>hashCode()<!>
            <!UNREACHABLE_CODE!><!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing")!>this<!>.hashCode()<!>
        }
        also {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing")!>it<!>
            <!UNREACHABLE_CODE!><!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Nothing")!>it<!>.hashCode()<!>
        }
    }<!>
}

/*
 * TESTCASE NUMBER: 64
 * UNEXPECTED BEHAVIOUR
 */
fun <T : Nothing?> T.case_64() {
    if (this <!EQUALS_MISSING, UNRESOLVED_REFERENCE!>!=<!> null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("T!! & T"), DEBUG_INFO_EXPRESSION_TYPE("T")!>this<!>.hashCode()

        hashCode()
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>apply<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>
            hashCode()
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>this<!>.hashCode()
        }
        <!DEBUG_INFO_IMPLICIT_RECEIVER_SMARTCAST!>also<!> {
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>
            <!DEBUG_INFO_EXPRESSION_TYPE("T!!")!>it<!>.hashCode()
        }
    }
}
