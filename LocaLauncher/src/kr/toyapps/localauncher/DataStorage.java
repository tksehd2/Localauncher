package kr.toyapps.localauncher;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcel;
import android.util.Base64;

public class DataStorage
{
	public static DataStorage Instance;
	
	private String parcelKey = "SavedData";
	private Parcel parcel;
	
	public DataStorage()
	{
		parcel = Parcel.obtain();
	}
	
	public void AddItem(LaunchItem item)
	{
		if(item == null)
			return ;
		
		parcel.writeParcelable(item, 0);
	}
	
	public LaunchItem Load(int itemId)
	{
		return null;
	}
	public LaunchItem[] LoadAll()
	{
		return null;
	}
	
	public void Save(Context context)
	{
		byte[] data = parcel.marshall();
		
		String encoded = Base64.encodeToString(data, Base64.DEFAULT);
		
		SharedPreferences prefs = context.getSharedPreferences(parcelKey, Context.MODE_PRIVATE);
		Editor edit = prefs.edit();
		
		edit.putString("parcelKey", encoded);
	}
	public void Load(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(parcelKey, Context.MODE_PRIVATE);
		byte[] data = Base64.decode(prefs.getString(parcelKey, "") , Base64.DEFAULT);
		
		parcel.unmarshall(data, 0, data.length);
		parcel.setDataPosition(0);
	}
}
