package main;
import javafx.print.PrintColor;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.time.*;

// import org.apache.commons.lang3.time;

public class dynamicTrafficSignalControlSystems {
    public static void main() {
        //run
        final physicalTrafficSignal physicalTrafficSignal= new physicalTrafficSignal();
        final controller controller = new controller();
        //java只給用final
    }
}
