package de.ollie.acf.utils;

import static de.ollie.utils.Check.ensure;

import org.apache.commons.lang3.StringUtils;

import de.ollie.archimedes.alexandrian.service.ColumnSO;
import de.ollie.archimedes.alexandrian.service.OptionSO;
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
		return firstChar.equals(firstChar.toUpperCase()); // NOSONAR OLI Want to see if is an upper case.
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
		return s.equals(s.toUpperCase()); // NOSONAR OLI Want to see if all are upper case characters.
	}

	private boolean startsWithLowerCaseCharacter(String s) {
		String firstChar = StringUtils.left(s, 1);
		return firstChar.equals(firstChar.toLowerCase()); // NOSONAR OLI Want to see if all are lower case characters.
	}

	/**
	 * Returns the names provider for a key service object of the passed table.
	 * 
	 * @param table The table which the key service class name is to find for.
	 * @return The names provider for a key service object of the passed table.
	 */
	public NamesProvider getKeySONamesProvider(TableSO table) {
		return new NamesProvider() //
				.setClassName(getClassName(table) + "KeySO") //
				.setPackageName("service.so") //
		;
	}

	/**
	 * Returns a plural name for the objects linked to the passed table.
	 * 
	 * @param table The table which the plural name is to return to.
	 * @return The plural name from the "PLURAL" option or an english plural generated from the table name.
	 */
	public String getPluralName(TableSO table) {
		return table.getMetaInfo().getOptions() //
				.stream() //
				.filter(option -> option.getName().equals("PLURAL")) //
				.findAny() //
				.map(OptionSO::getValue) //
				.orElse(firstCharToLowerCase(getClassName(table)) + "s") //
		;
	}

	/**
	 * Returns the names provider for a service impl class of the passed table.
	 * 
	 * @param table The table which the service impl class name is to find for.
	 * @return The names provider for a service impl class of the passed table.
	 */
	public NamesProvider getServiceImplClassNamesProvider(TableSO table) {
		return new NamesProvider() //
				.setClassName(getClassName(table) + "ServiceImpl") //
				.setPackageName("service.impl") //
		;
	}

	/**
	 * Returns the names provider for a service interface of the passed table.
	 * 
	 * @param table The table which the service interface name is to find for.
	 * @return The names provider for a service interface of the passed table.
	 */
	public NamesProvider getServiceInterfaceNamesProvider(TableSO table) {
		return new NamesProvider() //
				.setClassName(getClassName(table) + "Service") //
				.setPackageName("service") //
		;
	}

	/**
	 * Returns the names provider for a service object of the passed table.
	 * 
	 * @param table The table which the service object name is to find for.
	 * @return The names provider for a service object of the passed table.
	 */
	public NamesProvider getSONamesProvider(TableSO table) {
		return new NamesProvider() //
				.setClassName(getClassName(table) + "SO") //
				.setPackageName("service.so") //
		;
	}

}