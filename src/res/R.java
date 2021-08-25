package res;

import java.util.Locale;
import java.util.ResourceBundle;

public class R {


	public static ResourceBundle getResources() {
		return ResourceBundle.getBundle("res.lang", Locale.getDefault());
	}
}
