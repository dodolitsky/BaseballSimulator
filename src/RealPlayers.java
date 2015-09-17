
public class RealPlayers {
	
	static Player[] realPlayers = {
	
		new Player("Brett Gardner", 357, 106, 23, 3, 11, 45, 3, 0.60, 210, 15, 3),
		
		new Player("Jacoby Ellsbury", 214, 62, 7, 0, 3, 21, 3, 0.45, 117, 14, 6),
		
		new Player("Mark Texeira", 327, 84, 21, 0, 24, 51, 6, 0.21, 117, 2, 0),
				
		new Player("Carlos Beltran", 264, 67, 20, 1, 7, 20, 2, 0.26, 90, 0, 0),
		
		new Player("Didi Gregorious", 311, 80, 13, 1, 5, 17, 6, 0.45, 136, 4, 2),
		
		new Player("Stephen Drew", 268, 50, 12, 0, 13, 27, 1, 0.17, 73, 0, 2),
		
		new Player("Chase Headley", 362, 98, 16, 1, 9, 26, 3, 0.38, 161, 0, 1),
		
		new Player("Brian McCann", 284, 71, 10, 1, 15, 28, 6, 0.21, 100, 0, 0),
		
		new Player("Alex Rodriguez", 330, 92, 14, 1, 24, 51, 6, 0.33, 146, 1, 0)
	
	};

	//static Player trout = new Player("Mike Trout", 359, 113, 19, 1, 31, 48, 8, 0.63);
	
	public static Player[] getLineup(int[] indexes) {
		Player[] lineup = new Player[indexes.length];
		
		for (int i = 0; i < indexes.length; i++) {
			lineup[i] = realPlayers[indexes[i]];
		}
		
		return lineup;
	}
	
}
