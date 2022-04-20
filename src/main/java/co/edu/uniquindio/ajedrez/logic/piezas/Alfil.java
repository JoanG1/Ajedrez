package co.edu.uniquindio.ajedrez.logic.piezas;

import co.edu.uniquindio.ajedrez.logic.Casilla;
import co.edu.uniquindio.ajedrez.logic.Tablero;
import co.edu.uniquindio.ajedrez.logic.util.Coordinate;

import java.util.ArrayList;

public class Alfil extends Pieza implements IMover{

    private TipoPieza tipo = TipoPieza.ALFIL;

    public Alfil(Color color) {
        super(color);
    }

    @Override
    public void mover(Casilla destino) {
        Tablero tablero = this.getCasilla().getTablero();
        for (Coordinate cordenada:filtradas(this, tablero)) {
            if (destino.getCoordinate().compareTo(cordenada) == 0) {
                Casilla casilla = this.getCasilla();
                casilla.setPieza(null);
                destino.setPieza(this);
                this.setCasilla(destino);
                break;
            }
        }
    }

    public ArrayList<Coordinate> movidas(Pieza pieza) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        if (coordinate != null) {
            // Las posibles posiciones del caballo pueden ser obtenidas teniendo en cuenta que las posiciones
            // toman la forma de un circulo espaciado cada 45 grados.
            double radians = Math.toRadians(45.0);
            for (int j = 1; j < 8; j++) {
                for (int i = 0; i < 4; i++) {
                    int rowPos = coordinate.getRow() + (int) Math.round(Math.sin(radians) * ((double) j));
                    int colPos = coordinate.getCol() + (int) Math.round(Math.cos(radians) * ((double) j));
                    radians += Math.toRadians(90.0);

                    if (rowPos >= 0 && rowPos <= 7 && colPos >= 0 && colPos <= 7) {
                        coordinates.add(new Coordinate(rowPos, colPos));
                    }
                }
            }
        }
        return coordinates;
    }

    public ArrayList<Coordinate> filtradas(Pieza pieza, Tablero tablero){
        ArrayList<Coordinate> filtradas = new ArrayList<>();
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        if (coordinate != null) {
            double radians = Math.toRadians(45.0);
            // Iteramos sobre el angulo
            for (int i = 0; i < 4; i++) {
                // Iteramos del centro hacia afuera
                for (int j = 1; j < 8; j++) {
                    int rowPos = coordinate.getRow() + (int) Math.round(Math.sin(radians) * ((double) j));
                    int colPos = coordinate.getCol() + (int) Math.round(Math.cos(radians) * ((double) j));
                    if (rowPos >= 0 && rowPos <= 7 && colPos >= 0 && colPos <= 7) {
                        Casilla siguiente = tablero.getCasilla(rowPos, colPos);
                        if (siguiente.getPieza() == null) {
                            filtradas.add(new Coordinate(rowPos, colPos));
                        }
                        else {
                            if (!siguiente.getPieza().getColor().equals(this.getColor())) {
                                filtradas.add(new Coordinate(rowPos, colPos));
                            }
                            break;
                        }
                    }
                }
                radians += Math.toRadians(90.0);
            }
        }
        return filtradas;
    }

    public String toString() {
        // https://es.wikipedia.org/wiki/S%C3%ADmbolos_de_ajedrez_en_Unicode
        if (this.getColor().equals(Color.BLANCAS)) {
            return "\u2657";
        }
        else {
            return "\u265D";
        }
    }
}
