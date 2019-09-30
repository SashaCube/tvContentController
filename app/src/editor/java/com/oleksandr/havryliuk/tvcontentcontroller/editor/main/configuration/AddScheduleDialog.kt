package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration


import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.oleksandr.havryliuk.tvcontentcontroller.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

private const val ACTIVITY_PADDING = 16

class AddScheduleDialog(ui: AnkoContext<View>) {

    lateinit var dialog: DialogInterface
    lateinit var newPlayerName: TextInputEditText
    lateinit var cancelButton: TextView
    lateinit var okButton: TextView

    init {
        with(ui) {
            dialog = alert {

                customView {
                    verticalLayout {
                        padding = dip(ACTIVITY_PADDING)

                        textView(R.string.add_new_schedule) {
                            textSize = 24f
                            textColor = context.resources.getColor(R.color.black)
                        }.lparams {
                            bottomMargin = dip(ACTIVITY_PADDING)
                        }

                        textView(R.string.add_text).lparams {
                            bottomMargin = dip(ACTIVITY_PADDING)
                        }

                        textInputLayout {
                            newPlayerName = textInputEditText {
                                textSize = 16f
                            }
                        }

                        linearLayout {
                            topPadding = dip(24)
                            orientation = LinearLayout.HORIZONTAL
                            horizontalGravity = Gravity.END

                            cancelButton = textView(R.string.cancel) {
                                textSize = 14f
                                textColor = context.resources.getColor(R.color.colorAccent)
                            }.lparams {
                                marginEnd = dip(ACTIVITY_PADDING)
                            }

                            okButton = textView(R.string.add) {
                                textSize = 14f
                                textColor = context.resources.getColor(R.color.colorAccent)
                            }
                        }
                    }
                }
            }.show()
        }
    }
}

inline fun ViewManager.textInputEditText(theme: Int = 0, init: TextInputEditText.() -> Unit) =
        ankoView({ TextInputEditText(it) }, theme, init)

inline fun ViewManager.textInputLayout(theme: Int = 0, init: TextInputLayout.() -> Unit) =
        ankoView({ TextInputLayout(it) }, theme, init)