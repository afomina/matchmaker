package andrianova.matchmaker.api

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class User(@NotBlank val name: String,
           @NotNull val skill: Double,
           @NotNull val latency: Double) {

    override fun toString(): String {
        return name
    }
}
