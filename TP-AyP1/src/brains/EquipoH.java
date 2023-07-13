package brains;

import java.util.List;
import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class EquipoH extends Brain {

	public EquipoH() {
		super("Equipo H");
	}

	/**
	 * post: Devuelve el proximo movimiento recomendado que debe hacer la serpiente.
	 * 
	 * @param head, la posicion de la cabeza
	 * @param previous, la direccion en la que venia moviendose
	 */

	public Direction getDirection(Point head, Direction anterior) {

		Direction[] opciones = {
				Direction.UP,
				Direction.DOWN,
				Direction.LEFT,
				Direction.RIGHT };

		Direction recomendada = null;
		int i = 0;
		
		Point posiblePosicion = head.clone();
		while (recomendada == null && i < opciones.length) {
			posiblePosicion.moveTo(opciones[i]);
			
			if (movimientoValido(anterior, opciones[i])
					&& masCercaDeFruta(posiblePosicion)
					&& estaLibre(posiblePosicion)
					&& !probableColision(posiblePosicion)) {
				recomendada = opciones[i];
			} else {
			i++;
			posiblePosicion = head.clone();
			}
		}
		if (recomendada == null) {
			i = 0;
			recomendada = anterior;
			boolean sigoBuscando = true;
			posiblePosicion = head.clone();
		
			while (i < opciones.length && sigoBuscando) {
			
				posiblePosicion.moveTo(opciones[i]);
				
				if (movimientoValido(anterior, opciones[i])
					&& estaLibre(posiblePosicion)
					&& !probableColision(posiblePosicion)) {
					recomendada = opciones[i];
					sigoBuscando = false;
				}
				i++;
				posiblePosicion = head.clone();
			}
		}
		return recomendada;
	}

	/**
	 * post: Devuelve si un punto esta libre de peligro, o no.
	 * 
	 * @param unPunto
	 * @return
	 */
	private boolean estaLibre(Point unPunto) {
		List<Point> obstacles = info.getObstacles();
		List<List<Point>> enemies = info.getEnemies();
		List<Point> snake = info.getSnake();
		boolean libre = true;
		int i = 0;
		while (i < obstacles.size() && libre == true) {
			if (obstacles.get(i).equals(unPunto)) {
				libre = false;
			} else
				i++;
		}
		i = 0;
		while (i < enemies.size() && libre == true) {
			int j = 0;
			while (j < enemies.get(i).size() && libre == true) {
				if (enemies.get(i).get(j).equals(unPunto)) {
					libre = false;
				} else
					j++;
			}
			i++;
		}
		i = 0;
		while (i < snake.size() && libre == true) {
			if (snake.get(i).equals(unPunto)) {
				libre = false;
			} else
				i++;
		}
		return libre;
	}

	/**
	 * post: Devuelve la fruta mas cercana a la cabeza de la snake.
	 * 
	 * @return
	 */
	private Point frutaMasCercana() {
		List<Point> fruits = info.getFruits();
		Point head = info.getSnake().get(0);
		Point punto = fruits.get(1);
		if (distancia(head, fruits.get(0)) < distancia(head, fruits.get(1))) {
			punto = fruits.get(0);
		}

		return punto;
	}

	/**
	 * post: Devuelve si un punto esta mas cerca de una fruta, o no.
	 * 
	 * @param punto
	 * @return
	 */
	private boolean masCercaDeFruta(Point punto){
		Point head = info.getSnake().get(0);
		
		boolean masCerca = false;
		if (distancia(punto,frutaMasCercana()) < (distancia(head, frutaMasCercana()))) {
			masCerca = true;
		}
		return masCerca;
	}
	
	/**
	 * post: Devuelve la distancia de un punto a otro.
	 * 
	 * @param unPunto
	 * @param otroPunto
	 * @return
	 */
	private int distancia(Point unPunto, Point otroPunto) {

		int distancia = Math.abs(unPunto.getX() - otroPunto.getX())
				+ Math.abs(unPunto.getY() - otroPunto.getY());

		return distancia;
	}

	/**
	 * post: Devuelve si un movimiento es valido, o no.
	 * 
	 * @param ultima
	 * @param actual
	 * @return
	 */
	private boolean movimientoValido (Direction ultima,Direction actual) {
		boolean valido = true;

		if (actual.reverse() == ultima)
			valido = false;
		return valido;
	}
	
	/**
	 * post: Devuelve si una colision es posible en un punto, o no.
	 * 
	 * @param unPunto
	 * @return
	 */
	private boolean probableColision (Point unPunto) {
		List<List<Point>> enemies = info.getEnemies();
		int i = 0;
		boolean colision = false;
		while  (colision == false && i < enemies.size()) {
			if (distancia(unPunto,enemies.get(i).get(0)) == 1) {
			colision = true;
			}else {
				i++;
			}
		}
		return colision;
	}
}
