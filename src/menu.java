import java.io.*;
import java.util.*;


public class menu {
	public static void main(String args[]){
		Find f=new Find();
		Scanner cin = new Scanner(System.in);
		//String path=Config.in_file_path;
		//System.out.println("���������յ���շ�վ��ţ�");
		//System.out.println("���������յ���շ�վ��ţ�");
		//while(cin.hasNext()){
			try {
				//f.readfile(path);
				f.readeCsv(Config.in_file_path);
				f.readeETC(Config.ETC_file_path);
				f.readLength(Config.Length_file_path);
				//int number = cin.nextInt();
				//if(number==1){
					//String etc1 = cin.next();
					//String etc2 = cin.next();
					//f.BFS_ETC(etc1,etc2);
					//f.A_star_ETC(etc1, etc2);
					 f.bianli();
					//f.newFile();
					//String Origin = cin.next();
					//String Destination = cin.next();
					//f.BFS(Origin, Destination);
					//f.Best_First_Search(Origin,Destination);
				//}
				 
					//System.out.println("****************");
					//System.out.println("���������յ���շ�վ��ţ�");
					//System.out.println("���������յ���շ�վ��ţ�");
				//f.test();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	//}
}
