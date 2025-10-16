public class Bot{
	
	char pieceJoueur = 'o';
	char pieceAdverse = 'x';
	
	
	void principal(){
		System.out.println(">>> DÉBUT DU PROGRAMME <<<");
		int taille;
		do {
			taille = SimpleInput.getInt("Quelle taille voulez vous que le cote du plateau soit-il (4-6-8-10-12-16) ? ");
		}while (taille % 2 !=0 || taille < 4 || taille > 16);
		char[][] t = new char[taille][taille];

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
			for (int j = 0; k < tab.length; j++){
				if (tab[j][k] == '^'){
					char[][] tabCopie = copieTableau(tab) //copie les tableau tab
					coupsAuthorise[i] = PlaceEtRetournePiece(tabCopie); //retournePiece renvoie le nombre de pièces retournées
					i++;
				}
			}
		}
		coupsReflechi = IndicePlusGrand(coupsAuthorise);
		return coupsReflechi;
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
	
	int IndicePlusGrand(int[] tab){
		for(int i = 0; i < tab.length;; i++) 
}//ne pas oublier que si la valeur la plus grande est presente plusieurs fois dans le tableau, renvoyer lun des indices aléatoirement
