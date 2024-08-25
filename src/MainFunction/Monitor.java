package MainFunction;

import DataBase.JavaJDBC;
import java.io.*;   
import java.util.List;
import java.util.ArrayList;

public class Monitor {

	private int monitor_id;
    private String name;
    private String sys_name;
    private String ip_add;
    private String description;
    private String category;
    private String community_string;
    private String version;
    private String portnum;

    public Monitor(int monitor_id, String name, String sys_name, String ip_add, String description, String category,
            String community_string, String version, String portnum) {
        this.monitor_id = monitor_id;
        this.name = name;
        this.sys_name = sys_name;
        this.ip_add = ip_add;
        this.description = description;
        this.category = category;
        this.community_string = community_string;
        this.version = version;
        this.portnum = portnum;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setCommunity_string(String community_string) {
        this.community_string = community_string;
    }

    public Monitor() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Monitor( String name, String sys_name, String ip_add, String description, String category,
			String community_string, String version, String portnum) {
		this.monitor_id = IndexFind.getmonitorindex();
		this.name = name;
		this.sys_name = sys_name;
		this.ip_add = ip_add;
		this.description = description;
		this.category = category;
		this.community_string = community_string;
		this.version = version;
		this.portnum = portnum;
	}

    public String getCommunity_string() {
        return community_string;
    }

    public String getName() {
        return name;
    }

	public String getIp_add() {
        return ip_add;
    }

    public void setIp_add(String ip_add) {
        this.ip_add = ip_add;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getPortnum() {
        return portnum;
    }

    public void setPortnum(String portnum) {
        this.portnum = portnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMonitor_id() {
        return monitor_id;
    }

    public void setMonitor_id(int monitor_id) {
        this.monitor_id = monitor_id;
    }

    @Override
    public String toString() {
        return "Monitor [monitor_id=" + monitor_id + ", name=" + name + ", sys_name=" + sys_name + ", ip_add=" + ip_add
                + ", description=" + description + ", category=" + category + ", community_string=" + community_string
                + ", version=" + version + ", portnum=" + portnum + "]";
    }
    public static void addmonitordetail(Monitor monitor){
        
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter("monitordetail.txt",true));
            writer.write(monitor.getMonitor_id()+" "+monitor.getIp_add()+" "+monitor.getCommunity_string()+" "+monitor.getPortnum()+" "+monitor.getVersion()+"\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static List<Monitor> getmonitordetail(){
        String Line="";
        List <Monitor> list=new ArrayList<>();
        try {
            BufferedReader reader=new BufferedReader(new FileReader("monitordetail.txt"));
            while((Line=reader.readLine())!=null){
                System.out.println("hi");
                list.add(new Monitor(Integer.parseInt(Line.split(" ")[0]),"","",Line.split(" ")[1],"","",Line.split(" ")[2],Line.split(" ")[4],Line.split(" ")[3]));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
        
    }
    
}
class IndexFind{
    public static int getmonitorindex(){
        int index=0;
        //File file=new File("monitorindex.txt");
        try {
            if(!new File("monitorindex.txt").exists()){
                new File("monitorindex.txt").createNewFile();
            }
            BufferedReader reader=new BufferedReader(new FileReader("monitorindex.txt"));
            index=Integer.parseInt(reader.readLine()+"");
            System.out.println(index);
            reader.close();
            BufferedWriter writer=new BufferedWriter(new FileWriter("monitorindex.txt"));
            writer.write((index+1)+"");
            writer.close();
        } catch (Exception e) {e.printStackTrace();}
        
        return index;
    }
}
