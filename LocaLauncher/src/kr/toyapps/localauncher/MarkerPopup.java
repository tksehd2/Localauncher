package kr.toyapps.localauncher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerPopup
{
	protected View popupView;
	protected EditText _markerTitle;
	protected EditText _markerDesc;
	protected AlertDialog popup;
	
	public MarkerPopup(Context context)
	{
		if(popupView == null)
			popupView = View.inflate(context , R.layout.marker_add_popup, null);	
		_markerTitle = (EditText) popupView.findViewById(R.id.MarkerTitle);
		_markerDesc = (EditText) popupView.findViewById(R.id.MarkerDesc);
		
		popup = new AlertDialog.Builder(context).create();
		popup.setView(popupView);
	}
	
	void AddMarker(final GoogleMap map, final LatLng position)
	{
		_markerTitle.setText("");
		_markerDesc.setText("");
		
		popup.setTitle("Add to Marker");		
		popup.setButton(AlertDialog.BUTTON_POSITIVE, "Add", new OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				MarkerOptions marker = new MarkerOptions()
										.position(position)
										.title(_markerTitle.getText().toString())
										.snippet(_markerDesc.getText().toString())
										.draggable(true);
				map.addMarker(marker);
			}
		});
		popup.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", popupCloseListener);
		popup.show();
	}
	void ModifyMarker(final Marker marker)
	{
		_markerTitle.setText(marker.getTitle());
		_markerDesc.setText(marker.getSnippet());
		
		popup.setTitle("Modify Marker");
		popup.setButton(AlertDialog.BUTTON_POSITIVE, "Modify", new OnClickListener()
		{	
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				marker.setTitle(_markerTitle.getText().toString());
				marker.setSnippet(_markerDesc.getText().toString());
			}
		});
		popup.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", popupCloseListener );
		
		popup.show();
	}
	
	DialogInterface.OnClickListener popupCloseListener = new OnClickListener()
	{
		
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			dialog.dismiss();
		}
	};
}

