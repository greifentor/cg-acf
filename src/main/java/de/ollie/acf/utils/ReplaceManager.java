package de.ollie.acf.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * A manager for code fragment replacements.
 *
 * @author ollie (09.10.2019)
 */
public class ReplaceManager {

	private Map<String, String> replacements = new HashMap<>();

	/**
	 * Adds the passed replacement for the passed key.
	 * 
	 * @param key         The key which is to replace by the passed replacement.
	 * @param replacement The replacement for the passed key.
	 */
	public ReplaceManager add(String key, String replacement) {
		this.replacements.put(key, replacement);
		return this;
	}

	/**
	 * Replaces the keys stored in the manager by the replacements in the passed code.
	 * 
	 * @param code The code whose place holders are to replace.
	 * @return The code with the replaced place holders.
	 */
	public String replace(String code) {
		for (String key : this.replacements.keySet()) {
			code = code.replace("${" + key + "}", this.replacements.get(key));
			code = code.replace("$^{" + key + "}\n", this.replacements.get(key));
		}
		return code;
	}

}