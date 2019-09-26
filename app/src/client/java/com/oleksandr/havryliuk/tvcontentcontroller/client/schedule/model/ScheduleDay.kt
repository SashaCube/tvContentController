package com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model

class ScheduleDay(
        index: Int,
        val lessonsList: MutableList<MutableList<Lesson>>,
        var name: String = ""
) {
    init {
        name = when (index) {
            0 -> MONDAY
            1 -> TUESDAY
            2 -> WEDNESDAY
            3 -> THURSDAY
            else -> FRIDAY
        }
    }

    companion object {
        const val MONDAY = "Понеділок"
        const val TUESDAY = "Вівторок"
        const val WEDNESDAY = "Середа"
        const val THURSDAY = "Четвер"
        const val FRIDAY = "Пятниця"
    }
}