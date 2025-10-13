public class Location{
	
	char pieceJoueur = 'o';
	char pieceAdverse = 'x';
	
	/**Verifie quelle sont les cases sur lesquel le joueur peut jouer
	 * @param tab : tableau à deux dimensions dans lequel il faut verifier toute les cases
	 * @return coupsAuthorise : liste des coups autorises
	 **/
	boolean[][] authorizedLocation(char[][] tab){
		boolean[][] coupsAuthorise = new boolean[tab.length][tab.length];
		int k = 0;
		for(int i = 0; i < tab.length; i++){
			for (int j = 0; j < tab.length; j++){
				if (tab[i][j] == ' ') {
					coupsAuthorise[i][j] = pieceEncadree(tab, i, j);
				}
			}
		}
		return coupsAuthorise;
	}

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return resultat : true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadree(char[][] tab,int i,int j){
		boolean resultat = false;
		if (pieceEncadreeDroite(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeBas(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeGauche(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeHaut(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeDroiteBas(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeGaucheBas(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeGaucheHaut(tab, i, j)){
			resultat = true;
		}else if (pieceEncadreeDroiteHaut(tab, i, j)){
			resultat = true;
		}
		return resultat;
	}
	
	

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur la droite de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeDroite(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (j + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i][j + decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i][j + decalage];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur le dessous de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeBas(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur la gauche de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeGauche(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (j - decalage < 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i][j - decalage];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur le dessus de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeHaut(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i - decalage < 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur la diagonale droite-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeDroiteBas(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (j + decalage < tab.length && j + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j + decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j + decalage];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeGaucheBas(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i + decalage < tab.length && j - decalage < 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j - decalage];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeGaucheHaut(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i - decalage < 0 && j - decalage < 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j - decalage];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}

	/**
	 * Verifie si il y a un pion adverse d'encadré par le placement d'un pion sur la case selectionnee (sur la diagonale droite-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return true si au moins une pièce adverse est encadree, false sinon
	 **/
	boolean pieceEncadreeDroiteHaut(char[][] tab,int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i - decalage < 0 && j + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j + decalage];
		}
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}


}
