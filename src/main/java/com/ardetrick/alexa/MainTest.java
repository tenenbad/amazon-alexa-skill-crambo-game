package com.ardetrick.alexa;

import com.ardetrick.alexa.service.RhymeService;

import javax.inject.Inject;

/**
 * Created by Drew Tenenbaum on 11/21/2016.
 */
public class MainTest {


    public static void main(String[] args){
        RhymeService rhymeService = new  RhymeService();
        System.out.println(rhymeService.getWordsThatRhymeWith("boy"));
    }

}
