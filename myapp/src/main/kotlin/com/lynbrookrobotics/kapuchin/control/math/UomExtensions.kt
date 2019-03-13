package com.lynbrookrobotics.kapuchin.control.math

import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.*
import kotlin.math.pow
import kotlin.math.round

operator fun <Q : Quan<Q>> Q.div(that: Q): Double = this.siValue / that.siValue

infix fun <Q : Quan<Q>> Q.minMag(that: Q) = if (this.abs < that.abs) this else that
infix fun <Q : Quan<Q>> Q.maxMag(that: Q) = if (this.abs > that.abs) this else that

inline infix fun <Q : Number> Q.`±`(radius: Q): ClosedFloatingPointRange<Double> {
    val center = toDouble()
    val range = radius.toDouble()
    return center - range..center + range
}

fun <Q : Number> `±`(radius: Q) = 0.0 `±` radius

/**
 * Rounds a number to a fixed number of decimal places for cleaner logging
 *
 * @author Kunal
 *
 * @receiver number to round
 * @param decimalPlaces number of digits to keep left of the decimal. Negative numbers round to the right of the decimal.
 * @return asynchronous logging job
 */
infix fun Number.withDecimals(decimalPlaces: Int) = toDouble().let {
    val shifter = 10.0.pow(decimalPlaces)
    round(it * shifter) / shifter
}

/**
 * Rounds a number to a fixed number of decimal places for cleaner logging
 *
 * @author Kunal
 *
 * @receiver number to round
 * @param decimalPlaces number of digits to keep left of the decimal. Negative numbers round to the right of the decimal.
 * @return asynchronous logging job
 */
infix fun <Q : Quan<Q>> Q.withDecimals(decimalPlaces: Int) = new(siValue withDecimals decimalPlaces)

/**
 * Returns the closest, largest range to `current`
 *
 * @author Alvyn
 *
 * @param Q type of input
 *
 * @param sequence the sequence of ranges of `Q` to parse
 * @param current the reference location for determining which range is closest
 * @param leftBound the left extreme of `Q`. If the underlying mechanism for comparison is Int, make sure you
 * add 1 to leftBound otherwise `Q.abs` will fail due to integer overflow.
 */
fun <Q : Quan<Q>> unionizeAndFindClosestRange(sequence: Sequence<ClosedRange<Q>>, current: Q, leftBound: Q): ClosedRange<Q> {

    var currLeft = leftBound
    var currRight = leftBound

    sequence.sortedBy {
        it.start
    }.forEach {
        when {
            it.start <= currRight -> currRight = max(currRight, it.endInclusive)
            current in currLeft..currRight -> return@forEach
            (it.start - current).abs < (currRight - current).abs -> {
                currLeft = it.start
                currRight = it.endInclusive
            }
            else -> return@forEach
        }
    }

    return currLeft..currRight
}