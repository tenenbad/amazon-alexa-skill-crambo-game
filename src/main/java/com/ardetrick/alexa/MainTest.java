package com.ardetrick.alexa;

import com.ardetrick.alexa.service.DefinitionService;
import com.ardetrick.alexa.service.RhymeService;

import javax.inject.Inject;

/**
 * Created by Drew Tenenbaum on 11/21/2016.
 */
public class MainTest {


    public static void main(String[] args){
        RhymeService rhymeService = new  RhymeService();
        DefinitionService ds = new DefinitionService();
        //System.out.println(rhymeService.getWordsThatRhymeWith("boy"));
        //System.out.println(ds.getDictionaryResponse("young").getResults().get(0).getSenses().get(0).getDefinition());
        //System.out.println(ds.getDefinition("young"));
        //System.out.println(ds.getDefinition("hat"));
        System.out.println(ds.getDefinition("operate"));
    }

}
