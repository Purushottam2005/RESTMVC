package com.github.restmvc.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2013-04-06T07:59:06.361-0400")
@StaticMetamodel(Customer.class)
public class Customer_ {
	public static volatile SingularAttribute<Customer, Integer> customerId;
	public static volatile SingularAttribute<Customer, Character> discountCode;
	public static volatile SingularAttribute<Customer, String> zip;
	public static volatile SingularAttribute<Customer, String> name;
	public static volatile SingularAttribute<Customer, String> addressline1;
	public static volatile SingularAttribute<Customer, String> addressline2;
	public static volatile SingularAttribute<Customer, String> city;
	public static volatile SingularAttribute<Customer, String> state;
	public static volatile SingularAttribute<Customer, String> phone;
	public static volatile SingularAttribute<Customer, String> fax;
	public static volatile SingularAttribute<Customer, String> email;
	public static volatile SingularAttribute<Customer, Integer> creditLimit;
}
