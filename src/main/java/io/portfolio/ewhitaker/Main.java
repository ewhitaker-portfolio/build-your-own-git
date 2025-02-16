package io.portfolio.ewhitaker;

import java.util.Map;

public interface Main {
    static void main(String[] args) {
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            System.out.printf("key %s value %s\n", entry.getKey(), entry.getValue());
        }
    }
}
