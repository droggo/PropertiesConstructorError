import error.*

class BootStrap {

    def init = { servletContext ->
    	DomainTwo domainTwo = new DomainTwo(name: "saved domain").save(failOnError: true)
		DomainOne domainOne = new DomainOne(domainTwo: domainTwo, quantity: 10).save(failOnError: true)
		Map props = domainOne?.properties
		// Map props = domainOne?.properties?.findAll {!it.key.endsWith('Id')} ?: [:]
		DomainOne copiedDomain = new DomainOne(props)
		copiedDomain.save(failOnError: true)
    }
    def destroy = {
    }
}
