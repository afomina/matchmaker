package andrianova.matchmaker.dto

class UserDto(val name: String,
              val skill: Double,
              val latency: Double,
              val time: Long = System.currentTimeMillis()) {

    fun toAttributes(): UserAttributes {
        return UserAttributes(skill, latency, time)
    }

    override fun toString(): String {
        return "(skill=$skill, latency=$latency, time=$time)"
    }

}
