package andrianova.matchmaker.match

import andrianova.matchmaker.config.AppSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class OldUsersFinder {

    @Autowired
    lateinit var matchMaker: MatchMaker

    @Autowired
    lateinit var appSettings: AppSettings

    @Scheduled(fixedDelay = 180)
    fun start() {
        synchronized(matchMaker.groups) {
            for (group in matchMaker.groups) {
                val oldUsers = group.getOld(appSettings.maxTimeInQueue)
                if (oldUsers.isEmpty()) {
                    break
                }
                var groupToFill = group
                while (groupToFill.size() < appSettings.groupSize) {
                    val nearestGroup = matchMaker.findNearestGroup(groupToFill) ?: break
                    groupToFill = matchMaker.mergeGroups(groupToFill, nearestGroup)
                }
            }
        }
    }
}
