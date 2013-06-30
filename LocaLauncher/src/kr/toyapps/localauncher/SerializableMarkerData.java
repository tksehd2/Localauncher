package kr.toyapps.localauncher;

import java.io.Serializable;

import com.google.android.gms.maps.model.LatLng;

public class SerializableMarkerData implements Serializable
{
	private static final long serialVersionUID = 1001L;
	public SerializableLatLng latLng;
	public String title;
	public String snippet;
}
