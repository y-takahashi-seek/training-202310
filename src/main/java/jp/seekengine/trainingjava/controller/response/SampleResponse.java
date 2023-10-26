package jp.seekengine.trainingjava.controller.response;

import java.io.Serializable;

public record SampleResponse(
        String sampleField
) implements Serializable {}
