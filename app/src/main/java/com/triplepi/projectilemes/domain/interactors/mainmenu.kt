package com.triplepi.projectilemes.domain.interactors

import com.triplepi.projectilemes.App
import com.triplepi.projectilemes.data.network.dto.ScheduleItemActionIM
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class StageScheduleItemActionUseCase(val scheduleItemId: Long, val scheduleItemActionIM: ScheduleItemActionIM) :
    UseCase<Boolean>() {

    private val api = App.INSTANCE.api

    override val dispatcher: CoroutineDispatcher
        get() = Dispatchers.IO

    override suspend fun run(): Boolean {
//        api.postScheduleItemAction(scheduleItemId, scheduleItemActionIM).execute()
        return true
    }

}