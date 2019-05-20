/**
 * MES
 * MES API Reference
 *
 * OpenAPI spec version: v1
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package com.triplepi.projectilemes.data.network.dto


import com.google.gson.annotations.SerializedName

/**
 *
 * @param Date
 * @param Action
 */
data class ScheduleItemManageActionIM(
    val Date: java.time.LocalDateTime,
    @SerializedName("Action") val action: Action
) {

    /**
     *
     * Values: undo,reset
     */
    enum class Action(val value: kotlin.String) {

        @SerializedName("Undo")
        undo("Undo"),

        @SerializedName("Reset")
        reset("Reset");

    }

}

