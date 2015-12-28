package com.coolweather.app.db; 

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import android.content.ContentValues;
import android.content.Context;
import java.util.List;
import java.util.ArrayList;

public class CoolWeatherDB {
	
	private static final String DB_NAME = "cool_weather";
	private static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	private CoolWeatherDB(Context context)
	{
		CoolWeatherHelper dbHelper = new CoolWeatherHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	private static synchronized CoolWeatherDB getInstance(Context context)
	{
		if (coolWeatherDB == null)
		{
			synchronized (CoolWeatherDB.class) {
				coolWeatherDB = new CoolWeatherDB(context); 
			}
		}
		return coolWeatherDB;
	}
	
	public void saveProvince(Province province)
	{
		if (province != null)
		{
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	public void saveCity(City city)
	{
		if (city != null)
		{
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			db.insert("City", null, values);
		}
	}
	
	public void saveCounty(County county)
	{
		if (county != null)
		{
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			db.insert("County", null, values);
		}
	}
	
	public List<Province> loadProvinces()
	{
		List<Province>  list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null);
		if (cursor.moveToFirst())
		{
			do {
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	public List<City> loadCities(int provinceId)
	{
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", new String[] {String.valueOf(provinceId)}, null, null, null);
		if (cursor.moveToFirst())
		{
			do {
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvince_id(provinceId);
				list.add(city);
			} while (cursor.moveToNext());
		}
		return list;
	}
	
	public List<County> loadCounties(int cityId)
	{
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("County", null, "cityId = ?", new String[] {String.valueOf(cityId)}, null, null, null);
		if (cursor.moveToFirst())
		{
			do {
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCity_id(cityId);
				list.add(county);
			} while (cursor.moveToNext());
		}
		return list;
	}
}
