package com.oleksandr.havryliuk.tvcontentcontroller.client.schedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oleksandr.havryliuk.tvcontentcontroller.R
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.ScheduleDay
import kotlinx.android.synthetic.main.post_schedule.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TestParseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.post_schedule)


        doAsync {
            val daysList = NULPScheduleHelper.getGroupSchedule("КН-303")
            //val daysList = NULPScheduleHelper.getTeacherSchedule("Теслюк+Тарас+Васильович")

            uiThread {
                tv_group_name.text = "КН-303"
                updateView(daysList)
            }
        }
    }

    private fun updateView(data: List<ScheduleDay>) {
        recycler_view_1.adapter = ScheduleDayAdapter(data[0])
        recycler_view_2.adapter = ScheduleDayAdapter(data[1])
        recycler_view_3.adapter = ScheduleDayAdapter(data[2])
        recycler_view_4.adapter = ScheduleDayAdapter(data[3])
        recycler_view_5.adapter = ScheduleDayAdapter(data[4])
    }
}