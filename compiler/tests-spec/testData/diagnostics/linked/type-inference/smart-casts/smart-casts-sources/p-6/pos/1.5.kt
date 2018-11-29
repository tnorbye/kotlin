// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 5
 * DESCRIPTION: Multi smartcasts
 * UNEXPECTED BEHAVIOUR
 */

// TESTCASE NUMBER: 5, 6, 7, 8, 9, 10, 13, 14
open class A {
    fun t1() {}
}
open class B: A() {
    fun t2() {}
}
open class C: B() {}
open class D: C() {}
open class E: D() {}

// TESTCASE NUMBER: 11, 12, 13, 14
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
    if (x is Number?) {
        if (x !== null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Number & kotlin.Any?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 2
fun case_2(x: Any?) {
    if (x is Number? && x is Int? && x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 3
fun case_3(x: Any?) {
    if (x is Number?) {
        if (x !== null) {
            if (x is Int?) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?")!>x<!>
            }
        }
    }
}

// TESTCASE NUMBER: 4
fun case_4(x: Any?) {
    if (x != null) {
        if (x is Number) {
            if (x is Int?) {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?")!>x<!>
            }
        }
    }
}

// TESTCASE NUMBER: 5
fun case_5(x: Any?) {
    if (x is A?) {
        if (x is B?) {
            if (x is C?) {
                if (x is D?) {
                    if (x is E?) {
                        if (x != null) {
                            <!DEBUG_INFO_EXPRESSION_TYPE("A & B & C & D & E & kotlin.Any & kotlin.Any?")!>x<!>
                        }
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 6
fun case_6(x: Any?) {
    if (x is A?) {
        if (x is B?) {
            if (x is C?) {
                if (x != null && x is D?) {
                    if (x is E?) {
                        <!DEBUG_INFO_EXPRESSION_TYPE("A & B & C & D & E & kotlin.Any & kotlin.Any?")!>x<!>
                    }
                }
            }
        }
    }
}

// TESTCASE NUMBER: 7
fun case_7(x: Any?) {
    if (x is A? && x is B? && x is C?) {
        if (x is D? && x != null && x is E?) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A & B & C & D & E & kotlin.Any & kotlin.Any?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 8
fun case_8(x: Any?) {
    if (x is A? && x is B? && x is C?) {
        if (x is D? && x != null && x is E?) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A & B & C & D & E & kotlin.Any & kotlin.Any?")!>x<!>
        }
    }
}

// TESTCASE NUMBER: 9
fun case_9(x: Any?) {
    if (x is A? && x is B? && x is C? && x is D? && x != null && x is E?) {
        <!DEBUG_INFO_EXPRESSION_TYPE("A & B & C & D & E & kotlin.Any & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 10
fun case_10(x: Any?) {
    if (x is A? && x is B? && x is C?) {
        if (x is D?) {

        } else if (x is E? && x != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("A & B & C & E & kotlin.Any & kotlin.Any?")!>x<!>
        }
    } else if (x is D? && x != null && x is E?) {
        <!DEBUG_INFO_EXPRESSION_TYPE("D & E & kotlin.Any & kotlin.Any?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 11
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_11(t: Any?) {
    if (t is L1?) {
        if (t is L2?) {
            if (t != null) {
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
 * TESTCASE NUMBER: 12
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_12(t: Any?) {
    if (t is L1? && t != null && t is L2?) {
        <!DEBUG_INFO_EXPRESSION_TYPE("L1 & L2 & kotlin.Any & kotlin.Any?")!>t<!>
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g()

        <!DEBUG_INFO_EXPRESSION_TYPE("L1 & L2 & kotlin.Any & kotlin.Any?")!>t<!>.let { <!DEBUG_INFO_EXPRESSION_TYPE("{Any & L1 & L2}")!>it<!>.g1(); <!DEBUG_INFO_EXPRESSION_TYPE("{Any & L1 & L2}")!>it<!>.g2() }
    }
}

/*
 * TESTCASE NUMBER: 13
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_13(t: Any?) {
    if (t is L2?) {
        if (t is A?) {
            if (t is B? && t is L1?) {
                if (t !is L3?) {} else if (false) {
                    if (t != null) {
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L3"), DEBUG_INFO_SMARTCAST!>t<!>.g()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & A"), DEBUG_INFO_SMARTCAST!>t<!>.t1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B"), DEBUG_INFO_SMARTCAST!>t<!>.t2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("A & B & L1 & L2 & L3 & kotlin.Any & kotlin.Any?")!>t<!>
                    }
                }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 14
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_14(t: Any?) {
    if (t is L2?) {
        if (t is A?) {
            if (t == null || t !is L1?) else {
                if (t is B?) {
                    if (t is L3?) {
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L3"), DEBUG_INFO_SMARTCAST!>t<!>.g()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & A"), DEBUG_INFO_SMARTCAST!>t<!>.t1()
                        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B"), DEBUG_INFO_SMARTCAST!>t<!>.t2()
                        <!DEBUG_INFO_EXPRESSION_TYPE("A & B & L1 & L2 & L3 & kotlin.Any & kotlin.Any?")!>t<!>
                    }
                }
            }
        }
    }
}

/*
 * TESTCASE NUMBER: 15
 * NOTE: lazy smartcasts
 * DISCUSSION
 * ISSUES: KT-28362
 */
fun case_15(t: Any?) {
    if (t !is B? || <!USELESS_IS_CHECK!>t !is A?<!>) else {
        if (t === null || t !is L1?) else {
            if (t !is L2? || t !is L3?) {} else {
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L2"), DEBUG_INFO_SMARTCAST!>t<!>.g2()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L1"), DEBUG_INFO_SMARTCAST!>t<!>.g1()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & L3"), DEBUG_INFO_SMARTCAST!>t<!>.g()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B"), DEBUG_INFO_SMARTCAST!>t<!>.t1()
                <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any? & B"), DEBUG_INFO_SMARTCAST!>t<!>.t2()
                <!DEBUG_INFO_EXPRESSION_TYPE("A & B & L1 & L2 & L3 & kotlin.Any & kotlin.Any?"), DEBUG_INFO_SMARTCAST!>t<!>
            }
        }
    }
}

// TESTCASE NUMBER: 16
fun case_16(a: Any?, b: Int = if (a is Number? && a is Int? && a !== null) <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?"), DEBUG_INFO_SMARTCAST!>a<!> else 0) {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int")!>b<!>
}

// TESTCASE NUMBER: 17
fun case_17(a: Any?, b: Int = if (a !is Number? || a !is Int? || a == null) 0 else <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Int & kotlin.Number & kotlin.Any?"), DEBUG_INFO_SMARTCAST!>a<!>) {
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any?")!>a<!>
    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int")!>b<!>
}
