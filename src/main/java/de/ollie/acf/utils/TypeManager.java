package de.ollie.acf.utils;

import java.sql.Types;

import de.ollie.archimedes.alexandrian.service.ColumnSO;
import de.ollie.archimedes.alexandrian.service.TypeSO;

/**
 * A converter for type objects.
 *
 * @author ollie (10.10.2019)
 */
public class TypeManager {

	/**
	 * Converts a column service object into a string with the Java type name..
	 * 
	 * @param columnSO The column service object which should be converted.
	 * @return A string with the name of the Java type.
	 * @throws IllegalArgumentException If the passed type cannot be converted.
	 */
	public String typeSOToTypeString(ColumnSO columnSO) {
		return typeSOToTypeString(columnSO.getType(), columnSO.isNullable());
	}

	/**
	 * Converts a type service object into a string with the Java type name..
	 * 
	 * @param typeSO   The type service object which should be converted.
	 * @param nullable Set this flag if the type should be returned in a nullable form.
	 * @return A string with the name of the Java type.
	 * @throws IllegalArgumentException If the passed type cannot be converted.
	 */
	public String typeSOToTypeString(TypeSO typeSO, boolean nullable) {
		if (typeSO == null) {
			return null;
		}
		if (typeSO.getSqlType() == Types.BIGINT) {
			return getTypeRespectNullable(nullable, "long", "Long");
		} else if ((typeSO.getSqlType() == Types.BIT) || (typeSO.getSqlType() == Types.BOOLEAN)) {
			return getTypeRespectNullable(nullable, "boolean", "Boolean");
		} else if ((typeSO.getSqlType() == Types.CHAR) || (typeSO.getSqlType() == Types.LONGNVARCHAR)
				|| (typeSO.getSqlType() == Types.LONGVARCHAR) || (typeSO.getSqlType() == Types.NCHAR)
				|| (typeSO.getSqlType() == Types.NVARCHAR) || (typeSO.getSqlType() == Types.VARCHAR)) {
			if (typeSO.getLength() == 1) {
				return getTypeRespectNullable(nullable, "char", "Character");
			}
			return "String";
		} else if (typeSO.getSqlType() == Types.DATE) {
			return "LocalDate";
		} else if ((typeSO.getSqlType() == Types.DECIMAL) || (typeSO.getSqlType() == Types.DOUBLE)
				|| (typeSO.getSqlType() == Types.NUMERIC) || (typeSO.getSqlType() == Types.REAL)) {
			return getTypeRespectNullable(nullable, "double", "Double");
		} else if (typeSO.getSqlType() == Types.FLOAT) {
			return getTypeRespectNullable(nullable, "float", "Float");
		} else if (typeSO.getSqlType() == Types.INTEGER) {
			return getTypeRespectNullable(nullable, "int", "Integer");
		} else if (typeSO.getSqlType() == Types.SMALLINT) {
			return getTypeRespectNullable(nullable, "short", "Short");
		} else if (typeSO.getSqlType() == Types.TIME) {
			return "LocalTime";
		} else if (typeSO.getSqlType() == Types.TIMESTAMP) {
			return "LocalDateTime";
		} else if (typeSO.getSqlType() == Types.TIMESTAMP_WITH_TIMEZONE) {
			return "ZonedDateTime";
		} else if (typeSO.getSqlType() == Types.TINYINT) {
			return getTypeRespectNullable(nullable, "byte", "Byte");
		}
		throw new IllegalArgumentException("type " + typeSO.getSqlType() + " cannot be converted to a Java type.");
	}

	private String getTypeRespectNullable(boolean nullable, String simpleTypeName, String wrapperTypeName) {
		if (nullable) {
			return wrapperTypeName;
		}
		return simpleTypeName;
	}

	/**
	 * Converts a type service object to an SQL type String.
	 * 
	 * @param typeSO The type service object which is to convert.
	 * @return An SQL type string for the passed type service object or a "null" value if one is passed.
	 */
	public String typeSOToSQLTypeString(TypeSO typeSO) {
		if (typeSO == null) {
			return null;
		}
		if (typeSO.getSqlType() == Types.BIGINT) {
			return "BIGINT";
		} else if ((typeSO.getSqlType() == Types.BIT) || (typeSO.getSqlType() == Types.BOOLEAN)) {
			return "BOOLEAN";
		} else if (typeSO.getSqlType() == Types.CHAR) {
			return "CHAR" + (typeSO.getLength() != null ? "(" + typeSO.getLength() + ")" : "");
		} else if (typeSO.getSqlType() == Types.INTEGER) {
			return "INT";
		} else if (typeSO.getSqlType() == Types.VARCHAR) {
			return "VARCHAR" + (typeSO.getLength() != null ? "(" + typeSO.getLength() + ")" : "");
		}
		return "UNKNOWN";
	}

}