package de.ollie.acf.utils;

import static de.ollie.utils.Check.ensure;

import org.apache.commons.lang3.StringUtils;

import de.ollie.archimedes.alexandrian.service.ColumnSO;
import de.ollie.archimedes.alexandrian.service.TableSO;

/**
 * A manager for naming in the code factory context.
 *
 * @author ollie (09.10.2019)
 */
public class NameManager {

	/**
	 * Converts a column name into a Java attribute name.
	 * 
	 * @param columnSO The column service object whose name is to convert.
	 * @return A Java attribute name based on the name of the passed column service object, or a "null" value, if a
	 *         "null" value is passed.
	 */
	public String columnNameToAttributeName(ColumnSO columnSO) {
		if (columnSO == null) {
			return null;
		}
		String columnName = columnSO.getName();
		ensure(!columnName.isEmpty(), "column name cannot be empty.");
		if (containsUnderScores(columnName)) {
			columnName = buildTableNameFromUnderScoreString(columnName);
		} else if (allCharactersAreUpperCase(columnName)) {
			columnName = columnName.toLowerCase();
		}
		if (startsWithUpperCaseCharacter(columnName)) {
			columnName = firstCharToLowerCase(columnName);
		}
		return columnName;
	}

	private boolean startsWithUpperCaseCharacter(String s) {
		String firstChar = StringUtils.left(s, 1);
		return firstChar.equals(firstChar.toUpperCase());
	}

	/**
	 * Returns a string which is the passed string with first character as lower case.
	 *
	 * @param s The string to convert.
	 * @return The passed string with first character as lower case.
	 */
	public String firstCharToLowerCase(String s) {
		return StringUtils.left(s, 1).toLowerCase() + (s.length() > 1 ? s.substring(1) : "");
	}

	/**
	 * Returns a string which is the passed string with first character as upper case.
	 *
	 * @param s The string to convert.
	 * @return The passed string with first character as upper case.
	 */
	public String firstCharToUpperCase(String s) {
		return StringUtils.left(s, 1).toUpperCase() + (s.length() > 1 ? s.substring(1) : "");
	}

	/**
	 * Returns a well formed class name for the passed table.
	 *
	 * @param tableSo The table to get the name for.
	 * @return A well formed class name for the passed table.
	 * @throws IllegalArgumentException Passing a table with an empty name.
	 * @throws NullPointerException     Passing a table null value.
	 */
	public String getClassName(TableSO table) {
		String tableName = table.getName();
		ensure(!tableName.isEmpty(), "table name cannot be empty.");
		if (containsUnderScores(tableName)) {
			tableName = buildTableNameFromUnderScoreString(tableName);
		} else if (allCharactersAreUpperCase(tableName)) {
			tableName = tableName.toLowerCase();
		}
		if (startsWithLowerCaseCharacter(tableName)) {
			tableName = firstCharToUpperCase(tableName);
		}
		return tableName;
	}

	private boolean containsUnderScores(String s) {
		return s.contains("_");
	}

	private String buildTableNameFromUnderScoreString(String s) {
		StringBuilder name = new StringBuilder();
		String[] parts = StringUtils.split(s, "_");
		for (String p : parts) {
			if (allCharactersAreUpperCase(p)) {
				p = p.toLowerCase();
			}
			p = firstCharToUpperCase(p);
			name.append(p);
		}
		return name.toString();
	}

	private boolean allCharactersAreUpperCase(String s) {
		return s.equals(s.toUpperCase());
	}

	private boolean startsWithLowerCaseCharacter(String s) {
		String firstChar = StringUtils.left(s, 1);
		return firstChar.equals(firstChar.toLowerCase());
	}

	/**
	 * Returns the name of a key service object for the passed table.
	 * 
	 * @param table The table which the key service class name is to find for.
	 * @return The name of a key service object for the passed table.
	 */
	public String getKeySOClassName(TableSO table) {
		return getClassName(table) + "KeySO";
	}

	/**
	 * Returns a package name suffix for key service objects.
	 * 
	 * @return A package name suffix for key service objects.
	 */
	public String getKeySOPackageNameSuffix() {
		return "service.so";
	}

	/**
	 * Returns the name of a service interface for the passed table.
	 * 
	 * @param table The table which the service interface name is to find for.
	 * @return The name of a service interface for the passed table.
	 */
	public String getServiceInterfaceName(TableSO table) {
		return getClassName(table) + "Service";
	}

	/**
	 * Returns a package name suffix for service interfaces.
	 * 
	 * @return A package name suffix for service interfaces.
	 */
	public String getServiceInterfacePackageSuffix() {
		return "service";
	}

	/**
	 * Returns the name of a data service object for the passed table.
	 * 
	 * @param table The table which the data service object name is to find for.
	 * @return The name of a data service object for the passed table.
	 */
	public String getSOClassName(TableSO table) {
		return getClassName(table) + "SO";
	}

	/**
	 * Returns a package name suffix for data service objects.
	 * 
	 * @return A package name suffix for data service objects.
	 */
	public String getSOPackageNameSuffix() {
		return "service.so";
	}

}