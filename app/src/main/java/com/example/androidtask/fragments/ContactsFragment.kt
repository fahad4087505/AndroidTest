package com.example.androidtask.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.adapters.ContactAdapter
import com.example.androidtask.base.BaseFragment
import com.example.androidtask.databinding.FragmentContactsBinding
import com.example.androidtask.viewmodel.ContactViewModel
class ContactsFragment : BaseFragment() {
    private var contactViewModel: ContactViewModel? = null
    var binding: FragmentContactsBinding? =null
    private var recyclerView: RecyclerView? = null
    override fun onCreateChildView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View {
    binding = DataBindingUtil.inflate(inflater!!, R.layout.fragment_contacts, parent, false)
    contactViewModel = ContactViewModel(activity)
    recyclerView = binding!!.contactRecyclerView
    recyclerView!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    recyclerView!!.adapter = ContactAdapter(contactViewModel!!.getContacts(),activity!!)
    return binding!!.root
    }
}
