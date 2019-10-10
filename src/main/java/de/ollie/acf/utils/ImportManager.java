package de.ollie.acf.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A manager for (dynamic) imports.
 *
 * @author ollie (09.10.2019)
 */
public class ImportManager {

	private List<String> imports = new ArrayList<>();

	/**
	 * Adds the passed import to the list of imports (if not already in the list).
	 * 
	 * @param imp The import to add.
	 * @return The import manager.
	 */
	public ImportManager add(String imp) {
		if (!this.imports.contains(imp)) {
			this.imports.add(imp);
		}
		return this;
	}

	/**
	 * Converts the stored imports to a code fragment.
	 * 
	 * @return A code fragment with the imports which are stored in the manager.
	 */
	public String toCode() {
		String[] arr = this.imports.toArray(new String[0]);
		Arrays.sort(arr);
		StringBuilder code = new StringBuilder();
		for (String i : arr) {
			code.append("import ").append(i).append(";\n");
		}
		return (code.length() > 0 ? code.toString() + "\n" : "");
	}

}