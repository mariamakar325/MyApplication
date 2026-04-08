package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private val _userList = MutableLiveData<MutableList<String>>(
        mutableListOf("Анна", "Борис", "Виктор", "Галина", "Дмитрий")
    )

    val userList: LiveData<MutableList<String>> = _userList

    fun addUser(userName: String) {
        val currentList = _userList.value ?: mutableListOf()
        currentList.add(userName)
        _userList.value = currentList
    }

    fun deleteUser(position: Int) {
        val currentList = _userList.value ?: mutableListOf()
        if (position in currentList.indices) {
            currentList.removeAt(position)
            _userList.value = currentList
        }
    }

    fun getListSize(): Int {
        return _userList.value?.size ?: 0
    }
}