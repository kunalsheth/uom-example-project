package replicate.bug.test

import org.junit.Test

class MyJvmTest {
    @Test
    fun thisShouldPassOnJvm() {
        assert(1 + 1 == 2)
    }

//    @Test
//    fun thisShouldFailOnJvm() {
//        assert(2 + 2 == 5)
//    }
}