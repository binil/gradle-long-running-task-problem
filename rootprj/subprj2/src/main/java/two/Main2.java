package two;

import java.util.Date;

public class Main2
{
    public static void main(String[] args) throws InterruptedException
    {
        while (true)
        {
            long now = System.currentTimeMillis();
            System.out.println(new Date(now) + ": time now is " + now);

            Thread.sleep(2000);
        }
    }
}
