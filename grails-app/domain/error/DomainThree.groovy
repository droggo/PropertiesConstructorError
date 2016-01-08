package error

class DomainThree {
	DomainTwo domainTwo
	Integer quantity

	static hasMany = [domainsTwo: DomainTwo]
}