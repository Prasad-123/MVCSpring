
package com.cg.mypaymentapp.service;
import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cg.mypaymentapp.beans.Customer;
import com.cg.mypaymentapp.beans.Transactions;
import com.cg.mypaymentapp.beans.Wallet;
import com.cg.mypaymentapp.exception.InsufficientBalanceException;
import com.cg.mypaymentapp.exception.InvalidInputException;
import com.cg.mypaymentapp.repo.TransactionRepo;
import com.cg.mypaymentapp.repo.WalletRepo;

@Component(value="walletService")
public class WalletServiceImpl implements WalletService{
	@Autowired
	private WalletRepo repo;
	@Autowired
	private TransactionRepo trans;
	static Logger myLogger = Logger.getLogger(WalletServiceImpl.class);
	Map<String,Customer> data= new HashMap<String, Customer>();
	List<Transactions> transactions;
	
	public WalletServiceImpl(WalletRepo repo) 
	{
		super();
		myLogger.info("constructor called");
		this.repo = repo;
	}

	public WalletServiceImpl(Map<String, Customer> data) {
		super();
		this.data = data;
	}

	public WalletServiceImpl() 
	{
		
	}

	public Customer createAccount(Customer customer) throws SQLDataException
	{
		myLogger.info("create account");
		Customer cus=repo.save(customer);
		if(cus!=null)
			return cus;
		else
			throw new SQLDataException("account with this mobile number exists already");
	}

	public Customer showBalance(String mobileNo) 
	{
		if(!isValidMobile(mobileNo))
		{
			throw new InvalidInputException("Invalid Mobile number");
		}
		else
		{
		
		Customer customer=repo.findOne(mobileNo);
		
		myLogger.info("show balance");
		if(customer!=null)
			
			return customer;
			
		else
			throw new InvalidInputException("account with mobile number not found ");
		}
	}

	

	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) 
	{
		if( !isValidMobile(sourceMobileNo) || !isValidMobile(targetMobileNo) || !isValidAmount(amount))
		{
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer sourceCustomer=repo.findOne(sourceMobileNo);
		Customer destCustomer=repo.findOne(targetMobileNo);
		
		if(sourceCustomer!=null && destCustomer!=null)
		{	Wallet balance1 = sourceCustomer.getWallet();
			Wallet balance2 = destCustomer.getWallet();
			if(balance1.getBalance().compareTo(amount) > 0)
			{
				BigDecimal remainBalance = balance1.getBalance().subtract(amount);
				BigDecimal addedBalance = balance2.getBalance().add(amount);
				balance1.setBalance(remainBalance);
				balance2.setBalance(addedBalance);
				Transactions sTransaction=new Transactions();
				Transactions dTransaction=new Transactions();
				sTransaction.setBalance(remainBalance);
				sTransaction.setMobileNo(sourceMobileNo);
				java.sql.Date date = getCurrentJavaSqlDate();
				sTransaction.setTransactionDate(date);
				sTransaction.setTransactionType("fundTransfer");
				repo.save(sourceCustomer);
				trans.save(sTransaction);
				dTransaction.setBalance(addedBalance);
				dTransaction.setMobileNo(targetMobileNo);
				dTransaction.setTransactionDate(date);
				dTransaction.setTransactionType("fundTransfer");
				repo.save(destCustomer);
				trans.save(dTransaction);
				//repo.update(balance2,dTransaction);
				return sourceCustomer;
			}
			else
			{
				throw new InsufficientBalanceException("insufficient balance");
				
			}
		}
		else
		{
			throw new InvalidInputException("account with mobile number not found ");
		}
		
	}

	public Customer depositAmount(String mobileNo, BigDecimal amount) 
	{
		if( !isValidMobile(mobileNo) || !isValidAmount(amount))
		{
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer cus = repo.findOne(mobileNo);
		if(cus!= null)
		{
			myLogger.info("deposit money");
			Wallet balance = cus.getWallet();
			balance.setBalance(balance.getBalance().add(amount));
			Transactions transaction=new Transactions();
			transaction.setBalance(balance.getBalance());
			transaction.setMobileNo(mobileNo);
			java.sql.Date date = getCurrentJavaSqlDate();
			transaction.setTransactionDate(date);
			transaction.setTransactionType("deposit");
			repo.save(cus);
			trans.save(transaction);
			return cus;
		}
		else 
		{
			throw new InvalidInputException("account with mobile number not found ");
		}
	}

	public Customer withdrawAmount(String mobileNo, BigDecimal amount) 
	{
		if( !isValidMobile(mobileNo) || !isValidAmount(amount))
		{
			throw new InvalidInputException("Sorry , your details are incorrect");
		}
		Customer cus = repo.findOne(mobileNo);
		if(cus!= null)
		{
			Wallet balance = cus.getWallet();
			if(balance.getBalance().compareTo(amount)>=0)
			{
				BigDecimal addedBalance = balance.getBalance().subtract(amount);
				balance.setBalance(addedBalance);
				Transactions transaction=new Transactions();
				transaction.setBalance(balance.getBalance());
				transaction.setMobileNo(mobileNo);
				java.sql.Date date = getCurrentJavaSqlDate();
				transaction.setTransactionDate(date);
				transaction.setTransactionType("withdrawed");
				repo.save(cus);
				trans.save(transaction);
				myLogger.info("withdraw money");
				return cus;
			}
			else
			{
				throw new InsufficientBalanceException("Insufficient balance ");
			}
		}
		else 
		{
			throw new InvalidInputException("account with mobile number not found ");
		}
	}
	
	
	public List<Transactions> transactionDetails(String mobileNo) 
	{
		if(!isValidMobile(mobileNo))
		{
			throw new InvalidInputException("Invalid Mobile number");
		}
		else
		{
		
		Customer customer = repo.findOne(mobileNo);
		transactions = trans.findByMobileNo(mobileNo);
		myLogger.info("show balance");
		if(transactions!=null)
			return transactions;
		else
			throw new NullPointerException("no transactions available");
		}
	}

	 public static java.sql.Date getCurrentJavaSqlDate() 
	 {
		    java.util.Date today = new java.util.Date();
		    return new java.sql.Date(today.getTime());
	 }
	private boolean isValidMobile(String mobileNo) 
	{
		if(String.valueOf(mobileNo).matches("[1-9][0-9]{9}")) 
		{
			return true;
		}		
		else if(String.valueOf(mobileNo)==null)
		{
			return false;
		}
		else
		{
			return false;
		}
	}
	
	private boolean isValidAmount(BigDecimal amount)
	{
		BigDecimal val = new BigDecimal("0");
		if(amount.compareTo(val)>=0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private boolean isValidName(String name) 
	{
		if(name.isEmpty())
		{
			return false; 
		}
		else if(String.valueOf(name).matches("[A-Za-z][A-Za-z]+")) 
		{
			return true;
		}		
		else
		{
			return false;
		}
			
	}
	
}

