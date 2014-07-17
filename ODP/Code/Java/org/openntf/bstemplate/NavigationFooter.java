package org.openntf.bstemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NavigationFooter implements Serializable {

	private static final long serialVersionUID = -8857086205056457935L;
	private final List<Page> navigation;

	public NavigationFooter() {
		
		this.navigation = new ArrayList<Page>();
		this.navigation.add(new Page("Help", "help.xsp", "", false));

		
	}

	public List<Page> getNavigation() {
		return navigation;
	}
}
