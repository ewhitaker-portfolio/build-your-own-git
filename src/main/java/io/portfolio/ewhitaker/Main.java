package io.portfolio.ewhitaker;

import io.portfolio.ewhitaker.util.Optional;
import io.portfolio.ewhitaker.util.Optional.Some;
import io.portfolio.ewhitaker.util.Optional.None;

public interface Main {
    static void main(String[] args) {
        Optional<String> option = Optional.of("hello world");
        String message = switch (option) {
            case Some<String> s -> s.get();
            case None<String> ignore -> "goodbye moon";
        };
        System.out.println(message);
    }
}