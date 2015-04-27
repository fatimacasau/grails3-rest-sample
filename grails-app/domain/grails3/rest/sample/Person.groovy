package grails3.rest.sample

import grails.rest.Resource

@Resource(uri='/people', formats=['json', 'xml'])
class Person {

    String firstName
    String lastName
    Integer age

    static constraints = {
        lastName maxSize: 20
        firstName maxSize: 50
        age nullable: true
    }
}
