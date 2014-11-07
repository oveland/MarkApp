package com.example.markapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.markapp.R;
import com.example.markapp.client.JsonHttpRequest;
import com.example.markapp.models.Users;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	// Propiedades de Login
	static EditText nickname;
	static EditText password;
	TextView txt_new;
	Button btn_login;
	
	// Propiedades de Usuario:
	static Users users = new Users();
	
	// Propiedades de Tareas HTTP:	
	// JSON Constantes:
	private static final String SUCCESS = "success";
	private static final String MESSAGE = "message";
	private static final String USER = "user";
	private static final String FIRSTNAME = "firstname";
	private static final String LASTNAME = "lastname";		
	
	private ProgressDialog pDialog;
	JSONObject json_rta;
	JSONArray user;
	String result,fisrtname,lastname;
	int success = 0;
	    
	JsonHttpRequest Json_Http_Request = new JsonHttpRequest();	
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	//public static final String URL_LOGIN = "http://markapp.esy.es/MarkApp/db_login.php";
	public static final String URL_LOGIN = "http://10.0.2.2/MarkApp/db_login.php";
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        nickname = (EditText) findViewById(R.id.txt_nickname);
        password = (EditText) findViewById(R.id.txt_paswword);
        nickname.setText("");
		password.setText("");
		
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        
        txt_new = (TextView) findViewById(R.id.txt_register);
        txt_new.setOnClickListener(this);        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View v) {
		// Para verificar datos de login:
		if(v == btn_login){
			if(nickname.getHint() != "" && password.getHint() != ""){
				Toast.makeText(MainActivity.this, "Campos incompletos", Toast.LENGTH_SHORT).show();
			}
			else{
				params.add(new BasicNameValuePair("nickname", nickname.getText().toString()));
		        params.add(new BasicNameValuePair("password", password.getText().toString()));
		        new HTPPAsync().execute();
				}				
			
			
		}
		// Para registrar un nuevo usuario:
		if(v == txt_new){
			Intent i = new Intent(this, UserRegister.class);
			startActivity(i);
			
		}
	}
	
	 

public class HTPPAsync extends AsyncTask<String, String, String> {
	 
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Verificando datos. Espere...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();        
    }

	@Override
	protected String doInBackground(String... args) {
		//Se ejecuta la peticion Http y se recupera la tra en formato Json:
		json_rta = Json_Http_Request.MarkApp_HttpRequest(URL_LOGIN, "POST", params);

        //Se muestra la rta json en el LogCat:
        Log.d("Respuesta: ", json_rta.toString());
        result = json_rta.toString();
        
        try {
        	success = json_rta.getInt(SUCCESS);  
			if(success == 1){
			user = 	json_rta.getJSONArray(USER);
			JSONObject objson = user.getJSONObject(0);
			
			fisrtname = objson.getString(FIRSTNAME);
			lastname = objson.getString(LASTNAME);
			
			result = "Benvenid@ "+fisrtname.toString();
			
			runOnUiThread(
					new Runnable() {
		        		public void run() {
		        			
		        			//Intent i = new Intent(MainActivity.this,UserMenu.class);
		        			
		        			users.setFirst_name(fisrtname);
		        			users.setLast_name(lastname);
		        			users.setNickname(nickname.toString());
		        			users.setPassword(password.toString());
		        			
		        			nickname.setText("");
		        			password.setText("");
		        			
		        			//startActivity(i);
		        			
		        		}
		            }
					);
			
			Intent i = new Intent(getApplicationContext(), UserMenu.class);
            startActivity(i);
            
			
			}
			else{
			result = json_rta.getString(MESSAGE);
			
			runOnUiThread(
			new Runnable() {
        		public void run() {
        			password.setText("");
        		}
            }
			);
			
			}
		}
		catch (JSONException e) {
				e.printStackTrace();
				result = "Error interno!";
				Log.d("Error Excepción!!: ", e.toString());
		}
        return null;
	}
	
	protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        pDialog.dismiss();
        runOnUiThread(
        	new Runnable() {
        		public void run() {
        			Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        			
        		}
            }
        );
                
    }

}


	 
	
}
