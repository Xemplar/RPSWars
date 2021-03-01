package com.xemplarsoft.games.cross.rps.controller.ai;

public class Task {
    protected String command, name;
    
    public Task(String name, Object... args){
        this.name = name;
        StringBuilder sb = new StringBuilder();
        for(Object s : args){
            sb.append(":").append(s.toString());
        }
        this.command = sb.substring(1);
    }
    
    public String[] getArgs(){
        return command.split(":");
    }
    
    public String getName(){
        return name;
    }
}
