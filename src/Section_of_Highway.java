
public class Section_of_Highway {
	public String NO;
	public String Next_NO;
	public double Length;
	public double O_longitude;
	public double O_latitude;
	//public double Car_v;
	//public double Bus_v;
	//public double Truck_v;
	public String Tachymeter_Code;
	public String Tachymeter_NO;
	public String ETC_Code;
	public String ETC_NO;
	public boolean read;
	public Section_of_Highway(String nO, String next_NO, double length,
			double o_longitude, double o_latitude, String tachymeter_Code,
			String tachymeter_NO, String eTC_Code, String eTC_NO) {
		super();
		NO = nO;
		Next_NO = next_NO;
		Length = length;
		O_longitude = o_longitude;
		O_latitude = o_latitude;
		Tachymeter_Code = tachymeter_Code;
		Tachymeter_NO = tachymeter_NO;
		ETC_Code = eTC_Code;
		ETC_NO = eTC_NO;
		this.read = false;
	}
	public Section_of_Highway(String[] str){
		NO = str[0];
		Next_NO = str[1];
		if(str[2].equals("--"))
			Length = 0;
		else
			Length = Double.valueOf(str[2]);
		O_longitude = Double.valueOf(str[3]);
		O_latitude = Double.valueOf(str[4]);
		Tachymeter_Code = str[5];
		Tachymeter_NO = str[6];
		ETC_Code = str[7];
		ETC_NO = str[8];
		this.read = false;
	}	
	public Section_of_Highway(){
		
	}
	@Override
	public String toString() {
		return this.NO+","+this.Next_NO+","+this.Length+","+this.O_longitude+","
				+this.O_latitude+","+this.Tachymeter_Code+","+this.Tachymeter_NO+","+this.ETC_Code+","+this.ETC_NO+"/r/n";
	}
	public String[] toStringArray(){
		String[] str = {this.NO,this.Next_NO,String.valueOf(this.Length),String.valueOf(this.O_longitude),String.valueOf(this.O_latitude
				),this.Tachymeter_Code,this.Tachymeter_NO,this.ETC_Code,this.ETC_NO};
		return str;
	}
	public String[] toStringArray_Lon_Lat(){
		String[] str = {String.valueOf(this.O_longitude),String.valueOf(this.O_latitude),NO,ETC_Code};
		return str;
	}
}
