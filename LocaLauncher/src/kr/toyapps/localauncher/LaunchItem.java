package kr.toyapps.localauncher;

import java.io.Serializable;

import android.os.Parcel;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LaunchItem implements Serializable
{
/*
 * LaunchItem Listener Interface 
 */
	public interface OnMarkerAddListener
	{
		public void OnAddMarker(LaunchItem item);
	}
///////////////////////////////////////////////////////////////
	
	
	
	public long itemId;
	public SerializableMarkerData MarkerData;
	public String[] LaunchList;
	
	public LaunchItem()
	{
		itemId = 0;
		MarkerData = new SerializableMarkerData();
		LaunchList = new String[0];
	}
	
	void SetMarker(MarkerOptions Marker)
	{
		MarkerData.latLng = new SerializableLatLng(Marker.getPosition());
		MarkerData.title = Marker.getTitle();
		MarkerData.snippet = Marker.getSnippet();
	}
	void SetItemId(long id)
	{
		itemId = id;
	}
	void SetLaunchList(String[] launchList)
	{
		LaunchList = launchList;
	}
	
	public MarkerOptions toMarkerOptions()
	{
		MarkerOptions marker = new MarkerOptions();
		
		marker.position(new LatLng(MarkerData.latLng.latitude, MarkerData.latLng.longitude));
		marker.title(MarkerData.title);
		marker.snippet(MarkerData.snippet);
		
		return marker; 
	}
}

