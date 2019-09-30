package com.oleksandr.havryliuk.tvcontentcontroller.client.content.view

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.oleksandr.havryliuk.tvcontentcontroller.R
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.ContentContract
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.ContentPresenter
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.ContentViewUtils.*
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.Schedule
import com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.isUpToDate
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post.*
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.image_manager.ImageManager
import java.lang.Thread.sleep
import java.util.*

class ContentView : ContentContract.IContentView {

    private var presenter: ContentContract.IContentPresenter? = null
    private var fragment: Fragment? = null
    private var root: View? = null

    private var adPostView: ViewGroup? = null
    private var newsPostView: ViewGroup? = null
    private var imagePostView: ViewGroup? = null
    private var textPostView: ViewGroup? = null
    private var weatherPostView: ViewGroup? = null
    private var schedulePostView: ViewGroup? = null
    private var adPostImage: ImageView? = null
    private var imagePostImage: ImageView? = null
    private var newsPostImage: ImageView? = null
    private var emptyImage: ImageView? = null
    private var textPostText: TextView? = null
    private var adPostText: TextView? = null
    private var newsPostText: TextView? = null

    private var displayPostThread: Thread? = null
    private var postIndex = LOAD

    //for AD
    private var allPosts: List<Post>? = null
    private var showADState: Boolean = false

    //for weather forecast
    private lateinit var weatherHelper: WeatherHelper

    private var scheduleList: List<Schedule>? = null
    private var currentSchedule: Schedule? = null
    private var showScheduleState: Boolean = false

    // for main loop
    private var isScheduleShowed = false
    private var isWeatherShowed = false

    private val isPostReadyToShow: Boolean
        get() = posts != null && posts!!.isNotEmpty() && postIndex != LOAD


    override fun init(fragment: Fragment, root: View) {
        this.fragment = fragment
        this.root = root

        initView()
        hideAll()
        animation(emptyImage)
    }

    private fun initView() {
        adPostView = root!!.findViewById(R.id.post_ad)
        newsPostView = root!!.findViewById(R.id.post_news)
        imagePostView = root!!.findViewById(R.id.post_image)
        textPostView = root!!.findViewById(R.id.post_text)
        weatherPostView = root!!.findViewById(R.id.post_weather_forecast)
        schedulePostView = root!!.findViewById(R.id.post_schedule)


        adPostImage = root!!.findViewById(R.id.ad_post_image)
        newsPostImage = root!!.findViewById(R.id.news_post_image)
        imagePostImage = root!!.findViewById(R.id.image_post_image)

        adPostText = root!!.findViewById(R.id.ad_post_text)
        newsPostText = root!!.findViewById(R.id.news_post_text)
        textPostText = root!!.findViewById(R.id.text_post_text)

        emptyImage = root!!.findViewById(R.id.empty_image_view)

        weatherHelper = WeatherHelper(root, fragment!!.context)
    }

    private fun showWeatherForecast(weatherHelper: WeatherHelper): Boolean = with(weatherHelper) {
        if (!isShowWeatherState) {
            return false
        }

        if (weatherList == null || weatherList!!.isEmpty()) {
            presenter!!.loadWeather()
            return false
        }

        if (isUpToDate(weatherList!![0].time)) {
            presenter!!.loadWeather()
            return false
        }

        weatherHelper.showMainWeather()
        weatherHelper.showMainTemperature()
        weatherHelper.showFutureWeather()

        hideAll()
        weatherPostView!!.visibility = View.VISIBLE

        return true
    }

    private fun showSchedule(schedule: Schedule): Boolean {

        if (!showScheduleState) {
            return false
        }

        showScheduleView(schedule, root!!)

        hideAll()
        schedulePostView!!.visibility = View.VISIBLE

        return true
    }

    override fun setWeather(weatherList: List<MyWeather>?) = with(weatherHelper) {
        this.weatherList = weatherList
    }

    override fun setWeatherShowingState(showWeather: Boolean) {
        weatherHelper.isShowWeatherState = showWeather
        Log.i(TAG, (if (showWeather) "add" else "remove") + " weather forecast")
    }

    override fun setSchedule(scheduleList: List<Schedule>?) {
        this.scheduleList = scheduleList
    }

    override fun setScheduleShowingState(showSchedule: Boolean) {
        this.showScheduleState = showSchedule
        Log.i(TAG, (if (showSchedule) "add" else "remove") + " scheduler list")
    }

