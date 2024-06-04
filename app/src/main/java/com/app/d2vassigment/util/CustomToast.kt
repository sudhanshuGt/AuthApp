package com.app.d2vassigment.util


import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.app.d2vassigment.R


class CustomToast(private val activity: Activity) {
     fun showToast(message: String?) {
        if (activity != null) {
            try {
                val toast = Toast(activity)
                val view: View = LayoutInflater.from(activity).inflate(R.layout.toast_view, null)
                val textView = view.findViewById<View>(R.id.toastMessage) as TextView
                textView.text = message
                toast.setView(view)
                toast.setGravity(Gravity.BOTTOM, 0, 500)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}