package ch.hevs.bankservice;

import java.util.List;

import ch.hevs.businessobject.Account;
import ch.hevs.businessobject.Client;
import jakarta.ejb.Local;

@Local
public interface Bank {

	Account getAccount(String accountDescription, String lastnameOwner);
	
	public List<Account> getAccountListFromClientLastname(String lastname);

	void transfer(Account compteSrc, Account compteDest, int montant) throws Exception;

	List<Client> getClients();

	Client getClient(long clientid);
}
