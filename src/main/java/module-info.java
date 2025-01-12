module br.ufrn.imd.projetofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens br.ufrn.imd.projetofinal.control to javafx.fxml;
    exports br.ufrn.imd.projetofinal;
}
