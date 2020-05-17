package com.example.androidtask.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.databinding.CellContactBinding
import com.example.androidtask.model.contactmodel.Contact
import com.example.androidtask.BR

class ContactAdapter(private val contacts: List<Contact>?, val context: Context)  : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    private var binding: CellContactBinding? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ContactViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.cell_contact, viewGroup, false)
        return ContactViewHolder(binding!!)
    }

    override fun onBindViewHolder(contactViewHolder: ContactViewHolder, i: Int) {
        contactViewHolder.onBind(contacts!![i])
    }

    override fun getItemCount(): Int {
        return contacts?.size ?: 0
    }

    inner class ContactViewHolder(itemContactBinding: CellContactBinding) :
        RecyclerView.ViewHolder(itemContactBinding.root) {
        private val binding: CellContactBinding = itemContactBinding
        fun onBind(contact: Contact?) {
            binding.setVariable(BR.contact, contact)
            binding.executePendingBindings()
        }
    }
}