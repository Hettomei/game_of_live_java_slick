package jdlv;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.geom.Vector2f;

/*
 * 000
 * 001
 * 010
 * 011
 * 100
 * 101
 * 110
 * 111
 * 
 * 11 111 111 // 256 possibilité (avec 0)
 * /

 /*
 Fonctionnement générale :
 determination d'un point d'affichage (la premiere cellule pour centrer)
 une liste contient uniquement les cellules active
 au moment de la creation de la cellule, si vivante, ajout dans la liste automatique

 boucle principale :
 on parcour la liste des cellule vivante, chaque cellule "regarde" celle qui sont autour pour savoir si elle doivent "vivre"
 et ajoute "+1" au compteur des cellules autour


 */

public class StartJDLV extends BasicGame {

	// constantes
	private static int SCREEN_HEIGHT = 600;
	private static int SCREEN_WIDTH  = 800;
	private float      taille_cellule = 10; // a revoir

	private Vector2f camera_position 	 = new Vector2f(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
	private float    camera_vitesse      = 0.5f;
	private float    camera_vitesse_zoom = 1f;
	// contient uniquement les cellules vivante. liste chainé pour
	// ajouter/enlever à volonté
	// private LinkedList<Cellule> listeDesCellulesVivantes = new
	// LinkedList<Cellule>();
	private HashMap<Point, Cellule> cellules_total      = new HashMap<Point, Cellule>();
	private LinkedList<Cellule>     cellules_en_vie     = new LinkedList<Cellule>();
	private LinkedList<Cellule>     cellules_a_verifier = new LinkedList<Cellule>();

	private Point[] cellule_voisine = new Point[9];
	private int ggg;
	private boolean display_coordonne = false;
	
	public StartJDLV() {
		super("JDLV");
	}

	private void ce(int x, int y){
		Cellule temp = new Cellule(true, new Point(x,y));
		//obligé de le faire : l'ajouter dans les liste
		cellules_total.put(new Point(x,y), temp); // aucun doublon avec hashmap
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {

		initialisation();
		
		taille_cellule = 5;
//		camera_position.x = 10;
//		camera_position.y = 10;
		camera_vitesse = 20;
	}

	private void initialisation() {
		
		cellules_total.clear();
		cellules_en_vie.clear();
		cellules_a_verifier.clear();
//		celle(0,0); celle(0,1);
//		celle(1,0); celle(1,1);
//		celle(1,2);

		//glider
		ce(4,3); ce(5,3); ce(6,3);
						  ce(6,4);
				  ce(5,5);

				  
		
		//generation de cele aleatoire
		for (int i = 0; i < 1000; i++){
			int o =  (Math.round(Math.random()) == 0 ? -1:1);//-1 ou 1
			int p =  (Math.round(Math.random()) == 0 ? -1:1);//-1 ou 1
			int a = (int) Math.round(Math.random()*30);
			int b = (int) Math.round(Math.random()*30);
			System.out.println(a*o + " -- " + p*b);
			ce(a*o, p*b);
		}
		
		//on ajoute toutes les cellules créé à la liste des cell en vie
		Set<Point> keyset = cellules_total.keySet();
		Iterator<Point> iterator = keyset.iterator();
		while (iterator.hasNext()) {
			Point key = iterator.next(); // Clé de type String
			cellules_en_vie.add(cellules_total.get(key));
		}		
	}

	private void creerPointCellulesVoisine(Point positionInitial) {
		/*
		 * 123
		 * 8X4
		 * 765
		 */
		cellule_voisine[1] = new Point(positionInitial.x - 1, positionInitial.y - 1);
		cellule_voisine[2] = new Point(positionInitial.x    , positionInitial.y - 1);
		cellule_voisine[3] = new Point(positionInitial.x + 1, positionInitial.y - 1);
		cellule_voisine[4] = new Point(positionInitial.x + 1, positionInitial.y    );
		cellule_voisine[5] = new Point(positionInitial.x + 1, positionInitial.y + 1);
		cellule_voisine[6] = new Point(positionInitial.x    , positionInitial.y + 1);
		cellule_voisine[7] = new Point(positionInitial.x - 1, positionInitial.y + 1);
		cellule_voisine[8] = new Point(positionInitial.x - 1, positionInitial.y    );
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		//on test toutes les cellules qui étaient vivante
	//	ListIterator<Cellule> iterator = cellules_en_vie.listIterator();
		while (cellules_en_vie.size() > 0) {
			Cellule cell_vivante_en_cours = cellules_en_vie.pollFirst();
			cellules_a_verifier.add(cell_vivante_en_cours);
			//on parcour les cellules vivante, et pour chaque on regarde quelle cellule
			//il y a autour, on test leur : existance et morte ou vivante
			//Cellule temp_cell_vivante = iterator.next();
			creerPointCellulesVoisine(cell_vivante_en_cours.getPositionVirtuelle());
			
			for (int i = 1; i <= 8; i++){
				//si la cellule existe :
				//if (toutes_les_cellules.containsKey(cellule_voisine[i])){
				//Cellule cellVoisine = toutes_les_cellules.get(cellule_voisine[i]);
				Cellule cellVoisine = cellules_total.get(cellule_voisine[i]);
				if (cellVoisine != null){

					/*
					 * la cellule existe, on test si vivante
					 * si vivante, on ajoute uniquement notre compteur car elle sera testé plus tard
					 */
					if (cellVoisine.isAlive()) {
						cell_vivante_en_cours.addCompteur(1);
					}
					/*
					 * si morte, on incremente son compteur car elle ne sera pas testée
					 * on l'ajoute aux cellules à verifier
					 */
					else if (cellVoisine.isNotAlive()) {
						cellVoisine.addCompteur(1);
						if (cellVoisine.isFlag() == false){
							cellules_a_verifier.add(cellVoisine); // probleme, une cellule peut y etre 8 fois
							cellVoisine.setFlag(true);
						}
					}
				}
				else  {
					// la cellule n'existe pas, il faut la creer morte pour signaler qu'il faudra chercher dedans
					// et ajouter 1 au compteur.
					Cellule tempCell = new Cellule(false, cellule_voisine[i]);
					tempCell.addCompteur(1);
					cellules_total.put(cellule_voisine[i], tempCell);
					cellules_a_verifier.add(tempCell);
					tempCell.setFlag(true);
				}
			}
			//finalement on verifie si elle doit vivre ou mourrir
			//si vivante : reste dans la liste des cells vivantes
			//si morte on la retire
			//dans tous les cas on reinitialise le compteur
		}
		
		//pour le debug
		ggg=cellules_a_verifier.size();
		
		//2eme passe pour la "liste a verifier"
		//cellules_en_vie.clear();
		while (cellules_a_verifier.size()>0) {
			Cellule temp_cell = cellules_a_verifier.pollFirst();
			//verifie le compteur, met a jour "alive", et remet a zero Compteur
			temp_cell.miseAJourVivante();
			temp_cell.setCompteur(0);
			temp_cell.setFlag(false);
			//si vivante, on l'ajoute à la liste
			if (temp_cell.isAlive()){
				cellules_en_vie.add(temp_cell);
			}
		}
		cellules_a_verifier.clear();
		
		
		//on vide le hashmap de toutes les cell :
		//TODO ca fait planter....
		if (cellules_total.size()>500000){
			cellules_total.clear();
			//on met totues les cel en vie dans existante
			Iterator<Cellule> iterator = cellules_en_vie.iterator();
			while (iterator.hasNext()) {
				Cellule key = iterator.next();
				cellules_total.put(key.getPositionVirtuelle(),key);
			}
		}
		
		
		
		
	/*	
		System.out.println("cellule en vie : " + cellules_en_vie.size());

		ListIterator<Cellule> iterator3 = cellules_en_vie.listIterator();
		System.out.println("--debut--");
		while (iterator3.hasNext()) {
			//on parcour les cellules vivante, et pour chaque on regarde quelle cellule
			//il y a autour, on test leur : existance et morte ou vivante
			Cellule temp_cell_vivante = iterator3.next();
			System.out.println(temp_cell_vivante.getPositionVirtuelle());
		}
		System.out.println("--fin--");
		
		*/
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_UP)) {
			moveCameraUp();
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			moveCameraDown();
		}
		if (input.isKeyDown(Input.KEY_LEFT)) {
			moveCameraLeft();
		}
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			moveCameraRight();
		}

