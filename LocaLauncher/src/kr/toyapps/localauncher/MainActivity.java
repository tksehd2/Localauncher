package kr.toyapps.localauncher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements LocationSource, ConnectionCallbacks, OnConnectionFailedListener, OnMarkerClickListener, OnMarkerDragListener, OnMapLongClickListener
{
	private GoogleMap mMap;
	protected Button _registerBtn;
	protected Location _globalLocation;
	protected LocationClient mLocationClient;
	protected OnLocationChangedListener locListener;

	protected MarkerPopup markerPopup;

	protected Bundle _bundle;

	final float ZOOM_RATE = 19.0f;

	final String saveFileName = "sharedPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null)
		{
			MarkerOptions marker = savedInstanceState.getParcelable("marker");
			Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
		}

		setContentView(R.layout.activity_main);

		// _bundle = savedInstanceState;

		init();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);

		LatLng latLng = new LatLng(1.0, 2.0);
		MarkerOptions marker = new MarkerOptions();
		marker.position(latLng);
		marker.title("Parcelable test");
		marker.snippet("Parcelable test snippet");

		outState.putParcelable("marker", marker);

	}

	protected void init()
	{
		if (mMap == null)
		{
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			mMap.setMyLocationEnabled(true);
			mMap.setLocationSource(this);
			mMap.setOnMarkerDragListener(this);
			mMap.setOnMapLongClickListener(this);
			mMap.setOnMarkerClickListener(this);
		}
		if (mLocationClient == null)
		{
			mLocationClient = new LocationClient(this, this, this);
			mLocationClient.connect();
		}
		markerPopup = new MarkerPopup(this);
		
	}

	void ParcelableTest()
	{
		LatLng latLng = new LatLng(1.0, 2.0);
		MarkerOptions marker = new MarkerOptions();
		marker.position(latLng);
		marker.title("Parcelable test");
		marker.snippet("Parcelable test snippet");

		LaunchItem item = new LaunchItem();
		String[] launchList = { "tksehd2" , "tksehd3" } ;
		item.SetItemId(1000);
		item.SetMarker(marker);
		item.SetLaunchList(launchList);
				
		Parcel p = Parcel.obtain();
		
		p.writeParcelable(item, 0);
		byte[] marshall = p.marshall();
		p.recycle();
		
		Parcel newParcel = Parcel.obtain();
		
		newParcel.unmarshall(marshall, 0, marshall.length);
		newParcel.setDataPosition(0);
		LaunchItem newItem = newParcel.readParcelable(LaunchItem.class.getClassLoader());
		newParcel.recycle();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		if (locListener != null)
		{
			Location myLoc = mLocationClient.getLastLocation();
			if (myLoc == null)
				return;

			locListener.onLocationChanged(myLoc);

			LatLng latLng = new LatLng(myLoc.getLatitude(), myLoc.getLongitude());
			CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_RATE);
			mMap.animateCamera(cu);
		}
	}

	@Override
	public void onDisconnected()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void activate(OnLocationChangedListener listener)
	{
		// TODO Auto-generated method stub
		locListener = listener;
	}

	@Override
	public void deactivate()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDrag(Marker marker)
	{

	}

	@Override
	public void onMarkerDragEnd(Marker marker)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragStart(Marker marker)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onMapLongClick(LatLng point)
	{
		// TODO Auto-generated method stub
		markerPopup.AddMarker(mMap, point);
	}

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		markerPopup.ModifyMarker(marker);
		return false;
	}

}
