public class Fonctions{
	void principal(){
		System.out.println(">>> DÉBUT DU PROGRAMME <<<");
		int taille;
		do {
			taille = SimpleInput.getInt("Quelle taille voulez vous que le coté du plateau soit-il (4-6-8-10-12-16) ? ");
		}while (taille % 2 !=0 || taille < 4 || taille > 16);
		char[][] t = new char[taille][taille];
		remplirTableau(t);
		displayTab(t);
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
}
