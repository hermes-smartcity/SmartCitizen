package es.us.lsi.smartcitizen.exception;

public interface ErrorBundle {
    Exception getException();
    String getErrorMessage();
}