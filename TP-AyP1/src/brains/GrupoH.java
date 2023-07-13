package brains;

import java.util.ArrayList;
import java.util.List;

import edu.unlam.snake.brain.Brain;
import edu.unlam.snake.engine.Direction;
import edu.unlam.snake.engine.Point;

public class GrupoH extends Brain {

	public GrupoH() {
		super("GrupoH");
	}

	/*
	 * pre: recibe la posicion de la cabeza de la snake y la ultima direccion.
	 * 
	 * post: devuelve la proxima direccion que hara la snake.
	 * 
	 * @param head: cabeza de la snake del jugador.
	 * 
	 * @param anterior: la anterior posicion de la cabeza
	 */
	public Direction getDirection(Point head, Direction anterior) {

		List<distanciaDeFruta> fruitDistances = this.fruitDistancia(head);
		distanciaDeFruta frutita = fruitDistances.get(0);
		Direction direccionOptima = frutita.direccionesRecomendadas[0];
		Direction[] mejoresDirecciones;
		for (int length = (mejoresDirecciones = frutita.direccionesRecomendadas).length, i = 0; i < length; ++i) {
			Direction posibleDirection = mejoresDirecciones[i];
			if (posibleDirection == (frutita.direccionesRecomendadas[i])
					&& !this.puedeMorir(posibleDirection.move(head))
					&& !this.cabezaSnakeEnemigoCerca(
							posibleDirection.move(head), head)) {
				direccionOptima = posibleDirection;
				break;
			}
		}

		return direccionOptima;
	}

	/*
	 * pre: recibe la posicion de la cabeza de la snake del jugador.
	 * 
	 * post: devuelve si la cabeza en su posicion actual puede morir.
	 * 
	 * @param headPrincipal: posicion de la cabeza de la snake.
	 */
	private boolean puedeMorir(Point headPrincipal) {

		// La lista 'snakeEnemigas' recorre todos los puntos de las snakes vivas
		// enemigas.
		for (List<Point> snakeEnemigas : this.info.getEnemies()) {
			// recorre los puntos de las snakes enemigas y confirma
			// si la cabeza coincide con algun punto de ellas.
			for (Point punto1 : snakeEnemigas.subList(0, snakeEnemigas.size())) {
				if (punto1.equals(headPrincipal)) {
					return true;
				}
			}
		}// recorre todos los puntos de la snake "GrupoH" y confirma
			// si la cabeza coincide con el punto de su propio cuerpo.
		for (Point punto2 : this.info.getSnake().subList(0,
				this.info.getSnake().size())) {
			if (punto2.equals(headPrincipal)) {
				return true;
			}
		}// recorre todos los obstaculos en el mapa y confirma
			// si la cabeza coincide con algun obstaculo.
		for (Point obstacles : this.info.getObstacles()) {
			if (obstacles.equals(headPrincipal)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * pre: recibe los puntos de la cabeza de nuestra snake y los puntos de la
	 * cabeza de la snake enemiga.
	 * 
	 * post: devuelve, si es cierto, que hay una cabeza de snake enemiga cerca
	 * 
	 * @param headPrincipal, la cabeza de la snake del jugador.
	 * 
	 * @param headEnemiga, la cabeza de la snake enemiga.
	 */
	private boolean cabezaSnakeEnemigoCerca(Point headPrincipal,
			Point headEnemiga) {
		List<Point> posibles = new ArrayList<Point>();
		if (!Direction.UP.move(headPrincipal).equals(headEnemiga)) {
			posibles.add(Direction.UP.move(headPrincipal));
		}
		if (!Direction.LEFT.move(headPrincipal).equals(headEnemiga)) {
			posibles.add(Direction.LEFT.move(headPrincipal));
		}
		if (!Direction.DOWN.move(headPrincipal).equals(headEnemiga)) {
			posibles.add(Direction.DOWN.move(headPrincipal));
		}
		if (!Direction.RIGHT.move(headPrincipal).equals(headEnemiga)) {
			posibles.add(Direction.RIGHT.move(headPrincipal));
		}
		for (List<Point> snakeEnemigas : this.info.getEnemies()) {
			Point cabezasDeSnakeEnemigas = snakeEnemigas.get(0); // cabeza del enemigo (tiene que hacerse para dos)
			for (Point puntoPosible : posibles) {
				if (cabezasDeSnakeEnemigas.equals(puntoPosible)) { // si la cabeza del enemigo es igual al punto posible devuelve verdadero
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * post: devuelve las distancias de la snake a las frutas.
	 * 
	 * @param: head, el punto de la cabeza de la snake.
	 */
	private List<distanciaDeFruta> fruitDistancia(Point head) {
		List<distanciaDeFruta> distancias = new ArrayList<distanciaDeFruta>();
		for (Point fruitPoint : this.info.getFruits()) {
			distancias.add(new distanciaDeFruta(fruitPoint, head));
		}
		return distancias;
	}

	private class distanciaDeFruta {
		public int distanciaX;
		public int distanciaY;
		public Direction[] direccionesRecomendadas;

		/*
		 * pre:
		 * 
		 * post:
		 * 
		 * @param puntoDeLafruta:
		 * 
		 * @param referencia:
		 */
		public distanciaDeFruta(Point puntoDeLaFruta, Point referencia) {
			// En el arreglo unidimensional tipo Direction llamado
			// 'direccionesRecomendadas' se declara un arreglo de 4 espacios.
			this.direccionesRecomendadas = new Direction[4];
			this.distanciaX = referencia.getX() - puntoDeLaFruta.getX();
			this.distanciaY = referencia.getY() - puntoDeLaFruta.getY();
			// Si el valor absoluto de la distanciaX es mayor que el valor
			// absoluto de la distanciaY.
			if (Math.abs(this.distanciaX) > Math.abs(this.distanciaY)) {
				if (this.distanciaX > 0) {
					this.direccionesRecomendadas[0] = Direction.LEFT;
				} else {
					this.direccionesRecomendadas[0] = Direction.RIGHT;
				}

				if (this.distanciaY > 0) {
					this.direccionesRecomendadas[1] = Direction.DOWN;
				} else {
					this.direccionesRecomendadas[1] = Direction.UP;
				}

				if (this.distanciaY > 0) {
					this.direccionesRecomendadas[2] = Direction.UP;
				} else {
					this.direccionesRecomendadas[2] = Direction.DOWN;
				}

				if (this.distanciaX > 0) {
					this.direccionesRecomendadas[3] = Direction.RIGHT;
				} else {
					this.direccionesRecomendadas[3] = Direction.LEFT;
				}

			} else {// Si no, si el valor absoluto de la distanciaY es mayor que
					// el valor de la distanciaX
				if (this.distanciaY > 0) {
					this.direccionesRecomendadas[0] = Direction.DOWN;
				} else {
					this.direccionesRecomendadas[0] = Direction.UP;
				}

				if (this.distanciaX > 0) {
					this.direccionesRecomendadas[1] = Direction.LEFT;
				} else {
					this.direccionesRecomendadas[1] = Direction.RIGHT;
				}
				if (this.distanciaX > 0) {
					this.direccionesRecomendadas[2] = Direction.RIGHT;
				} else {
					this.direccionesRecomendadas[2] = Direction.LEFT;
				}
				if (this.distanciaY > 0) {
					this.direccionesRecomendadas[3] = Direction.UP;
				} else {
					this.direccionesRecomendadas[3] = Direction.DOWN;
				}

			}
		}
	}
}