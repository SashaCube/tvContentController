package com.oleksandr.havryliuk.tvcontentcontroller.client

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.oleksandr.havryliuk.tvcontentcontroller.R
import kotlinx.android.synthetic.main.test_parce_activity.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


class TestParseActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_parce_activity)

        Log.d(TAG, "before async")
        doAsync {
            val url = "http://www.lp.edu.ua/students_schedule?institutecode_selective=%D0%86%D0%9A%D0%9D%D0%86&edugrupabr_selective=%D0%9A%D0%9D-303"

            val document = Jsoup.connect(url).get()

            val daysList = mutableListOf<ScheduleDay>()

            uiThread {
                val contentFields = document.getElementsByClass("view-grouping").mapIndexed { dayIndex, day ->

                    val listListLessons = mutableListOf<MutableList<Lesson>>()

                    day.getElementsByClass("stud_schedule").map { lesson ->

                        var groupLesson: String
                        val lessons = mutableListOf<Lesson>()
                        val lessonBody = lesson.getElementsByClass("week_color")
                        Log.d(TAG, "$lessonBody")

                        for (element: Element in lessonBody) {
                            groupLesson = element.toString()

                            val para = when (element.attr(ID)) {
                                FULL_GROUP -> FullGroup(groupLesson)
                                SUB_1_GROUP -> Sub1Group(groupLesson)
                                SUB_2_GROUP -> Sub2Group(groupLesson)
                                else -> null
                            }

                            if (para != null) {
                                lessons.add(para)
                            }
                        }

                        listListLessons.add(lessons)
                    }

                    daysList.add(ScheduleDay(dayIndex, listListLessons))
                }

                daysList.map {
                    Log.d(TAG, "title - ${it.lessonsList}")
                }

                text_view.text = daysList[3].lessonsList.toString()
            }
        }

        Log.d(TAG, "after async")
    }

    companion object {
        private val TAG = TestParseActivity::class.java.name
        const val FULL_GROUP = "group_full"
        const val SUB_1_GROUP = "sub_1_full"
        const val SUB_2_GROUP = "sub_2_full"
        const val ID = "id"
    }
}


class ScheduleDay(
        val index: Int,
        val lessonsList: MutableList<MutableList<Lesson>>,
        var name: String = ""
) {
    init {
        name = when (index) {
            0 -> "Понеділок"
            1 -> "Вівторок"
            2 -> "Середа"
            3 -> "Четвер"
            else -> "Пятниця"
        }
    }
}

sealed class Lesson
data class Sub1Group(val htmlBody: String) : Lesson()
data class Sub2Group(val htmlBody: String) : Lesson()
data class FullGroup(val htmlBody: String) : Lesson()

