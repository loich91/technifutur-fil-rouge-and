package com.technipixl.filrouge

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Transformations.map
import com.technipixl.filrouge.UI.FoodAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.technipixl.filrouge.UI.ConnexionYelpImpl
import com.technipixl.filrouge.databinding.FragmentFoodBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FoodFragment : Fragment() {

    private lateinit var binding: FragmentFoodBinding
    private val conn by lazy { ConnexionYelpImpl() }
    companion object{
        const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
    private lateinit var map: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFoodBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = OnMapReadyCallback{

            map = it
            setupyelpdata()
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



    }
    fun setupyelpdata(){
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("missing","permission Manquante")
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

           val locationCoord = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        CoroutineScope(Dispatchers.IO).launch {
            val response = locationCoord?.let { conn.getSearch( it.latitude,it.longitude,20) }
            withContext(Dispatchers.Main){
                if (response != null) {
                    if (response.isSuccessful){
                        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

                        response.body()?.let {
                            binding.recyclerView.adapter = FoodAdapter(it.businesses)
                        }
                    }
                }
            }
        }
    }
}