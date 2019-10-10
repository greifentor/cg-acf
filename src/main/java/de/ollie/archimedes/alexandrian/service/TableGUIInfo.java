package de.ollie.archimedes.alexandrian.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A container for GUI information of table service objects.
 *
 * @author ollie
 *
 */
@Data
@Accessors(chain = true)
public class TableGUIInfo {

	private int x;
	private int y;

}