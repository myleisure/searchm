package search;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {

	public static void main(String[] args) {
		if (args. length == 0 ) {
		    throw new IllegalArgumentException( "No directory given to index." );
		}
		
		final String indexableDirectory = args[0];
		SearchFileEngineProcessing fileMetaDataIndexing = new SearchFileEngineProcessing();
		long numberFiles = fileMetaDataIndexing.startIndexingFile(Paths.get(indexableDirectory));
		
		if(numberFiles > 0) {
			System.out.println(numberFiles + " files read in directory " + indexableDirectory);
			
			try (Scanner keyboard = new Scanner(System. in )) {
				while (true) {
					System. out .print( "search> " );
					final String line = keyboard.nextLine();
					if(!line.isEmpty() && !line.isBlank()) { 
						TreeMap<String, Integer> result = fileMetaDataIndexing.searchInDirectory(line);
						if(result != null) {
							fileMetaDataIndexing.displayResult(result);
						} else {
							break;
						}
					}
				}
			}
		} else {
			throw new RuntimeException("Error when indexing directory " + indexableDirectory + " OR directory empty");
		}
	}

}
