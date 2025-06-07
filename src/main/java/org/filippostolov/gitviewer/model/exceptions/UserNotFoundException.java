package org.filippostolov.gitviewer.model.exceptions;



public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
