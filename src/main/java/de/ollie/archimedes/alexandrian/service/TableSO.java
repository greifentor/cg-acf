package de.ollie.archimedes.alexandrian.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * A container for table service objects.
 *
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@Generated
@NoArgsConstructor
public class TableSO {

	@NonNull
	private String name;
	@NonNull
	private List<ColumnSO> columns = new ArrayList<>();
	@NonNull
	private List<ForeignKeySO> foreignKeys = new ArrayList<>();
	private TableMetaInfo metaInfo = new TableMetaInfo();
	private TableGUIInfo guiInfo = new TableGUIInfo();

	public TableSO addOptions(OptionSO... options) {
		for (OptionSO option : options) {
			this.getMetaInfo().getOptions().add(option);
		}
		return this;
	}

}