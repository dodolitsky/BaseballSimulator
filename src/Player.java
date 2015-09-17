public class Player {
	String name;
	
	int plateAppearances;
	int singles;
	int doubles;
	int triples;
	int homeruns;
	int walks;
	int hbps;
	int outs;
	
	double singlePercentage;
	double doublePercentage;
	double triplePercentage;
	double homerunPercentage;
	double walkPercentage;
	double hbpPercentage;
	double outPercentage;
	
	double xbtPercentage;
	double stolenBaseOppPercentage;
	double stolenBasePercentage;
	
	public Player(int atBats, int hits, int doubles, int triples, int homeruns, int walks,
			int hbps, double xbtPercentage, int stolenBaseOpps, int stolenBases, int caughtStealing) {
		
		this.plateAppearances = atBats + walks + hbps;
		this.singles = hits - doubles - triples - homeruns;
		this.doubles = doubles;
		this.triples = triples;
		this.homeruns = homeruns;
		this.walks = walks;
		this.hbps = hbps;
		this.outs = atBats - hits;
		
		this.singlePercentage = calcPercentage(this.singles);
		this.doublePercentage = calcPercentage(this.doubles);
		this.triplePercentage = calcPercentage(this.triples);
		this.homerunPercentage = calcPercentage(this.homeruns);
		this.walkPercentage = calcPercentage(this.walks);
		this.hbpPercentage = calcPercentage(this.hbps);
		this.outPercentage = calcPercentage(this.outs);
		
		this.xbtPercentage = xbtPercentage;
		this.stolenBaseOppPercentage = (stolenBases + caughtStealing) / (stolenBaseOpps * 1.0);
		this.stolenBasePercentage = stolenBases / ((stolenBases + caughtStealing) * 1.0);
	}
	
	public Player(String name, int atBats, int hits, int doubles, int triples, int homeruns, int walks,
			int hbps, double xbtPercentage, int stolenBaseOpps, int stolenBases, int caughtStealing) {
		
		this.name = name;
		
		this.plateAppearances = atBats + walks + hbps;
		this.singles = hits - doubles - triples - homeruns;
		this.doubles = doubles;
		this.triples = triples;
		this.homeruns = homeruns;
		this.walks = walks;
		this.hbps = hbps;
		this.outs = atBats - hits;
		
		this.singlePercentage = calcPercentage(this.singles);
		this.doublePercentage = calcPercentage(this.doubles);
		this.triplePercentage = calcPercentage(this.triples);
		this.homerunPercentage = calcPercentage(this.homeruns);
		this.walkPercentage = calcPercentage(this.walks);
		this.hbpPercentage = calcPercentage(this.hbps);
		this.outPercentage = calcPercentage(this.outs);
		
		this.xbtPercentage = xbtPercentage;
		this.stolenBaseOppPercentage = (stolenBases + caughtStealing) / (stolenBaseOpps * 1.0);
		this.stolenBasePercentage = stolenBases / ((stolenBases + caughtStealing) * 1.0);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	private double calcPercentage(int number) {
		return number / (plateAppearances * 1.0);
	}

}