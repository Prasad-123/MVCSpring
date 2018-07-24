package com.cg.mypayment.controllers;

import java.awt.List;

import org.hibernate.metamodel.source.annotations.attribute.AssociationAttribute;
import org.omg.CORBA.TRANSACTION_MODE;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
@Controller
public class URIController {

	@RequestMapping(value="/")
	public String getIndexPage() {
		return "indexPage";
	}
	@RequestMapping(value="/login")
	public String getLoginPage() {
		return "loginPage";
	}
	@RequestMapping(value="/Operations")
	public String getOperationsPage() {
		return "Operations";
	}
	@RequestMapping(value="/register")
	public String getRegistrationPage() {
		return "RegistrationPage";
	}
	@ModelAttribute("customer")
	public Customer getCustomer() {
		return new Customer();
	}
	@RequestMapping(value="/deposit")
	public String getdepositPage() {
		return "depositPage";
	}
	
	@RequestMapping(value="/withdraw")
	public String getwithdrawPage() {
		return "withdrawpage";
	}
	@RequestMapping(value="/fundTransfer")
	public String getShowBalancePage() {
		return "fundTransferPage";
	}
	@ModelAttribute("transactions")
	public Transactions gettrans() {
		return new Transactions();
	}
}
