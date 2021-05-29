package andrianova.matchmaker.api

import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun should_return_ok() {
        mockMvc.perform(
            post("/users")
                .content(
                    "{" +
                            "    \"name\": \"user1\"," +
                            "    \"skill\": 99," +
                            "    \"latency\": 1" +
                            "}"
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

    @Test
    fun should_return_400_when_requestNotValid() {
        mockMvc.perform(
            post("/users")
                .content(
                    "{" +
                            "    \"skill\": 99," +
                            "    \"latency\": 1" +
                            "}"
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun should_return_400_when_requestSkillNotValid() {
        mockMvc.perform(
            post("/users")
                .content(
                    "{" +
                            "    \"name\": \"user\"," +
                            "    \"latency\": 1" +
                            "}"
                )
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }
}
