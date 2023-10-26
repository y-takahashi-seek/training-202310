package jp.seekengine.trainingjava.controller.request;

import java.io.Serializable;

public record MessageRequest(String message) implements Serializable {
}
