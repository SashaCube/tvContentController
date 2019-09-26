package com.oleksandr.havryliuk.tvcontentcontroller.client.schedule

import android.util.Log
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

object NULPScheduleHelper {

    private const val TAG = "NULPScheduleHelper"
    private const val BASE_GROUP_URL = "http://www.lp.edu.ua/students_schedule?institutecode_selective=ІКНІ&edugrupabr_selective="
    private const val BASE_TEACHER_URL = "http://www.lp.edu.ua/lecturer__schedule?namedepartment_selective=Автоматизовані+системи+управління&teachername_selective="

    private const val ID = "id"
    private const val FULL_GROUP = "group_full"
    private const val SUB_1_GROUP = "sub_1_full"
    private const val SUB_2_GROUP = "sub_2_full"
    private const val SUB_1_CHYS = "sub_1_chys"
    private const val SUB_2_CHYS = "sub_2_chys"

    private const val DAY = "view-grouping"
    private const val DAY_HEADER = "view-grouping-header"
    private const val LESSON = "stud_schedule"
    private const val GROUP = "week_color"

    fun getGroupSchedule(groupName: String): List<ScheduleDay> {
        val daysList = MutableList(5) { index -> ScheduleDay(index, mutableListOf()) }

        getGroupDocument(groupName)?.getElementsByClass(DAY)?.map { day ->

            val dayIndex = getIndexOfDay(day)

            daysList.add(
                    dayIndex,
                    ScheduleDay(dayIndex, getLessonsOfTheDay(day))
            )
        }

        Log.d(TAG, "schedule for group - $groupName, daysList - $daysList")

        return daysList
    }

    fun getTeacherSchedule(teacher: String): List<ScheduleDay> {
        val daysList = MutableList(5) { index -> ScheduleDay(index, mutableListOf()) }

        getTeacherDocument(teacher)?.getElementsByClass(DAY)?.map { day ->

            val dayIndex = getIndexOfDay(day)

            daysList.add(
                    dayIndex,
                    ScheduleDay(dayIndex, getLessonsOfTheDay(day))
            )
        }

        Log.d(TAG, "schedule for teacher - $teacher, daysList - $daysList")

        return daysList
    }

    private fun getIndexOfDay(day: Element) = with(day.getElementsByClass(DAY_HEADER)[0].toString()) {
        when {
            contains("Пн") -> 0
            contains("Вт") -> 1
            contains("Ср") -> 2
            contains("Чт") -> 3
            else -> 4
        }
    }

    private fun getGroupDocument(groupName: String) = Jsoup.connect(BASE_GROUP_URL + groupName).get()

    private fun getTeacherDocument(teacher: String) = Jsoup.connect(BASE_TEACHER_URL + teacher).get()

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
                SUB_1_CHYS -> Sub1Group(element.toString())
                SUB_2_GROUP -> Sub2Group(element.toString())
                SUB_2_CHYS -> Sub2Group(element.toString())
                else -> null
            }
}

