
public class Simulation {
	
	static String divider = "---------------------";
	
	static boolean comments = true;
	
	static PlayByPlay playByPlay;
	static BoxScore boxscore;
	
	static Player atThePlate;
	static Player firstBase;
	static Player secondBase;
	static Player thirdBase;
	
	static int runs;
	static int hits;
	static int outs;
	
	public static void main(String[] args) {
		
		int[] indexes = {1, 0, 8, 2, 7, 3, 6, 4, 5};
		Player[] yankees = RealPlayers.getLineup(indexes);
				
		simulateNGames(yankees, 1, true);
	}
	
	private static int simulateNInnings(Player[] lineup, int innings) {
		
		playByPlay = new PlayByPlay();
		boxscore = new BoxScore(lineup);
		
		runs = 0;
		hits = 0;
		
		int inning = 0;
		int current = 0;
		
		//playByPlay = "";
		
		while (inning < innings) {
			
			
			switch (inning) {
				case 0:
					playByPlay.addPlay("1st Inning\n");
					break;
					
				case 1:
					playByPlay.addPlay("2nd Inning\n");
					break;
					
				case 2:
					playByPlay.addPlay("3rd Inning\n");
					break;
					
				default:
					playByPlay.addPlay((inning + 1) + "th Inning\n");
					
			}
			
			
			outs = 0;
			
			firstBase = null;
			secondBase = null;
			thirdBase = null;
			
			int runsSoFar = runs;
			int hitsSoFar = hits;
			
			while (outs < 3) {
				
				atThePlate = lineup[current];
						
				checkSteal(false);
				
				if (outs == 3) {
					break;
				}
				
				if(checkSteal(true)) {
					checkSteal(false);
				}
				
				if (outs == 3) {
					break;
				}
				
				PlateAppearance result = determinePlateAppearance(atThePlate);
								
				switch (result) {
					
					case SINGLE:
						singlePA();
						break;
						
					case DOUBLE:
						doublePA();
						break;
						
					case TRIPLE:
						triplePA();
						break;
						
					case HOMERUN:
						homerunPA();
						break;
						
					case WALK:
						walkOrHbpPA(true);
						break;
						
					case HBP:
						walkOrHbpPA(false);
						break;
						
					case OUT:
						outPA();
						break;
						
						
				}
								
				current = (current + 1) % lineup.length;
				
			}
			
			int runsThisInning = runs - runsSoFar;
			int hitsThisInning = hits - hitsSoFar;
			int lobThisInning = 0;
			
			if (firstBase != null) {
				lobThisInning++;
			}
			if (secondBase != null) {
				lobThisInning++;
			}
			if (thirdBase != null) {
				lobThisInning++;
			}
									
			playByPlay.addPlay("\n" + hitsThisInning + " hits, " + runsThisInning +
					" runs scored, " + lobThisInning + " left on base");
			playByPlay.addPlay(divider);
			
			boxscore.setRunsByInning(runsThisInning);
			
			inning++;
		}
		
		if (comments) {
			System.out.println(playByPlay);
			System.out.println();
			System.out.println(boxscore);
		}
		
		return runs;
	}
	
	private static boolean checkSteal(boolean stealingSecond) {
		
		boolean stole = false;
		
		Player baseCurrent;
		Player baseNext;
		
		if (stealingSecond) {
			baseCurrent = firstBase;
			baseNext = secondBase;
		}
		
		else {
			baseCurrent = secondBase;
			baseNext = thirdBase;
		}
		
		if (baseCurrent != null && baseNext == null) {
			
			double random = Math.random();
			
			if (random < baseCurrent.stolenBaseOppPercentage) {
				
				double random2 = Math.random();
				
				if (random2 < baseCurrent.stolenBasePercentage) {
					
					playByPlay.addPlay(baseCurrent.name + " steals " + (stealingSecond ? "second ": "third"));
					
					boxscore.increment(baseCurrent, Stat.STEALS);
					
					baseNext = baseCurrent;
					baseCurrent = null;
					
					stole = true;
					
				}
				
				else {
					
					playByPlay.addPlay(baseCurrent.name + " caught stealing");
					
					boxscore.increment(baseCurrent, Stat.CAUGHT_STEALINGS);
					
					outs++;
					baseCurrent = null;

				}
				
			}
						
		}
		
		if (stealingSecond) {
			firstBase = baseCurrent;
			secondBase = baseNext;
		}
		
		else {
			secondBase = baseCurrent;
			thirdBase = baseNext;
		}
		
		return stole;
		
	}
	
