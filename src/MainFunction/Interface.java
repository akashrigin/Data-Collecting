package MainFunction;

import java.util.Date;

public class Interface {
	private String id;
    private int ind;
    private int monitor_id;
    private int admin;
    private String name;
    private String type;
    private long in_traffic;
    private float rx_utilized;
    private long out_traffic;
    private float tx_utilized;
    private float error;
    private float discards;
    private int status;
    private Date date;
    
    
    public Interface(String id, int ind, int monitor_id, int admin, String name, String type, long in_traffic,
			float rx_utilized, long out_traffic, float tx_utilized, float error, float discards, int status,
			Date date) {
		this.id = id;
		this.ind = ind;
		this.monitor_id = monitor_id;
		this.admin = admin;
		this.name = name;
		this.type = type;
		this.in_traffic = in_traffic;
		this.rx_utilized = rx_utilized;
		this.out_traffic = out_traffic;
		this.tx_utilized = tx_utilized;
		this.error = error;
		this.discards = discards;
		this.status = status;
		this.date = date;
	}


	public Interface( int ind, int monitor_id, int admin, String name, String type,
			long in_traffic, float rx_utilized, long out_traffic, float tx_utilized, float error, float discards,
			int status, Date date) {
		this.id = monitor_id+" "+ind+" "+new Date().getHours()+" "+new Date().getSeconds();
		this.ind = ind;
		this.monitor_id = monitor_id;
		this.admin = admin;
		this.name = name;
		this.type = type;
		this.in_traffic = in_traffic;
		this.rx_utilized = rx_utilized;
		this.out_traffic = out_traffic;
		this.tx_utilized = tx_utilized;
		this.error = error;
		this.discards = discards;
		this.status = status;
		this.date = date;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public int getInd() {
		return ind;
	}


	public void setInd(int ind) {
		this.ind = ind;
	}


	public int getMonitor_id() {
		return monitor_id;
	}


	public void setMonitor_id(int monitor_id) {
		this.monitor_id = monitor_id;
	}


	public int getAdmin() {
		return admin;
	}


	public void setAdmin(int admin) {
		this.admin = admin;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public long getIn_traffic() {
		return in_traffic;
	}


	public void setIn_traffic(long in_traffic) {
		this.in_traffic = in_traffic;
	}


	public float getRx_utilized() {
		return rx_utilized;
	}


	public void setRx_utilized(float rx_utilized) {
		this.rx_utilized = rx_utilized;
	}


	public long getOut_traffic() {
		return out_traffic;
	}


	public void setOut_traffic(long out_traffic) {
		this.out_traffic = out_traffic;
	}


	public float getTx_utilized() {
		return tx_utilized;
	}


	public void setTx_utilized(float tx_utilized) {
		this.tx_utilized = tx_utilized;
	}


	public float getError() {
		return error;
	}


	public void setError(float error) {
		this.error = error;
	}


	public float getDiscards() {
		return discards;
	}


	public void setDiscards(float discards) {
		this.discards = discards;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Interface [id=" + id + ", ind=" + ind + ", monitor_id=" + monitor_id + ", admin=" + admin + ", name="
				+ name + ", type=" + type + ", in_traffic=" + in_traffic + ", rx_utilized=" + rx_utilized
				+ ", out_traffic=" + out_traffic + ", tx_utilized=" + tx_utilized + ", error=" + error + ", discards="
				+ discards + ", status=" + status + ", date=" + date + "]";
	}
	
}
