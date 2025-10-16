public class PlayerPointGestion {
	
	char currentPlayer = 'x'; // le joueur avec les pions 'x' commence
	
	char[][] grid = {
		{'x', ' ', ' ', ' '},
		{' ', 'x', 'o', ' '},
		{' ', 'o', 'x', ' '},
		{' ', ' ', ' ', ' '}
	};
	
	boolean[][] allowedCoup = {
		{false, false, true, false},
		{false, false, false, true},
		{true, false, false, false},
		{false, true, false, false}
	};
	
	boolean[][] falseTab = new boolean[4][4];
	
	void principal(){
		//displayTab3d(grid);
		//addHat();
		//displayTab3d(grid);
		//removeHat();
		//displayTab3d(grid);
		
		// System.out.println(finJeu());
		
		initGameMenu();
	}
	
	/** Initialise le menu du jeu (choix taille grille / solo / duo / qui commence)
	 * a executer apres le test unitaire
	 */
	int[] initGameMenu(){
		int [] config = new int[4];
		int lengthGrid, gameMode;
		int firstPlayer = 0;
		int difficulty = 0;
		char SoloPlayerchoice = 'O';
		
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
		} while (lengthGrid < 4 && lengthGrid > 16); // on demande tant que lengthGrid n'est pas entre 4 et 16
		
		do {
			gameMode = SimpleInput.getInt("Saisir mode de jeu : (1:Solo / 2:Duo) : ");
		} while (gameMode != 1 && gameMode != 2); // on demande tant que game n'est pas 1 ou 2
		
		if (gameMode == 1){
			
			do {
				SoloPlayerchoice = SimpleInput.getChar("Vous allez jouer contre un BOT, voulez-vous commencer a jouer? (O/n)");
			} while (SoloPlayerchoice != 'O' && SoloPlayerchoice != 'o' && SoloPlayerchoice != 'N' && SoloPlayerchoice != 'n');
			
			if (SoloPlayerchoice == 'O' && SoloPlayerchoice == 'o') {
				firstPlayer = 1; // le joueur joue les 'x'
			} else {
				firstPlayer = 2; // le joueur joue les 'o'
			}
			
			do {
			difficulty = SimpleInput.getInt("Difficulte du bot (1:Facile / 2:Difficile) : ");
			} while (gameMode != 1 && gameMode != 2); // on demande tant que difficulty n'est pas 1 ou 2
			
		} else { // code a continuer pour le mode duo
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
	
	/**
	 * ajoute le caractere '^' dans la grille de jeu aux endroits ou le coup joue est legale
	 * @author S. GIRARDEAU
	 */
	void addHat(){
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
		//allowedCoup = authorizedLocation(char[][] tab); a changer + tard
		boolean end = true;
		
		for(int i = 0; i < allowedCoup.length; i++){
			for(int j = 0; j < allowedCoup[i].length; j++){
				if (allowedCoup[i][j] == true) {
					end = false;
				}
			}
		}
		return end;
	}
	
	/**Affiche correctement le tableau
	 * @param tab : le tableau a remplir
	 **/
	void displayTab3d (char[][] tab){
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
}
