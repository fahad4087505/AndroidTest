package com.example.androidtask.base
import android.Manifest
import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.androidtask.R
import com.example.colepower.view.CustomProgressBar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.alert_dialog.view.*

open class BaseActivity : AppCompatActivity() {
    val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeActionBarColor()
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS).withListener(object :
            MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
            }

            override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
            }
        }).check()
    }
    public fun showDialog(message: String) {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle("")
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
     fun changeActionBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.BLACK
        }
    }
}