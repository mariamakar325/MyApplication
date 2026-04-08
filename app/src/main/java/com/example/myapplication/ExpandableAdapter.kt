package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpandableAdapter(
    private val categories: MutableList<CategoryItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_CATEGORY = 1
        private const val TYPE_SUBITEM = 2
    }

    // ViewHolder для категории
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCategoryIcon: ImageView = itemView.findViewById(R.id.ivCategoryIcon)
        val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)
        val ivArrow: ImageView = itemView.findViewById(R.id.ivArrow)
    }

    // ViewHolder для подэлемента
    class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSubItemName: TextView = itemView.findViewById(R.id.tvSubItemName)
    }

    override fun getItemViewType(position: Int): Int {
        var currentIndex = 0

        for (category in categories) {
            // Проверяем, является ли текущая позиция категорией
            if (currentIndex == position) {
                return TYPE_CATEGORY
            }
            currentIndex++

            // Если категория развернута, проверяем подэлементы
            if (category.isExpanded) {
                for (subItem in category.subItems) {
                    if (currentIndex == position) {
                        return TYPE_SUBITEM
                    }
                    currentIndex++
                }
            }
        }

        return TYPE_CATEGORY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CATEGORY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_category, parent, false)
                CategoryViewHolder(view)
            }
            TYPE_SUBITEM -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subitem, parent, false)
                SubItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentIndex = 0

        for (category in categories) {
            // Категория
            if (currentIndex == position) {
                if (holder is CategoryViewHolder) {
                    holder.tvCategoryName.text = category.categoryName
                    holder.ivCategoryIcon.setImageResource(category.categoryImage)

                    // Анимация стрелки
                    holder.ivArrow.rotation = if (category.isExpanded) 180f else 0f

                    // Обработчик клика для раскрытия/сворачивания
                    holder.itemView.setOnClickListener {
                        toggleCategory(category, holder)
                    }
                }
                currentIndex++
                continue
            }

            // Подэлементы
            if (category.isExpanded) {
                for (subItem in category.subItems) {
                    if (currentIndex == position) {
                        if (holder is SubItemViewHolder) {
                            holder.tvSubItemName.text = subItem
                        }
                        currentIndex++
                        break
                    }
                    currentIndex++
                }
            }
        }
    }

    override fun getItemCount(): Int {
        var count = 0
        for (category in categories) {
            count++ // Сама категория
            if (category.isExpanded) {
                count += category.subItems.size // Подэлементы
            }
        }
        return count
    }

    // Метод для раскрытия/сворачивания категории
    private fun toggleCategory(category: CategoryItem, holder: CategoryViewHolder) {
        val position = getCategoryPosition(category)

        category.isExpanded = !category.isExpanded
        holder.ivArrow.rotation = if (category.isExpanded) 180f else 0f

        if (category.isExpanded) {
            notifyItemRangeInserted(position + 1, category.subItems.size)
        } else {
            notifyItemRangeRemoved(position + 1, category.subItems.size)
        }
        notifyItemChanged(position)
    }

    // Получение позиции категории в списке
    private fun getCategoryPosition(category: CategoryItem): Int {
        var position = 0
        for (cat in categories) {
            if (cat == category) {
                return position
            }
            position++
            if (cat.isExpanded) {
                position += cat.subItems.size
            }
        }
        return position
    }
}