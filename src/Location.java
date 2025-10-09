class Location{
	
	char pieceJoueur = 'o';
	char pieceAdverse = 'x';
	
	/**Vérifie quelle sont les cases sur lesquel le joueur peut jouer
	 * @param tab : tableau à deux dimensions dans lequel il faut vérifier toute les cases
	 * @return coupsAuthorise : liste des coups autorisés
	 **/
	int[] authorizedLocation(char[][] tab){
		String[] coupsAuthorise = new String[tab.length * tab.length];
		int k = 0;
		for(int i = 0; i < tab.length; i++){
			for (int j = 0; j < tab.length; j++){
				boolean CoupLegal = false;
				if (tab[i][j] == ' ') {
					if (pieceAdverseACote(tab, i, j)){ //fonction qui renvoie true si il y a une piece acverse a coté, false sinon
						if (pieceEncadree(tab, i, j)){ //fonction qui renvoie true si il y a au moins une piece adverse d'encadrée, false sinon
							coupsAuthorise[k] = Integer.toString(i) + Integer.toString(j);
						}
					}
				}
			}
		}
		return coupsAuthorise;
	}
	/**
	 * Vérifie si il y a un pion adverse a coté de la case séléctionnée
	 * @param i, j : coordonnée de la case vérifiée
	 * @param tab : plateau de jeu
	 * @return resultat : true si il y a une piece adverse a coté, false sinon
	 **/
	boolean pieceAdverseACote(char[][] tab,int i,int j){
		boolean resultat = false;
		if (j != tab.length-1) {
			if (tab[i][j+1] == pieceAdverse){
				resultat = true;
			}
			
		}
		 
	}
	/**
	 * Vérifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case séléctionnée
	 * @param i, j : coordonnée de la case vérifiée
	 * @param tab : plateau de jeu
	 * @return resultat : true si au moins une pièce adverse est encadrée, false sinon
	 **/
	boolean pieceEncadree(char[][] tab,int i,int j){
		
	}
}
