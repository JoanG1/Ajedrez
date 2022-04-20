package co.edu.uniquindio.ajedrez.logic.piezas;

import co.edu.uniquindio.ajedrez.logic.Casilla;
import co.edu.uniquindio.ajedrez.logic.Tablero;
import co.edu.uniquindio.ajedrez.logic.util.Coordinate;

import java.util.ArrayList;

public class Rey extends Pieza implements IMover{

    private TipoPieza tipo = TipoPieza.REY;

    public Rey(Color color) {
        super(color);
    }

    public ArrayList<Coordinate> movidas(Pieza pieza) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        if (coordinate != null) {
            double radians = Math.toRadians(0.0);
            for (int i = 0; i < 8; i++) {
                int rowPos = coordinate.getRow() + (int) Math.round(Math.sin(radians) * 1.0);
                int colPos = coordinate.getCol() + (int) Math.round(Math.cos(radians) * 1.0);
                radians += Math.toRadians(45.0);

                if (rowPos >= 0 && rowPos <= 7 && colPos >= 0 && colPos <= 7) {
                    coordinates.add(new Coordinate(rowPos, colPos));
                }
            }
        }
        return coordinates;
    }

    public ArrayList<Coordinate> filtradas(Pieza pieza, Tablero tablero){
        ArrayList<Coordinate> coordinates = movidas(this);
        ArrayList<Coordinate> filtradas = new ArrayList<>();
        for (Coordinate cordenada: coordinates) {
            Casilla siguiente = this.getCasilla().getTablero().getCasilla(cordenada.getRow(), cordenada.getCol());
            if (siguiente.getPieza() == null || (siguiente != null && !siguiente.getPieza().getColor().equals(this.getColor()))) {
                filtradas.add(cordenada);
            }
        }
        return filtradas;
    }

    public String toString() {
        // https://es.wikipedia.org/wiki/S%C3%ADmbolos_de_ajedrez_en_Unicode
        if (this.getColor().equals(Color.BLANCAS)) {
            return "\u2654";
        }
        else {
            return "\u265A";
        }
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
}
