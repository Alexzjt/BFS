import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.math.*;
import com.csvreader.*;

public class Find extends AbsReadFile {

	
	@Override
	public boolean readfile(String filepath) throws FileNotFoundException,
			IOException {
		// TODO Auto-generated method stub
		return super.readfile(filepath);
	}
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	public String[] fenjie_str (String str){
   	 String list[] = new String[10];
   	 int qi=-1,zhong=-1,num=0;
        for(int i=0;i<str.length();i++){
        	if(str.charAt(i)=='#'){
        		qi=zhong;
        		zhong=i;
        		list[num++]=str.substring(qi+1,zhong);
        	}
        }
        list[num++]=str.substring(zhong+1,str.length());
        return list;
    }
	public String GetNowDate(){   
	    String temp_str="";   
	    Date dt = new Date();   
	    //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制   
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");   
	    temp_str=sdf.format(dt);   
	    return temp_str;   
	} 
	@Override
	public void dealWithFile(File file) {
		// TODO Auto-generated method stub
				FileReader fr;
				
					try {
						fr = new FileReader(file);
						BufferedReader reader = new BufferedReader(fr);
						String line;
						File fileout = new File(Config.out_file_path+"\\"+file.getName());
						//System.out.println("处理文件"+file.getName());
						FileWriter fw = new FileWriter(fileout);
						//int x=0,y=0;
						while((line = reader.readLine())!=null)
						{
							
							
						}
						fw.close();
						fr.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}
	/**
     * 读取CSV文件
     */
	 HashMap<String,Section_of_Highway> HM = new HashMap<String,Section_of_Highway>();
	 HashMap<String,ETC> HM_ETC = new HashMap<String,ETC>();
	 HashMap<String,Double> HM_Length = new HashMap<String,Double>();
	 HashMap<String,Double>  HM_write_length = new HashMap<String,Double>();
	 public void Identification_String_length(String str){
    	 try {
    	 //CsvWriter wr =new CsvWriter(out_path+Config.wenjianjia+Config.Destination+"_流水号_"+(Config.water++)+".csv",',',Charset.forName("SJIS"));
    	 int qi=-1,zhong=-1;
    	 double cost=0;
    	 for(int i=0;i<str.length();i++){
    		 if(str.charAt(i)=='\n'){
    			 if(str.charAt(i)=='\n'){
    					qi=zhong+1;
    					zhong=i;
    					cost+=HM.get(str.substring(qi,zhong-1)).Length;
    			 }
    		 }
    	 }
    	 	//cost+=HM.get(str.substring(zhong+1,str.length())).Length;
    	 	if(!HM_write_length.containsKey(Config.Origin+"-"+Config.Destination))
    	 		HM_write_length.put(Config.Origin+"-"+Config.Destination,cost);
    	 	else{
    	 		double temp=HM_write_length.get(Config.Origin+"-"+Config.Destination);
    	 		HM_write_length.put(Config.Origin+"-"+Config.Destination,temp<cost?temp:cost);
    	 	}
    	 } catch (Exception e) {
    	 e.printStackTrace();
    	 }
    	// System.out.println(str.substring(zhong+1,str.length()));
     }
	 public void write_length(){
    	 try {
    	 File fileout = new File(Config.out_file_path+"道路长度BFS.txt");
    	 Iterator it = HM_write_length.entrySet().iterator();
    	 FileWriter fw = new FileWriter(fileout);
    	   while (it.hasNext()) {
    		   Map.Entry entry = (Map.Entry) it.next();
    		   Object key = entry.getKey();
    		   Object value = entry.getValue();
    		   System.out.println(key+"\t"+value+"\r\n");
    		   fw.write(key+"\t"+value+"\r\n");
    	    }
    	   	fw.close();
    	 }
    	 catch(Exception ex){
    		 ex.printStackTrace();
    	 }
     }
     public void  readeCsv(String inPath){
         try {    
              
              ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
              //Queue<String> Q = new LinkedList<String>();
                
              CsvReader reader = new CsvReader(inPath,',',Charset.forName("SJIS"));    //一般用这编码读就可以了    
              //CsvWriter wr =new CsvWriter(outPath,',',Charset.forName("SJIS"));
             // reader.readHeaders(); // 跳过表头   如果需要表头的话，不要写这句。
              
              while(reader.readRecord()){ //逐行读入除表头的数据    
                  csvList.add(reader.getValues());
              }            
              reader.close();
              for(int row=0;row<csvList.size();row++){
            	  HM.put(csvList.get(row)[0],new Section_of_Highway(csvList.get(row)));
            	  //HM_Reverse.put(csvList.get(row)[1],new Section_of_Highway(csvList.get(row)));
              }
              
              //wr.close();
              
         }catch(Exception ex){
        	 //System.out.println("Exception:" + ex.getMessage());
        	 ex.printStackTrace();
         }
     }
     public double callonlat(double lon1,double lat1,double lon2,double lat2){
    	double ppp = Math.PI/180;
 		double C=Math.sin(lat1*ppp)*Math.sin(lat2*ppp)+Math.cos(lat1*ppp)*Math.cos(lat2*ppp)*Math.cos((lon1-lon2)*ppp);
 		double Distance = 6371.004*Math.acos(C);
 		return Distance;
     }
     public void BFS(String Origin , String Destination){
    	 Section_of_Highway Destination_SOH = HM.get(Destination);
    	 LinkedList<Status> Q = new LinkedList<Status>();
    	 HashMap<String,Double> CF = new HashMap<String,Double>();
    	 Q.offer(new Status(Origin));
    	 CF.put(Origin, 0.0);
    	 while(Q.size()>0){
    		 Status now_Status = Q.poll();
    		 //System.out.println(now_Status.NO);
    		 if(now_Status.value>Config.jianzhi)
    			 continue;
    		 if(now_Status.NO.equals(Destination)){
    			 Config.Length = now_Status.value;
    			 //System.out.println((int)(Config.Length+0.5)+"_流水号_"+(Config.water++)+".csv");
    			 Identification_String(now_Status.path,Config.Lon_Lat_path);
    		 }
    		 Section_of_Highway now_SOH = HM.get(now_Status.NO);
    		 if(now_SOH.Next_NO.equals("end"))
    		 	continue;
    		 else if(!now_SOH.Next_NO.contains("#")){
    			 Status next_Status = new Status(now_SOH.Next_NO);
    			 next_Status.value = now_Status.value+now_SOH.Length;
    			 next_Status.path=now_Status.path+"\r\n"+next_Status.NO;
    			 if((!CF.containsKey(next_Status.NO))||next_Status.value<CF.get(next_Status.NO)){
    				 CF.put(next_Status.NO,next_Status.value);
    				 Q.offer(next_Status);
    			 }
    		 }
    		 else{
    			 String[] List = fenjie_str(now_SOH.Next_NO);
    			 for(int i=0;i<List.length;i++){
    				 if(List[i]==null)
    					 break;
    				 Status next_Status = new Status(List[i]);
        			 next_Status.value = now_Status.value+now_SOH.Length;
        			 next_Status.path=now_Status.path+"\r\n"+next_Status.NO;
        			 if((!CF.containsKey(next_Status.NO))||next_Status.value<CF.get(next_Status.NO)){
        				 CF.put(next_Status.NO,next_Status.value);
        				 Q.offer(next_Status);
        			 }
    			 }
    		 }
    	 }
     }
     
     public void readeETC(String inPath){
         try {    
             
             ArrayList<String[]> csvList = new ArrayList<String[]>(); //用来保存数据
          
             CsvReader reader = new CsvReader(inPath,',',Charset.forName("SJIS"));
             while(reader.readRecord()){ //逐行读入除表头的数据    
                 csvList.add(reader.getValues());
             }            
             reader.close();
             for(int row=0;row<csvList.size();row++){
           	  HM_ETC.put(csvList.get(row)[0],new ETC(csvList.get(row)));
           	  //HM_Reverse.put(csvList.get(row)[1],new Section_of_Highway(csvList.get(row)));
             }
             
             //wr.close();
             
        }catch(Exception ex){
       	 //System.out.println("Exception:" + ex.getMessage());
       	 ex.printStackTrace();
        }
    }
     public void BFS_ETC(String ETC1 , String ETC2){
    	 Config.wenjianjia=ETC1+"\\"+ETC2+"\\";
    	 Config.Origin=ETC1;
    	 Config.Destination=ETC2;
    	 Config.biaozhun=HM_Length.get(ETC1+"-"+ETC2);
    	 Config.jianzhi=Config.biaozhun*Config.beishu;
    	 ETC etc1 = HM_ETC.get(ETC1);
    	 ETC etc2 = HM_ETC.get(ETC2);
    	 BFS(etc1.Road1,etc2.Road1);
    	 BFS(etc1.Road1,etc2.Road2);
    	 BFS(etc1.Road2,etc2.Road1);
    	 BFS(etc1.Road2,etc2.Road2);
     }
     
     /**
      * 写入CSV文件
      */
     
     public void writeCsv(){
         try {
             
             String csvFilePath = "c:/test.csv";
              CsvWriter wr =new CsvWriter(csvFilePath,',',Charset.forName("SJIS"));
              String[] contents = {"aaaaa","bbbbb","cccccc","ddddddddd"};                    
              wr.writeRecord(contents);
              wr.close();
          } catch (IOException e) {
             e.printStackTrace();
          }
     }
     public void test(){
    	 System.out.println(HM.size());
    	 System.out.println(HM.get("G00G4201351964").toString());
     }
     public void Identification_String(String str,String out_path){
    	 try {
    	 CsvWriter wr =new CsvWriter(out_path+Config.wenjianjia+(int)(Config.Length+0.5)+"_流水号_"+(Config.water++)+".csv",',',Charset.forName("SJIS"));
    	 int qi=-1,zhong=-1;
    	 for(int i=0;i<str.length();i++){
    		 if(str.charAt(i)=='\n'){
    			 if(str.charAt(i)=='\n'){
    					qi=zhong+1;
    					zhong=i;
    					//System.out.println(str.substring(qi,zhong-1));
    					
							wr.writeRecord(HM.get(str.substring(qi,zhong-1)).toStringArray_Lon_Lat());
    			 }
    		 }
    		 
    	 }
    	 wr.writeRecord(HM.get(str.substring(zhong+1,str.length())).toStringArray_Lon_Lat());
    	 wr.close();
    	 } catch (IOException e) {
    	 e.printStackTrace();
    	 }
    	// System.out.println(str.substring(zhong+1,str.length()));
     }
     public void bianli(){
    	 try {
             ArrayList<String[]> csvList = new ArrayList<String[]>();
             CsvReader reader = new CsvReader(Config.ETC_file_path,',',Charset.forName("SJIS"));
             while(reader.readRecord()){ //逐行读入除表头的数据    
                 csvList.add(reader.getValues());
             }            
             reader.close();
             for(int row=0;row<csvList.size();row++){
            	 for(int row1=0;row1<csvList.size();row1++){
            		 BFS_ETC(csvList.get(row)[0],csvList.get(row1)[0]);
                 }
            	 System.out.println(csvList.get(row)[0]);
             }
             reader.close();
        }catch(Exception ex){
       	 //System.out.println(Origin ,Destination);
       	 ex.printStackTrace();
        }
     }
     public void newFile(){
    	 try {
             ArrayList<String[]> csvList = new ArrayList<String[]>();
             CsvReader reader = new CsvReader(Config.ETC_file_path,',',Charset.forName("SJIS"));
             while(reader.readRecord()){ //逐行读入除表头的数据    
                 csvList.add(reader.getValues());
             }            
             reader.close();
             for(int row=0;row<csvList.size();row++){
            	 for(int row1=0;row1<csvList.size();row1++){
            		 File file = new File("D:\\实验结果BFS\\"+csvList.get(row)[0]+"\\"+csvList.get(row1)[0]);
                	 file.mkdirs();
                 }
             }
             reader.close();
         }catch(Exception ex){
       	  	//System.out.println("Exception:" + ex.getMessage());
        	ex.printStackTrace();
         }
     }
     public void readLength(String inPath){
    	 try{
    	 ArrayList<String[]> csvList = new ArrayList<String[]>();
    	 CsvReader reader = new CsvReader(inPath,',',Charset.forName("SJIS"));
         while(reader.readRecord()){ //逐行读入除表头的数据    
             csvList.add(reader.getValues());
         }            
         reader.close();
         for(int row=0;row<csvList.size();row++){
   		 HM_Length.put(csvList.get(row)[0]+"-"+csvList.get(row)[1], Double.valueOf(csvList.get(row)[2]));
         }
    	 }
    	 catch(Exception ex){
    		 ex.printStackTrace();
    	 }
     }
}
