package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: List<String> = listOf()

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val ivUserImage: ImageView = itemView.findViewById(R.id.ivUserImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userName = userList[position]
        holder.tvUserName.text = userName
        holder.ivUserImage.setImageResource(android.R.drawable.ic_menu_myplaces)

        holder.itemView.setOnClickListener {
            onItemClick(userName)
        }
    }

    override fun getItemCount(): Int = userList.size

    // Метод обновления списка
    fun updateList(newList: List<String>) {
        userList = newList
        notifyDataSetChanged()
    }
}