package com.oleksandr.havryliuk.tvcontentcontroller.client.content.view

class MainLoopThread(
        private val runnable: Runnable
) : Thread() {
    override fun run() {
        super.run()
        runnable.run()
    }
}
