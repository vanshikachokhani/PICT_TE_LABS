import java.util.*;

public class SJF{
	static int waiting[];
	static double turnAroundTime(Process process[],int processes,int waiting[]){
		double ta = 0;
		double totalTA = 0;
		for(int i=0;i<processes;i++){
			ta = process[i].wt + process[i].burst;
			process[i].ta = ta;
			//System.out.println("Process " + i + " Turn Around Time: " + ta);
			totalTA+=ta;
		}
		return totalTA/processes;

	}
	static double waitingTime(Process process[],int processes){
		int time=0,done=0,shortest = 0,p_wait=0;
		double wait = 0;
		boolean flag = false;
		int min = Integer.MAX_VALUE;
		int temp[] = new int[processes];
		for(int i=0;i<processes;i++){
			temp[i] = process[i].burst;
		}
		while(done<processes){
			p_wait = 0;
			for(int i=0;i<processes;i++){
				if((process[i].arrival<=time)&&(temp[i]<min)&&temp[i]>0){
					min = temp[i];
					shortest = i;
					flag = true;
				}
			}
			if(flag==false){
				time++;
				if(done!=processes)
					System.out.print(" " + shortest);
			}
			else{
				temp[shortest]--;
				min = temp[shortest];
				if(min==0)
					min = Integer.MAX_VALUE;
				if(temp[shortest]==0){
					done++;
					flag = false;
					p_wait = (time+1)-process[shortest].burst-process[shortest].arrival;
					if(p_wait<0)
						p_wait = 0;
					process[shortest].wt = p_wait;
					wait+=p_wait;
				}
				time++;
				System.out.print(" " + process[shortest].id);
			}

		}
		System.out.print(" " + process[shortest].id + "\n");
		return wait/processes;
	}
	static void execute(){
		System.out.println("************************ SHORTEST JOB FIRST or SRTF *************************\n");
		int processes,id = 1;
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter Number of Process: ");
		processes = sc.nextInt();
		Process process[] = new Process[processes];
		//		waiting = new int[processes];
		System.out.println("Enter Arrival Times: ");
		for(int i=0;i<processes;i++){
			process[i] = new Process(id++);
			process[i].arrival = sc.nextInt();
		}
		System.out.println("Enter Burst Times: ");
		for(int i=0;i<processes;i++){
			process[i].burst = sc.nextInt();
		}
		System.out.println("\n-----GANNT CHART-----\n");
		double wt = waitingTime(process,processes);
		System.out.println("--------------------------------------------------------");
		double ta = turnAroundTime(process,processes,waiting);
		
		System.out.println("\n----------------------------------------------------------------------");
		System.out.println("Process\t\tArrival\t\tBurst\t\tW.T\t\tT.A");
		System.out.println("------------------------------------------------------------------------");
		for(int i=0;i<processes;i++) {
			System.out.println(process[i].id +"\t\t" + process[i].arrival+ "\t\t" +process[i].burst +"\t\t"+ process[i].wt + "\t\t"+process[i].ta);
		}
		System.out.println("------------------------------------------------------------------------");
		System.out.println("----AVERAGE WAITING TIME: " + wt);
		System.out.println("---AVERAGE TURNAROUND TIME: " + ta);
		
		System.out.println("\n*********************** END OF SJF ***************************");

	}
}