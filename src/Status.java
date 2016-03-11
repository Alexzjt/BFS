
public class Status {
	String NO;
	double value,cost,prediction;
	String path;
	public Status(String nO, double value) {
		super();
		NO = nO;
		this.value = value;
		path = nO;
	}
	public Status(String nO) {
		super();
		NO = nO;
		this.value = 0;
		path = nO;
	}
}
