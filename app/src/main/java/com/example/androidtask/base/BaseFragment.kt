package com.example.androidtask.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtask.R
import com.example.colepower.view.CustomProgressBar
import kotlinx.android.synthetic.main.alert_dialog.view.*


abstract class BaseFragment : Fragment() {
    val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanseState: Bundle?): View? {
        return onCreateChildView(inflater, parent, savedInstanseState)
    }

    abstract fun onCreateChildView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View
    public fun showDialog(message: String) {
        val mDialogView = LayoutInflater.from(activity!!).inflate(R.layout.alert_dialog, null)
        val mBuilder = AlertDialog.Builder(activity).setView(mDialogView).setTitle("")
        val mAlertDialog = mBuilder.show()
        val errorMessageTextView = mDialogView.error_message
        val dividerView = mDialogView.divider_view
        val okButton = mDialogView.okButton
        val cancelButton = mDialogView.dialogCancelBtn
        cancelButton.visibility = View.GONE
        dividerView.visibility = View.GONE
        errorMessageTextView.text = message
        okButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
        cancelButton.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}