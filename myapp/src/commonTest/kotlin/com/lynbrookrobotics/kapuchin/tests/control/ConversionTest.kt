package com.lynbrookrobotics.kapuchin.tests.control

import com.lynbrookrobotics.kapuchin.control.conversion.EncoderConversion
import com.lynbrookrobotics.kapuchin.control.conversion.GearTrain
import com.lynbrookrobotics.kapuchin.control.data.Gain
import com.lynbrookrobotics.kapuchin.control.math.withDecimals
import com.lynbrookrobotics.kapuchin.tests.`is equal to?`
import com.lynbrookrobotics.kapuchin.tests.anyDouble
import com.lynbrookrobotics.kapuchin.tests.anyInt
import info.kunalsheth.units.generated.*
import kotlin.test.Test

class ConversionTest {
    @Test
    fun `encoder ticks and angle methods are inverses`() {
        anyInt.filter { it != 0 }.map { resolution -> EncoderConversion(resolution, 360.Degree) }
                .forEach { conversion ->
                    anyDouble.map { it.Each }.forEach { x ->
                        x.Each `is equal to?` (conversion.ticks(conversion.angle(x.Each)) withDecimals 5)

                        val ix = x * Second
                        ix `is equal to?` conversion.ticks(conversion.angle(ix))

                        val dx = x / Second
                        dx `is equal to?` conversion.ticks(conversion.angle(dx))

                        val ddx = dx / Second
                        ddx `is equal to?` conversion.ticks(conversion.angle(ddx))
                    }
                }
    }


    @Test
    fun `gears input and output methods are inverses`() {
        anyInt.filter { it > 0 }.forEach { input ->
            anyInt.filter { it > 0 }.forEach { idlers ->
                anyInt.filter { it > 0 }
                        .map { output -> GearTrain(input, idlers, output) }
                        .forEach { gearTrain ->
                            anyDouble.map { it.Degree }.forEach { x ->
                                x `is equal to?` gearTrain.outputToInput(gearTrain.inputToOutput(x))

                                val dx = x / Second
                                dx `is equal to?` gearTrain.outputToInput(gearTrain.inputToOutput(dx))

                                val ddx = dx / Second
                                ddx `is equal to?` gearTrain.outputToInput(gearTrain.inputToOutput(ddx))
                            }
                        }
            }
        }
    }
}