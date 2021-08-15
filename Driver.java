import java.util.Scanner;
public class Driver{
    public static void main(String args[]){
    	long startTime = System.nanoTime();

        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        int num=0;
        while(numTestCases-->0){
            int size;
            size = sc.nextInt();
            A1DynamicMem obj = new A1DynamicMem(size,2);
            int numCommands = sc.nextInt();
            //testing purpose
            //int origNum = numCommands;
            //
            while(numCommands-->0) {
                String command;
                command = sc.next();
              /*  if (command.equals("Defragment")){
                    obj.Defragment();
                  //obj.printBlk();
                    continue;
                }*/
                int argument;
                argument = sc.nextInt();
                int result = -5;
                boolean toPrint = true;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        toPrint = false;
                        obj.Defragment();
                        break;
                    default:
                        break;
                }
            //    if(num>2477&&num<2500){
            //         System.out.println(result);
            //         System.out.println("command= "+command+" argument= "+argument);
            //         obj.printBlk();
                    
            //     }
            //     if(num==2501)break;
                num++;
                if(toPrint)
                    System.out.println(result);
                // //for testing
/*
                System.out.println("command= "+command+" argument= "+argument);
                obj.printBlk();
                System.out.println("End here");
  */          
            }
            
        }
    long stopTime = System.nanoTime();
	System.out.println((stopTime - startTime)/1000000000.0);
    }


}
