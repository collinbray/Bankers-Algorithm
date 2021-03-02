import java.util.Scanner;

public class Bankers {

    private int[][] alloc;
    private int[][] max;
    private int[] avail;
    private int[][] need;
    private int p; //process
    private int r; //resource

    public static void main(String[] args) {
        Bankers bank = new Bankers();
        bank.input();
        bank.safety();
    }

    void input() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter # of processes:");
        p = sc.nextInt();   //# of processes
        System.out.println("Enter # of resources:");
        r = sc.nextInt();   //# of resources

        //size the 2d arrays according to p and r
        alloc = new int[p][r];
        max = new int[p][r];
        avail = new int[r];
        need = new int[p][r];

        System.out.println("Enter Allocation Matrix:");
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                alloc[i][j] = sc.nextInt(); //alloc matrix
            }
        }

        System.out.println("Enter Max Matrix:");
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                max[i][j] = sc.nextInt(); //max matrix
            }
        }

        System.out.println("Enter Available Matrix:");
        for (int j = 0; j < r; j++) {
            avail[j] = sc.nextInt(); //avail matrix
        }
    }

    //calculates the resource needs of each process
    private int[][] calcNeed() {
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }
        return need;
    }

    //looks to see if safe sequence can be achieved
    void safety() {
        int check = 0;
        calcNeed();
        int[] safeSequence = new int[p];

        //boolean array to keep track of allocated processes
        boolean[] done = new boolean[p];
        for (int i = 0; i < p; i++) {
            done[i] = false;
        }

        //work = avail
        int[] work = new int[r];
        for (int i = 0; i < r; i++) {
            work[i] = avail[i];
        }

        while (check < p) {
            boolean flag = false;
            for (int i = 0; i < p; i++) {
                if (!done[i]) {
                    int j;
                    for (j = 0; j < r; j++) {
                        if (need[i][j] > work[j])
                            break;
                    }
                    if (j == r) {
                        safeSequence[check++] = i;
                        done[i] = true;
                        flag = true;

                        for (j = 0; j < r; j++) {
                            work[j] = work[j] + alloc[i][j];
                        }
                    }
                }
            }
            if (!flag) {
                break;
            }
        }
        //check to see if all process finish
        if (check < p) {
            System.out.println("Request Denied");
        } else {
            System.out.println("Safe Sequence:");
            System.out.print("<");
            for (int i = 0; i < p; i++) {
                System.out.print("P" + safeSequence[i]);
                if (i != p - 1) {
                    System.out.print(",");
                }
            }
            System.out.print(">");
        }
    }

}