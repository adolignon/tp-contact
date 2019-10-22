package fr.lp.ic.contact;

import org.junit.Assert;
import org.junit.Test;

import fr.lp.ic.contact.exception.ContactException;


public class ContactServiceTest {

	public static final String VALID_PHONE_NUMBER = "0277777777";
	public static final String VALID_EMAIL = "test@yopmail.com";
	private ContactService service = new ContactService();
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldFailIfNameLessThanThree() throws ContactException {
		//Empty test 
		service.newContact("ab", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldFailIfNameMoreThanFourty() throws ContactException {
		//Empty test 
		service.newContact("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldFailIfNameIsNull() throws ContactException {
		//Empty test 
		service.newContact(null, VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test(expected = ContactException.class)
	public void shouldFailIfNameAlreadyExist() throws ContactException {
		//Empty test 
		service.newContact("abc", VALID_PHONE_NUMBER, VALID_EMAIL);
		service.newContact("abc", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test
	public void shouldWorkNameEqualsThree() throws ContactException {
		//Empty test 
		service.newContact("abc", VALID_PHONE_NUMBER, VALID_EMAIL);
		Assert.assertTrue(true);;
	}
	@Test
	public void shouldWorkNameEqualsFourty() throws ContactException {
		//Empty test 
		service.newContact("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", VALID_PHONE_NUMBER, VALID_EMAIL);
		Assert.assertTrue(true);;
	}
}
