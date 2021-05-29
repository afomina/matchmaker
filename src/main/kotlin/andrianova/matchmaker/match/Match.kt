package andrianova.matchmaker.match

import andrianova.matchmaker.dto.UserAttributes
import andrianova.matchmaker.dto.UserDto

class Match {

    val users = ArrayDeque<UserDto>()

    private var skillSum = 0.0
    private var latencySum = 0.0

    private var minSkill = Double.MAX_VALUE
    private var minLatency = Double.MAX_VALUE

    private var maxSkill = Double.MIN_VALUE
    private var maxLatency = -1.0

    fun add(user: UserDto): Match {
        users.add(user)

        skillSum += user.skill
        latencySum += user.latency

        if (user.skill < minSkill) {
            minSkill = user.skill
        }
        if (user.skill > maxSkill) {
            maxSkill = user.skill
        }
        if (user.latency < minLatency) {
            minLatency = user.latency
        }
        if (user.latency > maxLatency) {
            maxLatency = user.latency
        }

        return this
    }

    fun size(): Int {
        return users.size
    }

    fun getAverage(): UserAttributes? {
        if (users.isEmpty()) return null
        val curTime = System.currentTimeMillis()
        val timeSum = users.map { u -> curTime - u.time }.sum()
        return UserAttributes(
            skillSum / users.size,
            latencySum / users.size,
            timeSum / users.size
        )
    }

    fun getMin(): UserAttributes {
        val curTime = System.currentTimeMillis()
        return UserAttributes(
            minSkill, minLatency,
            curTime - users.last().time
        )
    }

    fun getMax(): UserAttributes {
        val curTime = System.currentTimeMillis()
        return UserAttributes(
            maxSkill, maxLatency,
            curTime - users.first().time
        )
    }

    fun getOld(maxTime: Long): List<UserDto> {
        val curTime = System.currentTimeMillis()
        return users.filter { user -> curTime - user.time >= maxTime }
    }

    override fun toString(): String {
        return "$users"
    }

}
