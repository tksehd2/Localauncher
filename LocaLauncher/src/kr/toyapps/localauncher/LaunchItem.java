package kr.toyapps.localauncher;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.MarkerOptions;

public class LaunchItem implements Parcelable
{
	public int itemId;
	public MarkerOptions marker;
	public String[] LaunchList;
	
	public LaunchItem()
	{
		itemId = 0;
		marker = new MarkerOptions();
		LaunchList = new String[0];
	}
	public LaunchItem(Parcel in)
	{
		readFromParcel(in);	
	}
	
	void SetMarker(MarkerOptions Marker)
	{
		marker = Marker;
	}
	void SetItemId(int id)
	{
		itemId = id;
	}
	void SetLaunchList(String[] launchList)
	{
		LaunchList = launchList;
	}
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(itemId);
		dest.writeParcelable(marker, flags);
		dest.writeStringArray(LaunchList);
	}
	private void readFromParcel(Parcel in)
	{
		itemId = in.readInt();
		marker = in.readParcelable(MarkerOptions.class.getClassLoader());
		//in.readStringArray(LaunchList);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	{
		public LaunchItem createFromParcel(Parcel in)
		{
			return new LaunchItem(in);
		}

		public LaunchItem[] newArray(int size)
		{
			return new LaunchItem[size];
		}
	};

}
