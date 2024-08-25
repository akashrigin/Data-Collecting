package MainFunction;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

public class JsonCreate {

    public static Gson gson=new Gson();

    public String MonitorToJson(Monitor monitor){

        return gson.toJson(monitor);
    }

    public String InterfaceToJson(Interface interface1){

        return gson.toJson(interface1);
    }

    public List<String> InterfacesToJson(List<Interface> interfaces){
        List<String> list=new ArrayList<>();
        for(Interface i:interfaces){
            list.add(InterfaceToJson(i));
        }
        return list;
    }
}
