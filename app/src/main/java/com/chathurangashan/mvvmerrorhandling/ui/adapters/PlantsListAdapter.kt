package com.chathurangashan.mvvmerrorhandling.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chathurangashan.mvvmerrorhandling.data.general.Plant
import com.chathurangashan.mvvmerrorhandling.databinding.PlantListTemplateBinding
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class PlantsListAdapter (
    var data: List<Plant>,
    private val onSelectCountry: (Int) -> Unit
): RecyclerView.Adapter<PlantsListAdapter.ViewHolder>() {

    private lateinit var viewBinding:PlantListTemplateBinding
    var decimalFormatFormat = DecimalFormat("#.00")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        viewBinding = PlantListTemplateBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val plant = data[position]
        holder.bind(plant)

    }

    override fun getItemCount(): Int {
      return data.size
    }

    inner class ViewHolder(val view: PlantListTemplateBinding) : RecyclerView.ViewHolder(view.root) {

        init {

            view.root.setOnClickListener {
                onSelectCountry(adapterPosition)
            }
        }

        fun bind(item: Plant) = with(view) {

            name.text = item.name
            price.text = "$ ${decimalFormatFormat.format(item.price)}"
            Picasso.get().load(item.image).into(plantImage)
        }
    }

}