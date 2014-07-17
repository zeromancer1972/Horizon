package org.openntf.evex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.openntf.domino.utils.XSPUtil;

public class SysInfo implements Serializable {

	private static final long serialVersionUID = -6709687405993515378L;

	private final int aclLevel;
	private final int aclOptions;
	private final String userName;
	@SuppressWarnings("unchecked")
	private final Vector userRoles;

	public static int DBACL_CREATE_DOCS = 1;
	public static int DBACL_DELETE_DOCS = 2;
	public static int DBACL_CREATE_PRIV_AGENTS = 4;
	public static int DBACL_CREATE_PRIV_FOLDERS_VIEWS = 8;
	public static int DBACL_CREATE_SHARED_FOLDERS_VIEWS = 16;
	public static int DBACL_CREATE_SCRIPT_AGENTS = 32;
	public static int DBACL_READ_PUBLIC_DOCS = 64;
	public static int DBACL_WRITE_PUBLIC_DOCS = 128;
	public static int DBACL_REPLICATE_COPY_DOCS = 256;

	public SysInfo() {
		this.userName = XSPUtil.getCurrentSession().getEffectiveUserName();
		this.aclLevel = XSPUtil.getCurrentDatabase().queryAccess(this.userName);
		this.aclOptions = XSPUtil.getCurrentDatabase().queryAccessPrivileges(this.userName);
		this.userRoles = XSPUtil.getCurrentDatabase().queryAccessRoles(this.userName);
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAclLevel() {
		switch (this.aclLevel) {
		case 0:
			return "No access";
		case 1:
			return "Depositor";
		case 2:
			return "Reader";
		case 3:
			return "Author";
		case 4:
			return "Editor";
		case 5:
			return "Designer";
		case 6:
			return "Manager";
		default:
			return "don't know...";
		}

	}

	@SuppressWarnings("static-access")
	public List<String> getAclOptions() {
		List<String> options = new ArrayList<String>();
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_CREATE_DOCS) > 0)
			options.add("DBACL_CREATE_DOCS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_DELETE_DOCS) > 0)
			options.add("DBACL_DELETE_DOCS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_CREATE_PRIV_AGENTS) > 0)
			options.add("DBACL_CREATE_PRIV_AGENTS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_CREATE_PRIV_FOLDERS_VIEWS) > 0)
			options.add("DBACL_CREATE_PRIV_FOLDERS_VIEWS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_CREATE_SCRIPT_AGENTS) > 0)
			options.add("DBACL_CREATE_SCRIPT_AGENTS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_CREATE_SHARED_FOLDERS_VIEWS) > 0)
			options.add("DBACL_CREATE_SHARED_FOLDERS_VIEWS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_READ_PUBLIC_DOCS) > 0)
			options.add("DBACL_READ_PUBLIC_DOCS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_WRITE_PUBLIC_DOCS) > 0)
			options.add("DBACL_WRITE_PUBLIC_DOCS");
		if ((this.aclOptions & XSPUtil.getCurrentDatabase().DBACL_REPLICATE_COPY_DOCS) > 0)
			options.add("DBACL_REPLICATE_COPY_DOCS");
		return options;
	}

	public String getUserName() {
		return userName;
	}

	public int getAclLevelInt() {
		return this.aclLevel;
	}

	public boolean hasOption(final int option) {
		return ((this.aclOptions & option) > 0);
	}
	
	@SuppressWarnings("unchecked")
	public String getUserRoles() {
		String roles = "";
		Enumeration en = this.userRoles.elements();
		while (en.hasMoreElements()) {
			roles += en.nextElement() + ", ";
		}
		if (roles.length() > 2) {
			return roles.substring(0, roles.length() - 2);
		} else {
			return roles;
		}
	}

}
