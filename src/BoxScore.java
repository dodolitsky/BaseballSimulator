import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BoxScore {
	Player[] lineup;
	
	Map<Player, Integer> atBats = new HashMap<Player, Integer>();
	Map<Player, Integer> runs = new HashMap<Player, Integer>();
	Map<Player, Integer> hits = new HashMap<Player, Integer>();
	Map<Player, Integer> rbis = new HashMap<Player, Integer>();
	Map<Player, Integer> walks = new HashMap<Player, Integer>();
	
	Map<Player, Integer> doubles = new HashMap<Player, Integer>();
	Map<Player, Integer> triples = new HashMap<Player, Integer>();
	Map<Player, Integer> homeruns = new HashMap<Player, Integer>();
	Map<Player, Integer> steals = new HashMap<Player, Integer>();
	Map<Player, Integer> caughtStealings = new HashMap<Player, Integer>();
	Map<Player, Integer> hbps = new HashMap<Player, Integer>();
	
	int totalAtBats;
	int totalRuns;
	int totalHits;
	int totalRbis;
	int totalWalks;
	
	int totalDoubles;
	int totalTriples;
	int totalHomeruns;
	int totalSteals;
	int totalCaughtStealings;
	int totalHbps;
	
	List<Integer> runsByInning = new ArrayList<Integer>();
	
	public BoxScore(Player[] lineup) {
		this.lineup = lineup;
		
		for (Player player : this.lineup) {
			atBats.put(player, 0);
			runs.put(player, 0);
			hits.put(player, 0);
			rbis.put(player, 0);
			walks.put(player, 0);
			doubles.put(player, 0);
			triples.put(player, 0);
			homeruns.put(player, 0);
			steals.put(player, 0);
			caughtStealings.put(player, 0);
			hbps.put(player, 0);
		}
		
	}
	
	public void setRunsByInning(int runs) {
		runsByInning.add(runs);
	}
	
	public void increment(Player player, Stat stat) {
		switch (stat) {
			case AT_BATS:			incrementHelper(player, atBats); break;
			case RUNS:				incrementHelper(player, runs); break;
			case HITS:				incrementHelper(player, hits); break;
			case RBIS:				incrementHelper(player, rbis); break;
			case WALKS:				incrementHelper(player, walks); break;
			case DOUBLES:			incrementHelper(player, doubles); break;
			case TRIPLES:			incrementHelper(player, triples); break;
			case HOMERUNS:			incrementHelper(player, homeruns); break;
			case STEALS:			incrementHelper(player, steals); break;
			case CAUGHT_STEALINGS:	incrementHelper(player, caughtStealings); break;
			case HBPS:				incrementHelper(player, hbps); break;
		}
	}
	
	private void incrementHelper(Player player, Map<Player, Integer> stat) {
		stat.put(player, stat.get(player) + 1);
	}
	
	private void calculateTotals() {
		totalAtBats = total(atBats);
		totalRuns = total(runs);
		totalHits = total(hits);
		totalRbis = total(rbis);
		totalWalks = total(walks);
		
		totalDoubles = total(doubles);
		totalTriples = total(triples);
		totalHomeruns = total(homeruns);
		totalSteals = total(steals);
		totalCaughtStealings = total(caughtStealings);
		totalHbps = total(hbps);
	}
	
	private int total(Map<Player, Integer> stat) {
		int total = 0;
		for (Player player : lineup) {
			total += stat.get(player);
		}
		
		return total;
	}
	
	@Override
	public String toString() {
		
		calculateTotals();
		
		String boxscore = "";
		
		boxscore += "Box Score\n---------------------\n";
		
		for (int i = 1; i < runsByInning.size() + 1; i++) {
			boxscore += i + "\t";
		}
		boxscore += "-\tR\tH\n";
		
		for (Integer runs : runsByInning) {
			boxscore += runs + "\t";
		}
		boxscore += "-\t" + totalRuns + "\t" + totalHits + "\n\n";
		
		boxscore += stringWithTab(24, "Name") + "AB\tR\tH\tRBI\tBB\n";
		for (Player player : lineup) {
			boxscore += playerStatsString(player) + "\n";
		}
		
		boxscore += stringWithTab(24, "Totals") + totalAtBats + "\t" + totalRuns + "\t" +
				totalHits + "\t" + totalRbis + "\t" + totalWalks + "\n\n";
				
		if (totalDoubles > 0) {
			boxscore += "2B: " + additionalStatsString(doubles) + "\n";
		}
		
		if (totalTriples > 0) {
			boxscore += "3B: " + additionalStatsString(triples) + "\n";
		}
		
		if (totalHomeruns > 0) {
			boxscore += "HR: " + additionalStatsString(homeruns) + "\n";
		}
		
		if (totalSteals > 0) {
			boxscore += "SB: " + additionalStatsString(steals) + "\n";
		}
		
		if (totalCaughtStealings > 0) {
			boxscore += "CS: " + additionalStatsString(caughtStealings) + "\n";
		}
		
		if (totalHbps > 0) {
			boxscore += "HBP: " + additionalStatsString(hbps) + "\n";
		}
		
		return boxscore;
	}
	
	private String additionalStatsString(Map<Player, Integer> stat) {
		String statsString = "";
			
		boolean semicolon = false;
		
		for (Player player : lineup) {
			
			int num = stat.get(player);
			if (num == 1) {
				statsString += (semicolon ? "; " : "") + player.name;
				semicolon = true;
			}
			else if (num > 1) {
				statsString += (semicolon ? "; " : "") + player.name + " " + num;
				semicolon = true;
			}
		}
		
		return statsString;
	}
	
	private String playerStatsString(Player player) {
		String statsString = "";
		statsString += stringWithTab(24, player.name);
		statsString += atBats.get(player) + "\t";
		statsString += runs.get(player) + "\t";
		statsString += hits.get(player) + "\t";
		statsString += rbis.get(player) + "\t";
		statsString += walks.get(player);
		
		return statsString;
	}
	
	static String stringWithTab(int length, String string) {
		String tab = " ";
		for (int i = 0; i < length - string.length() - 1; i++) {
			tab += " ";
		}
		return string.substring(0, Math.min(string.length(), length-1)) + tab;
	}

}
