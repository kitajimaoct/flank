package ftl.util

import com.google.common.truth.Truth.assertThat
import ftl.args.AndroidArgs
import ftl.json.MatrixMap
import ftl.test.util.FlankTestRunner
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(FlankTestRunner::class)
class MatrixUtilTest {

    @After
    fun tearDown() = unmockkAll()

    @Test
    fun `testTimeoutToSeconds validInput`() {
        assertThat(timeoutToSeconds("0")).isEqualTo(0)
        assertThat(timeoutToSeconds("0s")).isEqualTo(0)
        assertThat(timeoutToSeconds("0m")).isEqualTo(0)
        assertThat(timeoutToSeconds("0h")).isEqualTo(0)
        assertThat(timeoutToSeconds("1")).isEqualTo(1)
        assertThat(timeoutToSeconds("1s")).isEqualTo(1)
        assertThat(timeoutToSeconds("1m")).isEqualTo(60)
        assertThat(timeoutToSeconds("1h")).isEqualTo(60 * 60)
    }

    @Test(expected = NumberFormatException::class)
    fun `testTimeoutToSeconds rejectsInvalid`() {
        timeoutToSeconds("1d")
    }

    @Test
    fun `resolveLocalRunPath validInput`() {
        val matrixMap = mockk<MatrixMap>()
        every { matrixMap.runPath } returns "a/b"

        assertThat(resolveLocalRunPath(matrixMap, AndroidArgs.default())).isEqualTo("results/b")
    }

    @Test
    fun `resolveLocalRunPath pathExists`() {
        val matrixMap = mockk<MatrixMap>()
        every { matrixMap.runPath } returns "/tmp"

        assertThat(resolveLocalRunPath(matrixMap, AndroidArgs.default())).isEqualTo("/tmp")
    }
}
