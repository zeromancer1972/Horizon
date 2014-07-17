package org.openntf.bstemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Navigation implements Serializable {

	private static final long serialVersionUID = 8031965383531253276L;
	private final List<Page> navigation;

	public Navigation() {
		this.navigation = new ArrayList<Page>();
		 this.navigation.add(new Page("All events", "calendar.xsp", "", false));
		 this.navigation.add(new Page("Rooms", "calendarRooms.xsp", "", false));
		 this.navigation.add(new Page("Resources", "calendarResources.xsp", "", false));
	}

	public List<Page> getNavigation() {
		return navigation;
	}
}
