package com.example.hp.neveralone.Model;

public class Chat {

    private String sender;
    private String reciever;
    private  String message;
    private boolean isseen;

    public Chat(String sender,String reciever,String message,boolean isseen){
        this.sender=sender;
        this.reciever=reciever;
        this.message=message;
        this.isseen=isseen;
    }


    public  Chat() {

    }

    public  void setSender(String sender)
    {
        this.sender=sender;
    }

    public String getSender() {
        return sender;
    }

    public void setReciever(String reciever){
        this.reciever = reciever;
    }

    public String getReciever() {
        return reciever;
    }

    public void setMessage(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public boolean isIsseen() {
        return isseen;
    }
}

