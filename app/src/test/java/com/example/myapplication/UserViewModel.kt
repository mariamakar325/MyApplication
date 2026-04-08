package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    // Приватный изменяемый LiveData (доступен только внутри ViewModel)
    private val _userList = MutableLiveData<MutableList<String>>(
        mutableListOf("Анна", "Борис", "Виктор", "Галина", "Дмитрий")
    )

    // Публичный неизменяемый LiveData (доступен для наблюдения из Activity)
    val userList: LiveData<MutableList<String>> = _userList

    // Метод для добавления пользователя
    fun addUser(userName: String) {
        val currentList = _userList.value ?: mutableListOf()
        currentList.add(userName)
        _userList.value = currentList
    }

    // Метод для удаления пользователя по позиции
    fun deleteUser(position: Int) {
        val currentList = _userList.value ?: mutableListOf()
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _userList.value = currentList
        }
    }

    // Метод для получения размера списка
    fun getListSize(): Int {
        return _userList.value?.size ?: 0
    }
}