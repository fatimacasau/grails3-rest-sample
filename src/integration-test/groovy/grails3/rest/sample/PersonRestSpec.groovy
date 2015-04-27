package grails3.rest.sample

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.transaction.Transactional
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import spock.lang.Specification

/**
 * incompatibility between our TransactionTransform and what Spock generates for expect/where type test methods.
 * The workaround is to use @org.springframework.transaction.annotation.Transactional instead of @Rollback.
 * related to: https://jira.grails.org/browse/GRAILS-12114 / https://github.com/grails/grails-core/issues/616
 */

@Integration
@org.springframework.transaction.annotation.Transactional
class PersonRestSpec extends Specification {

    @Value("\${local.server.port}")
    int port;

    @Value("\${server.context-path}")
    String contextPath

    def "create new person via REST with different params: #params"(){
        setup: "people uri"
            RESTClient rest = new RESTClient("http://localhost:8080")
            def uri = "$contextPath/people"
        expect: "status ok"
            result == rest.post(requestContentType : groovyx.net.http.ContentType.JSON, path : uri, body : params).status
        where: "different params"
            result                      | params
            HttpStatus.CREATED.value()  | [firstName:"fatima",lastName:"casau"]
            HttpStatus.OK.value()       | [firstName:"fatima",lastName:"casau",age:29] // this fails
    }

    def "find a person via REST"(){
        given: "an existing person"
            RESTClient rest = new RESTClient("http://localhost:8080")
            def person = Person.findAll()[0]
        and: "people uri"
            def uri = "$contextPath/people/${person.id}"
        when:
            def result = rest.get(path: uri)
        then:
            result.status == HttpStatus.OK.value()
    }

}
