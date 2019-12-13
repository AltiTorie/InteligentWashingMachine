package LOGIC;

public class NotEnoughResourceException extends Exception{
    public NotEnoughResourceException(){
        super();
    }

    NotEnoughResourceException(String message){
        super(message);
    }

}
