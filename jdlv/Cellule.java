package jdlv;

import java.awt.Point;

public class Cellule {

	// TODO probleme de redondance d'infos : on a un compteur qui détermine les
	// cellules autour.
	// maiso n a aussi les 8 cellules qui sont autour donc on pourrais le
	// savoir...

	// la cellule elle même, alive = true // vivante; alive = false//morte
	private boolean alive;
	private boolean flag = false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	private Point positionVirtuelle;
	private int compteur = 0;

	public Point getPositionVirtuelle() {
		return positionVirtuelle;
	}

	public void setPositionVirtuelle(Point positionVirtuelle) {
		this.positionVirtuelle = positionVirtuelle;
	}

	/*
	 * liste des Huits cellule qui l'entoure
	 * 123
	 * 8X4
	 * 765
	 */
//	private Point[] celluleVoisine = new Point[9];



	public Cellule(boolean alive, Point position) {
		this.alive = alive;
		positionVirtuelle = position;
//		creerPointCellulesVoisine(position);
	}

/*	public Cellule(boolean b, Point point,
			HashMap<Point, Cellule> listeDesCellulesExistantes22, int i) {
		this.alive = b;

		// les listes en reference
		// this.listeDesCellulesVivantes = listeDesCellulesVivantes;
		//StartJDLV.listeDesCellulesExistantes2.put(point, this);
		positionVirtuelle = point;
		creerPointCellulesVoisine(point);
	}*/
/*	private void creerPointCellulesVoisine(Point positionInitial) {

		
		 * 123
		 * 8X4
		 * 765
		 
		celluleVoisine[1] = new Point(positionInitial.x - 1, positionInitial.y - 1);
		celluleVoisine[2] = new Point(positionInitial.x    , positionInitial.y - 1);
		celluleVoisine[3] = new Point(positionInitial.x + 1, positionInitial.y - 1);
		celluleVoisine[4] = new Point(positionInitial.x + 1, positionInitial.y    );
		celluleVoisine[5] = new Point(positionInitial.x + 1, positionInitial.y + 1);
		celluleVoisine[6] = new Point(positionInitial.x    , positionInitial.y + 1);
		celluleVoisine[7] = new Point(positionInitial.x - 1, positionInitial.y + 1);
		celluleVoisine[8] = new Point(positionInitial.x - 1, positionInitial.y    );
	}

	@SuppressWarnings("unused")
	private void creerPointCellulesVoisine() {
		creerPointCellulesVoisine(positionVirtuelle);
	}*/

	public boolean definirSiVivante(int compteur) {
		// la cellule est morte si - de 2 ou + de 3 cellules strictement à coté
		if (compteur < 2 || compteur > 3) {
			return false;
		} else if (compteur == 3) {
			return true;
		}
		//dans le cas ou compteur == 2 , on garde l'etat actuel
		return alive;
	}
	
	//on teste si elle doit rester dans le compteur mais sans toucher a "alive"
	public boolean resteDansCompteurCelluleVivante() {
		// la cellule est morte si - de 2 ou + de 3 cellules strictement à coté
		if (compteur < 2 || compteur > 3) {
			return false;
		} else if (compteur == 3) {
			return true;
		}
		//dans le cas ou compteur == 2 , on garde l'etat actuel
		return alive;
	}
	
	public void miseAJourVivante() {
		// la cellule est morte si - de 2 ou + de 3 cellules strictement à coté
		if (compteur < 2 || compteur > 3) {
			alive = false;
		} 
		else if (compteur == 3) {
			alive = true;
		}
	}
/*	public void miseAJourFinale() {
		setAlive(definirSiVivante(compteur));
		compteur=0;
	}*/
	/*
	 * creation de l'arbre qui comporte Toutes les cellules chaque cellule
	 * possede en reference celle autour il ne faut pas de doublon, donc
	 * verifier que la référence Commune n'existe pas déjà
	 * 
	 * exemple : X mort, ABC cellules communes morte --- 1, 2 vivante XXAXX
	 * X1B2X XXCXX les deux cellule possede 3 cellules en commun A,B,C on créé
	 * 1, on verifie qu'il n'y a aucune cellule
	 */
	// on verifie que la cellule n'existe pas, si c'est le cas, on prend la
	// reference.

	/*
	 * public Cellule(boolean alive,LinkedList<Cellule>
	 * listeDesCellulesVivantes, Cellule cH, Cellule cB, Cellule cG, Cellule cD,
	 * Cellule cDHG, Cellule cDHD, Cellule cDBG, Cellule cDBD) { this.alive =
	 * alive; this.cH = cH; this.cB = cB; this.cG = cG; this.cD = cD;
	 * 
	 * this.cDHG = cDHG; this.cDHD = cDHD; this.cDBG = cDBG; this.cDBD = cDBD;
	 * this.listeDesCellulesVivantes = listeDesCellulesVivantes; }
	 */

	/*
	 * public boolean isG() { return g; }
	 * 
	 * public void setG(boolean g) { this.g = g; }
	 * 
	 * public boolean isD() { return d; }
	 * 
	 * public void setD(boolean d) { this.d = d; }
	 * 
	 * public boolean isH() { return h; }
	 * 
	 * public void setH(boolean h) { this.h = h; }
	 * 
	 * public boolean isB() { return b; }
	 * 
	 * public void setB(boolean b) { this.b = b; }
	 * 
	 * public boolean isDhg() { return dhg; }
	 * 
	 * public void setDhg(boolean dhg) { this.dhg = dhg; }
	 * 
	 * public boolean isDhd() { return dhd; }
	 * 
	 * public void setDhd(boolean dhd) { this.dhd = dhd; }
	 * 
	 * public boolean isDbg() { return dbg; }
	 * 
	 * public void setDbg(boolean dbg) { this.dbg = dbg; }
	 * 
	 * public boolean isDbd() { return dbd; }
	 * 
	 * public void setDbd(boolean dbd) { this.dbd = dbd; }
	 */

	public boolean isAlive() {
		return alive;
	}
	public boolean isNotAlive() {
		return !alive;
	}

	@SuppressWarnings("unused")
	private void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void update() {
	}

	public void addCompteur(int i) {
		compteur += i;
	}
	public int getCompteur() {
		return compteur;
	}

	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

}
