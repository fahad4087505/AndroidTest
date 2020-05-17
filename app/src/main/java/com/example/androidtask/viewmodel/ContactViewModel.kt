package com.example.androidtask.viewmodel

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import com.example.androidtask.model.contactmodel.Contact
import com.example.androidtask.repositories.ContactRepository

class ContactViewModel(context: Context?) : BaseObservable() {
    private val contacts: ObservableArrayList<Contact> = ObservableArrayList<Contact>()
    private val repository: ContactRepository = ContactRepository(context!!)
    fun getContacts(): List<Contact> {
        contacts.addAll(repository.fetchContacts())
        return contacts
    }
}