package andrianova.matchmaker.match

import andrianova.matchmaker.dto.UserDto
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.random.Random

@RunWith(SpringRunner::class)
@SpringBootTest
class MatchMakerTest {

    val requestCount = 1000
    val rand = Random(requestCount)

    @Autowired
    lateinit var matchMaker: MatchMaker

    @Test
    fun should_fill_groups() {
        for (i in 0 until requestCount) {
            Runnable { matchMaker.process(randomUser("$i")) }.run()
        }
        Thread.sleep(5200)
        Assert.assertFalse(matchMaker.completeGroups.isEmpty())
        println("###\n" +
                "Groups created: ${matchMaker.completeGroups.size}\n" +
                "###\n")
        println("Not created ${matchMaker.groups}")
    }

    fun randomUser(name: String): UserDto {
        return UserDto(name,
            rand.nextDouble(0.0, 100.0),
            rand.nextDouble(0.0, 100.0))
    }
}
