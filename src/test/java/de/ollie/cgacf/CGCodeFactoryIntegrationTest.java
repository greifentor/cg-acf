package de.ollie.cgacf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import archimedes.legacy.scheme.ArchimedesObjectFactory;
import archimedes.legacy.scheme.Diagramm;
import archimedes.model.DataModel;
import archimedes.scheme.xml.ModelXMLReader;

/**
 * An integration test for class "CGCodeFactory".
 * 
 * @author ollie (09.10.2019)
 *
 */
@ExtendWith(MockitoExtension.class)
public class CGCodeFactoryIntegrationTest {

	private static final String OUTPUT_PATH = "target/test/output/src/main/java";

	private CGCodeFactory unitUnderTest;

	@BeforeEach
	public void setUp() {
		System.setProperty("corentx.util.Str.suppress.html.note", "true");
		System.setProperty("corent.base.StrUtil.suppress.html.note", "true");
		this.unitUnderTest = new CGCodeFactory();
	}

	@DisplayName("Generate method creates correct output.")
	@ParameterizedTest
	@ValueSource(strings = { "de/ollie/chalkous9cp/service/FolkService.java",
			"de/ollie/chalkous9cp/service/impl/FolkServiceImpl.java",
			"de/ollie/chalkous9cp/service/so/FolkKeySO.java" })
	public void generate_PassADataModel_CreatesCorrectFolkServiceInterface(String fileName) throws Exception {
		if (new File(OUTPUT_PATH).exists()) {
			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.peek(System.out::println).forEach(File::delete);
		}
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		DataModel dm = (Diagramm) reader.read("src/test/resources/chalkous9.xml");
		this.unitUnderTest.setDataModel(dm);
		this.unitUnderTest.generate(OUTPUT_PATH);
		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", fileName)));
		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, fileName)));
		assertEquals(expected.toString(), generated.toString());
	}

	@DisplayName("Generate method does not override an already existing file if is not marked to override.")
	@Test
	public void generate_FileToGenerateAlreadyExistingMarkedAsNotToOverride_DoesNotOverrideTheFile() throws Exception {
		if (new File(OUTPUT_PATH).exists()) {
			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.peek(f -> System.out.println("deleted: " + f.getAbsolutePath())).forEach(File::delete);
		}
		String fileName = "de/ollie/chalkous9cp/service/FolkService.java";
		String expected = "Do not override this !!!";
		new File(OUTPUT_PATH + "/de/ollie/chalkous9cp/service").mkdirs();
		Files.write(Paths.get(OUTPUT_PATH, fileName), expected.getBytes(), StandardOpenOption.WRITE,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		DataModel dm = (Diagramm) reader.read("src/test/resources/chalkous9.xml");
		this.unitUnderTest.setDataModel(dm);
		this.unitUnderTest.generate(OUTPUT_PATH);
		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, fileName)));
		assertEquals(expected.toString(), generated.toString());
	}

	@DisplayName("Generate method overrides an already existing file if is marked as to override.")
	@Test
	public void generate_FileToGenerateAlreadyExistingMarkedAsToOverride_DoesNotOverrideTheFile() throws Exception {
		if (new File(OUTPUT_PATH).exists()) {
			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.peek(f -> System.out.println("deleted: " + f.getAbsolutePath())).forEach(File::delete);
		}
		String fileName = "de/ollie/chalkous9cp/service/FolkService.java";
		String expected = "Do not override this !!!\n" + CGCodeFactory.NOT_TO_OVERRIDE_MARK;
		new File(OUTPUT_PATH + "/de/ollie/chalkous9cp/service").mkdirs();
		Files.write(Paths.get(OUTPUT_PATH, fileName), expected.getBytes(), StandardOpenOption.WRITE,
				StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		DataModel dm = (Diagramm) reader.read("src/test/resources/chalkous9.xml");
		this.unitUnderTest.setDataModel(dm);
		this.unitUnderTest.generate(OUTPUT_PATH);
		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, fileName)));
		assertNotEquals(expected.toString(), generated.toString());
	}

}