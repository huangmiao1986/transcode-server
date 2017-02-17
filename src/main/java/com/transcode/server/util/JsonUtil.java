package com.transcode.server.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonUtil {
	
	private static Gson gson = new GsonBuilder()
		.registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
		.registerTypeAdapter(java.sql.Timestamp.class, new UtilTimeStampSerializer())
		.registerTypeAdapter(java.sql.Date.class, new UtilSqlDateSerializer())
		.setDateFormat(DateFormat.LONG).disableHtmlEscaping()
		.create();
	
	private static Gson gson1 = new GsonBuilder()
		.registerTypeAdapter(java.util.Date.class, new UtilDateSerializer())
		.registerTypeAdapter(java.sql.Timestamp.class, new UtilTimeStampSerializer())
		.registerTypeAdapter(java.sql.Date.class, new UtilSqlDateSerializer())
		.setDateFormat(DateFormat.LONG).disableHtmlEscaping()
//		.setPrettyPrinting()
		.disableHtmlEscaping()
		.create();

	public static String toJson(Object protocolObj) {
		return gson.toJson(protocolObj);
	}
	/**
	 * 直接返回T类型的对象
	 * @param json
	 * @param T 对象类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T fromJson2(String json,Class T){
		if(StringUtil.isNotBlank(json)){
			return (T) gson.fromJson(json, T);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object fromJson(String json, Class cls) {
		return gson.fromJson(json, cls);
	}

	public static Object fromJson(String json, Type type) {
		return gson.fromJson(json, type);
	}
	
	public static String toJson1(Object protocolObj) {
		return gson1.toJson(protocolObj);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object fromJson1(String json, Class cls) {
		return gson1.fromJson(json, cls);
	}

	public static Object fromJson1(String json, Type type) {
		return gson1.fromJson(json, type);
	}

	private static class UtilDateSerializer implements JsonSerializer<Date>,
			JsonDeserializer<Date> {
		@Override
		public JsonElement serialize(Date date, Type type,
				JsonSerializationContext context) {
			return new JsonPrimitive(date.getTime());
		}

		@Override
		public Date deserialize(JsonElement element, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			return new Date(element.getAsJsonPrimitive().getAsLong());
		}
	}

	private static class UtilSqlDateSerializer implements JsonSerializer<java.sql.Date>,
	JsonDeserializer<java.sql.Date> {
		@Override
		public JsonElement serialize(java.sql.Date date, Type type,
				JsonSerializationContext context) {
			return new JsonPrimitive(date.getTime());
		}
		
		@Override
		public java.sql.Date deserialize(JsonElement element, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			return new java.sql.Date(element.getAsJsonPrimitive().getAsLong());
		}
	}
	
	private static class UtilTimeStampSerializer implements JsonSerializer<java.sql.Timestamp>,
			JsonDeserializer<java.sql.Timestamp> {
		@Override
		public JsonElement serialize(java.sql.Timestamp date, Type type,
				JsonSerializationContext context) {
			return new JsonPrimitive(date.getTime());
		}
		
		@Override
		public java.sql.Timestamp deserialize(JsonElement element, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			return new java.sql.Timestamp(element.getAsJsonPrimitive().getAsLong());
		}
	}

	@SuppressWarnings("unused")
	private static class UtilCalendarSerializer implements
			JsonSerializer<Calendar>, JsonDeserializer<Calendar> {
		@Override
		public JsonElement serialize(Calendar cal, Type type,
				JsonSerializationContext context) {
			return new JsonPrimitive(Long.valueOf(cal.getTimeInMillis()));
		}

		@Override
		public Calendar deserialize(JsonElement element, Type type,
				JsonDeserializationContext context) throws JsonParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(element.getAsJsonPrimitive().getAsLong());
			return cal;
		}
	}
}
