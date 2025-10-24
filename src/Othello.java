class Othello {
	
	char currentPlayer = 'o'; // le joueur avec les pions 'x' commence
	
	char[][] grid; // penser a initialiser dans le principal
	
	char pieceJoueur = 'o';
	char pieceAdverse = 'x';
	int nombreDeTour = 0;
	void principal() {
		
		// Initialisation du jeu
		
		System.out.println("----------------- Tests Unitaires -----------------");
		testPointsCalculation();
		SimpleInput.getString("entrer une chaine de caractere pour commencer a jouer : ");
		int[] config = initGameMenu();
		grid = new char[config[0]][config[0]];
		remplirTableau();
		int gameMode = config[1];  // 1 solo 2 duo
		int botCommence = config[2];  //0 bot commence
		int difficulteeBot = config[3];
		int[] coup;
		while (!finJeu()){ 
			addHat();

			showGrid();
			if (gameMode == 1 && nombreDeTour % 2 == botCommence){
				if (peutJouer()){
					if (difficulteeBot == 1){
						coup = coupBotRandom();
						placeEtRetournePiece(grid, coup);

					}
					else{
						coup = coupBotReflechi();
						placeEtRetournePiece(grid, coup);
					}
				}
			}
			else{
				if (peutJouer()){
					coup = requestPiece();
					placeEtRetournePiece(grid, coup);
				}

			}

			switchPlayer();
			removeHat();
			nombreDeTour++;
		}
		showGrid();
		int pointsO = pointsCalculation(grid)[1];
		int pointsX = pointsCalculation(grid)[0];
		if (pointsO > pointsX) {
			System.out.println("Vainqueur : pions o avec " + pointsO + " points !");
			System.out.println("Defaitiste : pions x avec " + pointsX + " points ...");			
		}else if (pointsX > pointsO) {
			System.out.println("Vainqueur : pions x avec " + pointsX + " points !");
			System.out.println("Defaitiste : pions o avec " + pointsO + " points ...");			
		}else{
			System.out.println("Egalite ! ");	
		}
	}
	
	/**
	 * Initialise le tableau a deux dimensions
	 * @param tab : le tableau a remplir
	 * @author Antoine CLERO
	 **/
	void remplirTableau(){
		
		//Chaque case du plateau est remplacee par le charactere ' '
		for(int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[i].length; j++){
				grid[i][j] = ' ';
			}
		}
		
		//Placement des pions au centre du plateau au debut de la partie
		grid[(grid.length/2)-1][(grid.length/2)-1] = 'x';
		grid[grid.length/2][grid.length/2] = 'x';
		grid[(grid.length/2)-1][grid.length/2] = 'o';
		grid[grid.length/2][(grid.length/2)-1] = 'o';
	}
	
	/** Initialise le menu du jeu avec choix taille grille / solo / duo / qui commence
	 * a executer apres le test unitaire
	 * @return : tableau contenant la configuraton du jeu
	 * @author S. GIRARDEAU
	 */
	int[] initGameMenu(){
		int lengthGrid, gameMode;
		int StartPlayer = 0; // le joueur ne commence pas par defaut
		int difficulty = 0;
		char PlayerChoice = 'O';
		
		System.out.println("""
						Welcome to
			----------------------------------------------------------------
			 _______ _________          _______  _        _        _______ 
			(  ___  )\\__   __/|\\     /|(  ____ \\( \\      ( \\      (  ___  )
			| (   ) |   ) (   | )   ( || (    \\/| (      | (      | (   ) |
			| |   | |   | |   | (___) || (__    | |      | |      | |   | |
			| |   | |   | |   |  ___  ||  __)   | |      | |      | |   | |
			| |   | |   | |   | (   ) || (      | |      | |      | |   | |
			| (___) |   | |   | )   ( || (____/\\| (____/\\| (____/\\| (___) |
			(_______)   )_(   |/     \\|(_______/(_______/(_______/(_______)
																			
			----------------------------------------------------------------
			""");
		
		do {
			lengthGrid = SimpleInput.getInt("Saisir la taille du plateau (entre 4 a 16) : ");
		} while (lengthGrid < 4 || lengthGrid > 16); // on demande tant que lengthGrid n'est pas entre 4 et 16
		
		do {
			gameMode = SimpleInput.getInt("Saisir mode de jeu : (1:Solo / 2:Duo) : ");
		} while (gameMode != 1 && gameMode != 2); // on demande tant que game n'est pas 1 ou 2
		
		if (gameMode == 1){
			
			do {
				PlayerChoice = SimpleInput.getChar("Vous allez jouer contre un BOT, voulez-vous commencer a jouer? (o/n) : ");
			} while (PlayerChoice != 'O' && PlayerChoice != 'o' && PlayerChoice != 'N' && PlayerChoice != 'n');
			if (PlayerChoice == 'O' || PlayerChoice == 'o') {
				StartPlayer = 1; // le joueur joue les 'x'
			}
			
			do {
			difficulty = SimpleInput.getInt("Difficulte du bot (1:Facile / 2:Difficile) : ");
			} while (gameMode != 1 && gameMode != 2); // on demande tant que difficulty n'est pas 1 ou 2
			
		}
		
		return new int[] {lengthGrid, gameMode, StartPlayer, difficulty};
	}
	
	/**
	 * Demande les coordonnees de grille au joueur et les convertie en coordonnees de tableau (indices)
	 * @return coords : tableau de coordonnees int [0]
	 * @author S. GIRARDEAU
	 */
	int[] requestPiece() {
		String request;
		int i = -150;
		int j = -150;
		do {
			request = SimpleInput.getString("Entrez les coordonnees sous la forme (chiffrelettre) (ex: 1a) : ");
			if (request.length() == 2) {
				i = ((int) request.charAt(0)) -49; // calcul de la veritable valeur de i
				j = ((int) request.charAt(1)) -97; // calcul de la veritable valeur de j
							
			} else if (request.length() == 3 && request.charAt(0) == '1') {
				i = ((int) request.charAt(1)) -49 + 10; // on prends en compte le cas ou le nombre depasse 9
				j = ((int) request.charAt(2)) -97;
			}
				
			System.out.println(i + " " + j);
		} while (request.length() != 2 && request.length() != 3 || i < 0 || i >= grid.length || j < 0 || j >= grid.length || grid[i][j] != '^'); // || grid[i][j] != '^' ajouter les conditions pour verifier que les nombres ne soit pas out of bound dans la grille
		
		return new int[] {i, j};
	}
	
	/**Affiche correctement le tableau
	 * @author Antoine CLERO
	 **/
	void showGrid (){

		//Vide la console (ou fait comme si elle se vidais pour ne pas afficher le plateau du joueur precedent)
		for (int i = 0; i < 25;i++){
			System.out.println("\n");
		}
		
		System.out.println("Au tour des " + pieceJoueur);
		
		System.out.print("   ");
		for(int k = 97; k < grid.length + 96; k++){  //Affiche les lettres indiquant les colonnes 
			System.out.print((char)k + " ");        //transformation des chiffre en lettre (autorisee par Mme Naert)
		}
		System.out.println((char)(grid.length + 96));
		
		for(int i = 0; i < grid.length; i++){
			if (i < 9){
				System.out.print(" " + (i + 1) + " "); //Affiche les nombres indiquant les lignes
			}else{
				System.out.print((i + 1)+ " ");
			}
			for (int j = 0; j < grid[i].length-1; j++){
				System.out.print (grid[i][j]);       //Affichage du plateau
				System.out.print('|');
			}
			System.out.println(grid[i][grid[i].length-1]);
		}
	}
	
	/**Verifie quelle sont les cases sur lesquel le joueur peut jouer
	 * @return coupsAuthorise : liste des coups autorises
	 * @author Antoine CLERO
	 **/
	boolean[][] authorizedLocation(){
		boolean[][] coupsAutorise = new boolean[grid.length][grid.length];
		int k = 0;
		for(int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid.length; j++){
				if (grid[i][j] == ' ') {
					coupsAutorise[i][j] = pieceEncadree(grid, i, j); //Si la case est valide, la case est mise en true
				}
			}
		}
		return coupsAutorise;
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee
	 * @param i, j : coordonnees de la case verifiee
	 * @return resultat : true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadree(char[][] tab, int i,int j){
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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Verification si une piece serait encadre par le placement de la piece dans une direction specifique
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur la droite de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeDroite(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (j + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i][j + decalage] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i][j + decalage];
			decalage++;
		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeDroite : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur le dessous de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeBas(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j];
			decalage++;
		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeBas : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur la gauche de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeGauche(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (j - decalage > 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i][j - decalage] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i][j - decalage];
			decalage++;
		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeGauche : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur le dessus de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeHaut(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i - decalage > 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j];
			decalage++;
		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeHaut : " + (piecePositionIJ == pieceJoueur) );
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur la diagonale droite-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeDroiteBas(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (j + decalage < tab.length && i + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j + decalage] == pieceAdverse){
				nombrePieceEncadree ++;

			}
			piecePositionIJ = tab[i + decalage][j + decalage];
			decalage++;

		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeDroiteBas : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-bas de la case)
	 * @param i, j : coordonnees de la case verifiee

	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeGaucheBas(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i + decalage < tab.length && j - decalage > 0 && piecePositionIJ == pieceAdverse) {
			if (tab[i + decalage][j - decalage] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i + decalage][j - decalage];
			decalage++;

		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeGaucheBas : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeGaucheHaut(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i - decalage > 0 && j - decalage > 0 && (piecePositionIJ == pieceAdverse)) {
			if (tab[i - decalage][j - decalage] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j - decalage];
			decalage++;
		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeGaucheHaut : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}
	
	/**
	 * Verifie si il y a un pion adverse d'encadre par le placement d'un pion sur la case selectionnee (sur la diagonale droite-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return true si au moins une piece adverse est encadree, false sinon
	 * @author Antoine CLERO
	 **/
	boolean pieceEncadreeDroiteHaut(char[][] tab, int i,int j){
		int decalage = 1;
		int nombrePieceEncadree = 0;
		char piecePositionIJ = pieceAdverse;
		while (i - decalage > 0 && j + decalage < tab.length && piecePositionIJ == pieceAdverse) {
			if (tab[i - decalage][j + decalage] == pieceAdverse){
				nombrePieceEncadree ++;
			}
			piecePositionIJ = tab[i - decalage][j + decalage];
			decalage++;
		}
		  System.out.println(" i : " + i + " j : " + j);
		  System.out.println("pieceEncadreeDroiteHaut : " + (piecePositionIJ == pieceJoueur));
		return (nombrePieceEncadree != 0 && piecePositionIJ == pieceJoueur );
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * renvoie une liste contenant le coup joue par le bot aleatoire
	 * @return coupAleatoire : les coordonnees du coup a jouer ; indice 0 : i
	 * 														   ; indice 1 : j
	 * @author Antoine CLERO
	 **/
	int[] coupBotRandom(){
		int i,j;
		do{
			i = (int)(Math.random() * grid.length);
			j = (int)(Math.random() * grid.length);
		}while (grid[i][j] != '^');
		int[] coupAleatoire = new int[2];
		coupAleatoire[0] = i;
		coupAleatoire[1] = j;
		return coupAleatoire;
	}

	/**
	 * renvoie une liste contenant le coup rapportant le plus de pointjoue par le bot 
	 * @return coupAleatoire : les coordonnees du coup a jouer ; indice 0 : i
	 * 														   ; indice 1 : j
	 * @author Antoine CLERO
	 **/
	int[] coupBotReflechi(){
		int nombreCoupsAutorises = 0;
		int[] coupReflechi = new int[2];
		
		//calcul du nombre de coups possibles
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				if (grid[i][j] =='^'){
					nombreCoupsAutorises++;
				}
			} 
		}
		char[][] tabCopie = copieTableau();
		///Creation des listes qui compte le nombre de points rapporte (pointsRapportes) avec le placement de la piece a certaine coordonnee (coupsAutorise)
		///En lisant le tableau de droite a gauche et de heut en bas, l'indice 0 correspond au premier coup autorise, l'indice 1 au deuxieme coup autorise, etc
		int[][] coupsAutorise = new int[nombreCoupsAutorises][2];

		int[] pointsRapportes = new int[nombreCoupsAutorises];

		//calcul du nombre de points rapporte par chaque placement possibles
		int i = 0;
		  ///System.out.println("taille : " + grid.length);
		for (int j = 0; j < grid.length; j++){
			for (int k = 0; k < grid.length; k++){
				  ///System.out.println(" j : " + j +"  k : "+k);
				  
				if (grid[j][k] == '^'){
					
					tabCopie = copieTableau(); //copie le tableau grid
					  ///System.out.print("nouveau tableau");
					  ///displayTabTestUnitaire(tabCopie);

					coupsAutorise[i][0] = j; //Enregistrement des indice correspondant au coup i
					coupsAutorise[i][1] = k;
					pointsRapportes[i] = placeEtRetournePiece(tabCopie,coupsAutorise[i]); //retournePiece renvoie le nombre de pieces retournees au coup i
					  ///displayTabTestUnitaire(tabCopie);
					  ///displayTabTest(coupsAutorise);
					  ///displayTab(pointsRapportes);
					

					i++;
				}
			}
		}
		///displayTab(coupReflechi);
		  System.out.println( "nouveau tableau" );
		  displayTabTestUnitaire(grid);
		  System.out.println(grid == tabCopie );
		return coupsAutorise[indicePlusGrand(pointsRapportes)]; //Renvoie le couple de coordonnees (j,k) 

	}
	
	
	/**
	 * copie le tableau 
	 * @return tabCopie : copie du tableau
	 * @author Antoine CLERO
	 **/
	char[][] copieTableau(){
		char[][] tabCopie = new char[grid.length][grid.length];
		
		//copie du tableau
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid.length; j++){
				tabCopie[i][j] = grid[i][j];
			}
		}
		return tabCopie;
	}
	
	/**
	 * Recherche l'indice de la plus grande valeur dans une liste
	 * Si il y a plusieurs valeurs plus grande identiques, on en renvoie un aleatoirement
	 * @param tab : tableau d'entier a etudier
	 * @return l'un des indices du nombre le plus grand  
	 * @author Antoine CLERO
	 */
	int indicePlusGrand(int[] tab){
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
		
		//Choix aleatoire parmi les indices
		return listeIndex[(int) (Math.random() * (j-1))];
		
	}
	
	/**
	 * Place la piece correspondant au coordonnees saisie et retourne les pieces du plateau
	 * @param char[][] grid : grille du jeu
	 * @param int[] coordonnees : coordonnee de la piece a placer
	 * @return pieceJoueur, pieceAdverse
	 * @author Antoine CLERO
	 */
	int placeEtRetournePiece(char[][] tab, int[] coordonnees){
		int NombreDePointGagne = 0;

		//Placement de la piece 
		int i = coordonnees[0];
		int j = coordonnees[1];
		tab[i][j] = pieceJoueur;
		NombreDePointGagne++;
		
		//Retournemement des pieces dans toute les directions
		if (pieceEncadreeDroite(tab, i, j)){
			NombreDePointGagne += retournementDroite(tab, i, j);
		}
		if (pieceEncadreeBas(tab, i, j)){
			NombreDePointGagne += retournementBas(tab, i, j);
		}
		if (pieceEncadreeGauche(tab, i, j)){
			NombreDePointGagne += retournementGauche(tab, i, j);
		}
		if (pieceEncadreeHaut(tab, i, j)){
			NombreDePointGagne += retournementHaut(tab, i, j);
		}
		if (pieceEncadreeDroiteBas(tab, i, j)){
			NombreDePointGagne += retournementDroiteBas(tab, i, j);
		}
		if (pieceEncadreeGaucheBas(tab, i, j)){
			NombreDePointGagne += retournementGaucheBas(tab, i, j);
		}
		if (pieceEncadreeGaucheHaut(tab, i, j)){
			NombreDePointGagne += retournementGaucheHaut(tab, i, j);
		}
		if (pieceEncadreeDroiteHaut(tab, i, j)){
			NombreDePointGagne += retournementDroiteHaut(tab, i, j);
		}
		return NombreDePointGagne;
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Fonctions permettant de retourner les pieces dans toute les directions possibles

	
	
	/**
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur la droite de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementDroite(char[][] tab, int i,int j){
		
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
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur le dessous de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementBas(char[][] tab, int i,int j){
		
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
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur la gauche de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementGauche(char[][] tab, int i,int j){
	
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
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur le haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementHaut(char[][] tab, int i,int j){

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
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur la diagonale droite-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementDroiteBas(char[][] tab, int i,int j){
		
		int decalage = 1;
		int nombrePieceEncadree = 0;
		while (tab[i + decalage][j + decalage] == pieceAdverse) {
			tab[i + decalage][j+ decalage] = pieceJoueur;
			decalage++;
			nombrePieceEncadree ++;
		}
		System.out.println("1");
		return nombrePieceEncadree;
		}


	/**
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-bas de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementGaucheBas(char[][] tab, int i,int j){

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
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur la diagonale gauche-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementGaucheHaut(char[][] tab, int i,int j){

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
	 * Retourne les pions encadres par le placement d'un pion sur la case selectionnee (sur la diagonale droite-haut de la case)
	 * @param i, j : coordonnees de la case verifiee
	 * @return nombrePieceEncadree : Le nombre de pions retournes
	 * @author Antoine CLERO
	 **/
	int retournementDroiteHaut(char[][] tab, int i,int j){
	
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


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Calcule les points des 2 joueurs (IA ou Humains) en parcourant la grille de jeu
	 * @param char[][] grid : grille principale du jeu
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
	 * Teste la methode pointsCalculation()
	 */
	void testPointsCalculation() {
		System.out.println();
		System.out.println("*** pointsCalculation()");
		
		char[][] test1 = {
			{'x', ' ', ' ', ' '},
			{' ', 'x', 'o', ' '},
			{' ', 'o', 'x', ' '},
			{' ', ' ', ' ', ' '}
		};
		
		char[][] test2 = {
			{'x', 'x', 'x', 'x'},
			{'o', 'x', 'o', 'o'},
			{'o', 'o', 'x', 'o'},
			{'x', 'x', 'o', 'o'}
		};
		
		char[][] test3 = {
			{' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' '},
			{' ', ' ', ' ', ' '}
		};
		
		int[] result1 = {3, 2};
		int[] result2 = {8, 8};
		int[] result3 = {0, 0};
		
		
		testCasPointsCalculation(test1, result1);
		testCasPointsCalculation(test2, result2);
		testCasPointsCalculation(test3, result3);
	}
	
	/**
	 * teste un appel de pointsCalculation
	 * @param grid : grille du jeu
	 * @param result resultat attendu
	 */
	void testCasPointsCalculation(char[][] grid, int[] result) {
		// Affichage
		System.out.println("pointsCalculation("); 
		displayTabTestUnitaire(grid); 
		System.out.print("resultat = ");
		displayTab(result);
		System.out.print("\t : ");
	 
		//Appel
		int[] resExec = pointsCalculation(grid);
	
		// Verification
		if (resExec[0] == result[0] && resExec[1] == result[1]) {
			System.out.println("OK");
		} else {
			System.err.println("ERREUR");
		}
	}

	/**Affiche le plateau sans le clear console
	 * @param tab : grille du plateau
	 **/
	void displayTabTestUnitaire (char[][] tab){
		// System.out.print("\033c"); //Vide la console
		System.out.print("   ");
		for(int k = 97; k < tab.length + 96; k++){  //Affiche les lettres indiquant les colonnes 
			System.out.print((char)k + " ");        //transformation des chiffre en lettre (autorisee par Mme Naert)
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
	 * changement du joueur actuel pour passer au joueur suivant
	 * On utilise les variables globales pieceJoueur et PieceAdverse
	 * @author S. GIRARDEAU
	 */
	void switchPlayer(){
		if (pieceJoueur == 'o'){
			pieceJoueur = 'x';
			pieceAdverse = 'o';
		} else {
			pieceJoueur = 'o';
			pieceAdverse = 'x';
		}
	}
	
	/**
	 * ajoute le caractere '^' dans la grille de jeu aux endroits ou le coup joue est legale
	 * @author S. GIRARDEAU
	 */
	void addHat(){
		
		boolean[][] allowedCoup = authorizedLocation();
		
		for (int i = 0; i < allowedCoup.length; i++) { // allowedCoup est a remplacer par authorizedLocation
			for (int j = 0; j < allowedCoup[i].length; j++) {
				if (allowedCoup[i][j]) {
					grid[i][j] = '^';
				}
			}
		}
	}
	/**
	 * supprime tout les caracteres '^' de la grille de jeu
	 * @author S. GIRARDEAU
	 */
	void removeHat(){
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == '^'){
					grid[i][j] = ' ';
				}
			}
		}
	}
	
	/**
	 * Verifie si le jeu est fini ou non / s'il n'y a plus de positions autorisees dans la grille
	 * @return end : true si le jeu est fini / false sinon
	 * @author S. GIRARDEAU
	 */ 
	boolean finJeu() {
		boolean end = true;
		for (int d = 0; d < 2; d++){
			boolean[][] allowedCoup = authorizedLocation();
			for(int i = 0; i < allowedCoup.length && end; i++){
				for(int j = 0; j < allowedCoup[i].length && end; j++){
					if (allowedCoup[i][j] == true) {
						end = false;
					}
				}
			}
			switchPlayer();
			System.out.println("finJeu");
		}
		return end;
	}
	
	boolean peutJouer(){
		boolean resultat = false;
		int i = 0;
		while (!resultat && i < grid.length){
			int j = 0;
			while (!resultat && j < grid.length){
				if (grid[i][j] == '^'){
					resultat = true;
				}
				j++;
			}
			i++;
		}
		return resultat;
	}
	void displayTab(int[] t){
        int i = 0;
        System.out.print("{");
    
        while (i<t.length-1) {
            System.out.print(t[i] + ",");
            i++;
        }
        if(t.length != 0) {
            System.out.println(t[i]+"}");
        } else {
            System.out.println("}");
        }
    }
    
    
    
    
    
    void displayTabTest (int[][] tab){
		// System.out.print("\033c"); //Vide la console
		System.out.print("   ");
		for(int k = 97; k < tab.length + 96; k++){  //Affiche les lettres indiquant les colonnes 
			System.out.print((char)k + " ");        //transformation des chiffre en lettre (autorisee par Mme Naert)
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
