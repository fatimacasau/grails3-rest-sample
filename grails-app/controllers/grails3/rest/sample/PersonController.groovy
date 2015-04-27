package grails3.rest.sample

import grails.transaction.Transactional

@Transactional(readOnly = true)
class PersonController {

    static responseFormats = ['json', 'xml']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Person.list(params), model:[personCount: Person.count()]
    }

    def show(Person person) {
        if(person == null) {
            render status:404
        }
        else {
            respond person
        }
    }

}