	private static void singlePA() {
		
		playByPlay.addPlay(atThePlate.name + " singles");
		
		boxscore.increment(atThePlate, Stat.AT_BATS);
		boxscore.increment(atThePlate, Stat.HITS);
		
		hits++;
		
		if (thirdBase != null) {
			
			playByPlay.addToPrev(", " + thirdBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(thirdBase, Stat.RUNS);
			
			runs++;
			thirdBase = null;
			
		}
		
		if (secondBase != null) {
			
			thirdBase = secondBase;
			secondBase = null;
			
		}
		
		if (firstBase != null) {
			
			secondBase = firstBase;
			
		}
		
		firstBase = atThePlate;
		
		if (thirdBase != null) {
			
			double random = Math.random();
			
			if (random < thirdBase.xbtPercentage) {
				
				playByPlay.addToPrev(", " + thirdBase.name + " scores");
				
				boxscore.increment(atThePlate, Stat.RBIS);
				boxscore.increment(thirdBase, Stat.RUNS);
				
				runs++;
				thirdBase = null;
				
			}
			
			else {
				
				playByPlay.addToPrev(", " + thirdBase.name + " advances to third");
				
			}
			
		}
		
		if (secondBase != null && thirdBase == null) {
			
			double random = Math.random();
			
			if (random < secondBase.xbtPercentage) {
				
				playByPlay.addToPrev(", " + secondBase.name + " advances to third");
				
				thirdBase = secondBase;
				secondBase = null;
				
			}
			
			else {
				
				playByPlay.addToPrev(", " + secondBase.name + " advances to second");
				
			}
			
		}
		
		else if (secondBase != null && thirdBase != null) {
			
			playByPlay.addToPrev(", " + secondBase.name + " advances to second");
			
		}
		
	}
	
	private static void doublePA() {

		playByPlay.addPlay(atThePlate.name + " doubles");
		
		boxscore.increment(atThePlate, Stat.AT_BATS);
		boxscore.increment(atThePlate, Stat.HITS);
		boxscore.increment(atThePlate, Stat.DOUBLES);
		
		hits++;
		
		if (thirdBase != null) {
			
			playByPlay.addToPrev(", " + thirdBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(thirdBase, Stat.RUNS);
			
			runs++;
			thirdBase = null;
			
		}
		
		if (secondBase != null) {
			
			playByPlay.addToPrev(", " + secondBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(secondBase, Stat.RUNS);
			
			runs++;
			
		}
		
		if (firstBase != null) {
			
			thirdBase = firstBase;
			
		}
		
		firstBase = null;
		secondBase = atThePlate;
		
		if (thirdBase != null) {
			
			double random = Math.random();
			
			if (random < thirdBase.xbtPercentage) {
				
				playByPlay.addToPrev(", " + thirdBase.name + " scores");
				
				boxscore.increment(atThePlate, Stat.RBIS);
				boxscore.increment(thirdBase, Stat.RUNS);
				
				runs++;
				thirdBase = null;
				
			}
			
			else {
				
				playByPlay.addToPrev(", " + thirdBase.name + " advances to third");
				
			}
			
		}
		
	}
	
	private static void triplePA() {

		playByPlay.addPlay(atThePlate.name + " triples");
		
		boxscore.increment(atThePlate, Stat.AT_BATS);
		boxscore.increment(atThePlate, Stat.HITS);
		boxscore.increment(atThePlate, Stat.TRIPLES);
		
		hits++;
		
		if (thirdBase != null) {
			
			playByPlay.addToPrev(", " + thirdBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(thirdBase, Stat.RUNS);
			
			runs++;
			
		}
		
		if (secondBase != null) {
			
			playByPlay.addToPrev(", " + secondBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(secondBase, Stat.RUNS);
			
			runs++;
			
		}
		
		if (firstBase != null) {
			
			playByPlay.addToPrev(", " + firstBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(firstBase, Stat.RUNS);
			
			runs++;
			
		}
		
		firstBase = null;
		secondBase = null;
		thirdBase = atThePlate;
		
	}
	
	private static void homerunPA() {

		playByPlay.addPlay(atThePlate.name + " homers");
		
		boxscore.increment(atThePlate, Stat.AT_BATS);
		boxscore.increment(atThePlate, Stat.HITS);
		boxscore.increment(atThePlate, Stat.HOMERUNS);
		
		hits++;

		if (thirdBase != null) {
			
			playByPlay.addToPrev(", " + thirdBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(thirdBase, Stat.RUNS);
			
			runs++;
			
		}
		
		if (secondBase != null) {
			
			playByPlay.addToPrev(", " + secondBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(secondBase, Stat.RUNS);
			
			runs++;
			
		}
		
		if (firstBase != null) {
			
			playByPlay.addToPrev(", " + firstBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(firstBase, Stat.RUNS);
			
			runs++;
			
		}
		
		boxscore.increment(atThePlate, Stat.RBIS);
		boxscore.increment(atThePlate, Stat.RUNS);
		
		runs++;
		firstBase = null;
		secondBase = null;
		thirdBase = null;
		
	}
	
	private static void walkOrHbpPA(boolean walk) {

		if (walk) {
			
			playByPlay.addPlay(atThePlate.name + " walks");
			
			boxscore.increment(atThePlate, Stat.WALKS);
			
		}
		
		else {
			
			playByPlay.addPlay(atThePlate.name + " hit by pitch");
			
			boxscore.increment(atThePlate, Stat.HBPS);
			
		}
		
		if (firstBase != null && secondBase != null && thirdBase != null) {
			
			playByPlay.addToPrev(", " + thirdBase.name + " scores");
			
			boxscore.increment(atThePlate, Stat.RBIS);
			boxscore.increment(thirdBase, Stat.RUNS);
			
			runs++;
			
		}
		
		if (firstBase != null && secondBase != null) {
			
			playByPlay.addToPrev(", " + secondBase.name + " advances to third");
			
			thirdBase = secondBase;
			
		}
		
		if (firstBase != null) {
			
			playByPlay.addToPrev(", " + firstBase.name + " advances to second");
			
			secondBase = firstBase;
			
		}
		
		firstBase = atThePlate;
		
	}
	
	private static void outPA() {

		boxscore.increment(atThePlate, Stat.AT_BATS);

		playByPlay.addPlay(atThePlate.name + " gets out");
		
		outs++;
		
	}
	
	private static PlateAppearance determinePlateAppearance(Player atThePlate) {
		double random = Math.random();
		
		double percentage = atThePlate.singlePercentage;
		
		if (random < percentage) {
			return PlateAppearance.SINGLE;
		}
		
		percentage += atThePlate.doublePercentage;
		
		if (random < percentage) {
			return PlateAppearance.DOUBLE;
		}
		
		percentage += atThePlate.triplePercentage;
		
		if (random < percentage) {
			return PlateAppearance.TRIPLE;
		}
		
		percentage += atThePlate.homerunPercentage;
		
		if (random < percentage) {
			return PlateAppearance.HOMERUN;
		}
		
		percentage += atThePlate.walkPercentage;
		
		if (random < percentage) {
			return PlateAppearance.WALK;
		}
		
		percentage += atThePlate.hbpPercentage;
		
		if (random < percentage) {
			return PlateAppearance.HBP;
		}
		
		return PlateAppearance.OUT;
	}
	
	private static int simulateGame(Player[] lineup) {
		return simulateNInnings(lineup, 9);
	}
	
	public static double simulateNGames(Player[] lineup, int games) {
		return simulateNGames(lineup, games, false);
		
	}
	
	public static double simulateNGames(Player[] lineup, int games, boolean comments) {
		Simulation.comments = comments;
		int totalRuns = 0;
		for (int i = 0; i < games; i++) {
			totalRuns += simulateGame(lineup);
		}
		
		return totalRuns * 1.0 / games;
	}

}
