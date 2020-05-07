package search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SearchFileEngineProcessing {
	
	private static final int LIMIT_MAX = 100;
	
	// Using the inverted index file indexing
	private final Map<String , Set<String>> wordsIndexMapping = new HashMap<>();
	
	private Map<String, Integer> searchResutOccurencesMap = new HashMap<>();
	
	private int numerator = 1;
	
	/**
	 * 
	 * @param pathDirectory
	 */
	public long startIndexingFile(Path pathDirectory) {
		long numberFiles = 0;
		try {
			// Get number of files
			numberFiles = Files.list(pathDirectory).filter(path -> path.toFile().isFile())
												   .collect(Collectors.counting());
			//Initialize wordsIndexMapping items
			Files.newDirectoryStream(pathDirectory).forEach(this::processIndexFile);
		} catch(IOException ex) {
			ex.printStackTrace();
			numberFiles = 0;
		} 
		return numberFiles;
	}
	
	/**
	 * 
	 * @param words
	 */
	public TreeMap<String, Integer> searchInDirectory(String words) {
		if(!":quit".equals(words)) {
			initSearchParameters();
			
			words = words.trim().replaceAll(" +", " ");
			String [] text = words.split(" ");
			Set<String> textSet = new HashSet<String>(Arrays.asList(text));
			numerator = textSet.size();
			
			for(String word : textSet) {
				Set<String> referenceFiles = wordsIndexMapping.get(word);
				if(referenceFiles != null) {
					for(String fileName : referenceFiles) {
						if(searchResutOccurencesMap.get(fileName) == null) {
							searchResutOccurencesMap.put(fileName, 0);
						}
						searchResutOccurencesMap.put(fileName, searchResutOccurencesMap.get(fileName) + 1);
					}
				}
			}
			
			if(searchResutOccurencesMap.size() == 0) {
				return new TreeMap<String, Integer>();
			} 
			return ValueComparator.sortMapByValue(searchResutOccurencesMap);
		}
		return null;
	}
	
	/**
	 * Reset the value of searchResutOccurencesMap
	 */
	private void initSearchParameters() {
		searchResutOccurencesMap = new HashMap<>();
	}
	
	public void displayResult(TreeMap<String, Integer> searchResutValueMap) {
		if(searchResutValueMap != null) {
			if(searchResutValueMap.size() == 0) {
				System.out.println("no matches found");
			} else {
				int j = 0;
				for(Entry<String, Integer> entry : searchResutValueMap.entrySet()) {
					if(j < LIMIT_MAX) {
						System.out.println(entry.getKey() + " : " + RankProcessing.calculateRank(numerator, entry.getValue()) + "%");
						j++;
					} else {
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param filePath
	 */
	private void processIndexFile(Path filePath) {
		try {
			if(filePath.toFile().isFile()) {
				Files.lines(filePath).map(line -> line.trim().replaceAll(" +", " "))
								     .forEach(text -> processWordsMapping(text,filePath.getFileName().toString()));
			}
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param lineText
	 * @param indexFile
	 */
	private void processWordsMapping(String lineText, String nameFile) {
		String [] text = lineText.split(" ");
		
		if(text.length > 0) {
			for(String word : text) {
				if(!word.isBlank()) {
					if(wordsIndexMapping.get(word) == null) {
						Set<String> occurencesFile = new HashSet<>();
						occurencesFile.add(nameFile);
						wordsIndexMapping.put(word, occurencesFile);
					} else {
						wordsIndexMapping.get(word).add(nameFile);
					}
				}
			}
		}
	}

	public Map<String, Set<String>> getWordsIndexMapping() {
		return wordsIndexMapping;
	}
	
	
	
	
	
	
	

}
