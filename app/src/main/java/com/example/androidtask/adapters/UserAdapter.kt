package com.example.androidtask.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.activities.MapActivity
import com.example.androidtask.activities.UserListActivity
import com.example.androidtask.model.usermodel.User
import kotlinx.android.synthetic.main.cell_user_list.view.*
class UserAdapter(val items: List<User>, val context: Context, val listener: UserListActivity) : RecyclerView.Adapter<UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_user_list, parent, false))
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        try {
            holder.nameTextView.text=items[position].name
            holder.emailTextView.text=items[position].email
            holder.phoneTextView.text=items[position].phone.toString()
            holder.cellLayout.setOnClickListener {
                context.startActivity(Intent(context,MapActivity::class.java).putExtra("userDetails",items[position]))
            }
        } catch (ignored: Exception) {
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }
}

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameTextView:TextView=view.name_textview
    val emailTextView:TextView=view.email_textview
    val phoneTextView:TextView=view.phone_textview
    val cellLayout:LinearLayout=view.cell_layout
}