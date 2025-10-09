class PlayerPointGestion {
	
	char currentPlayer = 'x'; // le joueur avec les pions 'x' commence
	
	void principal(){
		// tests pointsCalculation
		// exemple de grille de jeu gridExample
		// pointsCalculation(gridExample);
		
		// tests pointsCalculation
		System.out.println(currentPlayer);
		switchPlayer();
		System.out.println(currentPlayer);
	}
	
	/**
	 * Calcule les points des 2 joueurs (IA ou Humains) en parcourant la grille de jeu
	 * @param int[][] grid : grille principale du jeu
	 * @return int[] pointsTab : tableau de 2 elements. - 1er element : score des pions 'x'
	 * 													- 2eme element : score des pions 'o'
	 * @author S. GIRARDEAU
	 */
	int[] pointsCalculation(char[][] grid){
		int[] pointsTab = new int[2]; // tableau contenant le score des deux joueurs
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'x') {
					pointsTab[0]++; // si le pion trouve est un 'x' on incremente le premier element
				} else if (grid[i][j] == 'o') {
					pointsTab[1]++; // si le pion trouve est un 'o' on incremente le deuxieme element
				}
			}
		}
		return pointsTab;
	}
	
	/**
	 * changement du joueur actuel pour passer au joueur suivant
	 * On utilise la variable global currentPlayer
	 * @author S. GIRARDEAU
	 */
	void switchPlayer(){
		if (currentPlayer == 'x'){
			currentPlayer = 'o';
		} else {
			currentPlayer = 'x';
		}
	}
}
