package com.triplepi.projectilemes.data.network

import com.triplepi.projectilemes.data.network.dto.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
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
    fun getProduct(@Path("productId") id: Long): Call<ProductIM>

    @DELETE("/api/product/{productId}")
    fun deleteProduct(@Path("productId") productId: Long)

    @POST("/api/product/{productId}/processes")
    fun postProductByProductIdProcesses(@Path("productId") productId: Long, @Body data: TechnologicalProcessIM)

    @GET("/api/product/{productId}/processes/{processId}")
    fun getProductByProductIdProcessesByProcessId(@Path("productId") productId: Long, @Path("processId") processId: Long): Call<TechnologicalProcessIM>

    @POST("/api/product/{productId}/processes/{processId}/operations")
    fun postProductByProductIdProcessesByProcessIdOperations(@Path("productId") productId: Long, @Path("processId") processId: Long, @Body data: TechnologicalOperationIM): Call<TechnologicalOperationIM>
//    @POST("/api/product/{productId}/processes/{processId}/operations/{operationId}/modes")

    @POST("/api/signin")
    fun signIn(@Body userData: UserDataDto): Deferred<Response<TokenDto>>

    //    @DELETE("/api/product/{productId}/processes/{processId}/operations/{operationId}/modes/{operationModeId}")
//
//    //report
//    @GET("/api/report/batchesForecast")
//    @GET("/api/report/dailyReport")
//    @GET("/api/report/scheduleDetailed")
//
//    schedule
    @GET("/api/schedule")
    fun getSchedule(@Query("workCenterId") workCenterId: Long?, @Query("begin") begin: String?, @Query("end") end: String?): Call<List<ScheduleItemDTO>>

    //    @POST("/api/schedule")
    @POST("/api/schedule/{id}/actions")
    fun postScheduleItemAction(@Path("id") scheduleItemId: Long, @Body scheduleItemActionIM: ScheduleItemActionIM): Call<Void>

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
    @GET("/api/workcenter")
    fun getWorkCenter(): Call<List<WorkCenterDTO>>

    @GET("/api/workcenter/{workCenterId}")
    fun getWorkCenterById(@Path("workCenterId") workCenterId: Long): Call<WorkCenterDTO>


//    @POST("/api/workcenter")
//    @DELETE("/api/workcenter/{workCenterId}")
//    @POST("/api/workcenter/{workCenterId}/service")
//    @POST("/api/workcenter/{workCenterId}/workingtimes")

    @GET("/api/batches/{batchId}")
    fun getBatch(@Path("batchId") batchId: Long): Call<BatchIM>
}