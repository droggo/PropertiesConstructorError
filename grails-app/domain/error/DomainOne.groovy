package error

class DomainOne {
	DomainTwo domainTwo
	Integer quantity

	static hasMany = [domainsTwo: DomainTwo]
}