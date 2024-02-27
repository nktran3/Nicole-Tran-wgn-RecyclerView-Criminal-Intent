package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimePoliceBinding

class CrimeHolder(private val binding: ListItemCrimeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

// created a new class for serious crime holder to support the contact police button
class SeriousCrimeHolder(private val binding: ListItemCrimePoliceBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        binding.requiresPoliceButton.setOnClickListener{
            Toast.makeText(
                binding.root.context,
                "Police contacted for ${crime.title}!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class CrimeListAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // used getItemViewType to get the view type and assign it to an int
    override fun getItemViewType(position: Int): Int {
        if (crimes[position].requiresPolice){
            return 1 // return 1 if crime requires police
        } else{
            return 0 // return 0 if crime does not require police
        }
    }

    // updated onCreateViewHolder to create the view holder for serious and normal crimes
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == 1) {
            val binding = ListItemCrimePoliceBinding.inflate(inflater, parent, false)
            return SeriousCrimeHolder(binding)
        } else{
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            return CrimeHolder(binding)
        }
    }

    // updated onBindViewHolder to support both CrimeHolder and SeriousCrimeHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]
        if (holder is SeriousCrimeHolder){
            holder.bind(crime)
        } else if (holder is CrimeHolder){
            holder.bind(crime)
        }
    }

    override fun getItemCount() = crimes.size
}
