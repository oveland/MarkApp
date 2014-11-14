package com.example.markapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.markapp.net.HttpAsyncTask;
import com.example.markapp.net.HttpAsyncTask.HttpAsyncInterface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdminNote extends ActionBarActivity implements OnClickListener, HttpAsyncInterface {

	Spinner spn;
	TextView btn_ok;
	JSONObject json_rta;
	
	
	String nkname_usr;
	EditText new_title, new_cont;
	
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	private String[] diasSemana = {"Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_note);
		
		Bundle bnd = this.getIntent().getExtras();
		try {
			nkname_usr = bnd.getString(Constant.NICKNAME);
		} catch (Exception e) {
			Log.d("Error en ONCREATE", e.toString());
			finish();
		}
		new_title= (EditText) findViewById(R.id.note_title);        
        new_cont= (EditText) findViewById(R.id.note_content);        
        
		spn=(Spinner) findViewById(R.id.spinner_op);
		
		spn.setAdapter(new MyCustomSpinnerAdapter(this, R.layout.spinner_row, this.diasSemana));
		
		btn_ok = (TextView) findViewById(R.id.btn_OK);
		btn_ok.setOnClickListener(this);
	}

	public class MyCustomSpinnerAdapter extends ArrayAdapter<Object>{

		private Context contexto;
		public MyCustomSpinnerAdapter(Context context, int textViewResourceId, String[] items) {
		super(context, textViewResourceId, items);
		this.contexto = context;
		}

		@Override
		public View getDropDownView(int position, View convertView,
		ViewGroup parent) {
		return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflador = ((Activity) this.contexto).getLayoutInflater();
		View fila = inflador.inflate(R.layout.spinner_row, parent, false);
		TextView texto = (TextView) fila.findViewById(R.id.category_sel);
		texto.setText(diasSemana[position]);

		ImageView icono = (ImageView) fila.findViewById(R.id.icon_category);

		if (diasSemana[position].equals("Sunday")){
		icono.setImageResource(R.drawable.personal);
		}else{
		icono.setImageResource(R.drawable.educacion);
		}

		return fila;
		}
		}

	@Override
	public void onClick(View v) {
		
		params.add(new BasicNameValuePair(Constant.NICKNAME, nkname_usr));
		params.add(new BasicNameValuePair(Constant.TITLE_NOTE, new_title.getText().toString()));
        params.add(new BasicNameValuePair(Constant.CONTENT_NOTE, new_cont.getText().toString()));
        params.add(new BasicNameValuePair(Constant.DATE_NOTE, UserMenu.GetCurrentDate()));
        
		HttpAsyncTask login_task = new HttpAsyncTask(this, params);
        login_task.execute(Constant.URL_SET_NOTE);		
	}

	@Override
	public void setResponse(String rta) {
		String msg_toast="";
		
		Log.d("Rta STRING", rta.toString());
		
		try {
			json_rta = new JSONObject(rta);
			int success = json_rta.getInt(Constant.SUCCESS);
			msg_toast = json_rta.getString(Constant.MESSAGE);
			
			switch (success) {			
			case Constant.SET_NOTE_OK:
				Toast.makeText(this, msg_toast, Toast.LENGTH_LONG ).show();
				finish();
				break;
			
			default:
				break;
			}
			Toast.makeText(this, msg_toast, Toast.LENGTH_LONG ).show();
			
		} catch (JSONException e) {
			Log.d("Error JSON conv String - Json Array", e.toString());
			e.printStackTrace();
		}
		
		
	}
	
}
