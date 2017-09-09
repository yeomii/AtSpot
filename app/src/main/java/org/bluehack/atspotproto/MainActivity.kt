package org.bluehack.atspotproto

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 200

    private val REQUEST_MAP_PERMISSION = 100
    private var mMap: GoogleMap? = null
    private var mGoogleApiClient : GoogleApiClient? = null
    private var mLocationPermissionGranted = false
    private var mLastKnownLocation : Location? = null
    private var mCameraPosition : CameraPosition? = null
    private var mLocationRequest : LocationRequest? = null

    private var mInquiryList : ArrayList<View> = ArrayList<View>()

    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)
    private val DEFAULT_ZOOM : Int = 15

    private val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.main_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Do other setup activities here too, as described elsewhere in this tutorial.

        // Build the Play services client for use by the Fused Location Provider and the Places API.
        // Use the addApi() method to request the Google Places API and the Fused Location Provider.
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this as GoogleApiClient.OnConnectionFailedListener/* OnConnectionFailedListener */)
                .addConnectionCallbacks(this as GoogleApiClient.ConnectionCallbacks)
                .addApi(LocationServices.API)
                .build()
        connectClient()

        addInquiryView(null, "이태원 근처 맛집 추천해주세요", "500")
        addInquiryView(null, "강남역에 사람 얼마나 있나요?", "1000")
        addInquiryView(null, "강남역에 사람 얼마나 있나요?", "2000")
        addInquiryView(null, "강남역에 사람 얼마나 있나요?", "3000")
        addInquiryView(null, "강남역에 사람 얼마나 있나요?", "4000")
        addInquiryView(null, "강남역에 사람 얼마나 있나요?", "5000")
        addInquiryView(null, "강남역에 사람 얼마나 있나요?", "6000")

    }

    fun addInquiryView(icon : ImageView?, question : String?, reward : String?)
    {
        var inquiry_layout = findViewById(R.id.main_inquiry_layout) as LinearLayout

        var inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var cv = inflater!!.inflate(R.layout.inquiry_listview_item, inquiry_layout, false)
        cv = cv as View

        var iconView = cv.findViewById(R.id.inquiry_list_icon) as ImageView?
        var questionView = cv.findViewById(R.id.inquiry_list_question_textview) as TextView?
        var rewardView = cv.findViewById(R.id.inquiry_list_reward_textview) as TextView?

        questionView?.setText(question)
        rewardView?.setText(reward)

        inquiry_layout.addView(cv)
    }


    protected fun connectClient() {
        // Connect the client.
        if (mGoogleApiClient != null) {
            mGoogleApiClient?.connect()
        }
    }
    override fun onConnected(p0: Bundle?) {
        mLastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        getDeviceLocation()
    }

    override fun onLocationChanged(p0: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun startLocationUpdates() {
        mLocationRequest = LocationRequest()
        mLocationRequest?.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap!!.setMyLocationEnabled(true)
            mMap!!.getUiSettings().setMyLocationButtonEnabled(true)
        } else {
            // Show rationale and request permission.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_MAP_PERMISSION)
        }

    }

    private fun getDeviceLocation() {
        /*
     * Before getting the device location, you must check location
     * permission, as described earlier in the tutorial. Then:
     * Get the best and most recent location of the device, which may be
     * null in rare cases when a location is not available.
     */
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient)
        }

        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap?.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition))
        } else if (mLastKnownLocation != null) {
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(mLastKnownLocation!!.getLatitude(),
                            mLastKnownLocation!!.getLongitude()), DEFAULT_ZOOM.toFloat()))
        } else {
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM.toFloat()))
            mMap!!.getUiSettings().isMyLocationButtonEnabled = false
        }
    }

    fun onClickFloatingButton(view : View)
    {
        startActivity(Intent(this, InquiryActivity::class.java))
    }
}
