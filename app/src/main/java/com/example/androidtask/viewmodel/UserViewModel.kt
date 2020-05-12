package com.example.androidtask.viewmodel
import androidx.lifecycle.ViewModel
import com.example.androidtask.network.RetrofitClient
import com.example.androidtask.network.RetrofitServices

class UserViewModel : ViewModel() {
    val getUserDetailService= RetrofitClient().getRetrofit().create(RetrofitServices::class.java)
    val getUserDetails = getUserDetailService.getUserDetails()
}