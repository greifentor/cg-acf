package de.ollie.chalkous9cp.service.so;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * A key object for folks.
 *
 * GENERATED CODE !!! DO NOT CHANGE !!!
 */
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Generated
public class FolkKeySO {

	public static final FolkKeySO DWARF = new FolkKeySO("DWARF");
	public static final FolkKeySO ELF = new FolkKeySO("ELF");
	public static final FolkKeySO HUMAN = new FolkKeySO("HUMAN");

	@NonNull
	private String name;

	public static FolkKeySO[] values() {
		return new FolkKeySO[] { //
				DWARF //
				ELF //
				HUMAN;
		};
	}

}