package com.triplepi.projectilemes.domain.interactors

import android.os.Build
import android.support.annotation.RequiresApi
import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import java.time.LocalDateTime

class LoadScheduleUseCase : UseCase<List<ScheduleItemDTO>>() {

    private val api = App.INSTANCE.api

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun run(): List<ScheduleItemDTO> {
        val scheduleItemIMList = api.getSchedule().execute().body().orEmpty()
            .filter { scheduleItemDTO -> scheduleItemDTO.StartDate.isBefore(LocalDateTime.now()) }
        return scheduleItemIMList.map { scheduleItemIM -> convertScheduleItemIMToDTO(scheduleItemIM) }
    }

    suspend fun convertScheduleItemIMToDTO(scheduleItemIM: ScheduleItemIM): ScheduleItemDTO {
        val operationDTO: ScheduleItemOperationDTO = ScheduleItemOperationDTO()
        val workCenterIM: WorkCenterIM = api.getWorkCenterById(scheduleItemIM.WorkCenterId!!).execute().body()!!
        val workCenterDTO = ScheduleItemWorkCenterDTO(
            Name = workCenterIM.Name,
            Id = workCenterIM.Id
        )
        val batchIM: BatchIM = api.getBatch(scheduleItemIM.BatchId!!).execute().body()!!
        val batchDTO =
            ScheduleItemBatchDTO(
                Id = batchIM.Id,
                Number = batchIM.Number,
                Quantity = batchIM.Quantity,
                FactQuantity = batchIM.Quantity,
                OrderId = 0,
                OrderNumber = "",
                StartOperationId = batchIM.StartOperationId
            )
        return ScheduleItemDTO(
            Operation = operationDTO,
            WorkCenter = workCenterDTO,
            Batch = batchDTO,
            StartDate = scheduleItemIM.StartDate,
            StartExecutionDate = scheduleItemIM.StartExecutionDate,
            FinishDate = scheduleItemIM.FinishDate,
            WorkCenterFinishDate = scheduleItemIM.FinishDate,
            Timestamp = null,
            FactStartDate = null,
            FactFinishDate = null,
            FactWorkCenterFinishDate = null,
            FactStartExecutionDate = null,
            PlanCount = 0,
            FactCount = 0,
            QuarantineCount = 0,
            Leftover = ScheduleItemLeftoverDTO(),
            LeftoverSolution = ScheduleItemLeftoverDTO(),
            status = ScheduleItemDTO.Status.New
        )
    }

}