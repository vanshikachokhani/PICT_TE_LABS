import java.util.*;

public class Priority {
	static int gannt[];
	static void gannt(Process[] process,int processes) {
		gannt[0] = 0;
		int w =0; 
		for(int i=0;i<processes;i++) {
			w+=process[i].burst;
			gannt[i+1] = w;
		}
	}
	static void calculate(Process process[],int processes) {
		double wt = 0,w = 0;
		for(int i=1;i<processes;i++) {
			wt +=process[i-1].burst;
			process[i].wt = wt;
		}
		for(int i=0;i<processes;i++) {
			process[i].ta = process[i].wt + process[i].burst;
		}
	}
	static void sort(Process[] process,int processes) {
		for (int i = 0; i < processes-1; i++) 
			for (int j = 0; j < processes-i-1; j++) 
				if (process[j].priority > process[j+1].priority) 
				{ 
					Process temp = process[j];
					process[j] = process[j+1];
					process[j+1] = temp;
				} 
	}

	static void execute(){
		// TODO Auto-generated method stub
		System.out.println("****************************** PRIORITY SCHEDULING **************************************");
		int processes,id = 1;
		System.out.print("Enter Number of Processes: ");
		Scanner sc = new Scanner(System.in);
		processes = sc.nextInt();
		Process process[] = new Process[processes];
		gannt = new int[processes+1];
		System.out.println("Enter Burst Times: ");
		for(int i=0;i<processes;i++){
			process[i] = new Process(id++);
			process[i].burst = sc.nextInt();
		}
		System.out.println("Enter Priorities: ");
		for(int i=0;i<processes;i++){
			process[i].priority = sc.nextInt();
		}
		sort(process,processes);
		gannt(process,processes);
		System.out.println("Id\tBurst\tPriority");
		for(int i=0;i<processes;i++) {
			System.out.println(process[i].id + "\t" + process[i].burst + "\t" + process[i].priority);
		}
		System.out.println("----GANNT CHART----");
		for(int i=0;i<processes;i++) {
			for(int j=0;j<process[i].burst;j++) {
				System.out.print(" " + process[i].id);
			}
		}
		System.out.println("\n-------------------");
		calculate(process,processes);
		System.out.println("\n----------------------------------------");
		System.out.println("Process\t\tBurst\t\tW.T\t\tT.A");
		double TAT=0,WT=0;
		System.out.println("----------------------------------------");
		for(int i=0;i<processes;i++) {
			TAT+=process[i].ta;
		}
		for(int i=0;i<processes;i++) {
			System.out.println(process[i].id +"\t\t" +process[i].burst +"\t\t"+ process[i].wt + "\t\t"+process[i].ta);
			WT+=process[i].wt;
		}
		System.out.println("----------------------------------------");
		double avgWT = WT/processes;
		System.out.println("----AVERAGE WAITING TIME: " + avgWT);

		double avgTAT = TAT/processes;
		System.out.println("---AVERAGE TURNAROUND TIME: " + avgTAT);
		
		System.out.println("\n *******************************************************************");
	}

}