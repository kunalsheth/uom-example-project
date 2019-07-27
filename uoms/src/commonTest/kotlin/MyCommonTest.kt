package replicate.bug.test

import kotlin.test.Test
import kotlin.test.assertTrue


class MyCommonTest {
    @Test
    fun thisShouldPassOnAllPlatforms() {
        assertTrue(1 + 1 == 2)
    }

//    @Test
//    fun thisShouldFailOnAllPlatforms() {
//        assertTrue(2 + 2 == 5)
//    }
}