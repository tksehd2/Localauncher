package kr.toyapps.localauncher;

import java.util.Dictionary;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcel;
import android.util.Base64;
import android.util.Log;

public class DataStorage
{
	public static DataStorage Instance = new DataStorage();
	
	private String parcelKey = "SavedData";
	private HashMap<Integer, LaunchItem> _itemMap; 
	
	public DataStorage()
	{
		_itemMap = new HashMap();
	}
	
	public void AddItem(LaunchItem item)
	{
		if(item == null)
			return ;
		
		_itemMap.put(item.itemId, item);
	}
	
	public LaunchItem Load(int itemId)
	{
		if(_itemMap.containsKey(itemId))
		{
			return _itemMap.get(itemId);
		}
		return null;
	}
	public LaunchItem[] LoadAll()
	{
		if(_itemMap.size() > 0)
		{
			LaunchItem[] items = new LaunchItem[_itemMap.size()];
			_itemMap.values().toArray(items);
			return items;
		}
		return null;
	}
	public void Clear(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(parcelKey, Context.MODE_PRIVATE);
		prefs.edit().clear().commit();
	}
	public void Save(Context context)
	{
		Parcel p = Parcel.obtain();
	
		p.writeMap(_itemMap);
		
		byte[] data = p.marshall();
		
		Editor edit = context.getSharedPreferences(parcelKey, Context.MODE_PRIVATE).edit();
		
		edit.putString(parcelKey, Base64.encodeToString(data, Base64.DEFAULT));
		edit.commit();
		
		p.recycle();
	}
	
	public void Load(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(parcelKey, Context.MODE_PRIVATE);
		
		byte[] data = Base64.decode(prefs.getString(parcelKey, ""), Base64.DEFAULT);
		
		if(data.length > 0)
		{
			Parcel p = Parcel.obtain();
			
			p.unmarshall(data, 0, data.length);
			p.setDataPosition(0);
			
			p.readMap(_itemMap, HashMap.class.getClassLoader());
			
/*			LaunchItem[] loadItem = (LaunchItem[])p.readParcelableArray(LaunchItem.class.getClassLoader());

			try
			{
				for (LaunchItem item : loadItem)
				{
					_itemMap.put(item.itemId, item);
				}
			} catch (Exception e)
			{
				Log.v("LocaLauncher Exception", e.toString());
			}
*/
			p.recycle();
		}
	}
}
