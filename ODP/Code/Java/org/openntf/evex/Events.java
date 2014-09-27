package org.openntf.evex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.openntf.domino.DateTime;
import org.openntf.domino.Document;
import org.openntf.domino.DocumentCollection;
import org.openntf.domino.utils.XSPUtil;

import com.ibm.commons.util.io.json.JsonGenerator;
import com.ibm.commons.util.io.json.JsonJavaFactory;
import com.ibm.commons.util.io.json.JsonJavaObject;

public class Events {

	private String from = "";
	private String to = "";

	public Events() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		try {
			this.from = context.getRequestParameterMap().get("from") != null ? context.getRequestParameterMap().get("from").toString() : "";
			this.to = context.getRequestParameterMap().get("to") != null ? context.getRequestParameterMap().get("to").toString() : "";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public String getEvents() {
		ArrayList<HashMap<String, Object>> dataMap = new ArrayList<HashMap<String, Object>>();

		// Search all docs in the range
		Long lFrom = 0L;
		Long lTo = 0L;
		if (!from.equals("") && !to.equals("")) {
			lFrom = Long.valueOf(from).longValue();
			lTo = Long.valueOf(to);
		}
		Date from = new Date(lFrom.longValue());
		Date to = new Date(lTo.longValue());

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		String searchString = "SELECT Form=\"event\" & (eventStart => [" + formatter.format(from) + "] | eventStart <= [" + formatter.format(to) + "])";
		DocumentCollection col = XSPUtil.getCurrentDatabase().search(searchString);
		HashMap valueMap;

		for (Document doc : col) {
			try {
				for (int i = 0; i < doc.getItemValueDateTimeArray("eventStart").size(); i++) {
					valueMap = new HashMap<String, Object>();
					valueMap.put("title", doc.getItemValueString("eventTitle"));
					valueMap.put("id", doc.getUniversalID());
					valueMap.put("class", doc.getItemValueString("eventClass"));
					valueMap.put("start", ((DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(i)).toJavaDate().getTime());
					valueMap.put("end", ((DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(i)).toJavaDate().getTime());
					valueMap.put("url", "event.xsp?documentId=" + doc.getUniversalID());
					dataMap.add(valueMap);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// JSON output
		JsonJavaObject jsobj = new JsonJavaObject();

		jsobj.put("success", 1);
		jsobj.put("result", dataMap);

		String json = "{}";
		try {
			json = JsonGenerator.toJson(JsonJavaFactory.instanceEx, jsobj);
		} catch (Exception e) {
			json = "{\"success\":0, \"message\":\"an error occured\"}";
		}

		return json;
	}

	@SuppressWarnings("unchecked")
	public String getRooms() {
		ArrayList<HashMap<String, Object>> dataMap = new ArrayList<HashMap<String, Object>>();

		// Search all docs in the range
		Date from = Helper.getDateFromTimeStamp(this.from);
		Date to = Helper.getDateFromTimeStamp(this.to);

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		String searchString = "SELECT Form=\"reservation\" & resType=\"room\" & (eventStart => [" + formatter.format(from) + "] | eventStart <= [" + formatter.format(to) + "])";

		DocumentCollection col = XSPUtil.getCurrentDatabase().search(searchString);

		for (Document doc : col) {
			for (int i = 0; i < doc.getItemValueDateTimeArray("eventStart").size(); i++) {
				HashMap valueMap = new HashMap<String, Object>();
				valueMap.put("title", doc.getItemValueString("roomName"));
				valueMap.put("id", doc.getUniversalID());
				valueMap.put("class", doc.getItemValueString("eventClass"));
				valueMap.put("start", ((DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(i)).toJavaDate().getTime());
				valueMap.put("end", ((DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(i)).toJavaDate().getTime());
				valueMap.put("url", "event.xsp?documentId=" + doc.getUniversalID());
				dataMap.add(valueMap);
			}

		}

		// JSON output
		JsonJavaObject jsobj = new JsonJavaObject();

		jsobj.put("success", 1);
		jsobj.put("result", dataMap);

		String json = "{}";
		try {
			json = JsonGenerator.toJson(JsonJavaFactory.instanceEx, jsobj);
		} catch (Exception e) {
			json = "{\"success\":0, \"message\":\"an error occured\"}";
		}

		return json;
	}

	@SuppressWarnings("unchecked")
	public String getResources() {
		ArrayList<HashMap<String, Object>> dataMap = new ArrayList<HashMap<String, Object>>();

		// Search all docs in the range
		Date from = Helper.getDateFromTimeStamp(this.from);
		Date to = Helper.getDateFromTimeStamp(this.to);

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		String searchString = "SELECT Form=\"reservation\" / resType=\"resource\" & (eventStart => [" + formatter.format(from) + "] | eventStart <= [" + formatter.format(to) + "])";

		DocumentCollection col = XSPUtil.getCurrentDatabase().search(searchString);

		for (Document doc : col) {
			for (int i = 0; i < doc.getItemValueDateTimeArray("eventStart").size(); i++) {
				HashMap valueMap = new HashMap<String, Object>();
				valueMap.put("title", doc.getItemValueString("eventTitle"));
				valueMap.put("id", doc.getUniversalID());
				valueMap.put("class", doc.getItemValueString("eventClass"));
				valueMap.put("start", ((DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(i)).toJavaDate().getTime());
				valueMap.put("end", ((DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(i)).toJavaDate().getTime());
				valueMap.put("url", "event.xsp?documentId=" + doc.getUniversalID());
				dataMap.add(valueMap);
			}

		}

		// JSON output
		JsonJavaObject jsobj = new JsonJavaObject();

		jsobj.put("success", 1);
		jsobj.put("result", dataMap);

		String json = "{}";
		try {
			json = JsonGenerator.toJson(JsonJavaFactory.instanceEx, jsobj);
		} catch (Exception e) {
			json = "{\"success\":0, \"message\":\"an error occured\"}";
		}

		return json;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<HashMap<String, Object>> getToday() {
		ArrayList<HashMap<String, Object>> dataMap = new ArrayList<HashMap<String, Object>>();

		// Search all docs in the range

		Date to = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		String searchString = "SELECT Form=\"event\" & (eventStart <= [" + formatter.format(to) + " 23:59] & eventEnd => [" + formatter.format(to) + " 00:00])";
		DocumentCollection col = XSPUtil.getCurrentDatabase().search(searchString);

		for (Document doc : col) {

			if (doc.getItemValueDateTimeArray("eventStart").size() > 1) {
				for (int i = 0; i < doc.getItemValueDateTimeArray("eventStart").size(); i++) {
					DateTime eventStart = (DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(i);
					DateTime eventEnd = (DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(i);

					if (eventStart.toJavaDate().after(Helper.getTodayStartDate()) && eventEnd.toJavaDate().before(Helper.getTodayEndDate())) {
						HashMap valueMap = new HashMap<String, Object>();
						valueMap.put("title", doc.getItemValueString("eventTitle"));
						valueMap.put("id", doc.getUniversalID());
						valueMap.put("class", doc.getItemValueString("eventClass"));
						valueMap.put("start", ((DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(0)).toJavaDate().getTime());
						valueMap.put("end", ((DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(0)).toJavaDate().getTime());
						dataMap.add(valueMap);
					}
				}
			} else {

				DateTime eventStart = (DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(0);
				DateTime eventEnd = (DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(0);

				if (eventStart.toJavaDate().before(Helper.getTodayStartEnd().getTime()) && eventEnd.toJavaDate().after(Helper.getTodayEndBegin().getTime())) {
					HashMap valueMap = new HashMap<String, Object>();
					valueMap.put("title", doc.getItemValueString("eventTitle"));
					valueMap.put("id", doc.getUniversalID());
					valueMap.put("class", doc.getItemValueString("eventClass"));
					valueMap.put("start", ((DateTime) doc.getItemValueDateTimeArray("eventStart").elementAt(0)).toJavaDate().getTime());
					valueMap.put("end", ((DateTime) doc.getItemValueDateTimeArray("eventEnd").elementAt(0)).toJavaDate().getTime());
					dataMap.add(valueMap);

				}

			}
		}
		return dataMap;
	}
}