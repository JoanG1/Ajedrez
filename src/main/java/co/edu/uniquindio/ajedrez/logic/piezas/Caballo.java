package co.edu.uniquindio.ajedrez.logic.piezas;

import co.edu.uniquindio.ajedrez.logic.Casilla;
import co.edu.uniquindio.ajedrez.logic.Tablero;
import co.edu.uniquindio.ajedrez.logic.util.Coordinate;

import java.util.ArrayList;

public class Caballo extends Pieza implements IMover{

    private TipoPieza tipo = TipoPieza.CABALLO;

    public Caballo(Color color) {
        super(color);
    }

    public void mover(Casilla destino) {
        Tablero tablero = this.getCasilla().getTablero();
        for (Coordinate cordenada:filtradas(this, tablero)) {
            if (destino.getCoordinate().compareTo(cordenada) == 0) {
                Casilla casilla = this.getCasilla();
                casilla.setPieza(null);
                destino.setPieza(this);
                this.setCasilla(destino);
                casilla.setTablero(tablero);
                break;
            }
        }
    }

    /*
    * Esta funcion no es compatible con el codigo inicial y es omitida.
    */
    public void mover(Pieza pieza, Casilla movimiento) {
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        ArrayList<Coordinate> movidas = movidas(pieza);
        System.out.println(movidas);
        int i=0;

        if (coordinate.getRow() <7 && coordinate.getCol()<7 && coordinate.getRow() >0 && coordinate.getCol()<0){
            switch(i){

                case 1 :
                    movimiento1(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 2 :
                    movimiento2(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 3 :
                    movimiento3(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 4 :
                    movimiento4(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 5 :
                    movimiento5(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 6 :
                    movimiento6(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 7 :
                    movimiento7(pieza);
                    validarPieza(pieza, movimiento);
                    break;

                case 8 :
                    movimiento8(pieza);
                    validarPieza(pieza, movimiento);
                    break;
            }
        }
    }

    /*
    * Esta funcion no es utilizada
    * */
    public void validarPieza(Pieza pieza, Casilla coordinate){
        Tablero tablero = this.getCasilla().getTablero();
        if (pieza instanceof Caballo && coordinate.getPieza() != null){
            if (( pieza.getColor().equals(Color.BLANCAS) && coordinate.getPieza().getColor().equals(Color.BLANCAS)) || (pieza.getColor().equals(Color.NEGRAS) && coordinate.getPieza().getColor().equals(Color.NEGRAS))){
                System.out.println("Movimiento inválido, La pieza es del mismo color");
            }
        }
        else {
            comerPieza(pieza, coordinate);
        }
    }

    public void movimiento1 (Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setCol(coordinate.getCol() + row);
        coordinate.setRow(coordinate.getRow() + col);

    }

    public void movimiento2(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() - col);
        coordinate.setCol(coordinate.getCol() + row);

    }

    public void movimiento3(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() + col);
        coordinate.setCol(coordinate.getCol() - row);
    }

    public void movimiento4(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() - col);
        coordinate.setCol(coordinate.getCol() - row);
    }

    public void movimiento5(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() + row);
        coordinate.setCol(coordinate.getCol() + col);
    }

    public void movimiento6(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() + row);
        coordinate.setCol(coordinate.getCol() - col);
    }

    public void movimiento7(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() - row);
        coordinate.setCol(coordinate.getCol() + col);
    }

    public void movimiento8(Pieza pieza){
        int col=1, row=3;
        Coordinate coordinate = pieza.getCasilla().getCoordinate();
        coordinate.setRow(coordinate.getRow() - row);
        coordinate.setCol(coordinate.getCol() - col);
    }

    public ArrayList<Coordinate> movidas(Pieza pieza) {
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        Coordinate coordinate = this.getCasilla().getCoordinate();
        if (coordinate != null) {
            // Las posibles posiciones del caballo pueden ser obtenidas teniendo en cuenta que las posiciones
            // toman la forma de un circulo espaciado cada 45 grados.
            double radians = Math.toRadians(45.0 / 2.0);
            for (int i = 0; i < 8; i++) {
                int rowPos = coordinate.getRow() + (int) Math.round(Math.sin(radians) * 2.0);
                int colPos = coordinate.getCol() + (int) Math.round(Math.cos(radians) * 2.0);
                radians += Math.toRadians(45.0);

                if (rowPos >= 0 && rowPos <= 7 && colPos >= 0 && colPos <= 7) {
                    coordinates.add(new Coordinate(rowPos, colPos));
                }
            }
        }
        return coordinates;
    }

    public ArrayList<Coordinate> filtradas(Pieza pieza, Tablero tablero){
        ArrayList<Coordinate> filtradas =  new ArrayList<>();
        for (Coordinate coordenada: movidas(this)) {
            Casilla casilla = pieza.getCasilla().getTablero().getCasilla(coordenada.getRow(), coordenada.getCol());
            casilla.setTablero(pieza.getCasilla().getTablero());
            if (casilla != null) {
                if (casilla.getPieza() == null || (casilla.getPieza() != null && casilla.getPieza().getColor() != this.getColor())) {
                    filtradas.add(coordenada);
                }
            }
        }
        return filtradas;
    }

    public void comerPieza(Pieza pieza,Casilla coordinate){
        coordinate.setPieza(pieza);
    }

    public String toString() {
        // https://es.wikipedia.org/wiki/S%C3%ADmbolos_de_ajedrez_en_Unicode
        if (this.getColor().equals(Color.BLANCAS)) {
            return "\u2658";
        }
        else {
            return "\u265E";
        }
    }
}
