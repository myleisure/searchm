package searchm.search;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import search.RankProcessing;

@RunWith(MockitoJUnitRunner.class)
public class RankProcessingUT {
	
	@Spy
	private RankProcessing rankProcessing;
	
	@Test
	@SuppressWarnings("static-access")
	public void calculateRank_UT() {
		int N = 10, value = 5;
		int result = rankProcessing.calculateRank(N, value);
		assertTrue("Value doesn't march", result == 50);
		
		N = 10;
		value = 10;
		result = rankProcessing.calculateRank(N, value);
		assertTrue("Value doesn't march", result == 100);
		
		N = 10;
		value = 1;
		result = rankProcessing.calculateRank(N, value);
		assertTrue("Value doesn't march", result == 10);
		
		N = 4;
		value = 1;
		result = rankProcessing.calculateRank(N, value);
		assertTrue("Value doesn't march", result == 25);
	}

}
