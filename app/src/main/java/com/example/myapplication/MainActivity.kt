package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpandableAdapter
    private lateinit var btnOpenDetail: Button
    private lateinit var fabAdd: FloatingActionButton

    // Список категорий с подэлементами
    private val categories = mutableListOf(
        CategoryItem(
            categoryName = "Пользователи",
            categoryImage = android.R.drawable.ic_menu_myplaces,
            subItems = listOf("Анна", "Борис", "Виктор"),
            isExpanded = false
        ),
        CategoryItem(
            categoryName = "Администраторы",
            categoryImage = android.R.drawable.ic_menu_manage,
            subItems = listOf("Галина", "Дмитрий"),
            isExpanded = false
        ),
        CategoryItem(
            categoryName = "Гости",
            categoryImage = android.R.drawable.ic_menu_gallery,
            subItems = listOf("Елена", "Жанна", "Игорь", "Константин"),
            isExpanded = false
        ),
        CategoryItem(
            categoryName = "Модераторы",
            categoryImage = android.R.drawable.ic_menu_edit,
            subItems = listOf("Людмила", "Михаил"),
            isExpanded = false
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        btnOpenDetail = findViewById(R.id.btnOpenDetail)
        fabAdd = findViewById(R.id.fabAdd)

        //ExpandableAdapte
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExpandableAdapter(categories)
        recyclerView.adapter = adapter


        setupRecyclerViewDivider()

        btnOpenDetail.setOnClickListener {
            startActivity(Intent(this, DetailActivity::class.java))
        }

        fabAdd.setOnClickListener {
            // Добавление новой категории
            val newCategory = CategoryItem(
                categoryName = "Новая категория ${categories.size + 1}",
                categoryImage = android.R.drawable.ic_menu_add,
                subItems = listOf("Подэлемент 1", "Подэлемент 2"),
                isExpanded = false
            )
            categories.add(newCategory)
            adapter.notifyItemInserted(categories.size - 1)
        }
    }

    private fun setupRecyclerViewDivider() {
        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager.VERTICAL
        )

        val dividerDrawable = ColorDrawable(Color.parseColor("#DDDDDD")).apply {
            setBounds(0, 0, 1, 2)
        }

        dividerItemDecoration.setDrawable(dividerDrawable)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }
}