import grails3.rest.sample.Person

class BootStrap {

    def init = { servletContext ->

        def p1 = Person.findOrSaveWhere(firstName: "Fátima", lastName: "Casaú", age: 29)
        println p1
    }
    def destroy = {
    }
}
