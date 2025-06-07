package org.filippostolov.gitviewer.model.exceptions;

public class ReposNotFoundException extends RuntimeException{
    public ReposNotFoundException(String message){
        super(message);
    }
}
