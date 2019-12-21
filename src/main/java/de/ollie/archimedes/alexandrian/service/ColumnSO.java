package de.ollie.archimedes.alexandrian.service;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * A container for column service objects.
 *
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@Generated
@NoArgsConstructor
public class ColumnSO {

	@NonNull
	private String name;
	private TypeSO type;
	private boolean nullable;
	private boolean pkMember;
	private boolean unique;
	@NonNull
	private TableSO table;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ColumnSO)) {
			return false;
		}
		return toString().equals(o.toString());
	}

	/**
	 * Returns the table and the column name separated by a dot.
	 * 
	 * @return The table and the column name separated by a dot.
	 */
	public String getNameWithTable() {
		return (this.table != null ? this.table.getName() + "." : "") + this.name;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return "ColumnSO(name=" + (this.name != null ? this.name : null) + ", type="
				+ (this.type != null ? this.type : null) + ", nullable=" + this.nullable + ", pkMember=" + this.pkMember
				+ ", table=" + (this.table != null ? this.table.getName() : null) + ", unique=" + this.unique + ")";
	}

}