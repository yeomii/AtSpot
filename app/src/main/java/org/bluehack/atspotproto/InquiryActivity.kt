package org.bluehack.atspotproto

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds


class InquiryActivity : FragmentActivity(), OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,  GoogleApiClient.ConnectionCallbacks{

    private var mMap: GoogleMap? = null
    private var mGoogleApiClient : GoogleApiClient? = null
    var PLACE_PICKER_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.inquiry_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var builder = PlacePicker.IntentBuilder()

        builder.setLatLngBounds(LatLngBounds(LatLng(1.0, 1.0), LatLng(1.0, 1.0)))
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)

    }

    private fun setGoogleServiceBuilder() {

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this as GoogleApiClient.OnConnectionFailedListener/* OnConnectionFailedListener */)
                .addConnectionCallbacks(this as GoogleApiClient.ConnectionCallbacks)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build()
        mGoogleApiClient?.connect()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                var place = PlacePicker.getPlace(this, data)
                var msg = String.format("Place : %s", place.name)
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap!!.setMyLocationEnabled(true)
            mMap!!.getUiSettings().setMyLocationButtonEnabled(true)
        } else {
            // Show rationale and request permission.
        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnected(p0: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
