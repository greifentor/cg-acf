package de.ollie.cgacf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

	@Test
	public void generate_PassADataModel_CreatesCorrectFolkServiceInterface() throws Exception {
		if (new File(OUTPUT_PATH).exists()) {
			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
					.peek(System.out::println).forEach(File::delete);
		}
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		DataModel dm = (Diagramm) reader.read("src/test/resources/chalkous9.xml");
		this.unitUnderTest.setDataModel(dm);
		this.unitUnderTest.generate(OUTPUT_PATH);
		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
		String expected = new String(Files.readAllBytes(
				Paths.get("src/test/resources/", "de", "ollie", "chalkous9cp", "service", "FolkService.java")));
		String generated = new String(Files
				.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "chalkous9cp", "service", "FolkService.java")));
		assertEquals(expected.toString(), generated.toString());
	}

//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackDBOFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "persistence", "dbo", "RackDBO.java")));
//		String generated = new String(Files
//				.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence", "dbo", "RackDBO.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackRepositoryFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"persistence", "repository", "RackRepository.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence", "repository", "RackRepository.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookRepositoryFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"persistence", "repository", "BookRepository.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence", "repository", "BookRepository.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackDTOFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "rest", "v1", "dto", "RackDTO.java")));
//		String generated = new String(Files
//				.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "rest", "v1", "dto", "RackDTO.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackDTOConverterFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"rest", "v1", "converter", "RackDTOConverter.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "rest", "v1", "converter", "RackDTOConverter.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookDTOConverterFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"rest", "v1", "converter", "BookDTOConverter.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "rest", "v1", "converter", "BookDTOConverter.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackSOFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "service", "so", "RackSO.java")));
//		String generated = new String(
//				Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service", "so", "RackSO.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackDBOConverterFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"persistence", "converter", "RackDBOConverter.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence", "converter", "RackDBOConverter.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookDBOConverterFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"persistence", "converter", "BookDBOConverter.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence", "converter", "BookDBOConverter.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackPersistenceAdapterFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"persistence", "adapter", "RackRDBMSPersistenceAdapter.java")));
//		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence",
//				"adapter", "RackRDBMSPersistenceAdapter.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookPersistenceAdapterFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"persistence", "adapter", "BookRDBMSPersistenceAdapter.java")));
//		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "persistence",
//				"adapter", "BookRDBMSPersistenceAdapter.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackPersistencePortFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"service", "persistence", "port", "RackPersistencePort.java")));
//		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service",
//				"persistence", "port", "RackPersistencePort.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookPersistencePortFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"service", "persistence", "port", "BookPersistencePort.java")));
//		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service",
//				"persistence", "port", "BookPersistencePort.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectApplicationClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files
//				.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library", "LibraryApplication.java")));
//		String generated = new String(
//				Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "LibraryApplication.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackServiceInterfaceFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "service", "RackService.java")));
//		String generated = new String(
//				Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service", "RackService.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookServiceInterfaceFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "service", "BookService.java")));
//		String generated = new String(
//				Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service", "BookService.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRackServiceImplClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "service", "impl", "RackServiceImpl.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service", "impl", "RackServiceImpl.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectBookServiceImplClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "service", "impl", "BookServiceImpl.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service", "impl", "BookServiceImpl.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectRESTControllerClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"rest", "v1", "controller", "RackRESTController.java")));
//		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "rest", "v1",
//				"controller", "RackRESTController.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectInitialApplicationPropertiesFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "application.properties")));
//		String generated = new String(
//				Files.readAllBytes(Paths.get(OUTPUT_PATH, "..", "resources", "application.properties")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectInitialDBXMLFile() throws Exception {
//		String outputPath = "target/test/output";
//		if (new File(outputPath).exists()) {
//			Files.walk(Paths.get(outputPath)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(outputPath);
//		assertThat(new File(outputPath).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "InitialDB.xml")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(outputPath, "..", "resources", "db", "change-log", "InitialDB", "InitialDB.xml")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectPersistenceExceptionClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(Paths.get("src/test/resources/", "de", "ollie", "library",
//				"service", "persistence", "exception", "PersistenceException.java")));
//		String generated = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service",
//				"persistence", "exception", "PersistenceException.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectResultPageDTOClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "rest", "v1", "dto", "ResultPageDTO.java")));
//		String generated = new String(Files.readAllBytes(
//				Paths.get(OUTPUT_PATH, "de", "ollie", "library", "rest", "v1", "dto", "ResultPageDTO.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}
//
//	@Test
//	public void generate_PassADataModel_CreatesCorrectResultPageSOClassFile() throws Exception {
//		if (new File(OUTPUT_PATH).exists()) {
//			Files.walk(Paths.get(OUTPUT_PATH)).sorted(Comparator.reverseOrder()).map(Path::toFile)
//					.peek(System.out::println).forEach(File::delete);
//		}
//		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
//		DataModel dm = (Diagramm) reader.read("src/test/resources/library.xml");
//		this.unitUnderTest.setDataModel(dm);
//		this.unitUnderTest.generate(OUTPUT_PATH);
//		assertThat(new File(OUTPUT_PATH).exists(), equalTo(true));
//		String expected = new String(Files.readAllBytes(
//				Paths.get("src/test/resources/", "de", "ollie", "library", "service", "so", "ResultPageSO.java")));
//		String generated = new String(Files
//				.readAllBytes(Paths.get(OUTPUT_PATH, "de", "ollie", "library", "service", "so", "ResultPageSO.java")));
//		assertEquals(expected.toString(), generated.toString());
//	}

}
