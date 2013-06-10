package kr.toyapps.localauncher;

import java.io.FileDescriptor;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

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
import com.google.android.gms.maps.model.MarkerOptionsCreator;

public class MainActivity extends FragmentActivity 
					      implements LocationSource, ConnectionCallbacks , OnConnectionFailedListener , OnMarkerClickListener ,OnMarkerDragListener , OnMapLongClickListener
{
	private GoogleMap mMap;
	protected Button _registerBtn; 
	protected Location _globalLocation;
	protected LocationClient mLocationClient;
	protected OnLocationChangedListener locListener;
	
	protected MarkerPopup markerPopup;
	
	final float ZOOM_RATE = 19.0f;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init(); 
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
		if(mLocationClient == null)
		{
			mLocationClient = new LocationClient(this, this , this);
			mLocationClient.connect();
		}
		markerPopup = new MarkerPopup(this);
		ParcelableTest();
	}
	
	void ParcelableTest()
	{
		Parcel p = Parcel.obtain();
		
		LatLng latLng = new LatLng(1.0, 2.0);
		MarkerOptions marker = new MarkerOptions();
		marker.position(latLng);
		marker.title("Parcelable test");
		marker.snippet("Parcelable test snippet");
		
		p.writeParcelable(marker, MarkerOptions.PARCELABLE_WRITE_RETURN_VALUE);
		
		p.toString();
	}
	

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		if(locListener != null)		
		{
			Location myLoc = mLocationClient.getLastLocation();
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
		markerPopup.AddMarker(mMap , point);
	}

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		markerPopup.ModifyMarker(marker);
		return false;
	}
	
}

