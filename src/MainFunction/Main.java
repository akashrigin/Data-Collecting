package MainFunction;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.DateFormat;

import DataBase.JavaJDBC;
import SnmpFunction.SnmpService;
class threadfunction{
	public static SentData sent=new SentData();
	public static SnmpService snmpService=new SnmpService();
	public static JsonCreate json=new JsonCreate();
	public void updateInterface() throws Exception{
		while(true){
			List<Monitor> monitors = Monitor.getmonitordetail();
            System.out.println("Update Operation started by thread"+Thread.currentThread().getName());
            for (Monitor monitor : monitors) {
                sent.sentInterfacesData(json.InterfacesToJson(snmpService.addInterface(monitor)));
            }
            System.out.println("Entering sleep for 20 sec at"+java.time.LocalTime.now());
            Thread.sleep(20000);
            System.out.println("Finished sleep for 20 sec"+java.time.LocalTime.now());
		}
	}
}

public class Main {
	public static SnmpService snmpservice=new SnmpService();
	public static void main(String[] args) throws Exception {
		Scanner sc=new Scanner(System.in);
		SentData sent=new SentData();
		JsonCreate json=new JsonCreate();
		threadfunction tf=new threadfunction();
		Thread updateThread=new Thread(()->{
			try {
				tf.updateInterface();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});
		updateThread.start();
		// Monitor m=AddMonitor(sc);
		// sent.sentMonitorData(json.MonitorToJson(m));
		// sent.sentInterfacesData(json.InterfacesToJson(snmpservice.addInterface(m)));
		
	}

	private static Monitor AddMonitor(Scanner sc) throws IOException {
		Monitor monitor=snmpservice.addMonitor(sc);
		//snmpservice.addInterface(monitor);
		return monitor;
	}

}
