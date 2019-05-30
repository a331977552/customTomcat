package org.tomcat.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class StringManager {

	private static Map<String, StringManager> map = new HashMap<>();

	private ResourceBundle bundle;
	private String packageName;

	public String getString(String name) {
		if (name == null)
			throw new NullPointerException("key is null");
		String value;
		try {
			value = bundle.getString(name);
		} catch (java.util.MissingResourceException e) {
			value = "cannot find  message assosicated with key' " + name + "'";
		}
		return value;

	}

	public String getString(String name, Object... obj) {
		String value = getString(name);
		Object[] args = obj;
		String format;
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null) {
					// dont change the original args
					if (args[i] == obj) {
						args = obj.clone();
					}
					args[i] = "null";
				}
			}
			format = MessageFormat.format(value, args);

		} catch (IllegalArgumentException e) {
			StringBuffer buf = new StringBuffer();
			buf.append(value);
			for (int i = 0; i < args.length; i++) {
				buf.append(" arg[" + i + "]=" + args[i]);
			}
			format = buf.toString();
		}
		return format;

	}

	private StringManager(String packageName) {

		this.packageName = packageName;
	}

	public static StringManager getManager(String packageName) {
		StringManager stringManager = map.get(packageName);
		if (stringManager == null) {
			synchronized (StringManager.class) {
				stringManager = map.get(packageName);
				if (stringManager == null) {
					stringManager = new StringManager(packageName);
					stringManager.init();
					map.put(packageName, stringManager);
				}
			}
		}
		return stringManager;

	}

	private void init() {

		bundle = ResourceBundle.getBundle(packageName + ".LocalStrings",new Locale("en","GB"));
	}
}
