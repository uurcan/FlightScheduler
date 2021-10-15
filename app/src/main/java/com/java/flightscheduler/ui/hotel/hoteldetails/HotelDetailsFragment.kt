package com.java.flightscheduler.ui.hotel.hoteldetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.REQUEST_CODE_CALL_PERMISSION
import com.java.flightscheduler.databinding.FragmentHotelDetailBinding
import com.java.flightscheduler.utils.PermissionUtility
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


@AndroidEntryPoint
class HotelDetailsFragment : Fragment(),OnMapReadyCallback,EasyPermissions.PermissionCallbacks,View.OnClickListener {
    private val args by navArgs<HotelDetailsFragmentArgs>()
    private val hotelDetailsViewModel : HotelDetailsViewModel by viewModels()
    private lateinit var binding : FragmentHotelDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hotel_details,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        initializeVariables()
        initializeMap()
    }

    private fun initializeViews() {
        binding.layoutHotelDetailContact.setOnClickListener(this)
    }

    private fun initializeVariables() {
        binding.setVariable(BR.hotelDetail, args.hotel)
    }

    private fun initializeMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.layout_hotel_detail_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        hotelDetailsViewModel.getCoordinatesLiveData(
            args.hotel.hotel!!.latitude, args.hotel.hotel!!.longitude
        )?.observe(viewLifecycleOwner, { coordinates ->
            val location = LatLng(coordinates.first, coordinates.second)
            map.addMarker(MarkerOptions().position(location))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15F))
        })
    }

    private fun requestPermissions() {
        if (PermissionUtility.hasPhoneDialPermission(requireContext())) {
            startDialActivity()
        }
        EasyPermissions.requestPermissions(
            this,
            getString(R.string.text_permission_required),
            REQUEST_CODE_CALL_PERMISSION,
            android.Manifest.permission.CALL_PHONE
        )
    }

    private fun startDialActivity() {
        val number = args.hotel.hotel?.contact?.phone.toString()
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            binding.layoutHotelDetailContact.id -> requestPermissions()
        }
    }
}