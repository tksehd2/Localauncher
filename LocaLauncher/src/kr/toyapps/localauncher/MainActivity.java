package kr.toyapps.localauncher;

import kr.toyapps.localauncher.LaunchItem.OnMarkerAddListener;
import android.location.Location;
import android.os.Bundle;
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
	
		setContentView(R.layout.activity_main);
		
		DataStorage.Instance.Load(this);
		
		init();
	}
	@Override
	protected void onResume()
	{
		DataStorage.Instance.Load(this);
		init();
		
		super.onResume();
	}
	@Override
	protected void onPause()
	{
		DataStorage.Instance.Save(this);
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	protected void init()
	{
		//DataStorage.Instance.Load(this);
		
		if (mMap == null)
		{
			mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();			
			initMapSetting();
		}
		if (mLocationClient == null)
		{
			mLocationClient = new LocationClient(this, this, this);
			mLocationClient.connect();
		}
		markerPopup = new MarkerPopup(this);
		
		LaunchItem[] items = DataStorage.Instance.GetAllItems();
		if(items != null && items.length > 0)
		{
			initMapSetting();
			for(LaunchItem item : items)
			{
				mMap.addMarker(item.toMarkerOptions());
			}
		}
	}

	void initMapSetting()
	{
		mMap.clear();
		mMap.setOnMarkerDragListener(this);
		mMap.setOnMarkerClickListener(this);			
		mMap.setMyLocationEnabled(true);
		mMap.setLocationSource(this);			
		mMap.setOnMapLongClickListener(this);
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
		markerPopup.AddMarker(mMap, point , new OnMarkerAddListener()
		{
			@Override
			public void OnAddMarker(LaunchItem item)
			{
				DataStorage.Instance.AddItem(item);
			}
		}); 
	}	

	@Override
	public boolean onMarkerClick(Marker marker)
	{
		markerPopup.ModifyMarker(marker);
		return false;
	}

}
