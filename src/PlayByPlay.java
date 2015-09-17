import java.util.ArrayList;
import java.util.List;


public class PlayByPlay {

	List<String> plays = new ArrayList<String>();
	
	public void addPlay(String play) {
		plays.add(play);
	}
	
	public void addToPrev(String play) {
		plays.add(plays.remove(plays.size() - 1) + play);
	}
	
	@Override
	public String toString() {
		
		String playByPlay = "";
		
		playByPlay += "Play-By-Play\n---------------------\n";
		
		for (String play : plays) {
			playByPlay += play + "\n";
		}
		
		return playByPlay;
	}
	
}
