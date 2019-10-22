package fr.lp.ic.contact.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.lp.ic.contact.model.Contact;
/**
 * Implementation de contact 
 * NE PAS MODIFIER 
 * @author athorel
 */
public class ContactDaoImpl implements IContactDao {

	List<Contact> contacts = new ArrayList<>();

	public List<Contact> findAll() {
		return contacts.stream()//
				.collect(Collectors.toList());
	}

	public Optional<Contact> findByName(String name) {
		return findWithName(name);
	}

	private Optional<Contact> findWithName(String name) {
		return contacts.stream()//
				.filter(x -> x.getName().equalsIgnoreCase(name))//
				.findFirst();
	}
	
	@Override
	public boolean update(String name, Contact contact) {
		Optional<Contact> foundContact = findWithName(name);
		foundContact.ifPresent(x->{
			x.setPhone(contact.getPhone());
			x.setEmail(contact.getEmail());
		});	
		return !foundContact.isPresent();
	}
	
	@Override
	public boolean save(Contact contact) {
		Optional<Contact> foundContact = findWithName(contact.getName());
		if(!foundContact.isPresent()) {
		 return	contacts.add(contact);
		}
		return false;
	}

	@Override
	public boolean delete(String name) {
		Optional<Contact> foundContact = findWithName(name);
		if(!foundContact.isPresent()) {
		 return	contacts.remove(foundContact.get());
		}
		return false;
	}
	
}
