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
 * Unit tests for class "ReferenceSO".
 *
 * @author ollie
 *
 */
@ExtendWith(MockitoExtension.class)
public class ReferenceSOTest {

	private static final String COLUMN0_NAME = "Column0";
	private static final String COLUMN1_NAME = "Column1";
	private static final String TABLE_NAME = "TableName";
	private static final int TYPE_BIGINT = Types.BIGINT;
	private static final int TYPE_VARCHAR = Types.VARCHAR;
	private static final int TYPE_VARCHAR_LENGTH = 50;

	@InjectMocks
	private ReferenceSO unitUnderTest;

	@Test
	public void equals_PassANullValue_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ReferenceSO toCheck = createFullyLoadedReferenceSO();
		// Run
		boolean returned = toCheck.equals(null);
		// Check
		assertThat(returned, equalTo(expected));
	}

	private ReferenceSO createFullyLoadedReferenceSO() {
		ColumnSO column0 = new ColumnSO().setName(COLUMN0_NAME).setType(new TypeSO().setSqlType(TYPE_BIGINT))
				.setNullable(false).setPkMember(true);
		ColumnSO column1 = new ColumnSO().setName(COLUMN1_NAME)
				.setType(new TypeSO().setSqlType(TYPE_VARCHAR).setLength(TYPE_VARCHAR_LENGTH)).setNullable(true);
		ColumnSO column2 = new ColumnSO().setName("Reference").setType(new TypeSO().setSqlType(TYPE_BIGINT))
				.setNullable(true);
		TableSO table = new TableSO().setName(TABLE_NAME).setColumns(Arrays.asList(column0, column1));
		TableSO tableReferencing = new TableSO().setName(TABLE_NAME + "Referencing").setColumns(Arrays.asList(column2));
		column0.setTable(table);
		column1.setTable(table);
		column2.setTable(tableReferencing);
		ReferenceSO reference = new ReferenceSO().setReferencedColumn(column0).setReferencingColumn(column2);
		ForeignKeySO foreignKey = new ForeignKeySO().setReferences(Arrays.asList(reference));
		tableReferencing.setForeignKeys(Arrays.asList(foreignKey));
		return reference;
	}

	@Test
	public void equals_PassAnotherClass_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ReferenceSO toCheck = createFullyLoadedReferenceSO();
		// Run
		boolean returned = toCheck.equals(";ob");
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentReferencedColumn_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ReferenceSO toCheck = createFullyLoadedReferenceSO();
		ReferenceSO passed = createFullyLoadedReferenceSO().setReferencedColumn(new ColumnSO().setName("OtherColumn"));
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnObjectWithDifferentReferencingColumn_ReturnsFalse() {
		// Prepare
		boolean expected = false;
		ReferenceSO toCheck = createFullyLoadedReferenceSO();
		ReferenceSO passed = createFullyLoadedReferenceSO().setReferencingColumn(new ColumnSO().setName("OtherColumn"));
		// Run
		boolean returned = toCheck.equals(passed);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassAnEqualObject_ReturnsTrue() {
		// Prepare
		boolean expected = true;
		ReferenceSO toCheck = createFullyLoadedReferenceSO();
		// Run
		boolean returned = toCheck.equals(createFullyLoadedReferenceSO());
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void equals_PassSameObject_ReturnsTrue() {
		// Prepare
		boolean expected = true;
		ReferenceSO toCheck = createFullyLoadedReferenceSO();
		// Run
		boolean returned = toCheck.equals(toCheck);
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void hashCode_ReturnsEqualValuesForEqualObjects() {
		// Prepare
		int expected = createFullyLoadedReferenceSO().hashCode();
		// Run
		int returned = createFullyLoadedReferenceSO().hashCode();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void hashCode_ReturnsEqualValuesCalledTwiceForSameObject() {
		// Prepare
		ReferenceSO reference = createFullyLoadedReferenceSO();
		int expected = reference.hashCode();
		// Run
		int returned = reference.hashCode();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void toString_ReturnsACorrectStringRepresentation() {
		// Prepare
		String expected = "ReferenceSO(referencedColumn=" + TABLE_NAME + "." + COLUMN0_NAME + ", referencingColumn="
				+ TABLE_NAME + "Referencing.Reference)";
		// Run
		String returned = createFullyLoadedReferenceSO().toString();
		// Check
		assertEquals(expected, returned);
	}

}