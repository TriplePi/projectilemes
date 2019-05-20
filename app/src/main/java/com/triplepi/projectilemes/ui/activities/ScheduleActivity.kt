package com.triplepi.projectilemes.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.triplepi.projectilemes.R
import com.triplepi.projectilemes.extensions.bind
import com.triplepi.projectilemes.mvp.MvpActivity
import com.triplepi.projectilemes.presentation.SchedulePresenter
import com.triplepi.projectilemes.presentation.ScheduleView
import com.triplepi.projectilemes.ui.adapters.ScheduleAdapter

class ScheduleActivity : MvpActivity<ScheduleView, SchedulePresenter>(), ScheduleView {
    override var scheduleList: List<ScheduleAdapter.ScheduleItem> = emptyList()
    private val scheduleListView: RecyclerView by bind(R.id.schedule_view)
    private val backButton: Button by bind(R.id.back)


    override fun createPresenter() = SchedulePresenter(this)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule)
        presenter.loadSchedule()
        scheduleListView.layoutManager = LinearLayoutManager(this)
        scheduleListView.adapter = ScheduleAdapter(scheduleList)
        backButton.setOnClickListener { presenter.onBackButtonClicked() }
    }

    override fun showMainMenuScreen() {
        val intent = Intent(this@ScheduleActivity, MainMenuActivity::class.java)
        startActivity(intent)
    }


}