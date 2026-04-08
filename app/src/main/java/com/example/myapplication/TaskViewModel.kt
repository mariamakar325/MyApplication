package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _tasks = MutableLiveData<MutableList<Task>>(mutableListOf())
    val tasks: LiveData<MutableList<Task>> = _tasks

    private var nextId = 1

    // Добавление задачи
    fun addTask(title: String) {
        val currentList = _tasks.value ?: mutableListOf()
        val newTask = Task(
            id = nextId++,
            title = title,
            isCompleted = false
        )
        currentList.add(newTask)
        _tasks.value = currentList
    }

    // Переключение статуса задачи
    fun toggleTask(id: Int) {
        val currentList = _tasks.value ?: mutableListOf()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            val task = currentList[index]
            currentList[index] = task.copy(isCompleted = !task.isCompleted)
            _tasks.value = currentList
        }
    }

    // Удаление задачи
    fun deleteTask(id: Int) {
        val currentList = _tasks.value ?: mutableListOf()
        currentList.removeAll { it.id == id }
        _tasks.value = currentList
    }
}