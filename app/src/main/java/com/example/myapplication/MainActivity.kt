package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var btnAddUser: Button
    private lateinit var etUserName: EditText

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация элементов
        recyclerView = findViewById(R.id.recyclerView)
        fabAdd = findViewById(R.id.fabAdd)
        btnAddUser = findViewById(R.id.btnAddUser)
        etUserName = findViewById(R.id.etUserName)

        // Получение ViewModel
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Настройка RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter { userName ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        // Подписка на LiveData
        viewModel.userList.observe(this) { list ->
            adapter.updateList(list)
        }

        // FAB - быстрое добавление
        fabAdd.setOnClickListener {
            val userName = "Пользователь ${viewModel.getListSize() + 1}"
            viewModel.addUser(userName)
        }

        // Кнопка добавления с полем ввода
        btnAddUser.setOnClickListener {
            val userName = etUserName.text.toString().trim()

            if (userName.isNotEmpty()) {
                viewModel.addUser(userName)
                etUserName.text.clear()
            } else {
                Snackbar.make(recyclerView, "Введите имя!", Snackbar.LENGTH_SHORT).show()
            }
        }

        // Удаление свайпом
        setupSwipeToDelete()
    }

    private fun setupSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteUser(position)

                Snackbar.make(recyclerView, "Удалено", Snackbar.LENGTH_LONG)
                    .setAction("Отмена") { }
                    .show()
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}