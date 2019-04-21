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
package io.swagger.client.models

import io.swagger.client.models.ProblemScheduleItemDTO

/**
 * 
 * @param Id 
 * @param FitnessValue 
 * @param Schedule 
 * @param Timestamp 
 */
data class ProblemSolutionDataDTO (
    val Id: kotlin.Long? = null,
    val FitnessValue: kotlin.Double? = null,
    val Schedule: kotlin.Array<ProblemScheduleItemDTO>? = null,
    val Timestamp: kotlin.Long? = null
)
