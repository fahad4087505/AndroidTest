package com.example.androidtask.network
import androidx.lifecycle.LiveData
import com.example.androidtask.model.usermodel.User
import retrofit2.http.GET

interface RetrofitServices {
    @GET("users")
    fun getUserDetails(): LiveData<ApiResponse<List<User>>>
  }