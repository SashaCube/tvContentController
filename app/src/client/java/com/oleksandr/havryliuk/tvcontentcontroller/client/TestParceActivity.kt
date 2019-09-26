package com.oleksandr.havryliuk.tvcontentcontroller.client

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.oleksandr.havryliuk.tvcontentcontroller.R
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.NULPScheduleHelper
import kotlinx.android.synthetic.main.test_parce_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TestParseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_parce_activity)

        doAsync {
            val daysList = NULPScheduleHelper.getGroupSchedule("КН-302")

            uiThread {
                text_view.text = Html.fromHtml(daysList[2].lessonsList.toString())
            }
        }
    }
}