		if (input.isKeyDown(Input.KEY_O)) {
			zoomOut();
		}
		if (input.isKeyDown(Input.KEY_I)) {
			zoomIn();
		}
		//reinitialisation
		if (input.isKeyDown(Input.KEY_R)) {
			//on vide les listes :
			initialisation();
		}
		if (input.isKeyDown(Input.KEY_A)) {
			//on vide les listes :
			display_coordonne = !display_coordonne;
		}
		if (input.isKeyDown(Input.KEY_C)) {
			//Pointer sur une cellule vivante
			Point temp_cell = cellules_en_vie.peekLast().getPositionVirtuelle();
			camera_position.set(- (temp_cell.x * taille_cellule) + SCREEN_WIDTH/2 ,
								- (temp_cell.y * taille_cellule) + SCREEN_HEIGHT/2
								);
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}

	private void moveCameraUp() {
		camera_position.y += camera_vitesse;
	}

	private void moveCameraDown() {
		camera_position.y -= camera_vitesse;
	}

	private void moveCameraLeft() {
		camera_position.x += camera_vitesse;
	}

	private void moveCameraRight() {
		camera_position.x -= camera_vitesse;
	}

	private void zoomOut() {
		taille_cellule -= camera_vitesse_zoom;
		if (taille_cellule < 0.2) {
			taille_cellule = 0.2f;
		}
	}

