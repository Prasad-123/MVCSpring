package com.cg.mypayment.controllers;


import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.service.WalletService;

@Controller
public class CustomerActionController {
	
	@Autowired
	WalletService walletService;
	
	public static String mobileNo;
	@RequestMapping(value="/registerCustomer")
	public ModelAndView registerCustomer(@Valid @ModelAttribute("customer") Customer customer,BindingResult result) {
		if(result.hasErrors())
			return new ModelAndView("RegistrationPage");
		try {
			Customer customer1=walletService.createAccount(customer);
			return new ModelAndView("registrationSuccessPage","customer",customer);	
		} catch (SQLDataException e) {
			return new ModelAndView("loginPage","errorMessage",e.getMessage());
		}
		
	}
	@RequestMapping(value="/showMenu",method=RequestMethod.POST)
	public ModelAndView showMenu(@ModelAttribute("customer")Customer customer) { 
		
		try {
			mobileNo=customer.getMobileNo();
			Customer customer1=walletService.showBalance(mobileNo);
			return new ModelAndView("Operations","customer",customer1);
		} catch (InvalidInputException e) {
			return new ModelAndView("loginPage","errorMessage",e.getMessage());
		}
		
		
	}
	@RequestMapping(value="/showBalance")
	public ModelAndView showBalance() { 
		Customer customer=walletService.showBalance(mobileNo);
		return new ModelAndView("showBalance","customer",customer);
	}
	@RequestMapping(value="/depositAction",method=RequestMethod.POST)
	public ModelAndView depositBalance(@ModelAttribute("customer")Customer customer,@RequestParam("wallet.balance")BigDecimal amount) { 
		
		try {
			Customer customer1;
			customer1 = walletService.depositAmount(mobileNo,amount);
			return new ModelAndView("showBalance","customer",customer1);
		} catch (InvalidInputException e) {
			
			return new ModelAndView("depositPage","errorMessage",e.getMessage());
		}
		
	}
	@RequestMapping(value="/withdrawAction",method=RequestMethod.POST)
	public ModelAndView withdrawBalance(@ModelAttribute("customer")Customer customer,@RequestParam("wallet.balance")BigDecimal amount) { 
		
		try {
			Customer customer1 = walletService.withdrawAmount(mobileNo, amount);
			return new ModelAndView("showBalance","customer",customer1);
		} catch (Exception e) {
			return new ModelAndView("withdrawpage","errorMessage",e.getMessage());
		}
	
	}
	@RequestMapping(value="/fundTransferAction",method=RequestMethod.POST)
	public ModelAndView fundTransfer(@ModelAttribute("customer")Customer customer,@RequestParam("mobileNo")String mobileNum,@RequestParam("wallet.balance")BigDecimal amount) { 
		Customer customer1;
		try {
			customer1 = walletService.fundTransfer(mobileNo, mobileNum, amount);
			return new ModelAndView("showBalance","customer",customer1);
		} catch (InvalidInputException | InsufficientBalanceException e) {
			return new ModelAndView("fundTransferPage","errorMessage",e.getMessage());
		}
		
	}
	
	
	
	@RequestMapping(value="/transactionPage")
	public ModelAndView listOfTransactions()
	{
		List<Transactions> customer1 = walletService.transactionDetails(mobileNo);
		return new ModelAndView("transactionSuccessPage","transactions",customer1);
		 
		/*try {
			
		} catch (NullPointerException e) {
			return new ModelAndView("Operations","errorMessage",e.getMessage());
		} catch (Exception e) {
			return new ModelAndView("Operations","errorMessage",e.getMessage());
		}*/
		
		
	}
}
