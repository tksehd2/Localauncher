package kr.toyapps.localauncher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

import org.apache.http.entity.SerializableEntity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcel;
import android.util.Base64;
import android.util.Base64InputStream;

public class DataStorage
{
	public static DataStorage Instance = new DataStorage();
	
	private String SerializableKey = "SavedData";
	private HashMap<Long, LaunchItem> _itemMap; 
	
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
	
	public LaunchItem GetItem(Long itemId)
	{
		if(_itemMap.containsKey(itemId))
		{
			return _itemMap.get(itemId);
		}
		return null;
	}
	public LaunchItem[] GetAllItems()
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
		SharedPreferences prefs = context.getSharedPreferences(SerializableKey, Context.MODE_PRIVATE);
		prefs.edit().clear().commit();
	}
	public void Save(Context context)
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		SerializableEntity se;
		try
		{
			ObjectOutputStream objOutput = new ObjectOutputStream(os);
			objOutput.writeObject(_itemMap);
			
			String encodedMap = Base64.encodeToString(os.toByteArray() , Base64.DEFAULT);
			
			Editor edit = context.getSharedPreferences(SerializableKey, Context.MODE_PRIVATE).edit();
			edit.putString(SerializableKey, encodedMap);
			edit.commit();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Load(Context context)
	{
		SharedPreferences prefs = context.getSharedPreferences(SerializableKey, Context.MODE_PRIVATE);
		byte[] data = Base64.decode(prefs.getString(SerializableKey, ""), Base64.DEFAULT);
		
		if(data.length > 0)
		{
			ByteArrayInputStream is = new ByteArrayInputStream(data);
			ObjectInputStream objInput;
			try
			{
				 objInput = new ObjectInputStream(is);
				 
				 _itemMap.clear();
				 _itemMap = (HashMap<Long, LaunchItem>)objInput.readObject();
				 
			} catch (StreamCorruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}