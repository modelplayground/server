package com.modelplayground.server.algorithms.persistentranking.domain;

public class SampleResponse {
    String text;

    public SampleResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
