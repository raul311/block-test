package com.raul311.employeedirectory.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raul311.employeedirectory.R
import com.raul311.employeedirectory.models.Employee

class DirectoryAdapter(private val employeeList: List<Employee>) : RecyclerView.Adapter<DirectoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemsViewModel = employeeList[position]

        holder.nameView.text = itemsViewModel.fullName
        holder.teamView.text = itemsViewModel.team
        holder.phoneView.text = itemsViewModel.phoneNumber
        holder.emailView.text = itemsViewModel.emailAddress

        Glide
            .with(holder.imageView)
            .load(itemsViewModel.photoUrlLarge)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = employeeList.size

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.employeeImage)
        val nameView: TextView = itemView.findViewById(R.id.name)
        val teamView: TextView = itemView.findViewById(R.id.team)
        val phoneView: TextView = itemView.findViewById(R.id.phoneNumber)
        val emailView: TextView = itemView.findViewById(R.id.email)
    }
}