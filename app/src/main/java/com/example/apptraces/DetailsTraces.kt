package com.example.apptraces


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.tracesapp.Stoper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsTraces : Fragment(R.layout.fragment_details) {
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        fun newInstance(name: String,stage: String,description: String,time: String,photo: Int,difficulty: String): Fragment {
            val fragment = DetailsTraces()
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("stage", stage)
            bundle.putString("description", description)
            bundle.putString("time", time)
            bundle.putInt("photo", photo)
            bundle.putString("difficulty", difficulty)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name")
        val stage = arguments?.getString("stage")
        val description = arguments?.getString("description")
        val timeExcepted = arguments?.getString("time")
        val photo = arguments?.getInt("photo")
        val difficulty = arguments?.getString("difficulty")

        val width = view.resources.configuration.smallestScreenWidthDp
        val iconL = view.findViewById<ImageView>(R.id.iconL)
        if (width >= 720) {
            iconL.visibility = View.GONE
        }
        iconL.setOnClickListener{
            if (width < 720) {
                val manager = (view.context as FragmentActivity).supportFragmentManager
                val fragmentTransaction = manager.beginTransaction()
                fragmentTransaction.replace(R.id.container, ListTraces.newInstance(difficulty.toString()))
                fragmentTransaction.commit()
            }
        }

        if (photo != null) {
            view.findViewById<ImageView>(R.id.imageTraces).setImageResource(photo)
        }
        view.findViewById<TextView>(R.id.place).text = name
        view.findViewById<TextView>(R.id.place).text = name
        view.findViewById<TextView>(R.id.stages).text = stage
        view.findViewById<TextView>(R.id.description).text = description
        view.findViewById<TextView>(R.id.time).text = "Przewidywany czas: "+timeExcepted

        view.findViewById<RadioButton>(R.id.fast).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val newTime = calculateTime(timeExcepted.toString(), 1.0 / 1.5)
                view.findViewById<TextView>(R.id.time).text = "Przewidywany czas: $newTime"
            }
        }
        view.findViewById<RadioButton>(R.id.normal).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.findViewById<TextView>(R.id.time).text = "Przewidywany czas: $timeExcepted"
            }
        }

        view.findViewById<RadioButton>(R.id.slow).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val newTime = calculateTime(timeExcepted.toString(),  1.5)
                view.findViewById<TextView>(R.id.time).text = "Przewidywany czas: $newTime"
            }
        }


        sharedPreferences = requireActivity().getSharedPreferences("com.example.apptraces",Context.MODE_PRIVATE)
        val theBest = sharedPreferences.getString("theBest$name","")
        val last = sharedPreferences.getString("last$name","")

        view.findViewById<TextView>(R.id.theBest).text = "Najlepszy czas: "+theBest
        view.findViewById<TextView>(R.id.last).text = "Ostatni czas: "+last


        view.findViewById<FloatingActionButton>(R.id.fabStoper).setOnClickListener {
            if (name != null) {
                changeFragment(Stoper(),name)
            }
        }
        view.findViewById<FloatingActionButton>(R.id.fabCamera).setOnClickListener {
            Toast.makeText(context, "Uśmiechnij się! Zaraz zrobię ci zdjęcie!",Toast.LENGTH_SHORT).show()
        }
    }

    fun calculateTime(timeExcepted: String, multiplier: Double): String {
        val table = timeExcepted.split(":")
        val seconds = ((table[0].toInt() * 3600 + table[1].toInt() * 60 + table[2].toInt()) * multiplier).toInt()
        val hour = seconds / 3600
        val min = (seconds % 3600 / 60).toInt()
        val sec = (seconds % 60).toInt()
        return String.format("%02d:%02d:%02d", hour, min, sec)
    }

    fun changeFragment(fragment: Fragment,name: String){
        val bundle = Bundle()
        bundle.putString("name", name)
        val fragmentTransaction = parentFragmentManager.beginTransaction()
        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.container3, fragment)
        fragmentTransaction.commit()
    }

}