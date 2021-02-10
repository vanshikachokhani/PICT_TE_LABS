import java.util.Scanner;
import java.util.Vector;

public class RoundRobin {
	static int rem_bt[];
	static void schedule(Vector<Process>queue,int quantum_time,int processes,int[] rem_bt) {
		int time = 0;
		boolean flag = true;
		for(int i=0;i<processes;i++) {
			rem_bt[i] = queue.get(i).burst;
		}
		while(true) {
			flag = true;
			for(int i=0;i<processes;i++) {
				if(rem_bt[i]>0) {
					flag = false;
					if(rem_bt[i]<=quantum_time) {
						time+=rem_bt[i];
						queue.get(i).wt = time - queue.get(i).burst;
						for(int j=0;j<rem_bt[i];j++)
							System.out.print(" " + queue.get(i).id);
						rem_bt[i] = 0;
					}
					else {
						time+=quantum_time;
						for(int j=0;j<quantum_time;j++)
							System.out.print(" " + queue.get(i).id);
						rem_bt[i]-=quantum_time;
					}
				}
			}
			if(flag)
				break;
		}

	}

	static void execute() {
		// TODO Auto-generated method stub
		System.out.println("******************************** ROUND ROBIN SCHEDULING **********************************");
		Scanner sc = new Scanner(System.in);
		int processes, quantum_time,id = 1;
		System.out.println("Enter number of processes: ");
		processes =	sc.nextInt();
		System.out.println("Enter Quantum Time: ");
		quantum_time = sc.nextInt();
		Process process[] = new Process[processes];
		rem_bt = new int[processes];
		Vector<Process> queue = new Vector<Process>();
		System.out.println("Enter Burst Time: ");
		for (int i = 0; i < processes; ++i) {
			process[i] = new Process(id++);
			process[i].burst = sc.nextInt(); 
			queue.add(process[i]);
		}
		System.out.println("\n-------------GANNT CHART---------------");
		schedule(queue,quantum_time,processes,rem_bt);
		double WT = 0, TAT = 0;
		System.out.println();
		System.out.println("\n----------------------------------------");
		System.out.println("Process\t\tBurst\t\tW.T\t\tT.A");
		System.out.println("----------------------------------------");
		for(int i=0;i<processes;i++) {
			queue.get(i).ta = queue.get(i).wt + queue.get(i).burst;
			TAT+=queue.get(i).ta;
		}
		for(int i=0;i<processes;i++) {
			System.out.println(queue.get(i).id +"\t\t" +queue.get(i).burst +"\t\t"+ queue.get(i).wt + "\t\t"+queue.get(i).ta);
			WT+=queue.get(i).wt;
		}
		System.out.println("----------------------------------------");
		double avgWT = WT/processes;
		System.out.println("----AVERAGE WAITING TIME: " + avgWT);

		double avgTAT = TAT/processes;
		System.out.println("---AVERAGE TURNAROUND TIME: " + avgTAT);
		
		System.out.println("\n ************************** END OF ROUND ROBIN ********************************************");
	}

}