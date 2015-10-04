package com.company;
public class Fork {
    private boolean isUsing;
    public boolean getState(){
       return isUsing;
    }
    public void  setState(boolean state){
        isUsing = state;
    }
}
