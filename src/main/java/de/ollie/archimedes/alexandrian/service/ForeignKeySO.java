package de.ollie.archimedes.alexandrian.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

/**
 * A container for foreign key service objects.
 *
 * @author ollie
 *
 */
@Accessors(chain = true)
@Data
@NoArgsConstructor
public class ForeignKeySO {

	@NonNull
	private List<ReferenceSO> references = new ArrayList<>();

}