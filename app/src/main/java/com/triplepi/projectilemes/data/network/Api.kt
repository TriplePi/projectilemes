package com.triplepi.projectilemes.data.network

import com.triplepi.projectilemes.data.network.dto.AdjustmentBaseIM
import com.triplepi.projectilemes.data.network.dto.OrderIM
import com.triplepi.projectilemes.data.network.dto.ProblemDTO
import com.triplepi.projectilemes.data.network.dto.ProductDto
import io.swagger.client.models.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

    //    adjustment
    @POST("/api/adjustment")
    fun postAdjustment(@Body adjustmentIM: AdjustmentIM): Call<AdjustmentIM>

    @DELETE("/api/adjustment")
    fun dalateAdjustment()

    @DELETE("/api/adjustment/{adjustmentId}")
    fun deleteAdjustment(@Query("adjustmentId") adjustmentId: Long)

    @POST("/api/adjustment/operations/{operationId}")
    fun postAdjustmentOperationById(@Path("operationId") operationId: Long, @Body data: List<OperationAdjustmentIM>)

    @POST("/api/adjustment/operations/{operationId}/workcenters/{workCenterId}")
    fun postAdjustmentOperationsByOperationIdWorkcentersByWorkCenterId(@Path("operationId") operationId: Long, @Path("workCenterId") workcenterId: Long, @Body data: List<AdjustmentBaseIM>)

    @POST("/api/adjustment/workcenters/{workCenterId}")
    fun postAdjustmentWorkcentersByWorkcenterId(@Path("workCenterId") workcenterId: Long, @Body data: List<WorkCenterAdjustmentIM>)

    @POST("/api/adjustment/workcenters/{workCenterId}/operations/{operationId}")
    fun postAdjustmentWorkcentersByWorkCenterIdOperationsByOperationId(@Path("workcenterId") workcenterId: Long, @Path("operationId") operationId: Long, @Body data: List<AdjustmentBaseIM>)
//    order

    @POST("/api/order")
    fun postOrder(@Body oder: OrderIM): Call<OrderIM>

    @GET("/api/order/batches")
    fun getOrderBatches(@Query("processId") processId: Long, @Query("quantity") quantity: Int): Call<List<Int>>

//    problem

    @GET("/api/problem")
    fun getProblem(@Query("minTs") minTs: Long): Call<ProblemDTO>

    @POST("/api/problem")
    fun postProblem(@Body planningSettings: PlanningSettingsIM): Call<PlanningSettingsIM>

//    @GET("/api/problem/{id}")
//    fun getProblemById(@Query("id") id: Long): Call<ProblemWithSolutionFitnessDTO>
//
//    @DELETE("/api/problem/{id}")
//    fun deleteProblemById(@Query("id") id: Long): Call<ProblemWithSolutionFitnessDTO>

    @GET("/api/problem/{id}/solutions")
    fun getSolutionsByProblemId(@Query("id") id: Long): Call<List<ProblemSolutionDataDTO>>

    @POST("/api/problem/{id}/solutions")
    fun postSolutionsByProblemId(
        @Path("id") id: Long, @Header("if-match") ifMatch: String, @Body solution: ProblemSolutionDataDTO, @Field(
            "IsFinal"
        ) isFinal: Boolean
    ): Call<List<ProblemSolutionDataDTO>>

    @POST("/api/problem/{id}/solutions/{solutionId}")
    fun postSolutionsByProblemIdBySolutionId(@Path("id") id: Long, @Path("solutionId") solutionId: Long, @Body planningSettings: RePlanningSettingsIM): Call<List<ProblemSolutionDataDTO>>

//    product

    @POST("/api/product")
    fun postProduct(@Body data: ProductIM)

    @GET("/api/products/{productId}")
    fun getProduct(@Path("productId") id: Long): Call<ProductDto>
    @DELETE("/api/product/{productId}")
    fun deleteProduct(@Path("productId") productId:Long)
    @POST("/api/product/{productId}/processes")
    fun postProductByProductIdProcesses(@Path("productId") productId: Long,@Body data:TechnologicalProcessIM)
    @GET("/api/product/{productId}/processes/{processId}")
    fun getProductByProductIdProcessesByProcessId(@Path("productId")productId: Long,@Path("processId") processId: Long):Call<TechnologicalProcessDTO>
    @POST("/api/product/{productId}/processes/{processId}/operations")
    fun postProductByProductIdProcessesByProcessIdOperations(@Path("productId")productId: Long,@Path("processId") processId: Long,@Body data:TechnologicalOperationIM):Call<TechnologicalOperationIM>
//    @POST("/api/product/{productId}/processes/{processId}/operations/{operationId}/modes")

//    @DELETE("/api/product/{productId}/processes/{processId}/operations/{operationId}/modes/{operationModeId}")
//
//    //report
//    @GET("/api/report/batchesForecast")
//    @GET("/api/report/dailyReport")
//    @GET("/api/report/scheduleDetailed")
//
////    schedule
//    @GET("/api/schedule")
//    @POST("/api/schedule")
//    @POST("/api/schedule/{id}/actions")
//    @GET("/api/schedule/{id}/history")
//    @POST("/api/schedule/{id}/manageactions")
//    @GET("/api/schedule/deleted")
//
////    timeline
//    @GET("/api/timeline")
//    @GET("/api/timeline/adjustments")
//    @GET("/api/timeline/workcenterinaccessibility")
//    @GET("/api/timeline/workcenters")
//    @GET("/api/timeline/workcenterservice")
//
////    workcenter
//    @GET("/api/workcenter")
//    @POST("/api/workcenter")
//    @DELETE("/api/workcenter/{workCenterId}")
//    @POST("/api/workcenter/{workCenterId}/service")
//    @POST("/api/workcenter/{workCenterId}/workingtimes")
}