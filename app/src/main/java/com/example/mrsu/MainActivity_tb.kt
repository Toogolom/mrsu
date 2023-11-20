package com.example.mrsu

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.example.TimeTableToDate
import com.example.mrsu.databinding.ActivityMainLkBinding
import com.example.mrsu.databinding.ActivityMainTbBinding
import com.example.mrsu.retrofit.MainApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity_tb : AppCompatActivity() {
    lateinit var binding:ActivityMainTbBinding
    private val adapter = TB_Adapter()
    var date = "2023-11-14"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_tb)
        binding = ActivityMainTbBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra("token").toString()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://papi.mrsu.ru")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
        val mainApi = retrofit.create(MainApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val timetable = mainApi.getTimeTable(date, token)
            runOnUiThread {
                binding.facInfo.text = timetable.get(0).FacultyName + "(${timetable.get(0).Group})";
                binding.apply {
                    RWCalendar.layoutManager = LinearLayoutManager(this@MainActivity_tb)
                    RWCalendar.adapter = adapter
                    adapter.addTB(timetable.get(0))
                }
            }
        }
        binding.Calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            val dateFormat = "yyyy-MM-dd"
            val sdf = java.text.SimpleDateFormat(dateFormat, Locale.getDefault())

            val newDate = sdf.format(selectedDate.time)

            CoroutineScope(Dispatchers.IO).launch {
                val newTimetable = mainApi.getTimeTable(newDate, token)
                runOnUiThread {
                   updateAdapter(newTimetable.get(0)) // Обновляем адаптер с новыми данными
                }

            }
            binding.Calendar.visibility = View.GONE
            var isCalendarViewVisible = false
            binding.buttCalendar.setOnClickListener {
                if (isCalendarViewVisible) {
                    binding.Calendar.visibility = View.GONE
                    isCalendarViewVisible = false
                    // binding.RWCalendar.visibility = View.VISIBLE
                } else {
                    binding.Calendar.visibility = View.VISIBLE
                    isCalendarViewVisible = true
                    // binding.RWCalendar.visibility = View.GONE
                }
            }


            binding.bNav.setOnNavigationItemSelectedListener() {
                when (it.itemId) {
                    R.id.lk -> {
                        val intent = Intent(this, MainActivity_lk::class.java)
                        startActivity(intent)
                    }
                }
                true
            }
        }
    }
    private fun updateAdapter(newData: TimeTableToDate) { // Замените YourTimetableDataType на тип данных вашего расписания
        binding.facInfo.text = newData.FacultyName + "(${newData.Group})"
        binding.apply {
            RWCalendar.layoutManager = LinearLayoutManager(this@MainActivity_tb)
            RWCalendar.adapter = adapter // Устанавливаем обновленные данные в адаптер
            adapter.addTB(newData)
        }
    }
}


