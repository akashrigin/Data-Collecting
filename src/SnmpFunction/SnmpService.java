package SnmpFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.snmp4j.Snmp;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import DataBase.JavaJDBC;
import MainFunction.Monitor;
import MainFunction.Interface;


public class SnmpService {
    private static String COMMUNITY;
    private static String TARGET_ADDRESS;

    public static JavaJDBC jdbc=new JavaJDBC();

    private static Target setTarget() {
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString(COMMUNITY));
        target.setAddress(GenericAddress.parse(TARGET_ADDRESS));
        target.setRetries(2);
        target.setTimeout(1500);
        target.setVersion(SnmpConstants.version2c);
        return target;
    }

    public static String performGet(Snmp snmp, String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);
        String s="";

        Target target = setTarget();
        ResponseEvent responseEvent = snmp.send(pdu, target);

        if (responseEvent != null && responseEvent.getResponse() != null) {
            PDU responsePDU = responseEvent.getResponse();
            //System.out.println("GET Response: " + responsePDU.getVariableBindings());
            s=responsePDU.getVariableBindings().get(0).getVariable().toString();
            //return responsePDU.getVariableBindings().get(0).getVariable().toString();

        } else {
            s="false";
            //System.out.println("GET Request failed");
        }
        return s;
    }

    public static String performGetNext(Snmp snmp, String oid) throws IOException {
        PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);
        String s="";

        Target target = setTarget();
        ResponseEvent responseEvent = snmp.send(pdu, target);

        if (responseEvent != null && responseEvent.getResponse() != null) {
            PDU responsePDU = responseEvent.getResponse();
            //System.out.println("GETNEXT Response: " + responsePDU.getVariableBindings());
            s=responsePDU.getVariableBindings().get(0).getOid().toString()+" "+responsePDU.getVariableBindings().get(0).getVariable().toString();
            //return responsePDU.getVariableBindings().get(0).getOid()+" "+responsePDU.getVariableBindings().get(0).getVariable();
        } else {
            s="false";
            //System.out.println("GETNEXT Request failed");
        }
        return s;
    }

    public static List<String> performGetSubTreeIndex(Snmp snmp, String baseOID) throws IOException {
        List <String> index=new ArrayList<>();
        //OID base = new OID(baseOID);
        OID o=new OID(baseOID);
        String s=performGetNext(snmp, baseOID);
        String indOid=s.split(" ")[0];
        index.add(indOid.split("[.]")[indOid.split("[.]").length-1]);
        OID n=new OID(s.split(" ")[0]);
        String id;
        while(n.size()>=o.size()&&(n+"").startsWith(baseOID+"")){
            o=n;
             s=performGetNext(snmp, o+"");
             indOid=s.split(" ")[0];
             id=indOid.split("[.]")[indOid.split("[.]").length-1];
             if(!index.contains(id)) {
                 index.add(indOid.split("[.]")[indOid.split("[.]").length - 1]);
             }
            n=new OID(s.split(" ")[0]);
        };
        return index;
    }

    public static String hextoascii(String s){
        String ans;
        if(s.split(":").length<2){
            ans=s;
        }
        else{
            String newString=s.replace(":","");
            int length = newString.length();
            byte[] bytes = new byte[length / 2];

            for (int i = 0; i < length; i += 2) {
                bytes[i / 2] = (byte) Integer.parseInt(newString.substring(i, i + 2), 16);
            }
            ans=new String(bytes);
            ans=ans.substring(0,ans.length()-1);
        }
        return ans;
    }

    public Monitor addMonitor(Scanner sc) throws IOException {
        System.out.println("Enter name:");
		String name=sc.nextLine();
		System.out.println("Enter ip_add:");
		String ip_add=sc.nextLine();
		System.out.println("Enter category:");
		String category=sc.nextLine();
		System.out.println("Enter community string:");
		String communityString=sc.nextLine();
		System.out.println("Enter version:");
		String version=sc.nextLine();
		System.out.println("Enter portnum:");
		String portnum=sc.nextLine();
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();
        COMMUNITY=communityString;
        TARGET_ADDRESS="udp:"+ip_add+"/"+portnum;
        if(performGet(snmp,"1.3.6.1.2.1.1.5.0").equals("false")){
            System.out.println("Monitor Entry failed.");
            return null;
        }
        Monitor monitor= new Monitor( name, performGet(snmp,"1.3.6.1.2.1.1.5.0"), ip_add, performGet(snmp,"1.3.6.1.2.1.1.1.0"), category, communityString, version, portnum);
        snmp.close();
        Monitor.addmonitordetail(monitor);
        return monitor;
    }
	
	public List<Interface> addInterface(Monitor monitor) throws IOException {
        //System.out.println("Update done By "+Thread.currentThread().getName());
        List <Interface> list=new ArrayList<>();
        Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
        snmp.listen();
        COMMUNITY=monitor.getCommunity_string();
        TARGET_ADDRESS="udp:"+monitor.getIp_add()+"/"+monitor.getPortnum();
        List <String> index=performGetSubTreeIndex(snmp, "1.3.6.1.2.1.2.2.1.2");
        String name,type;
        long in_traffic,out_traffic;
        int errors,discards,status,ind,admin;
        //int monitorid=monitor.getid();
        float rx_Utilized,tx_utilized,errorpercent,discardpercent;
        for(String s:index){
            if(!performGet(snmp,"1.3.6.1.2.1.2.2.1.8."+s).equals("false")){
                //System.out.println("inside loop for"+s);
                status =Integer.parseInt(performGet(snmp,"1.3.6.1.2.1.2.2.1.8."+s));
                ind=Integer.parseInt(s);
                admin=Integer.parseInt(performGet(snmp,"1.3.6.1.2.1.2.2.1.7."+s));
                name=hextoascii(performGet(snmp,"1.3.6.1.2.1.2.2.1.2."+s));
                type=performGet(snmp,"1.3.6.1.2.1.2.2.1.3."+s);
                in_traffic=Long.parseLong(performGet(snmp,"1.3.6.1.2.1.2.2.1.10."+s))/1048576;
                if(in_traffic==0){
                    rx_Utilized=0;
                    out_traffic=0;
                    tx_utilized=0;
                    errorpercent=0;
                    discardpercent=0;
                }
                else {
                    rx_Utilized = (Float.parseFloat(performGet(snmp, "1.3.6.1.2.1.2.2.1.11." + s)) * 64) / (float) in_traffic/1048576;
                    out_traffic = Long.parseLong(performGet(snmp, "1.3.6.1.2.1.2.2.1.16." + s)) / 1048576;
                    tx_utilized = (Float.parseFloat(performGet(snmp, "1.3.6.1.2.1.2.2.1.17." + s)) * 64) / (float) out_traffic/1048576;
                    errors = Integer.parseInt(performGet(snmp, "1.3.6.1.2.1.2.2.1.20." + s)) + Integer.parseInt(performGet(snmp, "1.3.6.1.2.1.2.2.1.14." + s));
                    discards = Integer.parseInt(performGet(snmp, "1.3.6.1.2.1.2.2.1.19." + s)) + Integer.parseInt(performGet(snmp, "1.3.6.1.2.1.2.2.1.13." + s));
                    errorpercent = errors / (float) (in_traffic + out_traffic);
                    discardpercent = discards / (float) (in_traffic + out_traffic);
                }
                list.add(new Interface(ind,monitor.getMonitor_id(),admin,name,type,in_traffic,rx_Utilized,out_traffic,tx_utilized,errorpercent,discardpercent,status,null));

            }
            else{
                list.add(new Interface(Integer.parseInt(s),monitor.getMonitor_id(),2,"","",0,0,0,0,0,0,2,null));
            }

        }
        return list;
        
    }
}
