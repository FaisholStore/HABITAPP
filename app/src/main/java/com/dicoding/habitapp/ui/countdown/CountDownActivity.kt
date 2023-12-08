package com.dicoding.habitapp.ui.countdown

import NotificationWorker
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.databinding.ActivityCountDownBinding
import com.dicoding.habitapp.utils.HABIT
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {
    private var _binding: ActivityCountDownBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCountDownBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Count Down"

        val habit = getParcelableExtra(intent, HABIT, Habit::class.java)

        if (habit != null){
            findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

            val viewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

            //TODO 10 : Set initial time and observe current time. Update button state when countdown is finished
            viewModel.setInitialTime(habit.minutesFocus)
            viewModel.currentTimeString.observe(this){time->
                binding.tvCountDown.text = time
            }

            //TODO 13 : Start and cancel One Time Request WorkManager to notify when time is up.
            val workManager = WorkManager.getInstance(this)
            val data = Data.Builder().putString(HABIT_TITLE, habit.title).putInt(HABIT_ID, habit.id)
                .build()
            val oneTimeWorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                .setInputData(data)
                .setInitialDelay(habit.minutesFocus, TimeUnit.MINUTES)
                .build()

            findViewById<Button>(R.id.btn_start).setOnClickListener {
                workManager.enqueue(oneTimeWorkRequest)
                viewModel.startTimer()
                updateButtonState(true)
            }

            findViewById<Button>(R.id.btn_stop).setOnClickListener {
                workManager.cancelWorkById(oneTimeWorkRequest.id)
                viewModel.resetTimer()
                updateButtonState(false)
            }
        }

    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }
}