package com.mobileinteractionlabs.notes;

public class Category {
	public static final long PADLOCK = 0;
	public static final long SWHEEL = 1;
	public static final long BULB = 2;

	private int mId;
	private String mTitle;
	private String mIcon;
	private int mColor;
	
	public Category() {
	}
	
	public Category(String title, String icon, int color) {
		mTitle = title;
		mIcon = icon;
		mColor = color;
	}
	
	public int getId() {
		return mId;
	}
	
	public String getTitle() {
		return mTitle;
	}
	
	public String getIcon() {
		return mIcon;
	}
	
	public int getColor() {
		return mColor;
	}
	
	public void setId(int id) {
		mId = id;
	}
	
	public void setTitle(String title) {
		mTitle = title;
	}
	
	public void setIcon(String icon) {
		mIcon = icon;
	}
	
	public void setColor(int color) {
		mColor = color;
	}
	
	public void save() {
		//TODO: Implement code to save category
	}
	
	public void delete() {
		//TODO: Implement code to delete category
	}
}
