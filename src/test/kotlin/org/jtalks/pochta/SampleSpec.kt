import kotlin.test.assertEquals
import org.jtalks.pochta.http.filters.TokenAuthenticationFilter
import org.jtalks.pochta.config.Config
import java.util.Properties
import org.jetbrains.spek.api.Spek

public class SampleSpecs : Spek() {{

    given("A string with default locale settings") {
        val string = "OloLo"
        on("lowercasing") {
            val value = string.toLowerCase()
            it("should result in a string with lowercased chars only") {
                assertEquals("ololo", value)
            }
        }
    }
}}