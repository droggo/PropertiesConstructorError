package error

import spock.lang.Specification
import grails.test.mixin.Mock
import grails.persistence.Entity

@Mock([DomainOne, DomainTwo, DomainThree])
class PropertiesConstructorSpec extends Specification {
	void 'ensure saved association is copied'(){
		given: 
			DomainTwo domainTwo = new DomainTwo(name: "saved domain").save(failOnError: true)
			DomainOne domainOne = new DomainOne(domainTwo: domainTwo, quantity: 10).save(failOnError: true)

		when: 'new domain is created from domain properties'
			DomainOne copiedDomain = new DomainOne(domainOne.properties)

		then: 'both properties are set in new object'
			copiedDomain.quantity == 10
			copiedDomain.domainTwo
			copiedDomain.domainTwo.name == 'saved domain'

		when: 'domain is saved'
			copiedDomain.save(failOnError: true)

		then:
			DomainOne.count() == 2
	}

	void 'ensure not saved association is copied'(){
		given:
			DomainTwo domainTwo = new DomainTwo(name: "saved domain")
			DomainOne domainOne = new DomainOne(domainTwo: domainTwo, quantity: 10).save(failOnError: true)

		when: 'new domain is created from domain properties'
			DomainOne copiedDomain = new DomainOne(domainOne.properties)

		then: 'both properties are set in new object'
			copiedDomain.quantity == 10
			copiedDomain.domainTwo
			copiedDomain.domainTwo.name == 'saved domain'

		when: 'domain is saved'
			copiedDomain.save(failOnError: true)

		then:
			DomainOne.count() == 2
	}

	void 'ensure constructor with domain collections works fine'(){
		when:
			DomainOne domainOne = new DomainOne(domainsTwo: [new DomainTwo()])

		then:
			domainOne.domainsTwo
			domainOne.domainsTwo.size() == 1
	}

	void 'ensure saved association is copied to different domain'(){
		given: 
			DomainTwo domainTwo = new DomainTwo(name: "saved domain").save(failOnError: true)
			DomainOne domainOne = new DomainOne(domainTwo: domainTwo, quantity: 10).save(failOnError: true)

		when: 'new domain is created from domain properties'
			DomainThree copiedDomain = new DomainThree(domainOne.properties)

		then: 'both properties are set in new object'
			copiedDomain.quantity == 10
			copiedDomain.domainTwo
			copiedDomain.domainTwo.name == 'saved domain'

		when: 'domain is saved'
			copiedDomain.save(failOnError: true)

		then:
			DomainThree.count() == 1
	}
}

/*
@Entity
class DomainOne {
	DomainTwo domainTwo
	Integer quantity

	static hasMany = [domainsTwo: DomainTwo]
}

@Entity
class DomainTwo {
	String name
}

@Entity
class DomainThree {
	DomainTwo domainTwo
	Integer quantity

	static hasMany = [domainsTwo: DomainTwo]
}
*/