package com.example.myapplication

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class UserDetailDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_USER_NAME = "user_name"
        private const val ARG_USER_IMAGE = "user_image"

        fun newInstance(userName: String, userImageResId: Int): UserDetailDialogFragment {
            return UserDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USER_NAME, userName)
                    putInt(ARG_USER_IMAGE, userImageResId)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val userName = arguments?.getString(ARG_USER_NAME) ?: "Неизвестно"
        val userImageResId = arguments?.getInt(ARG_USER_IMAGE) ?: 0

        val builder = AlertDialog.Builder(requireContext())
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_user_detail, null)

        val ivDialogImage: ImageView = view.findViewById(R.id.ivDialogUserImage)
        val tvDialogName: TextView = view.findViewById(R.id.tvDialogUserName)
        val btnClose: Button = view.findViewById(R.id.btnDialogClose)

        tvDialogName.text = userName
        ivDialogImage.setImageResource(userImageResId)

        btnClose.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        builder.setTitle("Информация о пользователе")

        return builder.create()
    }
}