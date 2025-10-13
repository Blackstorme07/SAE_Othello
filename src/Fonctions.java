public class Fonctions{
	
	char pieceJoueur = 'o';
	char pieceAdverse = 'x';
	
	
	void principal(){
		System.out.println(">>> DÉBUT DU PROGRAMME <<<");
		int taille;
		do {
			taille = SimpleInput.getInt("Quelle taille voulez vous que le cote du plateau soit-il (4-6-8-10-12-16) ? ");
		}while (taille % 2 !=0 || taille < 4 || taille > 16);
		char[][] t = new char[taille][taille];
		remplirTableau(t);
		displayTab(t);
		displayTabBool(authorizedLocation(t));
	}
	/**Initialise le tableau à deux dimensions
	 * @param tab : le tableau à remplir
	 **/
	void remplirTableau(char[][] tab){
		for(int i = 0; i < tab.length; i++){
			for (int j = 0; j < tab[i].length; j++){
				tab[i][j] = ' ';
			}
		}
		//Placement des pions au centre du plateau au début de la partie
		tab[(tab.length/2)-1][(tab.length/2)-1] = 'x';
		tab[tab.length/2][tab.length/2] = 'x';
		tab[(tab.length/2)-1][tab.length/2] = 'o';
		tab[tab.length/2][(tab.length/2)-1] = 'o';
	}
	/**Affiche correctement le tableau
	 * @param tab : le tableau à remplir
	 **/
	void displayTab (char[][] tab){
		System.out.print("\033c"); //Vide la console
		System.out.print("   ");
		for(int k = 97; k < tab.length + 96; k++){  //Affiche les lettres indiquant les colonnes 
			System.out.print((char)k + " ");        //transformation des chiffre en lettre (autorisée par Mme Naert)
		}
		System.out.println((char)(tab.length + 96));
		
		for(int i = 0; i < tab.length; i++){
			if (i < 9){
				System.out.print(" " + (i + 1) + " "); //Affiche les nombres indiquant les lignes
			}else{
				System.out.print((i + 1)+ " ");
			}
			for (int j = 0; j < tab[i].length-1; j++){
				System.out.print (tab[i][j]);       //Affichage du plateau
				System.out.print('|');
			}
			System.out.println(tab[i][tab[i].length-1]);
		}
	}
	

	
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
	
	
//Verification de toute les directions
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
		System.out.println("1");
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
		System.out.println("2");

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
		while (j - decalage >= 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i][j - decalage];
		}
		System.out.println("3");

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
		while (i - decalage >= 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j];
		}
		System.out.println("4");

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
		while (j + decalage < tab.length && i + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j + decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j + decalage];
		}
		System.out.println("5");
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
		while (i + decalage < tab.length && j - decalage >= 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j - decalage];
		}
		System.out.println("6");
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
		while (i - decalage >= 0 && j - decalage >= 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j - decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j - decalage];
		}
		System.out.println("7");
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
		while (i - decalage >= 0 && j + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j + decalage] == pieceAdverse){
				decalage++;
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j + decalage];
		}
		System.out.println("8");
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}







	void displayTabBool (boolean[][] tab){
		System.out.print("\033c"); //Vide la console
		System.out.print("   ");
		for(int k = 97; k < tab.length + 96; k++){  //Affiche les lettres indiquant les colonnes 
			System.out.print((char)k + " ");        //transformation des chiffre en lettre (autorisée par Mme Naert)
		}
		System.out.println((char)(tab.length + 96));
		
		for(int i = 0; i < tab.length; i++){
			if (i < 9){
				System.out.print(" " + (i + 1) + " "); //Affiche les nombres indiquant les lignes
			}else{
				System.out.print((i + 1)+ " ");
			}
			for (int j = 0; j < tab[i].length-1; j++){
				System.out.print (tab[i][j]);       //Affichage du plateau
				System.out.print('|');
			}
			System.out.println(tab[i][tab[i].length-1]);
		}
	}
	
}
