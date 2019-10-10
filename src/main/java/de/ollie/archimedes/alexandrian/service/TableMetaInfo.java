package de.ollie.archimedes.alexandrian.service;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A container for table meta information like stereo types and options.
 *
 * @author ollie
 *
 */
@Data
@Accessors(chain = true)
public class TableMetaInfo {

	private List<OptionSO> options;
	private List<StereotypeSO> stereotypes;

}