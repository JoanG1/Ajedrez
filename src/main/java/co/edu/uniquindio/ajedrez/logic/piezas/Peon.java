package co.edu.uniquindio.ajedrez.logic.piezas;

import co.edu.uniquindio.ajedrez.logic.Casilla;
import co.edu.uniquindio.ajedrez.logic.Tablero;
import co.edu.uniquindio.ajedrez.logic.util.Coordinate;

import java.util.ArrayList;
import java.util.Scanner;

public class Peon extends Pieza implements IMover{

    private TipoPieza tipo = TipoPieza.PEON;
    private boolean primerMovimiento = true;

    public Peon(Color color) {
        super(color);
    }

    public ArrayList<Coordinate> movidas(Pieza pieza) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();

        Casilla casilla = this.getCasilla();
        if (casilla == null) {
            return coordinates;
        }

        Coordinate coordenadaActual = casilla.getCoordinate();

        int direccion = 1;
        if (this.getColor().equals(Color.BLANCAS)) {
            direccion = -1;
        }

        Casilla siguiente = casilla.getTablero().getCasilla(coordenadaActual.getRow() + direccion, coordenadaActual.getCol());
        if (siguiente.getPieza() == null) {
            coordinates.add(siguiente.getCoordinate());
        }

        siguiente = casilla.getTablero().getCasilla(coordenadaActual.getRow() + (direccion * 2), coordenadaActual.getCol());
        if (primerMovimiento && coordinates.size() == 1 && siguiente.getPieza() == null) {
            coordinates.add(siguiente.getCoordinate());
        }

        return coordinates;
    }

    public ArrayList<Coordinate> filtradas(Pieza pieza, Tablero tablero){
        return movidas(this);
    }

    public void mover(Casilla destino) {
        Tablero tablero = this.getCasilla().getTablero();
        for (Coordinate cordenada:filtradas(this, tablero)) {
            if (destino.getCoordinate().compareTo(cordenada) == 0) {
                Casilla casilla = this.getCasilla();
                casilla.setPieza(null);
                destino.setPieza(this);
                this.setCasilla(destino);
                this.primerMovimiento = false;
                break;
            }
        }
        int row = this.getCasilla().getCoordinate().getRow();
        if (row == 0 || row == 7) {
            Reina reina = new Reina(this.getColor());
            reina.setCasilla(this.getCasilla());
            this.getCasilla().setPieza(reina);
            this.setCasilla(null);

        }
    }

    public String toString() {
        // https://es.wikipedia.org/wiki/S%C3%ADmbolos_de_ajedrez_en_Unicode
        if (this.getColor().equals(Color.BLANCAS)) {
            return "\u2659";
        }
        else {
            return "\u265F";
        }
    }

}
