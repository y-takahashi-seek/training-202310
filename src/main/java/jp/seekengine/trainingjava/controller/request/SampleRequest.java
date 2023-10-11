package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;

public record SampleRequest(
        String sampleField1,
        String sampleField2
) implements Serializable {}
