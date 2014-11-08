package com.example.markapp;

import java.util.ArrayList;
import java.util.List;

import com.example.markapp.adapters.NotesAdpater;
import com.example.markapp.models.Notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class UserMenu extends ActionBarActivity implements OnClickListener, OnItemClickListener{

	// Propiedades de usuario:
	TextView txt_user;
	ArrayList<String> user_data;
	
	String notes[],content[],date[];
	ListView list;
	NotesAdpater adapter;
	List<Notes> data;	
	AlertDialog dialog;
	EditText title, cont, time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_menu);
		
		txt_user = (TextView) findViewById(R.id.txt_user);
		Bundle bnd = this.getIntent().getExtras();
		user_data = bnd.getStringArrayList("User_data");		
		txt_user.setText(user_data.get(0)+" "+user_data.get(1));
		
		list=(ListView) findViewById(R.id.list);
		
		notes= getResources().getStringArray(R.array.title_note);
        content= getResources().getStringArray(R.array.content_notes);
        date= getResources().getStringArray(R.array.date_notes);
        
        data= new ArrayList<Notes>();
        adapter= new NotesAdpater(this, data);
        
        
        list.setAdapter(adapter);        
        registerForContextMenu(list);   
        
        View dialogContent= View.inflate(this, R.layout.dialog_new_note, null);
        
        title= (EditText) dialogContent.findViewById(R.id.note_title);        
        cont= (EditText) dialogContent.findViewById(R.id.note_content);        
        time= (EditText) dialogContent.findViewById(R.id.note_date);
        
        
        dialog= new AlertDialog.Builder(this)
        .setTitle("Agregar Nota")
        .setView(dialogContent)
        .setPositiveButton("Aceptar", this)
        .setNegativeButton("Cancelar", this)
        .create();
        
        
       list.setOnItemClickListener(this);
        
        SetNotesToView();
        
	}

	private void SetNotesToView() {
		for(int i=0; i<notes.length;i++){
			Notes n= new Notes(notes[i], content[i], date[i]);
			data.add(n);
		}
		adapter.notifyDataSetChanged();
	}

	//Configuracion del menu de opciones
	
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			
			getMenuInflater().inflate(R.menu.add_note, menu);		
			return super.onCreateOptionsMenu(menu);
		}
		
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
}
