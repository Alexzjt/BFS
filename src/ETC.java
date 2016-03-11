
public class ETC {
	public String ETC_NO;
	public String Road1;
	public String Road2;
	public ETC(String eTCNO, String road1, String road2) {
		super();
		ETC_NO = eTCNO;
		Road1 = road1;
		Road2 = road2;
	}
	public ETC(String[] str){
		ETC_NO = str[0];
		Road1 = str[1];
		Road2 = str[2];
	}
	public ETC(String eTCNO , String road1) {
		super();
		ETC_NO = eTCNO;
		Road1 = road1;
	}
	public ETC(String eTCNO) {
		super();
		ETC_NO = eTCNO;
	}
	public String[] toStringArray(){
		String[] str = {ETC_NO,Road1,Road2};
		return str;
	}
	@Override
	public String toString() {
		return ETC_NO+","+Road1+","+Road2+"\r\n";
	}
	
}
