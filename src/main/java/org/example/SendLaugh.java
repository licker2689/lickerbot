package org.example;

public class SendLaugh extends Thread {
    public static boolean b = true;
    public static boolean getCoolLaugh(){
        return b;
    }

    public static void setCoolLaugh() {
        try {
            b = false;
            Thread.sleep(60000);  // 1초 대기한다.
            b = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}