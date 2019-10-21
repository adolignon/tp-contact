package fr.lp.ic.contact;
import java.util.List;
import java.util.Optional;

import fr.lp.ic.contact.dao.ContactDaoImpl;
import fr.lp.ic.contact.dao.IContactDao;
import fr.lp.ic.contact.exception.ContactException;
import fr.lp.ic.contact.exception.ContactNotFoundException;
import fr.lp.ic.contact.model.Contact;

/**
 * Service - Méthodes a développer ici
 * 
 * @author athorel
 *
 */
public class ContactService {
	
	private static final int MIN_NAME_CHARS = 3;
	private static final int MAX_NAME_CHARS = 40;


	// Ne pas bouger
	private IContactDao contactDao = new ContactDaoImpl();

	/**
	 * Développer ici la méthode qui retourne une liste de contact, trié par le nom
	 * 
	 * @return list des contact triés
	 */
	public List<String> listAll() {
		return null;
	}

	/**
	 * Développer ici la méthode permettant d'afficher le nombre de contact
	 * 
	 * @return nombre de contact
	 */
	public int count() {
		return listAll().size();
	}

	/**
	 * Développer ici la méthode permettant d'ajouter un nouveau contact
	 * 
	 * @param name        le nom doit être compris entre 3 et 40 caractéres
	 * @param phoneNumber le numéro de téléphone doit commencer par 02 et contenir
	 *                    10 chiffres
	 * @param email       l'email doit contenir au mois 5 caractéres et avoir un @
	 *                    et un .
	 * @throws ContactException Le nom doit être unique, si il est déjà existant on
	 *                          lève une ContactException
	 */
	public void newContact(String name, String phoneNumber, String email) throws ContactException {
		if(name == null || name.length() < MIN_NAME_CHARS || name.length() > MAX_NAME_CHARS) {
			throw new IllegalArgumentException("Name should contain between 3 and 40 chars and can't be null");
		}
		Optional<Contact> contactFound = contactDao.findByName(name);
		if(contactFound.isPresent())
			throw new ContactException();
		Contact contact = new Contact();
		contact.setName(name);
		contact.setPhone(phoneNumber);
		contact.setEmail(email);
		contactDao.save(contact);


	}

	/**
	 * Développer ici la méthode permettant de mettre à jour un contact
	 * 
	 * @param name        le nom doit être compris entre 3 et 40 caractères
	 * @param phoneNumber le numéro de téléphone doit commencer par 02 et contenir
	 *                    10 chiffres
	 * @param email       l'email doit contenir au mois 5 caractéres et avoir un @
	 *                    et un .
	 * @throws ContactException         Le nom doit être unique, si il est déjà
	 *                                  existant on lève une ContactException
	 *                                  
	 * @throws ContactNotFoundException Si l'utilisateur n'existe pas on lève une
	 *                                  ContactNotFoundException
	 */
	public void updateContact(String name, String newName, String phoneNumber, String email)
			throws ContactException, ContactNotFoundException {
		Optional<Contact> contactFound = contactDao.findByName(name);
		if(!contactFound.isPresent())
			throw new ContactNotFoundException();
		
		if(!newName.equalsIgnoreCase(name) && contactDao.findByName(newName).isPresent()){
				throw new ContactException();
		}
		
		contactFound.get().setName(newName);
		contactFound.get().setPhone(phoneNumber);
		contactFound.get().setEmail(email);
		contactDao.update(name, contactFound.get());

	}

	/**
	 * Développer ici la méthode permettant de supprimer un contact
	 * 
	 * @param name        le nom de l'utilisateur a supprimer                                 
	 * @throws ContactNotFoundException Si l'utilisateur n'existe pas on lève une
	 *                                  ContactNotFoundException
	 */
	public void deleteContact(String name) throws ContactNotFoundException {
		Optional<Contact> contactFound = contactDao.findByName(name);
		if(!contactFound.isPresent())
			throw new ContactNotFoundException();
		contactDao.delete(name);

	}

}
