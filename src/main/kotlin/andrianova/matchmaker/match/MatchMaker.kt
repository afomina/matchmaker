package andrianova.matchmaker.match

import andrianova.matchmaker.config.AppSettings
import andrianova.matchmaker.dto.UserAttributes
import andrianova.matchmaker.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList

@Service
class MatchMaker {

    @Autowired
    lateinit var appSettings: AppSettings

    private var matchNum = AtomicInteger(1)

    val groups = ConcurrentLinkedDeque<Match>()

    val completeGroups: MutableList<Match> = ArrayList()

    fun process(user: UserDto) {
        val nearestGroup = findNearestGroup(user.toAttributes(), appSettings.maxAllowedDistance)
        if (nearestGroup == null) {
            groups.add(Match().add(user))
        } else {
            addUserToGroup(nearestGroup, user)
        }
    }

    private fun addUserToGroup(group: Match, user: UserDto) {
        synchronized(group) {
            group.add(user)
            if (isFull(group)) {
                printGroupInfo(group)

                groups.remove(group)
                completeGroups.add(group)
                matchNum.incrementAndGet()
            }
        }
    }

    fun findNearestGroup(user: UserAttributes, maxDistance: Double = Double.MAX_VALUE): Match? {
        var minDistance = Double.MAX_VALUE
        var nearestGroup: Match? = null
        synchronized(groups) {
            for (group in groups) {
                val distance = Distance.calculate(group.getAverage()!!, user)
                if (distance <= maxDistance && distance < minDistance && distance != 0.0) {
                    minDistance = distance
                    nearestGroup = group
                }
            }
        }
        return nearestGroup
    }

    private fun isFull(group: Match): Boolean {
        return group.size() == appSettings.groupSize
    }

    private fun printGroupInfo(group: Match) {
        println("Group #$matchNum=${group.users.map { u -> u.name }}")
        val average = group.getAverage()
        val min = group.getMin()
        val max = group.getMax()
        println("AVERAGE skill: ${average?.skill}, latency: ${average?.latency}, time in queue: ${average?.time} ms")
        println("MIN skill: ${min.skill}, latency: ${min.latency}, time in queue: ${min.time} ms")
        println("MAX skill: ${max.skill}, latency: ${max.latency}, time in queue: ${max.time} ms")
    }

    fun mergeGroups(group1: Match, group2: Match): Match {
        while (group2.users.isNotEmpty()) {
            val user = group2.users.removeFirst()
            addUserToGroup(group1, user)
            if (isFull(group1)) break
        }
        if (group2.size() == 0) {
            groups.remove(group2)
        }
        return group1
    }
}
