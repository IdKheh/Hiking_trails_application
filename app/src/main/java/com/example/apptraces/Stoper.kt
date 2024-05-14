package com.example.tracesapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import code.with.cal.timeronservicetutorial.StoperService
import com.example.apptraces.DetailsTraces
import com.example.apptraces.R
import kotlin.math.roundToInt



class Stoper : Fragment(R.layout.fragment_stoper) {

    private lateinit var timeStoper: TextView
    private lateinit var buttonStart: View
    private lateinit var buttonStop: View
    private lateinit var buttonReset: View
    private lateinit var sharedPreferences: SharedPreferences
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private lateinit var name:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_stoper, container, false)
        name = arguments?.getString("name").toString()
        timeStoper = view.findViewById(R.id.displayTime)
        buttonStart = view.findViewById(R.id.fabStart)
        buttonStop = view.findViewById(R.id.fabStop)
        buttonReset = view.findViewById(R.id.fabReset)

        buttonStart.setOnClickListener { startStoper() }
        buttonStop.setOnClickListener { stopStoper() }
        buttonReset.setOnClickListener { resetStoper() }

        serviceIntent = Intent(requireActivity().applicationContext, StoperService::class.java)
        requireActivity().registerReceiver(updateTime, IntentFilter(StoperService.TIMER_UPDATED))
        return view
    }
    private fun startStoper() {
        serviceIntent.putExtra(StoperService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        timerStarted = true
    }

    private fun stopStoper() {
        requireActivity().stopService(serviceIntent)
        timerStarted = false
    }

    private fun resetStoper() {
        if(timerStarted) {
            stopStoper()
            sharedPreferences = requireActivity().getSharedPreferences("com.example.apptraces", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("last$name", time.toString()).apply()
            val bestTime = sharedPreferences.getString("theBest$name", "")
            val stringTime = getTimeStringFromDouble(time)
            sharedPreferences.edit().putString("last$name", stringTime).apply()

            if (bestTime.isNullOrEmpty()) {
                sharedPreferences.edit().putString("theBest$name", stringTime).apply()
            } else {
                val resultInt = time.roundToInt()
                val hours = resultInt % 86400 / 3600
                val minutes = resultInt % 86400 % 3600 / 60
                val seconds = resultInt % 86400 % 3600 % 60

                val currentTime = hours * 3600 + minutes * 60 + seconds
                val table = bestTime.split(":")
                val bestSeconds = table[0].toInt() * 3600 + table[1].toInt() * 60 + table[2].toInt()

                if (currentTime < bestSeconds) {
                    sharedPreferences.edit().putString("theBest$name", getTimeStringFromDouble(time)).apply()
                }
            }
            time = 0.0
            timeStoper.text = getTimeStringFromDouble(time)
        }

    }
    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent)
        {
            time = intent.getDoubleExtra(StoperService.TIME_EXTRA, 0.0)
            timeStoper.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)
}