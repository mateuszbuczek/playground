import java.util.ArrayList;
import java.util.Date;

class Main {

    public static void main(String[]args){
        Date start = new Date();

        ArrayList<String> strings = new ArrayList<>();

        for (Integer i = 1; i < 10_000_000; i++) {
            String s = i.toString().intern();
            strings.add(s);
        }

        Date end = new Date();

        System.out.println("exec time: " + (end.getTime() - start.getTime()) + " ms");
    }
}
