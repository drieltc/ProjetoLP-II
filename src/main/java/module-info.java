module br.ufrn.imd.projetofinal {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.ufrn.imd.projetofinal.control to javafx.fxml;
    exports br.ufrn.imd.projetofinal;
}
