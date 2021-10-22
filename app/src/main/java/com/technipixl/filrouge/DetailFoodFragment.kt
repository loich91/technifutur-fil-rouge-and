package com.technipixl.filrouge

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.technipixl.filrouge.DBFood.Database.DatabaseFood
import com.technipixl.filrouge.DBFood.model.BusineseDb
import com.technipixl.filrouge.Model.Businesse
import com.technipixl.filrouge.databinding.FragmentDetailFoodBinding
import kotlinx.coroutines.*
import kotlin.math.roundToInt


class DetailFoodFragment : Fragment() {
    private lateinit var binding: FragmentDetailFoodBinding
    private val args : DetailFoodFragmentArgs by navArgs()
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  var locationCallback: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.lastLocation?.let {location->
                fusedLocationClient.removeLocationUpdates(this)
                addMarquer(args.business.coordinates.latitude,args.business.coordinates.longitude,args.business.name)

            }
        }
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
        request.interval = 1000
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
    ): View {
       binding = FragmentDetailFoodBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameFoodRestau.text = args.business.name
        args.business.categories.forEach {
            binding.typeDeRestaure.text = it.title
        }
        binding.ratingbar.numStars = 5
        binding.ratingbar.rating = args.business.rating.toFloat()
        binding.addressTextDetail.text = args.business.location.address1
        binding.villeZip.text = args.business.location.city + " " +args.business.location.state+" "+ args.business.location.zip_code
        val fragment = childFragmentManager.findFragmentById(R.id.fragmentContainerViewMapDetail) as SupportMapFragment?
        fragment?.getMapAsync(OnMapReadyCallback{
            map = it
            tacklocation()
        })
        binding.addFavoriteType.setOnClickListener {

            val result = BusinesseMapper().transformToBusineseDb(args.business)
           recuplistDb(result)



        }
    }
    fun addMarquer(latitude: Double,longitude: Double,title: String){
        val boundBuild = LatLngBounds.builder()
        val coord = LatLng(latitude,longitude)
            addMarker(latitude,longitude,title)
            boundBuild.include(coord)

        val width = getResources().getDisplayMetrics().widthPixels
        val height = getResources().getDisplayMetrics().heightPixels
        val  padding =  (width * 0.10) .toInt()
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(boundBuild.build(),width, height, padding))
    }
    private fun addMarker(latitude: Double, longitude: Double, title: String) {
        val markerOptions = MarkerOptions().position(LatLng(latitude, longitude))
            .title(title)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_pin_pizza))
        map.addMarker(markerOptions)
    }
    @SuppressLint("SetTextI18n")
    private fun addFavorite(args: DetailFoodFragmentArgs) {
        binding.ratingbar.rating = args.business.rating.toFloat()
        binding.addressTextDetail.text = args.business.location.address1
        binding.villeZip.text = args.business.location.city + " " +args.business.location.state+" "+ args.business.location.zip_code

    }
    private fun recuplistDb(busineseDb: BusineseDb) {
        CoroutineScope(Dispatchers.IO).launch {
            val resultDb =  DatabaseFood.getDb(requireContext()).foodDao().getFavoriteFoodBusi()

            withContext(Dispatchers.Main){
                var reponse :Boolean = false
                resultDb.forEach {
                    reponse = it.id == busineseDb.id
                }
                if (!reponse){
                    withContext(Dispatchers.IO){
                        DatabaseFood.getDb(requireContext()).foodDao().insertDataFood(busineseDb)
                    }
                }
                else {
                    withContext(Dispatchers.IO){
                        DatabaseFood.getDb(requireContext()).foodDao().deleteById(busineseDb.id)
                    }
                }

            }

        }

    }

}