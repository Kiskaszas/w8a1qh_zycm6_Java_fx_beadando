module org.example {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    //requires org.hibernate.orm.core;
    //requires jakarta.persistence;
    requires jakarta.xml.bind;
    requires jakarta.xml.ws;
    requires java.sql;
    //requires com.sun.xml.txw2;
    //requires com.sun.xml.bind;
    //requires com.sun.xml.ws;

    opens org.example.models to org.hibernate.orm.core, javafx.base;
    opens org.example.controllers to javafx.fxml;
    opens org.example.soap to jakarta.xml.bind, jakarta.xml.ws;

    exports org.example;
    exports org.example.soap;
}
