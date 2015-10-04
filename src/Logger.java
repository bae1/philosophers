package com.company;

public class Logger {
    public Logger(boolean flag){
        this.flag = flag;
    }
    private boolean flag;
    public void log(String logMessage) {
        if (flag) {
            System.out.println(logMessage);
        }
    }

}
