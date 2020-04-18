package com.cellstudio.cellvideo.presentation.screens.alertdialogs

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Animatable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.cellstudio.cellvideo.R


object DialogUtils {

    fun showPositiveButtonDialog(
        context: Context,
        title: String,
        message: String,
        positiveText: String,
        positiveClickListener: DialogInterface.OnClickListener
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText, positiveClickListener)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    fun showLoadingDialog(context: Context): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context,R.style.AppThemeLoadingAlertDialog)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View =
            inflater.inflate(R.layout.fragment_loading, null)
        val loading = view.findViewById<ImageView>(R.id.ivLoadingLoading)
        (loading?.drawable as Animatable?)?.start()
        builder.setView(view)
        val dialog =builder.create()
        return dialog
    }
}