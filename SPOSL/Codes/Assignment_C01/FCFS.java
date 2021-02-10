//0 2 4 6 8 
//3 6 4 5 2
import java.util.*;
public class FCFS
{
	static void ganntChart(Process process[],int processNo,int gannt[]){
		int sum = 0;
		gannt[0] = 0;
		//		System.out.print(sum + "->");
		for(int i=0;i<processNo;i++){
			sum+=process[i].burst;
			gannt[i+1] = sum;
		}
		for(int i=0;i<processNo;i++) {
			for(int j=0;j<process[i].burst;j++) {
				System.out.print(" " + process[i].id);
			}
		}
	}
	static double waitingTime(Process process[],int gannt[],int processNo){
		double time,totalWaitingTime = 0;
		for(int i=0;i<processNo;i++){
			time = gannt[i] - process[i].arrival;
			process[i].wt = time;
			totalWaitingTime+=time;
			//			System.out.println("PROCESS " + i + " W.T: " + time);
		}
		return totalWaitingTime/processNo;
	}
	static double turnaround(Process[] process,int gannt[],int processNo){
		double time,totalTurnAroundTime = 0;
		for(int i=0;i<processNo;i++){
			time = (gannt[i] - process[i].arrival)+process[i].burst;
			process[i].ta  = time;
			totalTurnAroundTime+=time;
			//			System.out.println("PROCESS " + i + " Turnaround Time: " + time);
		}
		return totalTurnAroundTime/processNo;
	}
	static void execute(){
		System.out.println("*********************** FIRST COME FIRST SERVE **********************\n");
		int processNo,id=1;
		int[] arrival,burst,gannt;
		System.out.print("Enter the number of processNo: ");
		Scanner sc = new Scanner(System.in);
		processNo =sc.nextInt();
		System.out.println(processNo);
		Process process[] = new Process[processNo];
		arrival = new int[processNo];
		burst = new int[processNo];
		gannt = new int[processNo+1];
		System.out.println("Enter the arrival times: ");
		for(int i=0;i<processNo;i++){
			process[i] = new Process(id++);
			process[i].arrival =sc.nextInt();
		}
		System.out.println("Enter the burst times: ");
		for(int i=0;i<processNo;i++){
			process[i].burst =sc.nextInt();
		}
		System.out.println("---------------GANNT CHART----------");
		ganntChart(process,processNo,gannt);
		System.out.println("\n---------------------------------");
		double wt = waitingTime(process,gannt,processNo);
		double ta = turnaround(process,gannt,processNo);



		System.out.println("\n------------------------------------------------------------------------------");
		System.out.println("Process\t\tArrival\t\tBurst\t\tW.T\t\tT.A");
		System.out.println("---------------------------------------------------------------------------------");
		for(int i=0;i<processNo;i++) {
			System.out.println(process[i].id +"\t\t" +process[i].arrival + "\t\t" +process[i].burst +"\t\t"+ process[i].wt + "\t\t"+process[i].ta);
		}
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println("----AVERAGE WAITING TIME: " + wt);
		System.out.println("---AVERAGE TURNAROUND TIME: " + ta);
		
		System.out.println("\n**************************** END OF FCFS ****************************");
	}
}