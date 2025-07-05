package ru.netology.model;

public enum StatusOperation {
    DONE_ERROR("ERROR"),
    START("START"),
    IN_PROGRESS("PROGRESS"),
    WITHDRAWING_FROM_ACCOUNT("WITHDRAWING_FROM_ACCOUNT"),
    TRANSFER_TO_ACCOUNT("TRANSFER_TO_ACCOUNT"),
    DONE_SUCCESSFUL("DONE");

    private final String description;
    StatusOperation(String description){
        this.description = description;
    }

    public String getValue(){
        return description;
    }
}
