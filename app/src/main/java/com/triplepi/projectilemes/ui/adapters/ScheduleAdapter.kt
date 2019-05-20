package com.triplepi.projectilemes.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.triplepi.projectilemes.R

class ScheduleAdapter(private val schedule: List<ScheduleItem>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val scheduleItemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.schedule_view_grid, parent, false)

        return ScheduleViewHolder(scheduleItemLayout as View)
    }

    override fun getItemCount(): Int = schedule.size

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedule[position])
    }

    class ScheduleViewHolder(scheduleItemLayout: View) : RecyclerView.ViewHolder(scheduleItemLayout) {
        private var operationView: TextView = scheduleItemLayout.findViewById(R.id.operation_name)
        private var productView: TextView = scheduleItemLayout.findViewById(R.id.product_name)
        private var quantityView: TextView = scheduleItemLayout.findViewById(R.id.quantity)
        private var orderView: TextView = scheduleItemLayout.findViewById(R.id.order_name)
        private var startDateView: TextView = scheduleItemLayout.findViewById(R.id.start_date)
        private var endDateView: TextView = scheduleItemLayout.findViewById(R.id.end_date)
        private var durationView: TextView = scheduleItemLayout.findViewById(R.id.duration)

        fun bind(scheduleItem: ScheduleItem) {
            operationView.text = scheduleItem.operation
            productView.text = scheduleItem.product
            quantityView.text = scheduleItem.quantity
            orderView.text = scheduleItem.order
            startDateView.text = scheduleItem.startDate
            endDateView.text = scheduleItem.endDate
            durationView.text = scheduleItem.duration
        }
    }


    data class ScheduleItem(
        val operation: String,
        val product: String,
        val quantity: String,
        val order: String,
        val startDate: String,
        val endDate: String,
        val duration: String
    )
}

