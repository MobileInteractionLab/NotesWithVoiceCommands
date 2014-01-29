package com.mobileinteractionlabs.notes;

public class Note {

	/**
	 * _id 			Auto increment
	 * _position 	position on the grid
	 * _time 		time when the note is created
	 * _date 		date when the note is created
	 * _note 		Note text
	 * _picture		Name of the picture file (if not exist = "")
	 * _audio		Name of the audio file (if not exist = "")
	 * _latitude	Latitude 
	 * _longitude	Longitude
	 * _color		Color of the week
	 */ 
	
	int _id;
	int _position;
	String _time;
	String _date;
	String _note;
	String _picture;
	String _audio;
	String _latitude;
	String _longitude;
	int _color;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int get_position() {
		return _position;
	}
	public void set_position(int _position) {
		this._position = _position;
	}
	public String get_time() {
		return _time;
	}
	public void set_time(String _time) {
		this._time = _time;
	}
	public String get_date() {
		return _date;
	}
	public void set_date(String _date) {
		this._date = _date;
	}
	public String get_note() {
		return _note;
	}
	public void set_note(String _note) {
		this._note = _note;
	}
	public String get_picture() {
		return _picture;
	}
	public void set_picture(String _picture) {
		this._picture = _picture;
	}
	public String get_audio() {
		return _audio;
	}
	public void set_audio(String _audio) {
		this._audio = _audio;
	}
	public String get_latitude() {
		return _latitude;
	}
	public void set_latitude(String _latitude) {
		this._latitude = _latitude;
	}
	public String get_longitude() {
		return _longitude;
	}
	public void set_longitude(String _longitude) {
		this._longitude = _longitude;
	}
	public int get_color() {
		return _color;
	}
	public void set_color(int _color) {
		this._color = _color;
	}
	
	
	/**
	 * @param _id
	 * @param _position
	 * @param _time
	 * @param _date
	 * @param _note
	 * @param _picture
	 * @param _audio
	 * @param _latitude
	 * @param _longitude
	 * @param _color
	 */
	public Note(int _id, int _position, String _time, String _date,
			String _note, String _picture, String _audio, String _latitude,
			String _longitude, int _color) {
		super();
		this._id = _id;
		this._position = _position;
		this._time = _time;
		this._date = _date;
		this._note = _note;
		this._picture = _picture;
		this._audio = _audio;
		this._latitude = _latitude;
		this._longitude = _longitude;
		this._color = _color;
	}
	/**
	 * @param _position
	 * @param _time
	 * @param _date
	 * @param _note
	 * @param _picture
	 * @param _audio
	 * @param _latitude
	 * @param _longitude
	 * @param _color
	 */
	public Note(int _position, String _time, String _date, String _note,
			String _picture, String _audio, String _latitude,
			String _longitude, int _color) {
		super();
		this._position = _position;
		this._time = _time;
		this._date = _date;
		this._note = _note;
		this._picture = _picture;
		this._audio = _audio;
		this._latitude = _latitude;
		this._longitude = _longitude;
		this._color = _color;
	}
	
	
	
}
