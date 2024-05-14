package com.example.apptraces

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment


class Information : Fragment(R.layout.fragment_information) {
    @SuppressLint("Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val iconR = view.findViewById<ImageView>(R.id.iconR)
        var darkmode = false

        val sun = view.findViewById<ImageView>(R.id.sun)
        val mountains = view.findViewById<ImageView>(R.id.mountains)
        val screenHeight = Resources.getSystem().displayMetrics.heightPixels.toFloat()
        sun.translationY = screenHeight
        mountains.translationY = screenHeight

        val animateSun = ObjectAnimator.ofFloat(sun, "translationY", 150f)
        animateSun.duration = 4350
        val animateMountains = ObjectAnimator.ofFloat(mountains, "translationY",screenHeight/8 )
        animateMountains.duration = 4050

        sun.setOnClickListener {
            Toast.makeText(context, "Jestem Słońcem oświetlającym drogę podróżującym!", Toast.LENGTH_SHORT).show()
            animateSun.cancel()
            Toast.makeText(context, "Witaj w mojej aplikacji!!", Toast.LENGTH_SHORT).show()
            sun.visibility=View.INVISIBLE
            mountains.visibility=View.INVISIBLE
        }
        mountains.setOnClickListener {
            Toast.makeText(context, "Jestem celem każdej podróży!", Toast.LENGTH_SHORT).show()
            animateMountains.cancel()
            Toast.makeText(context, "Witaj w mojej aplikacji!!", Toast.LENGTH_SHORT).show()
            sun.visibility=View.INVISIBLE
            mountains.visibility=View.INVISIBLE

        }
        animateSun.start()
        animateMountains.start()

        iconR.setOnClickListener{
            if(darkmode){
                iconR.setImageResource(R.drawable.light_mode_fill0_wght400_grad0_opsz24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkmode = false
            }
            else{
                iconR.setImageResource(R.drawable.dark_mode_fill0_wght400_grad0_opsz24)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkmode = true
            }
        }
    }
}
