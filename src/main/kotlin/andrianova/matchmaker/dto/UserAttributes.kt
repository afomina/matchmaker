package andrianova.matchmaker.dto

class UserAttributes(val skill: Double,
                     val latency: Double,
                     val time: Long = System.currentTimeMillis()) {

    override fun toString(): String {
        return "UserAttributes(skill=$skill, latency=$latency, time=$time)"
    }

}
