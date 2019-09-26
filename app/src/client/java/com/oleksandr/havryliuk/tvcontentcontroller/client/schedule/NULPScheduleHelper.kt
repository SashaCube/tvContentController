package com.oleksandr.havryliuk.tvcontentcontroller.client.schedule

import android.util.Log
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object NULPScheduleHelper {

    private const val TAG = "NULPScheduleHelper"
    private const val BASE_URL = "http://www.lp.edu.ua/students_schedule?institutecode_selective=ІКНІ&edugrupabr_selective="

    private const val ID = "id"
    private const val FULL_GROUP = "group_full"
    private const val SUB_1_GROUP = "sub_1_full"
    private const val SUB_2_GROUP = "sub_2_full"

    private const val DAY = "view-grouping"
    private const val LESSON = "stud_schedule"
    private const val GROUP = "week_color"

    fun getGroupSchedule(groupName: String): List<ScheduleDay> {
        val daysList = mutableListOf<ScheduleDay>()

        getDocument(groupName)?.getElementsByClass(DAY)?.mapIndexed { dayIndex, day ->
            daysList.add(
                    ScheduleDay(dayIndex, getLessonsOfTheDay(day))
            )
        }

        Log.d(TAG, "schedule for group - $groupName, daysList - $daysList")

        return daysList
    }

    private fun getDocument(groupName: String) = Jsoup.connect(BASE_URL + groupName).get()

    private fun getLessonsOfTheDay(day: Element): MutableList<MutableList<Lesson>> {
        val listListLessons = mutableListOf<MutableList<Lesson>>()

        day.getElementsByClass(LESSON).map { lesson ->
            listListLessons.add(getLessonsFromLessonElement(lesson))
        }

        return listListLessons
    }

    private fun getLessonsFromLessonElement(lesson: Element): MutableList<Lesson> {
        val lessons = mutableListOf<Lesson>()
        val lessonBody = lesson.getElementsByClass(GROUP)

        for (element: Element in lessonBody) {
            val para = getLessonByElement(element)

            if (para != null) {
                lessons.add(para)
            }
        }

        return lessons
    }

    private fun getLessonByElement(element: Element) =
            when (element.attr(ID)) {
                FULL_GROUP -> FullGroup(element.toString())
                SUB_1_GROUP -> Sub1Group(element.toString())
                SUB_2_GROUP -> Sub2Group(element.toString())
                else -> null
            }
}

