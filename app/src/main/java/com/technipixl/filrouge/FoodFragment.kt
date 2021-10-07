package com.technipixl.filrouge

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.technipixl.filrouge.UI.FoodAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.technipixl.filrouge.Model.Businesse
import com.technipixl.filrouge.Model.DataYelp
import com.technipixl.filrouge.UI.ConnexionYelpImpl
import com.technipixl.filrouge.databinding.FragmentFoodBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.security.auth.callback.Callback


class FoodFragment : Fragment() {

    private lateinit var binding: FragmentFoodBinding
    private val conn by lazy { ConnexionYelpImpl() }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map:GoogleMap

    private  var locationCallback: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.lastLocation?.let {
                fusedLocationClient.removeLocationUpdates(this)
                centerMap(it.latitude,it.longitude)
                setupyelpdata(it.latitude,it.longitude)
            }
        }
    }


    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }

    private fun tacklocation(){
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED  && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        val request = LocationRequest.create()
        request.interval = 1000
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

    }

 fun  onPermissionGranted(){
     tacklocation()
 }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentContainerViewMap) as SupportMapFragment?
        fragment?.getMapAsync(OnMapReadyCallback{
            map = it
            tacklocation()
        })

    }

    private fun centerMap(latitude: Double, longitude: Double) {

        val fragment = childFragmentManager.findFragmentById(R.id.fragmentContainerViewMap) as SupportMapFragment?
        fragment?.getMapAsync(OnMapReadyCallback{
            it.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)))
        })
    }
    fun addMarquer(response:List<Businesse>){
        val boundBuild = LatLngBounds.builder()
        response.forEach { dataBusiness->
            var lat = dataBusiness.coordinates.latitude
            var long = dataBusiness.coordinates.longitude
            var title = dataBusiness.name

            var coord = LatLng(lat,long)
            addMarker(lat,long,title)
            boundBuild.include(coord)
        }
       val width = getResources().getDisplayMetrics().widthPixels
        val height = getResources().getDisplayMetrics().heightPixels
       val  padding =  (width * 0.20) .toInt()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundBuild.build(),width, height, padding))
    }
    private fun addMarker(latitude: Double, longitude: Double, title: String) {
        val markerOptions = MarkerOptions().position(LatLng(latitude, longitude))
            .title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_pin_pizza))
        map.addMarker(markerOptions)
    }

    fun setupyelpdata(latitude:Double,longitude:Double){

        CoroutineScope(Dispatchers.IO).launch {
            val response = conn.getSearch(latitude,longitude,20)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

                    response.body()?.let {
                        binding.recyclerView.adapter = FoodAdapter(it.businesses)
                        addMarquer(it.businesses)
                    }
                }
            }
        }
    }
}