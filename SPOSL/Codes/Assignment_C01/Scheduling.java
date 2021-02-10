import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Scheduling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String choice="";
		boolean flag = true;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);
		System.out.println("1)FCFS\n2)SJF\n3)Priority\n4)Round Robin\n5)Exit");
		do {
			System.out.println("Enter Choice: ");
			try {
				choice = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(choice) {
			case "1": 	FCFS obj = new FCFS();
						obj.execute();
						break;
			case "2": 	SJF obj1 = new SJF();
						obj1.execute();
						break;
			case "3": 	Priority obj2 = new Priority();
						obj2.execute();
						break;
			case "4": 	RoundRobin r = new RoundRobin();
						r.execute();
						break;
			default:	flag = false;
						break;
			}

		}while(flag);
	}

}