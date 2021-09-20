package io.github.jatoxo;

import java.util.Locale;
import java.util.ResourceBundle;

public class R {


	public static ResourceBundle getResources() {
		//return ResourceBundle.getBundle("res.lang", Locale.GERMAN);
		return ResourceBundle.getBundle("lang", Locale.getDefault());
	}

	public static String string(String key) {
		return getResources().getString(key);
	}


}
