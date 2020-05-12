package com.example.androidtask.activities

import android.os.Bundle
import com.example.androidtask.R
import com.example.androidtask.model.usermodel.User
import com.example.androidtask.viewmodel.UserViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


open class MapActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: UserViewModel
    var user: User ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if(intent.hasExtra("userDetails")){
            user = intent.getSerializableExtra("userDetails") as User
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        if(user!!.address.geo.lat!=null&&user!!.address.geo.lng!=null){
        createMarker(user!!.address.geo.lat.toDouble(), user!!.address.geo.lng.toDouble(), user!!.address.city,user!!.address.street)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(user!!.address.geo.lat.toDouble(), user!!.address.geo.lng.toDouble()), 0.0f))

        }
    }
    private fun createMarker(latitude: Double, longitude: Double, title: String?, snippet: String?): Marker? {
        return mMap.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).anchor(0.5f, 0.5f).title(title).snippet(snippet))
    }
}