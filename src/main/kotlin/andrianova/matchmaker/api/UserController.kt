package andrianova.matchmaker.api

import andrianova.matchmaker.dto.UserDto
import andrianova.matchmaker.match.MatchMaker
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("users")
class UserController(val matchMaker: MatchMaker) {

    @PostMapping
    fun add(@RequestBody @Valid user: User) {
        matchMaker.process(UserDto(user.name, user.skill, user.latency))
    }
}
