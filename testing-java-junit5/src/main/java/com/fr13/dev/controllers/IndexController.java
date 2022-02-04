package com.fr13.dev.controllers;

public class IndexController {

    public String index(){

        return "index";
    }

    public String oopsHandler(){
        throw new ValueNotFoundException();
    }
}
