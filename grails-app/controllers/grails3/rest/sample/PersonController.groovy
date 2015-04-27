package grails3.rest.sample

import grails.rest.RestfulController

class PersonController extends RestfulController{

    static responseFormats = ['json', 'xml']

    PersonController(){
        super(Person)
    }
}