	private void zoomIn() {
		taille_cellule += camera_vitesse_zoom ;
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		displayInfos(g);
//		 g.rotate(SCREEN_WIDTH/2,SCREEN_HEIGHT/2,test+=2f);
		// TODO refactor la partie "camera" pour deplacer agrandir, rotationner
		displayCells(g);
	}

	private void displayCells(Graphics g) {
		g.setColor(Color.white);
		// afficher les cellules
		Iterator<Cellule> iterator = cellules_en_vie.iterator();
		while (iterator.hasNext()) {
			Cellule key = iterator.next();
			g.setColor(Color.white);
			g.fillRect(key.getPositionVirtuelle().x * taille_cellule + camera_position.x,
					   key.getPositionVirtuelle().y * taille_cellule + camera_position.y,
					   taille_cellule, taille_cellule);
			if (display_coordonne ){
				String s = new String(key.getPositionVirtuelle().x+","+key.getPositionVirtuelle().y);
				afficheCoorodnnee(s, key.getPositionVirtuelle().x * taille_cellule + camera_position.x,
						key.getPositionVirtuelle().y * taille_cellule + camera_position.y, g);
			}
			}
	}

	private void afficheCoorodnnee(String s, float f, float h, Graphics g) {
		g.setColor(Color.red);
		g.drawString(s, f, h);
	}

	private void displayInfos(Graphics g) {
		// ptite infos de commandes//debug
		int x = 100;
		int y = 0;
		g.setColor(Color.white);
		g.drawString("zoom in : I, zoom out : O -- Reinitialiser : R -- afficher les cases : A", x, y);
		g.drawString("deplacer la camera : toutes les flèches directionnelle", x, y += 13);
		g.drawString("coordonné X : " + camera_position.x + " Coordonnée Y : " + camera_position.y, x, y += 13);
		g.drawString("nombre de cell en vie :" + cellules_en_vie.size(), x, y += 13);
		g.drawString("nombre de cell existante :" + cellules_total.size(), x, y += 13);
		g.drawString("nombre de cell à verifier :" + ggg, x, y += 13);

		// afficher le centre de la camera
		g.setColor(Color.cyan);
		g.drawLine(camera_position.x - 20, camera_position.y,
				camera_position.x + 20, camera_position.y);
		g.drawLine(camera_position.x, camera_position.y - 20,
				camera_position.x, camera_position.y + 20);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new StartJDLV());
			app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
//			app.setTargetFrameRate(10);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public float getCamera_vitesse() {
		return camera_vitesse;
	}

	public void setCamera_vitesse(float camera_vitesse) {
		this.camera_vitesse = camera_vitesse;
	}
}