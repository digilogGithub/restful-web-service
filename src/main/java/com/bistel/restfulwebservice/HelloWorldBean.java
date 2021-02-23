package com.bistel.restfulwebservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// check enable annotation processing in intellij
@Data
@AllArgsConstructor
@NoArgsConstructor // make default constructor - blank
public class HelloWorldBean {
    private String message;

//    When using lombok collusion constructor
/*    public HelloWorldBean(String message) {
        this.message = message;
    }*/
}
