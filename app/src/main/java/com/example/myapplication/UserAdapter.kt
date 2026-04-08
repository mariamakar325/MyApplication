package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val userList: MutableList<UserItem>,
    private val activity: FragmentActivity  // Добавляем ссылку на Activity
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {

    private var filteredList = userList.toList()

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
        val user = filteredList[position]
        holder.tvUserName.text = user.name
        holder.ivUserImage.setImageResource(user.imageResId)

        // === ЗАДАНИЕ 9: Обработчик клика для открытия DialogFragment ===
        holder.itemView.setOnClickListener {
            val dialog = UserDetailDialogFragment.newInstance(
                userName = user.name,
                userImageResId = user.imageResId
            )
            dialog.show(activity.supportFragmentManager, "UserDetailDialog")
        }
    }

    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""

                val filtered = if (query.isEmpty()) {
                    userList.toList()
                } else {
                    userList.filter {
                        it.name.lowercase().contains(query)
                    }
                }

                return FilterResults().apply {
                    values = filtered
                }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? List<UserItem> ?: userList.toList()
                notifyDataSetChanged()
            }
        }
    }
}