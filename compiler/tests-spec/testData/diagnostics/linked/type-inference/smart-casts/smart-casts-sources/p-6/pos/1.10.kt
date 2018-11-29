// !LANGUAGE: +NewInference
// !DIAGNOSTICS: -UNUSED_EXPRESSION
// !WITH_CLASSES_WITH_PROJECTIONS
// SKIP_TXT

/*
 * KOTLIN DIAGNOSTICS SPEC TEST (POSITIVE)
 *
 * SPEC VERSION: 0.1-draft
 * PLACE: type-inference, smart-casts, smart-casts-sources -> paragraph 6 -> sentence 1
 * NUMBER: 10
 * DESCRIPTION: Nullability condition, if, complex types with projections
 */

// TESTCASE NUMBER: 1
fun <T> case_1_1(x: List<T>): T? = x[0]

fun case_1() {
    val x = case_1_1(listOf(10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 2
fun <T>case_2_1(x: MutableList<out T>): T? = x[0]

fun case_2() {
    val x = case_2_1(mutableListOf(10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 3
fun <T>case_3_1(x: List<T?>) = x[0]

fun case_3() {
    val x = case_3_1(listOf(10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 4
fun <T>case_4_1(x: MutableList<out T?>) = x[0]

fun case_4() {
    val x = case_4_1(mutableListOf(10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 5
fun <T>case_5_1(x: MutableList<in T>) = x[0]

fun case_5() {
    val x = case_5_1(mutableListOf(10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 6
fun <T>case_6_1(x: MutableList<in T?>) = x[0]

fun case_6() {
    val x = case_6_1(mutableListOf(10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Any & kotlin.Any?")!>x<!>
    }
}

// TESTCASE NUMBER: 7
fun <T>case_7_1(x: MutableMap<T?, out T?>) = select(x.values.first(), x.keys.first())

fun case_7() {
    val x = case_7_1(mutableMapOf(10 to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 8
fun <T>case_8_1(x: MutableMap<T, out T>) = select(x.values.first(), x.keys.first())

fun case_8() {
    val x = case_8_1(mutableMapOf(10 to null))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 9
fun <T>case_9_1(x: MutableMap<T, out T>) = select(x.values.first(), x.keys.first())

fun case_9() {
    val x = case_9_1(mutableMapOf(null to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 10
fun <T>case_10_1(x: MutableMap<T?, out T>) = select(x.values.first(), x.keys.first())

fun case_10() {
    val x = case_10_1(mutableMapOf(10 to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 11
fun <T>case_11_1(x: MutableMap<T, out T?>) = select(x.values.first(), x.keys.first())

fun case_11() {
    val x = case_11_1(mutableMapOf(10 to 10))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 12
fun <T, K: T, M: K>case_12_1(x: MutableMap<M, K?>) = select(x.values.first(), x.keys.first())

fun case_12() {
    val x = case_12_1(mutableMapOf(10 to 11))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 13
 * ISSUES: KT-28334
 * NOTE: before fix of the issue type is inferred to {Int? & Byte? & Short? & Long?} (smart cast from {Int? & Byte? & Short? & Long?}?)
 */
fun <T>case_13_1(x: MutableList<T?>?, y: List<T>) = select(x, y)

fun case_13() {
    val x = case_13_1(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        }
    }
}

// TESTCASE NUMBER: 14
fun <T>case_14_1(x: MutableList<T>?, y: List<T?>) = select(x, y)

fun case_14() {
    val x = case_14_1(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        }
    }
}

// TESTCASE NUMBER: 15
fun <T>case_15_1(x: MutableList<T>, y: List<T>?) = select(x, y)

fun case_15() {
    val x = case_15_1(mutableListOf(null), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 16
 * ISSUES: KT-28334
 * NOTE: before fix of the issue type is inferred to {Int? & Byte? & Short? & Long?} (smart cast from {Int? & Byte? & Short? & Long?}?)
 */
fun <T>case_16_1(x: MutableList<T?>, y: List<T>) = select(x, select(y, null))

fun case_16() {
    val x = case_16_1(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        }
    }
}

// TESTCASE NUMBER: 17
fun <T>case_17_1(x: MutableList<T>, y: List<T?>) = select(x, select(y, null))

fun case_17() {
    val x = case_17_1(mutableListOf(1), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?> & kotlin.collections.List<kotlin.Int?>?")!>x<!>
        val y = <!DEBUG_INFO_SMARTCAST!>x<!>[0]
        if (y != null) {
            <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
        }
    }
}

/*
 * TESTCASE NUMBER: 18
 * ISSUES: KT-28334
 * NOTE: before fix of the issue type is inferred to {Int? & Byte? & Short? & Long?} (smart cast from {Int? & Byte? & Short? & Long?}?)
 */
fun <T>case_18_1(x: MutableList<T?>, y: List<T>) = select(x, y)

fun case_18() {
    val x = case_18_1(mutableListOf(1), listOf(1))

    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>")!>x<!>
    val y = x[0]
    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
    }
}

// TESTCASE NUMBER: 19
fun <T>case_19_1(x: MutableList<T>, y: List<T>) = select(x, y)

fun case_19() {
    val x = case_19_1(mutableListOf(1), listOf(null))

    <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.Int?>")!>x<!>
    val y = x[0]
    if (y != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>y<!>
    }
}

// TESTCASE NUMBER: 20
fun <T>case_20_1(x: MutableList<T>, y: List<T>) = select(x.first(), y.last())

fun case_20(y: Int?) {
    val x = case_20_1(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 21
fun <T>case_21_1(x: MutableList<T?>, y: List<T>) = select(x.first(), y.last())

fun case_21(y: Int) {
    val x = case_21_1(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 22
fun <T>case_22_1(x: MutableList<T?>, y: List<T>): T? = select(x.first(), y.last())

fun case_22(y: Int) {
    val x = case_22_1(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 23
fun <T>case_23_1(x: MutableList<T>, y: List<T>): T = select(x.first(), y.last())

fun case_23(y: Int?) {
    val x = case_23_1(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 24
fun <A, B : A, C: B, D: C, E: D, F>case_24_1(x: MutableList<A>, y: List<F>) where F : E? = select(x.first(), y.last())

fun case_24(y: Int) {
    val x = case_24_1(mutableListOf(y), listOf(1))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

// TESTCASE NUMBER: 25
fun <A, B : List<A>, C: List<B>, D: List<C>, E: List<C>, F>case_25_1(x: F, y: MutableList<C>) where F : List<E?> =
    select(x.first()?.first()?.first()?.first(), y.last().last().last())

fun case_25(y: Int) {
    val x = case_25_1(mutableListOf(listOf(mutableListOf(mutableListOf(y)))), mutableListOf(listOf(mutableListOf(y))))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}

/*
 * TESTCASE NUMBER: 26
 * UNEXPECTED BEHAVIOUR
 * ISSUES: KT-26816
 */
fun <A, B : List<A>, C: List<B>, D: List<C>, E: List<C>, F>case_26_1(x: F, y: MutableList<C>) where F : List<E?> =
    select(x.first()?.first()?.first()?.first(), y.last().last().last())

fun case_26(y: Int) {
    val x = case_26_1(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.MutableList<kotlin.collections.List<kotlin.collections.List<kotlin.Nothing>>>")!>mutableListOf(<!DEBUG_INFO_EXPRESSION_TYPE("kotlin.collections.List<kotlin.collections.List<kotlin.collections.MutableList<kotlin.Int>>>"), TYPE_MISMATCH, TYPE_MISMATCH!>listOf(listOf(mutableListOf(y)))<!>)<!>, mutableListOf(listOf(mutableListOf(y))))

    if (x != null) {
        <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.Int & kotlin.Int?")!>x<!>
    }
}
