package ru.sunoplyaandesin.simplemessenger.exception;

public class YoutubeNotFoundException extends RuntimeException{
    public YoutubeNotFoundException(String text) {
        super(text + " not found.");
    }
}
