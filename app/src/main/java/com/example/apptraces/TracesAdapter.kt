package com.example.apptraces

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class TracesAdapter(private val tracesList: List<Traces>) : RecyclerView.Adapter<TracesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):TracesAdapter.ViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: TracesAdapter.ViewHolder, position: Int) {
        val name = "${tracesList[position].trace}"
        val image = "${tracesList[position].photo}".toInt()
        holder.itemText.text = name
        holder.image.setImageResource(image)
    }

    override fun getItemCount(): Int {
        return tracesList.size
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var image: ImageView
        var itemText: TextView

        init {
            image = view.findViewById(R.id.imageTrace)
            itemText = view.findViewById(R.id.textTrace)


            view.setOnClickListener {
                val name = tracesList[position].trace
                val stage = tracesList[position].stages
                val description = tracesList[position].description
                val timeTrace = tracesList[position].time
                val photoTrace = tracesList[position].photo
                val type = tracesList[position].type

                val width = view.resources.configuration.smallestScreenWidthDp
                val manager = (view.context as FragmentActivity).supportFragmentManager
                val fragmentTransaction = manager.beginTransaction()

                if (width < 720)
                    fragmentTransaction.replace(
                        R.id.container,
                        DetailsTraces.newInstance(name, stage, description, timeTrace,photoTrace,type)
                    )
                else
                    fragmentTransaction.replace(
                        R.id.container2,
                        DetailsTraces.newInstance(name, stage, description, timeTrace,photoTrace,type)
                    )

                fragmentTransaction.commit()

            }
        }
    }
}