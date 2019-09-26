package com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model

sealed class Lesson
data class Sub1Group(val htmlBody: String) : Lesson()
data class Sub2Group(val htmlBody: String) : Lesson()
data class FullGroup(val htmlBody: String) : Lesson()