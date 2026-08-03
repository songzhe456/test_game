module test.game {
    requires javafx.controls;
    requires javafx.graphics;
    requires org.slf4j;

    opens com.test to javafx.graphics;

    exports com.test.client.display;
}