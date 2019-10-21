package fr.lp.ic.contact;

import java.util.ConcurrentModificationException;
import java.util.Optional;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fr.lp.ic.contact.dao.IContactDao;
import fr.lp.ic.contact.exception.ContactException;
import fr.lp.ic.contact.exception.ContactNotFoundException;
import fr.lp.ic.contact.model.Contact;

public class ContactServiceMockTest  extends BaseMockTest{
	public static final String VALID_PHONE_NUMBER = "0277777777";
	public static final String VALID_EMAIL = "test@yopmail.com";
	@Rule
	public EasyMockRule rule = new EasyMockRule(this);
	
	@TestSubject
	ContactService contactService = new ContactService();
	
	@Mock
	IContactDao contactDao;
	
	@Test(expected = ContactException.class)
	public void shouldFailIfNameAlreadyExists() throws ContactException{
		String name = "Axel";
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		EasyMock.replay(contactDao);
		
		contactService.newContact(name, VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test
	public void shouldInsertElementIfNameAlreadyExists() throws ContactException{
		String name = "Axel";
		expect(contactDao.findByName(name)).andReturn(Optional.empty());
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.save(capture(capturedContact))).andReturn(true);
		EasyMock.replay(contactDao);
		
		contactService.newContact(name, VALID_PHONE_NUMBER, VALID_EMAIL);
		Contact value = capturedContact.getValue();
		Assert.assertEquals(name, value.getName());
		Assert.assertEquals("Phone error",VALID_PHONE_NUMBER ,value.getPhone());
		Assert.assertEquals("Email error", VALID_EMAIL,value.getEmail());
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void shouldFailDeletionIfNameDoesntExist() throws ContactNotFoundException {
		String name = "Axel";
		expect(contactDao.findByName(name)).andReturn(Optional.empty());
		EasyMock.replay(contactDao);
		
		contactService.deleteContact(name);
	}
	
	@Test(expected = ContactNotFoundException.class)
	public void shouldFailUpdateIfNameDoesntExist() throws ContactNotFoundException, ContactException {
		String name = "Axel";
		expect(contactDao.findByName(name)).andReturn(Optional.empty());
		EasyMock.replay(contactDao);
		
		contactService.updateContact(name, "Axel", VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	
	@Test
	public void VerifyUpdateSameName() throws ContactNotFoundException, ContactException {
		String name = "Axel";
		String newName = "Axel";
		
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.update(EasyMock.eq(name), capture(capturedContact))).andReturn(true);
		EasyMock.replay(contactDao);
		
		contactService.updateContact(name, newName, VALID_PHONE_NUMBER, VALID_EMAIL);
		Contact value = capturedContact.getValue();
		Assert.assertEquals(newName, value.getName());
		Assert.assertEquals("Phone error",VALID_PHONE_NUMBER ,value.getPhone());
		Assert.assertEquals("Email error", VALID_EMAIL,value.getEmail());
	}
	
	@Test
	public void VerifyUpdateChangeName() throws ContactNotFoundException, ContactException {
		String name = "Axel";
		String newName = "Test";
		
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.update(EasyMock.eq(name), capture(capturedContact))).andReturn(true);
		expect(contactDao.findByName(newName)).andReturn(Optional.empty());
		EasyMock.replay(contactDao);
		
		contactService.updateContact(name, newName, VALID_PHONE_NUMBER, VALID_EMAIL);
		Contact value = capturedContact.getValue();
		Assert.assertEquals(newName, value.getName());
		Assert.assertEquals("Phone error",VALID_PHONE_NUMBER ,value.getPhone());
		Assert.assertEquals("Email error", VALID_EMAIL,value.getEmail());
	}
	
	@Test(expected = ContactException.class)
	public void VerifyUpdateChangeNameButNewNameExists() throws ContactNotFoundException, ContactException {
		String name = "Axel";
		String newName = "Test";
		
		expect(contactDao.findByName(name)).andReturn(Optional.of(new Contact()));
		Capture<Contact> capturedContact =  newCapture();
		expect(contactDao.update(EasyMock.eq(name), capture(capturedContact))).andReturn(true);
		expect(contactDao.findByName(newName)).andReturn(Optional.of(new Contact()));
		EasyMock.replay(contactDao);
		
		contactService.updateContact(name, newName, VALID_PHONE_NUMBER, VALID_EMAIL);
	}
	


	
}
