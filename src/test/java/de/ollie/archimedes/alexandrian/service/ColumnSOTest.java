package de.ollie.archimedes.alexandrian.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Types;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for class "ColumnSO".
 *
 * @author ollie
 *
 */
@ExtendWith(MockitoExtension.class)
public class ColumnSOTest {

	private static final String COLUMN0_NAME = "Column0";
	private static final String COLUMN1_NAME = "Column1";
	private static final String TABLE_NAME = "TableName";
	private static final int TYPE_BIGINT = Types.BIGINT;
	private static final int TYPE_VARCHAR = Types.VARCHAR;
	private static final int TYPE_VARCHAR_LENGTH = 50;

	@InjectMocks
	private ColumnSO unitUnderTest;

	@Test
	public void equals_PassANullValue_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		// Run
		boolean returned = toCheck.equals(null);
		// Check
		assertThat(returned, equalTo(expected));
	}

	private ColumnSO createFullyLoadedColumnSO() {
		ColumnSO column0 = new ColumnSO().setName(COLUMN0_NAME).setType(new TypeSO().setSqlType(TYPE_BIGINT))
				.setNullable(false).setPkMember(true).setUnique(true);
		ColumnSO column1 = new ColumnSO().setName(COLUMN1_NAME)
				.setType(new TypeSO().setSqlType(TYPE_VARCHAR).setLength(TYPE_VARCHAR_LENGTH)).setNullable(true);
		TableSO table = new TableSO().setName(TABLE_NAME).setColumns(Arrays.asList(column0, column1));
		column0.setTable(table);
		return column0;
	}

	@Test
	public void equals_PassAnotherClass_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		// Run
		boolean returned = toCheck.equals(";ob");
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentName_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		ColumnSO passed = createFullyLoadedColumnSO().setName("Foo");
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentType_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		ColumnSO passed = createFullyLoadedColumnSO()
				.setType(new TypeSO().setSqlType(TYPE_VARCHAR).setLength(TYPE_VARCHAR_LENGTH));
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentNullable_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		ColumnSO passed = createFullyLoadedColumnSO().setNullable(true);
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentPkMember_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		ColumnSO passed = createFullyLoadedColumnSO().setPkMember(false);
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentTable_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ColumnSO toCheck = createFullyLoadedColumnSO();
		ColumnSO passed = createFullyLoadedColumnSO().setTable(new TableSO().setName("AnotherTable"));
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void getNameWithTable_ReturnsTheTableNameDotColumnName() {
		// Prepare
		String expected = TABLE_NAME + "." + COLUMN0_NAME;
		// Run
		String returned = createFullyLoadedColumnSO().getNameWithTable();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void hashCode_ReturnsEqualValuesForEqualObjects() {
		// Prepare
		int expected = createFullyLoadedColumnSO().hashCode();
		// Run
		int returned = createFullyLoadedColumnSO().hashCode();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void hashCode_ReturnsEqualValuesCalledTwiceForSameObject() {
		// Prepare
		ColumnSO column = createFullyLoadedColumnSO();
		int expected = column.hashCode();
		// Run
		int returned = column.hashCode();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void toString_ReturnsACorrectStringRepresentation() {
		// Prepare
		String expected = "ColumnSO(name=" + COLUMN0_NAME + ", type=TypeSO(sqlType=" + TYPE_BIGINT
				+ ", length=null, precision=null), nullable=false, pkMember=true, table=" + TABLE_NAME
				+ ", unique=true)";
		// Run
		String returned = createFullyLoadedColumnSO().toString();
		// Check
		assertEquals(expected, returned);
	}

}