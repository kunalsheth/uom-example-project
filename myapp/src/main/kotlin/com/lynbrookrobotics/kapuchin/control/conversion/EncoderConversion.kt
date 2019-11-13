package com.lynbrookrobotics.kapuchin.control.conversion

import com.lynbrookrobotics.kapuchin.control.math.div
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.times

/**
 * Encoder conversion utility
 *
 * Utility functions for converting between encoder outputs and angles
 *
 * @author Kunal
 * @see OffloadedNativeConversion
 * @see GearTrain
 *
 * @param ticks number of encoder ticks in 1 `perRevolution`
 * @param perRevolution angle corresponding `ticks` encoder ticks
 */
class EncoderConversion(ticks: Int, perRevolution: Angle) {
    private val anglePerTick = perRevolution / ticks.Each

    fun ticks(x: AngularAbsement) = ticks(x / Second) * Second
    fun ticks(x: Angle): Double = x / anglePerTick
    fun ticks(x: AngularVelocity) = ticks(x * Second) * Hertz
    fun ticks(x: AngularAcceleration) = ticks(x * Second) * Hertz

    fun angle(x: Time): AngularAbsement = angle((x / Second).Each) * Second
    fun angle(x: Double): Angle = x * anglePerTick
    fun angle(x: Frequency) = angle((x * Second).Each) * Hertz
    fun angle(x: `T⁻²`) = angle(x * Second) * Hertz
}