package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration

import android.view.View
import org.jetbrains.anko.AnkoContext

fun addSchedulerDialog(view: View, onAddClick: (String) -> Boolean) {
    val addPlayerDialog by lazy {
        AddScheduleDialog(AnkoContext.create(view.context, view))
    }

    addPlayerDialog.okButton.setOnClickListener {
        onAddClick(addPlayerDialog.newPlayerName.text.toString())
        addPlayerDialog.dialog.dismiss()
    }

    addPlayerDialog.cancelButton.setOnClickListener {
        addPlayerDialog.dialog.dismiss()
    }
}