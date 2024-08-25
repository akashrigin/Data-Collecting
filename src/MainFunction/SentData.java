package MainFunction;
import java.net.URL;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

public class SentData {
    public void sentMonitorData(String body) throws Exception{
        URL url = new URL("http://localhost:8080/getmonitor");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
    
    public void sentInterfaceData(String body) throws Exception{
        URL url = new URL("http://localhost:8080/getinterface");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
    public void sentInterfacesData(List<String> body) throws Exception{
        for(String s:body){
            sentInterfaceData(s);
        }
    }
    
}
