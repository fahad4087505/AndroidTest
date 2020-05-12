package com.example.androidtask.activities

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.adapters.UserAdapter
import com.example.androidtask.model.usermodel.User
import com.example.androidtask.utils.Utils
import com.example.androidtask.viewmodel.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_users.*
import org.json.JSONObject
import java.lang.reflect.Type

class UserListActivity : BaseActivity() {
    var isScrolling: Boolean? = false
    var currentItems: Int = 0
    var totalItems: Int = 0
    var scrollOutItems: Int = 0
    var manager: LinearLayoutManager? = null
    var count: Int = 6
    var items: MutableList<User> = mutableListOf()
    private lateinit var viewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(UserViewModel::class.java)
        setContentView(R.layout.activity_users)
        init()
    }

    private fun init() {
        manager = LinearLayoutManager(this)
        users_recyclerview.adapter = UserAdapter(items, this, this)
        users_recyclerview.layoutManager = manager
        apiCall()
        swipeToRefresh?.setOnRefreshListener {
            apiCall()
        }
        pagination()
        Dexter.withActivity(this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE).withListener(object :
            MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
            }

            override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
            }
        }).check()
    }
    private fun apiCall(){
        if (Utils.checkConnection(this)) {
            progressBar.show(this)
            getUserApiResponse()
        } else {
            showDialog("No Internet Connection")
            recyclerViewVisibility(false)
            swipeToRefresh.isRefreshing=false
        }
    }

    private fun pagination() {
        users_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = manager!!.childCount
                totalItems = manager!!.itemCount
                scrollOutItems = manager!!.findFirstVisibleItemPosition()
                if (isScrolling == true && currentItems + scrollOutItems == totalItems && dy > 0) {
                    isScrolling = false
                    count += 1
                }
            }
        })
    }

    private fun getUserApiResponse() {
        viewModel.getUserDetails.observe(this, Observer { userResponse ->
            progressBar.dialog.dismiss()
            if (userResponse != null) {
                val gson = Gson()
                val json = gson.toJson(userResponse)
                val jsonResponse = JSONObject(json)
                items.clear()
                if(jsonResponse.has("body")) {
                    val body = jsonResponse.getJSONArray("body")
                    val listType: Type = object : TypeToken<List<User>>() {}.type
                    val posts: List<User> = gson.fromJson(body.toString(), listType)
                    items.addAll(posts)
                    users_recyclerview.adapter!!.notifyDataSetChanged()
                }
                if (items.size > 0) {
                    recyclerViewVisibility(true)
                } else {
                    recyclerViewVisibility(false)
                }
            }
            else{
                recyclerViewVisibility(false)
            }
            swipeToRefresh.isRefreshing=false
        })
    }

    private fun recyclerViewVisibility(visibility: Boolean) {
        if (visibility) {
            users_recyclerview.visibility = View.VISIBLE
            no_result_textview.visibility = View.GONE
        } else {
            users_recyclerview.visibility = View.GONE
            no_result_textview.visibility = View.VISIBLE
        }
    }
}