package samples.simplecrud;

/**
 * Created by Juan Carlos on 05/07/2017.
 * Event to send through the bus
 */

public class EventChangeStudent {

    private int id_student;
    private String action;

    public EventChangeStudent(int id, String action){
        this.id_student = id;
        this.action = action;
    }

    public int getId_student(){
        return id_student;
    }

    public String getAction(){
        return action;
    }
}
