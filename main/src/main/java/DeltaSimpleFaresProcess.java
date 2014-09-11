import Entities.States;
import Entities.Zones;
import Fare.AirFare;
import Fare.State;

import java.io.IOException;

/**
 * Created by Marion on 9/10/2014.
 */
public class DeltaSimpleFaresProcess {
    public static void main(String args[]) throws Exception
    {
        States states = new States();
        states.setArrivalState("SC");
        states.setDepartureState("NC");

        State state = new State(states.getArrivalState());
        State state2 = new State(states.getDepartureState());

           Zones k =  state.toZones();
           Zones b = state2.toZones();
            System.out.println("Arrival State:"+" "+states.getArrivalState()+" "+ "Departure State:"+" "+states.getDepartureState()+" "+
                    "Cost: " +AirFare.calculate(b.getZone(), k.getZone()));

    }
}
