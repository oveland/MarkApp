package com.example.markapp.models;

public class Notes {
	String note_name, note_content, note_date;
	int id_note;

	public Notes(int id_note, String note_name, String note_content, String note_date) {
		this.id_note = id_note;
		this.note_name = note_name;
		this.note_content = note_content;
		this.note_date = note_date;
	}

	public int getId_note() {
		return id_note;
	}

	public void setId_note(int id_note) {
		this.id_note = id_note;
	}
	
	public String getNote_name() {
		return note_name;
	}

	public void setNote_name(String note_name) {
		this.note_name = note_name;
	}

	public String getNote_content() {
		return note_content;
	}

	public void setNote_content(String note_content) {
		this.note_content = note_content;
	}

	public String getNote_date() {
		return note_date;
	}

	public void setNote_date(String note_date) {
		this.note_date = note_date;
	}
	
}
