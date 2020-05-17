package com.example.androidtask.repositories
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import com.example.androidtask.model.contactmodel.Contact
import java.util.*
class ContactRepository(private val context: Context) {
    private val contacts: MutableList<Contact>
    fun fetchContacts(): List<Contact> {
        val contact = Contact()
        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        if (cursor?.count ?: 0 > 0) {
            while (cursor!!.moveToNext()) {
                val name =   cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNo =  cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                contact.name = name
                contact.phoneNumber = phoneNo
                contact.photoUri = photoUri
                contacts.add(contact)
            }
        }
        cursor?.close()
        return contacts
    }

    init {
        contacts = ArrayList<Contact>()
    }
}
