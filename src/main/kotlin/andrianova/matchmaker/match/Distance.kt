package andrianova.matchmaker.match

import andrianova.matchmaker.dto.UserAttributes
import kotlin.math.pow
import kotlin.math.sqrt

class Distance {
    companion object {
        fun calculate(user1: UserAttributes, user2: UserAttributes): Double {
            return sqrt((user1.skill - user2.skill).pow(2) +
                    (user1.latency - user2.latency).pow(2))
        }
    }
}
