package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainFunction.Interface;
import MainFunction.Monitor;

@Deprecated
public class JavaJDBC {
    private static String url="jdbc:mysql://localhost:3305/network";
    private static String username="root";
    private static String password="system";

	public static List<Monitor> getMonitor(){ 
        List <Monitor> list=new ArrayList<>(); 
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(url,username,password);  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from monitor");  
            while(rs.next())  
                list.add(new Monitor(rs.getInt(1),rs.getString(6),rs.getString(8),rs.getString(5),rs.getString(4),rs.getString(2),rs.getString(3),rs.getString(9),rs.getString(7)));
                //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6)+"  "+rs.getString(7)+"  "+rs.getString(8)+"  "+rs.getString(9));  
            con.close(); 
            return list; 
        }
        catch(Exception e){ System.out.println(e+" hi");}  
        return list;
    }  
    public int getIndex(){ 
        int index=1; 
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(url,username,password);  
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery("select * from indextable");  
            System.out.println("hi");
            while(rs.next())  
                index=rs.getInt(1);
                
            PreparedStatement prepstmt=con.prepareStatement("update indextable set ind=(?)");
            prepstmt.setInt(1,index+1);
            prepstmt.executeUpdate(); 
            con.close(); 
            return index; 
        }
        catch(Exception e){ System.out.println(e);}  
        return index;
    }

    public void addMonitor(Monitor monitor){ 
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(url,username,password);  
            PreparedStatement stmt=con.prepareStatement("insert into monitor values(?,?,?,?,?,?,?,?,?)");  
            stmt.setInt(1,monitor.getMonitor_id()); 
            stmt.setString(2,monitor.getCategory());  
            stmt.setString(3,monitor.getCommunity_string());
            stmt.setString(4,monitor.getDescription());
            stmt.setString(5,monitor.getIp_add());
            stmt.setString(6,monitor.getName());
            stmt.setString(7,monitor.getPortnum());
            stmt.setString(8,monitor.getSys_name());
            stmt.setString(9,monitor.getVersion());
            int i=stmt.executeUpdate();  
            System.out.println(i+" records inserted");  
            con.close(); 
        }
        catch(Exception e){ System.out.println(e);}
    } 
    
    public void addInterface(Interface inter){ 
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con=DriverManager.getConnection(url,username,password);  
            PreparedStatement stmt=con.prepareStatement("insert into interface values(?,?,date(now()),?,?,?,?,?,?,?,?,?,?,?)");  
            stmt.setString(1,inter.getId()); 
            stmt.setInt(2,inter.getAdmin());  
            stmt.setFloat(3,inter.getDiscards());
            stmt.setFloat(4,inter.getError());
            stmt.setLong(5,inter.getIn_traffic());
            stmt.setInt(6,inter.getInd());
            stmt.setInt(7,inter.getMonitor_id());
            stmt.setString(8,inter.getName());
            stmt.setLong(9,inter.getOut_traffic());
            stmt.setFloat(10,inter.getRx_utilized());
            stmt.setInt(11,inter.getStatus());
            stmt.setFloat(12,inter.getTx_utilized());
            stmt.setString(13,inter.getType());
            stmt.executeUpdate();  
            System.out.println(" interface inserted");  
            con.close(); 
        }
        catch(Exception e){ System.out.println(e);}
    }

    public void addInterfaces(List<Interface> interface1) {
        for(Interface i:interface1){
            addInterface(i);
        }
    } 
}  

