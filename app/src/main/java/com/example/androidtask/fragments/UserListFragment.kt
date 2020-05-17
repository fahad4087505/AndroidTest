package com.example.androidtask.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.adapters.UserAdapter
import com.example.androidtask.base.BaseFragment
import com.example.androidtask.model.postmodel.Post
import com.example.androidtask.model.usermodel.User
import com.example.androidtask.utils.Utils
import com.example.androidtask.viewmodel.UserViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_users.view.*
import org.json.JSONObject
import java.lang.reflect.Type

class UserListFragment : BaseFragment() {
    var manager: LinearLayoutManager? = null
    var items: MutableList<User> = mutableListOf()
    var mView : View? = null
    private lateinit var viewModel: UserViewModel

    override fun onCreateChildView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider.NewInstanceFactory().create(UserViewModel::class.java)
        mView = inflater!!.inflate(R.layout.fragment_users, parent, false)
        init()
        return mView!!
    }
    private fun init() {
        manager = LinearLayoutManager(activity!!)
        mView!!.users_recyclerview.adapter = UserAdapter(items, activity!!, this)
        mView!!.users_recyclerview.layoutManager = manager
        apiCall()
        mView!!.swipeToRefresh?.setOnRefreshListener {
            apiCall()
        }
    }
    private fun apiCall(){
        if (Utils.checkConnection(activity)) {
            progressBar.show(activity!!)
            getUserApiResponse()
        } else {
            showDialog("No Internet Connection")
            recyclerViewVisibility(false)
            mView!!.swipeToRefresh.isRefreshing=false
        }
    }
    private fun getUserApiResponse() {
        viewModel.getUserDetails.observe(activity!!, Observer { userResponse ->
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
                    mView!!.users_recyclerview.adapter!!.notifyDataSetChanged()
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
            mView!!.swipeToRefresh.isRefreshing=false
            clearObserver()
        })
    }

    private fun recyclerViewVisibility(visibility: Boolean) {
        if (visibility) {
            mView!!.users_recyclerview.visibility = View.VISIBLE
            mView!!.no_result_textview.visibility = View.GONE
        } else {
            mView!!.users_recyclerview.visibility = View.GONE
            mView!!.no_result_textview.visibility = View.VISIBLE
        }
    }
    private fun clearObserver(){
        if (viewModel != null && viewModel.getUserDetails.hasObservers()) {
            viewModel.getUserDetails.removeObservers(this);
        }
    }
}