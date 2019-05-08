package com.triplepi.projectilemes.presentation

import com.triplepi.projectilemes.mvp.MvpPresenter
import com.triplepi.projectilemes.mvp.MvpView
import io.swagger.client.models.WorkCenterDTO

interface WorkStationView : MvpView {

    var workStation: WorkCenterDTO

}

class WorkStationPresenter(view: WorkStationView) : MvpPresenter<WorkStationView>(view) {


}