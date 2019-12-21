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
public class ReferenceSO {

	@NonNull
	private ColumnSO referencingColumn;
	@NonNull
	private ColumnSO referencedColumn;

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ReferenceSO)) {
			return false;
		}
		return toString().equals(o.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return "ReferenceSO(" //
				+ "referencedColumn=" + (referencedColumn != null ? referencedColumn.getNameWithTable() : "null") //
				+ ", referencingColumn=" + (referencingColumn != null ? referencingColumn.getNameWithTable() : "null")
				+ ")";
	}

}