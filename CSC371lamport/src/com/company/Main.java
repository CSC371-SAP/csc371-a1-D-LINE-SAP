package com.company;
//
//import java.net.InetAddress;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.util.Arrays;
//import java.util.List;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        if (args.length == 0) {
//            System.out.println("Usage: java Main (number of processses) [filename of commands]");
//            return;
//        }
//
//        // TODO: add support for reading commands from a file
//
//        String input;
//
//        try {
//            int n = Integer.parseInt(args[0]);
//            LamportClock[] clocks = new LamportClock[n];
//            System.setProperty("java.net.preferIPv4Stack" , "true");
//            InetAddress group = InetAddress.getByName("224.255.255.255");
//            for (int i = 0; i < n; ++i) {
//                int port = 8888;
//                LamportClock lc = new LamportClock(group, port, i);
//                lc.start();
//                clocks[i] = lc;
//            }
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//            while (true) {
//                input = in.readLine();
//                if (input.equals("exit"))
//                    return;
//
//                /**
//                 * A message format is of the following:
//                 * EVENT_NAME ID_OF_SENDER (ID_OF_RECEIVER)
//                 *
//                 * EVENT_NAME is of the following:
//                 *  - SEND
//                 *  - LOCAL
//                 *
//                 * For example:
//                 * SEND 1 2 (process 1 sends a message to process 2)
//                 * LOCAL 3 (process 3 performs a local event)
//                 */
//                // perform a string split operation based on space
//                String[] splits = input.split(" ");
//                if (splits.length == 0) {
//                    continue;
//                }
//
//                switch(splits[0].toUpperCase()) {
//
//                    case "SEND":
//                        int clockArrayId = Integer.parseInt(splits[1]);
//                        long firstProcessId = clocks[clockArrayId].getId();
//                        long secondProcessId = clocks[Integer.parseInt(splits[2])].getId();
//                        String messageContent = "";
//                        if (splits.length >= 3) {
//                            List<String> wordsList = Arrays.asList(
//                                    Arrays.copyOfRange(splits, 3, splits.length));
//                            messageContent = String.join(" ", wordsList);
//                        }
//
//                        Event e = new Event(1, firstProcessId, secondProcessId, messageContent);
//                        clocks[clockArrayId].updateTime(e);
//                        break;
//
//                    case "LOCAL":
//                        clockArrayId = Integer.parseInt(splits[1]);
//                        firstProcessId = clocks[clockArrayId].getId();
//                        secondProcessId = 0;
//                        messageContent = "";
//
//                        e = new Event(0, firstProcessId, secondProcessId, messageContent);
//                        clocks[clockArrayId].updateTime(e);
//                        break;
//
//                    case "REQUEST":
//                        clockArrayId = Integer.parseInt(splits[1]);
//                        firstProcessId = clocks[clockArrayId].getId();
//                        e = new Event(3, firstProcessId, -1, "");
//                        clocks[clockArrayId].updateTime(e);
//                        break;
//
//                    default:
//                        throw new RuntimeException("Invalid event name");
//
//                }
//            }
//        } catch(Exception e) {
//            System.err.println(e);
//            return;
//        }
//    }
//
//}
//

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final int N = 10;
    private volatile static int number[] = new int[N];
    private volatile static boolean pnumber[] = new boolean[N];

    public static Thread createThread(final int i){
        Thread th1 = new Thread(new Runnable(){
//            @override
            public void run(){
                pnumber[i] =true;
                number[i] = 1 + maximum(number);
                pnumber[i] = false;

                for(int j=0;j<N;j++){
                    while (pnumber[j]);

                    while ((number[j] != 0)&&((number[j]<number[i])||((number[j]==number[i])&&(j<i))));

                }
                try{
                    System.out.println("Enter person "+i);
                    System.out.println("person "+i+" Hello! ");
                    System.out.println("person "+i+" what's up! ");
                    Thread.sleep(4000);
                } catch (Exception e) {
                    //TODO: handle exception
                }
                number[i]=0;
//                System.out.println("Leaving person "+i);
                System.out.println("person "+i+" me leaving! ");
            }
        }, "Thread"+i);
        return th1;
    }

    public static int maximum(int[] number) {
        int maxi=0;
        for(int i=0;i<number.length;i++){
            maxi += number[i];
        }
        return maxi;
    }

    public static void main(String[] args) {
        List<Thread> lista = new ArrayList<Thread>();
        for(int i=0;i<N;i++){
            Thread th = createThread(i);
            lista.add(th);
        }
        for(int i=0;i<N;i++){
            Thread th = lista.get(i);
            th.start();
        }
    }
}