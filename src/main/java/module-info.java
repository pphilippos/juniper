module org.fxyz3d  {
    requires javafx.base;//.controls;
    //requires javafx.fxml;
    requires javafx.swing;
    requires java.logging;
    requires org.fxyz3d.core;
    requires poly2tri;//.core;
    
    opens org.fxyz3d to javafx.fxml;
    exports org.fxyz3d;

    requires org.fxyz3d.importers;
}
