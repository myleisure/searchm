package search;

public class RankProcessing {
	
	public static int calculateRank(int N, int value) {
		double vFraction = ((value * 1.)/N) * 100;
		return roundingValue(vFraction);
	}
	
	private static int roundingValue(double value) {
		return (int)Math.round(value);
	}

}