    override fun setADShowingState(showAD: Boolean) {
        this.showADState = showAD
        if (showAD) {
            addAD()
        } else {
            removeAD()
        }
    }

    private fun removeAD() {
        posts = getActivePostsWithoutAD(allPosts)
        Log.i(TAG, "remove AD posts")
    }

    private fun addAD() {
        posts = getActivePosts(allPosts)
        Log.i(TAG, "add AD posts")
    }

    override fun setPresenter(presenter: ContentContract.IContentPresenter) {
        this.presenter = presenter
    }

    private fun showADPost(post: Post) {
        ImageManager.loadInto(root!!.context, post.imagePath, adPostImage, null)

        if (post.text.isEmpty()) {
            adPostText!!.visibility = View.GONE
        } else {
            adPostText!!.visibility = View.VISIBLE
            adPostText!!.text = post.text
        }

        hideAll()
        adPostView!!.visibility = View.VISIBLE
    }

    private fun showNewsPost(post: Post) {
        ImageManager.loadInto(root!!.context, post.imagePath, newsPostImage, null)
        newsPostText!!.text = post.text

        hideAll()
        newsPostView!!.visibility = View.VISIBLE
    }


    private fun showTextPost(post: Post) {
        textPostText!!.text = post.text

        hideAll()
        textPostView!!.visibility = View.VISIBLE
    }


    private fun showImagePost(post: Post) {
        ImageManager.loadInto(root!!.context, post.imagePath, imagePostImage, null)

        hideAll()
        imagePostView!!.visibility = View.VISIBLE
    }

    private fun hideAll() {
        adPostView!!.visibility = View.GONE
        newsPostView!!.visibility = View.GONE
        imagePostView!!.visibility = View.GONE
        textPostView!!.visibility = View.GONE
        weatherPostView!!.visibility = View.GONE
        schedulePostView!!.visibility = View.GONE
    }

    override fun isActive(): Boolean {
        return fragment!!.isAdded
    }

    override fun setPosts(posts: List<Post>?) {
        this.allPosts = posts
        if (posts != null) {
            setADShowingState(showADState)

            if (postIndex == LOAD || postIndex >= posts.size) {
                postIndex = 0
            }
        }
    }

    private fun showPost(post: Post) {
        when (post.type) {
            NEWS -> showNewsPost(post)
            AD -> showADPost(post)
            IMAGE -> showImagePost(post)
            TEXT -> showTextPost(post)
        }
    }

    override fun startDisplayPosts() {
        Log.i(TAG, "start display posts")
        initDisplayPostThread()
        displayPostThread!!.start()
    }

    override fun stopDisplayPosts() {
        Log.i(TAG, "stop display posts")
        if (isActive) {
            displayPostThread!!.interrupt()
        }
    }

    private fun initDisplayPostThread() {
        displayPostThread = object : Thread() {
            override fun run() {
                try {
                    while (!isInterrupted) {
                        if (isActive) {
                            mainDisplaying()
                        } else {
                            interrupt()
                        }
                    }
                } catch (e: InterruptedException) {

                }

            }
        }
    }

    private fun mainDisplaying() {
        displayPosts(posts!!)
        displayWeather(weatherHelper)
        displaySchedule(scheduleList)
    }

    private fun runOnUiThread(runnable: () -> Unit) {
        Objects.requireNonNull<FragmentActivity>(fragment!!.activity).runOnUiThread { runnable() }
    }

    private fun displayPosts(posts: List<Post>) {
        posts.map {
            runOnUiThread { showPost(it) }
            sleep(it.duration * 1000)
        }
    }

    private fun displayWeather(weatherHelper: WeatherHelper) {
        if (weatherHelper.isShowWeatherState) {
            runOnUiThread { showWeatherForecast(weatherHelper) }
            sleep(DEFAULT_DURATION)
        }
    }

    private fun displaySchedule(scheduleList: List<Schedule>?) {
        if (showScheduleState && scheduleList != null) {
            scheduleList.map {
                runOnUiThread { showSchedule(it) }
                sleep(DEFAULT_DURATION)
            }
        }
    }

    companion object {
        private val TAG = ContentPresenter::class.java.name
        private const val LOAD = -1
        private const val DEFAULT_DURATION = (10 * 1000).toLong()
        @Volatile
        private var posts: List<Post>? = null
    }
}
