package searchm.search;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import search.SearchFileEngineProcessing;

@RunWith(MockitoJUnitRunner.class)
public class SearchFileEngineProcessingUT {
	
	@Spy
	private SearchFileEngineProcessing searchFileEngineProcessing;
	
	private static final String FILE_DIRECTORY = "./directoryTU";
	
	@Test
	public void startIndexingFile_UT() {
		Path pathDirectory = getPathDirectory(FILE_DIRECTORY);
		if(pathDirectory != null) {
			long numberFiles = searchFileEngineProcessing.startIndexingFile(pathDirectory);
			
			assertTrue("Number files don't match the expected value", numberFiles == 3);
			assertTrue("Error when starting indexation", searchFileEngineProcessing.getWordsIndexMapping().size() > 0);
		} else {
			assertTrue("Path directory not found", false);
		}
	}
	
	@Test
	public void searchInDirectory_UT() {
		Path pathDirectory = getPathDirectory(FILE_DIRECTORY);
		String words = "hello world";
		
		if(pathDirectory != null) {
			searchFileEngineProcessing.startIndexingFile(pathDirectory);
			
			words = "hello world";
			TreeMap<String, Integer> result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result.size() == 2);
			
			words = "tekken";
			result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result.size() == 0);
			
			words = "tekken";
			result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result.size() == 0);
			
			words = ":quit";
			result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result == null);
		} else {
			assertTrue("Path directory not found", false);
		}
	}
	
	@Test
	public void displayResult_UT() {
		Path pathDirectory = getPathDirectory(FILE_DIRECTORY);
		String words = "hello world";
		
		if(pathDirectory != null) {
			searchFileEngineProcessing.startIndexingFile(pathDirectory);
			
			words = "hello world";
			TreeMap<String, Integer> result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result.size() == 2);
			searchFileEngineProcessing.displayResult(result);
			
			words = "tekken";
			result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result.size() == 0);
			searchFileEngineProcessing.displayResult(result);
			
			words = "tekken";
			result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result.size() == 0);
			searchFileEngineProcessing.displayResult(result);
			
			words = ":quit";
			result = searchFileEngineProcessing.searchInDirectory(words);
			assertTrue("Result values don't match", result == null);
			searchFileEngineProcessing.displayResult(result);
		} else {
			assertTrue("Path directory not found", false);
		}
	}
	
	private Path getPathDirectory(String pathDir) {
		try {
			return Paths.get(ClassLoader.getSystemResource(pathDir).toURI());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
