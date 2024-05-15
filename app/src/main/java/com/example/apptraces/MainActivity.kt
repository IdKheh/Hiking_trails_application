package com.example.apptraces

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(R.layout.activity_main), GestureDetector.OnGestureListener {

    private lateinit var gestureDetector: GestureDetector
    private var x2: Float = 0.0f
    private var x1: Float = 0.0f
    private var tabId = 0

    companion object {
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            changeFragment(Information())
        }

        val home = Information()
        val listTraces1 = ListTraces.newInstance("easy")
        val listTraces2 = ListTraces.newInstance("difficult")

        changeFragment(home)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> changeFragment(home)
                    R.id.traces1 -> changeFragment(listTraces1)
                    R.id.traces2 -> changeFragment(listTraces2)
                }
                true
            }
        gestureDetector = GestureDetector(this, this)
        }

    fun changeFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val home = Information()
        val listTraces1 = ListTraces.newInstance("easy")
        val listTraces2 = ListTraces.newInstance("difficult")

        when (event?.action) {
            0 -> {
                x1 = event.x
            }
            1 -> {
                x2 = event.x

                if (kotlin.math.abs(x2 - x1) > MIN_DISTANCE) {
                    if (x2 > x1) {
                        when (tabId) {
                            0 -> {
                                changeFragment(listTraces1)
                                tabId = 1
                            }
                            1 -> {
                                changeFragment(listTraces2)
                                tabId = 2
                            }
                            2 -> {
                                changeFragment(home)
                                tabId = 0
                            }
                        }
                    } else {
                        when (tabId) {
                            0 -> {
                                changeFragment(listTraces2)
                                tabId = 2
                            }
                            1 -> {
                                changeFragment(home)
                                tabId = 0
                            }
                            2 -> {
                                changeFragment(listTraces1)
                                tabId = 1
                            }
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        TODO("Not yet implemented")
    }

    override fun onShowPress(e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        TODO("Not yet implemented")
    }

    override fun onScroll(e1: MotionEvent?,e2: MotionEvent,distanceX: Float,distanceY: Float
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLongPress(e: MotionEvent) {
        TODO("Not yet implemented")
    }

    override fun onFling(e1: MotionEvent?,e2: MotionEvent,velocityX: Float,velocityY: Float): Boolean {
        TODO("Not yet implemented")
    }
}