package com.technipixl.filrouge

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Transformations.map
import androidx.navigation.fragment.findNavController
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
import com.technipixl.filrouge.UI.ConnexionYelpImpl
import com.technipixl.filrouge.UI.FoodAdapter
import com.technipixl.filrouge.databinding.FragmentFoodBinding
import com.technipixl.filrouge.databinding.FragmentMoveBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MoveFragment : Fragment(),FoodAdapter.OnclickFoodListener {
    private val conn by lazy { ConnexionYelpImpl() }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap

    private lateinit var binding: FragmentMoveBinding
    private  var locationCallback: LocationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.lastLocation?.let {location->
                fusedLocationClient.removeLocationUpdates(this)
                centerMap(location.latitude,location.longitude)
                setupyelpdata(location.latitude,location.longitude)

            }
        }
    }
    private fun centerMap(latitude: Double, longitude: Double) {

        val fragment = childFragmentManager.findFragmentById(R.id.fragmentContainerViewMap) as SupportMapFragment?
        fragment?.getMapAsync(OnMapReadyCallback{
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude,longitude),10F))
        })
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
                Manifest.permission.ACCESS_COARSE_LOCATION),
                FoodFragment.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val request = LocationRequest.create()
        request.interval = 500
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoveBinding.inflate(layoutInflater)
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
    fun setupyelpdata(latitude: Double, longitude: Double) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = conn.getPark(latitude, longitude, 20, )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false
                    )
                    response.body()?.let {
                        Log.d("hello",it.businesses.toString())
                        binding.recyclerView.adapter = FoodAdapter(it.businesses, this@MoveFragment)
                        addMarquer(it.businesses)
                    }
                }
            }
        }
    }

    fun addMarquer(response: List<Businesse>) {
        val boundBuild = LatLngBounds.builder()
        response.forEach { dataBusiness ->
            val lat = dataBusiness.coordinates.latitude
            val long = dataBusiness.coordinates.longitude
            val title = dataBusiness.name

            val coord = LatLng(lat, long)
            addMarker(lat, long, title)
            boundBuild.include(coord)
        }
    }

    private fun addMarker(latitude: Double, longitude: Double, title: String) {
        val markerOptions = MarkerOptions().position(LatLng(latitude, longitude))
            .title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_pin_pizza))
        map.addMarker(markerOptions)
    }

    override fun onclickFoodListener(businesse: Businesse) {
        val action = MoveFragmentDirections.actionMoveFragmentToDetailFoodFragment(businesse)
        findNavController().navigate(action)
    }
}