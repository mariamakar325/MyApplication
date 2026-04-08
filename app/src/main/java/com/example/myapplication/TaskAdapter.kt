package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onTaskClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList: List<Task> = listOf()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cbTask: CheckBox = itemView.findViewById(R.id.cbTask)
        val tvTaskTitle: TextView = itemView.findViewById(R.id.tvTaskTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.tvTaskTitle.text = task.title
        holder.cbTask.isChecked = task.isCompleted

        // Зачеркивание выполненного
        holder.tvTaskTitle.paint.isStrikeThruText = task.isCompleted

        holder.cbTask.setOnCheckedChangeListener { _, isChecked ->
            onTaskClick(task.copy(isCompleted = isChecked))
        }
    }

    override fun getItemCount(): Int = taskList.size

    fun updateList(newList: List<Task>) {
        taskList = newList
        notifyDataSetChanged()
    }
}