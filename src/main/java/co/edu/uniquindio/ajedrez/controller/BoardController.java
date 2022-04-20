package co.edu.uniquindio.ajedrez.controller;

import co.edu.uniquindio.ajedrez.logic.Casilla;
import co.edu.uniquindio.ajedrez.logic.Tablero;
import co.edu.uniquindio.ajedrez.logic.piezas.Pieza;
import co.edu.uniquindio.ajedrez.logic.util.Coordinate;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class BoardController {

    @FXML
    private GridPane gridPane;

    private Button[][] buttons = new Button[8][8];
    private Tablero tablero;
    private Pieza piezaSeleccionada;
    private ArrayList<Coordinate> posiblesMovidas;

    @FXML
    public void initialize() {
        tablero = new Tablero();
        for (Node node: gridPane.getChildren()) {
//            if (node.getClass().equals(Button.class)) {
            if (node instanceof Button) {
                Button button = ((Button) node);
                button.setText(" ");
                Integer row = GridPane.getRowIndex(button);
                Integer column = GridPane.getColumnIndex(button);

                if (row == null) {
                    row = 0;
                }

                button.setStyle("-fx-min-width: 80px;");
                button.setTextFill(Color.BLACK);
                button.setFont(new Font(16));

                buttons[row][column - 1] = button;

                button.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    clicCasillaHandler(event);
                });
            }
        }
        pintarBotones();
        render();
    }

    private void clicCasillaHandler(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        Button button = null;
        for (int i = 0; i < buttons.length ; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                button = buttons[i][j];
                if (clickedButton.equals(button)) {
                    Casilla casilla = tablero.getCasilla(i, j);
                    if (casilla == null) {
                        continue;
                    }

                    casilla.setTablero(this.tablero);

                    // Validamos para mover la pieza seleccionada a un lugar valido
                    if (piezaSeleccionada != null) {
                        piezaSeleccionada.mover(casilla);
                        piezaSeleccionada = null;
                    }

                    if (casilla.getPieza() != null) {
                        if (piezaSeleccionada == null) {
                            piezaSeleccionada = casilla.getPieza();
                        }
                        else if (casilla.getPieza().equals(piezaSeleccionada)) {
                            piezaSeleccionada = null;
                        }
                    }

                    verMovidas(piezaSeleccionada);
                }
            }
        }
        render();
    }

    private void verMovidas(Pieza pieza) {
        pintarBotones();
        if (pieza == null) {
            return;
        }
        ArrayList<Coordinate> coordenadas = pieza.filtradas(pieza, this.tablero);
        if (coordenadas == null) {
            return;
        }
        for (Coordinate coordenada: coordenadas) {
            int column = coordenada.getCol();
            int row = coordenada.getRow();

            String style = buttons[row][column].getStyle();
            style += "-fx-border-color: red; -fx-border-width: 2;";
            buttons[row][column].setStyle(style);
        }
    }

    private void pintarBotones() {
        for (int i = 0; i < buttons.length ; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                if ((i + j) % 2 == 0) {
                    button.setStyle("-fx-background-color: #ffffff;");
                }
                else {
                    button.setStyle("-fx-background-color: #888888;");
                }
            }
        }
    }

    private void render() {
        for (int i = 0; i < buttons.length ; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                Button button = buttons[i][j];
                if (button != null && tablero.getCasilla(i, j).getPieza() instanceof Pieza) {
                    button.setText(tablero.getCasilla(i, j).getPieza().toString());
                }
                else {
                    button.setText("");
                }
            }
        }
    }
}