package org.openntf.evex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.openntf.domino.DateTime;
import org.openntf.domino.Document;
import org.openntf.domino.ViewEntry;
import org.openntf.domino.ViewEntryCollection;
import org.openntf.domino.utils.XSPUtil;

public class FinderBean implements Serializable {

	private static final long serialVersionUID = 24090843425472665L;
	private String resType;
	private Document doc;

	public FinderBean() {

	}

	public List<SelectItem> getRooms() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "")); // empty value
		ViewEntryCollection col = XSPUtil.getCurrentDatabase().getView("rooms").getAllEntries();
		for (ViewEntry ent : col) {
			if (this.isFree(ent, "room")) {
				items.add(new SelectItem(ent.getDocument().getItemValueString("roomName")));
			}
		}
		return items;
	}
	
	public List<SelectItem> getResources() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", "")); // empty value
		ViewEntryCollection col = XSPUtil.getCurrentDatabase().getView("resources").getAllEntries();
		for (ViewEntry ent : col) {
			if (this.isFree(ent, "resource")) {
				items.add(new SelectItem(ent.getDocument().getItemValueString("resourceName")));
			}
		}
		return items;
	}

	
	private boolean isFree(final ViewEntry ent, final String sType) {
		try {
			DateTime sdt = (DateTime) this.getDoc().getItemValue("eventStartDate").elementAt(0);
			DateTime edt = (DateTime) this.getDoc().getItemValue("eventEndDate").elementAt(0);
			DateTime stt = (DateTime) this.getDoc().getItemValue("eventStartTime").elementAt(0);
			DateTime ett = (DateTime) this.getDoc().getItemValue("eventEndTime").elementAt(0);

			@SuppressWarnings("unused")
			String start = sdt.getDateOnly() + " " + stt.getTimeOnly();
			@SuppressWarnings("unused")
			String end = edt.getDateOnly() + " " + ett.getTimeOnly();

			// find reservations for period
			
		} catch (Exception e) {
			
		}

		return true;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(final String resType) {
		this.resType = resType;
	}

	public org.openntf.domino.Document getDoc() {
		return this.doc;
	}

	public void setDoc(final lotus.domino.Document doc) {
		this.doc = XSPUtil.wrap(doc);
	}

}
