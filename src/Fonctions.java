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
		//Verification de toute les directions

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
	
	
	
	/**
	 * renvoie une liste contenant le coup joué par le bot aléatoire
	 * @param tab : le tableau à analyser
	 * @return coupAleatoire : les coordonnées du coup à jouer ; indice 0 : i
	 * 														   ; indice 1 : j
	 **/
	int[] coupBotRandom(char[][] tab){
		int i,j;
		do{
			i = (int)(Math.random() * tab.length);
			j = (int)(Math.random() * tab.length);
		}while (tab[i][j] == '^');
		int[] coupAleatoire = new int[2];
		coupAleatoire[0] = i;
		coupAleatoire[1] = j;
		return  coupAleatoire;
	}
	
	/**
	 * renvoie une liste contenant le coup rapportant le plus de pointjoué par le bot 
	 * @param tab : le tableau à analyser
	 * @return coupAleatoire : les coordonnées du coup à jouer ; indice 0 : i
	 * 														   ; indice 1 : j
	 **/
	int[] coupBotReflechi(char[][] tab){
		int nombreCoupsAutorises = 0;
		int[] coupReflechi = new int[2];
		
		//calcul du nombre de coups possibles
		for(int i = 0; i < tab.length; i++){
			for(int j = 0; j < tab.length; j++){
				if (tab[i][j] =='^'){
					nombreCoupsAutorises++;
				}
			} 
		}
		int[][] coupsAuthorise = new int[nombreCoupsAutorises][2];
		int[] pointsRapportes = new int[nombreCoupsAutorises];

		//calcul du nombre de points rapporté par chaque placement possibles
		int i = 0;
		for (int j = 0; i < tab.length; i++){
			for (int k = 0; k < tab.length; j++){
				if (tab[j][k] == '^'){
					char[][] tabCopie = copieTableau(tab); //copie les tableau tab
					coupsAuthorise[i][0] = j; //Enregistrement des indice correspondant au coup i
					coupsAuthorise[i][1] = k;
					pointsRapportes[i] = PlaceEtRetournePiece(tabCopie,coupsAuthorise[i]); //retournePiece renvoie le nombre de pièces retournées au coup i
					
					i++;
				}
			}
		}
		return coupsAuthorise[IndicePlusGrand(pointsRapportes)]; //Renvoie le couple de coordonnées (j,k) 

	}
	
	/**
	 * copie le tableau 
	 * @param tab : le tableau à copier
	 * @return tabCopie : copie du tableau
	 **/
	char[][] copieTableau(char[][] tab){
		char[][] tabCopie = new char[tab.length][tab.length];
		
		//copie du tableau
		for(int i = 0; i < tab.length; i++){
			for(int j = 0; j < tab.length; j++){
				tabCopie[i][j] = tab[i][j];
			}
		}
		return tabCopie;
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
	 * Recherche le coup qui rapporte le plus de points
	 * Si il y a plusieurs valeurs plus grande identiques, on en renvoie un aléatoirement
	 * @param
	 * @author Antoine CLERO
	 */
	int IndicePlusGrand(int[] tab){
		int indiceARenvoyer;
		int[] listeIndex = new int[tab.length];
		int tampon = tab[0];
		
		//Recherche du nombre le plus grand
		for(int i = 1; i < tab.length; i++){
			if (tampon < tab[i]){
				tampon = tab[i];
			}
		}
		
		//Recherche des indices qui corresppondent a la valeur la plus grande (car il peut y avoir plusieurs fois la meme valeur)
		int j = 0;
		for (int i = 0; i < tab.length; i++){
			if (tab[i] == tampon){
				listeIndex[j] = i;
				j++;
			}
		}
		
		//Choix aléatoire parmi les indices
		return listeIndex[(int) (Math.random() * (j-1))];
		
	}
	/**
	 * Place la piece correspondant au coordonnees saisie et retourne les pieces du plateau
	 * @param int[][] grid : grille principale du jeu
	 * @param int[] coordonnees : coordonnee de la piece a placer
	 * @return pieceJoueur, pieceAdverse
	 * @author Antoine CLERO
	 */
	int PlaceEtRetournePiece(char[][] plateau, int[] coordonnees){
		int NombreDePointGagne = 0;
		
		//Placement de la pièce 
		int i = coordonnees[0];
		int j = coordonnees[1];
		plateau[i][j] = pieceJoueur;
		NombreDePointGagne++;
		
		//Retournemement des pieces
		if (pieceEncadreeDroite(plateau, i, j)){
			NombreDePointGagne += retournementDroite(plateau, i, j);
		}
		if (pieceEncadreeBas(plateau, i, j)){
			NombreDePointGagne += retournementBas(plateau, i, j);
		}
		if (pieceEncadreeGauche(plateau, i, j)){
			NombreDePointGagne += retournementGauche(plateau, i, j);
		}
		if (pieceEncadreeHaut(plateau, i, j)){
			NombreDePointGagne += retournementHaut(plateau, i, j);
		}
		if (pieceEncadreeDroiteBas(plateau, i, j)){
			NombreDePointGagne += retournementDroiteBas(plateau, i, j);
		}
		if (pieceEncadreeGaucheBas(plateau, i, j)){
			NombreDePointGagne += retournementGaucheBas(plateau, i, j);
		}
		if (pieceEncadreeGaucheHaut(plateau, i, j)){
			NombreDePointGagne += retournementGaucheHaut(plateau, i, j);
		}
		if (pieceEncadreeDroiteHaut(plateau, i, j)){
			NombreDePointGagne += retournementDroiteHaut(plateau, i, j);
		}
		return NombreDePointGagne;
	}
	

//Fonctions permettant de retourner les pieces dans toute les directions possibles

	
	
	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur la droite de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementDroite(char[][] tab,int i,int j){
		
		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i][j + decalage] == pieceAdverse) {
			tab[i][j + decalage] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur le dessous de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementBas(char[][] tab,int i,int j){
		
		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i + decalage][j] == pieceAdverse) {
			tab[i + decalage][j] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur la gauche de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementGauche(char[][] tab,int i,int j){
	
		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i][j - decalage] == pieceAdverse) {
			tab[i][j - decalage] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur le haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementHaut(char[][] tab,int i,int j){

		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i - decalage][j] == pieceAdverse) {
			tab[i - decalage][j] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}

	
	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur la diagonale droite-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementDroiteBas(char[][] tab,int i,int j){
		
		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i + decalage][j + decalage] == pieceAdverse) {
			tab[i + decalage][j ] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementGaucheBas(char[][] tab,int i,int j){

		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i + decalage][j - decalage] == pieceAdverse) {
			tab[i + decalage][j - decalage] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementGaucheHaut(char[][] tab,int i,int j){

		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i - decalage][j - decalage] == pieceAdverse) {
			tab[i - decalage][j - decalage] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadrés par le placement d'un pion sur la case selectionnee (sur la diagonale droite-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @param tab : plateau de jeu
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 **/
	int retournementDroiteHaut(char[][] tab,int i,int j){
	
		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i - decalage][j + decalage] == pieceAdverse) {
			tab[i - decalage][j + decalage] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}

}


