package kr.toyapps.localauncher;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class SerializableLatLng implements Serializable
{
	private static final long serialVersionUID = 1002L;
	public double longitude;
	public double latitude;
	
	public SerializableLatLng(LatLng latLng)
	{
		if(latLng == null)
			return ;
		
		longitude = latLng.longitude;
		latitude = latLng.latitude;
	}
}
