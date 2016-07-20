package ua.rd.foodorder.service.impl;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.domain.VendorCredentials;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordGenerator;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordHash;
import ua.rd.foodorder.repository.VendorCredentialsRepository;
import ua.rd.foodorder.repository.VendorRepository;
import ua.rd.foodorder.service.VendorService;

@Service
@Transactional
public class SimpleVendorService implements VendorService {

	private VendorRepository vendorRepository;

	private VendorCredentialsRepository vendorCredentialsRepository;

	private SimpleMailMessage templateMessage;

	private MailSender mailSender;

	private static final Logger LOG = LoggerFactory.getLogger(SimpleVendorService.class);

	@Autowired
	public void setVendorRepository(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	@Autowired
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	@Autowired
	public void setVendorCredentialsRepository(VendorCredentialsRepository vendorCredentialsRepository) {
		this.vendorCredentialsRepository = vendorCredentialsRepository;
	}

	@Autowired
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public Vendor update(Vendor vendor) {

		Vendor vendorInBD = vendorRepository.findOne(vendor.getId());

		if (vendorInBD == null) {
			throw new EntityNotFoundException(vendor.getId());
		}
		
		return vendorRepository.save(vendor);
	}

	@Override
	public Iterable<Vendor> findAll() {
		return vendorRepository.findAll();
	}
	
	@Override
	public Iterable<Long> vendorsWithCredentials(){
		Iterable<VendorCredentials> vendorsWithCredentials = vendorCredentialsRepository.findAll();
		return StreamSupport.stream(vendorsWithCredentials.spliterator(), false).map(vendorCredentials -> vendorCredentials.getId()).collect(Collectors.toList());
	}

	@Override
	public Vendor findById(Long id) {
		Vendor vendorInBD = vendorRepository.findOne(id);

		if (vendorInBD == null) {
			throw new EntityNotFoundException(id);
		}

		return vendorInBD;
	}

	@Override
	public void remove(Long id) {
		Vendor vendorInDB = vendorRepository.findOne(id);

		if (vendorInDB == null) {
			throw new EntityNotFoundException(id);
		}

		vendorInDB.setActive(false);

		vendorRepository.save(vendorInDB);

	}

	@Override
	public Vendor save(Vendor vendor) {
		return vendorRepository.save(vendor);
	}

	@Override
	public boolean generatePasswordAndSendByMail(Long id) {

		char[] password = PasswordGenerator.generatePswd();
		if (sendPasswordByMail(id, password)) {
			saveVendorCredentials(id, PasswordHash.hash(password));
			return true;
		} else {
			return false;
		}
	}


	@Override
	public char[] generatePasswordAndSaveInDatabase(Long id) {

		char[] password = PasswordGenerator.generatePswd();

		saveVendorCredentials(id, PasswordHash.hash(password));

		return password;
	}

	@Override
	public boolean sendPasswordByMail(Long id, char[] password) {
		SimpleMailMessage msg = generateEmailMessage(id, password);
		return sendMail(msg);
	}

	private boolean sendMail(SimpleMailMessage msg) {
		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			LOG.warn(ex.getMessage());
			return false;
		}

		return true;
	}

	private SimpleMailMessage generateEmailMessage(Long id, char[] password) {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		Vendor vendor = findById(id);
		msg.setTo(vendor.getEmail());
		msg.setText(String.format(msg.getText(), vendor.getEmail(), new String(password)));
		return msg;
	}

	private void saveVendorCredentials(Long id, String password) {

		Vendor vendor = findById(id);
		
		VendorCredentials credentials = vendorCredentialsRepository.findOne(id);
		if (credentials != null) {
			credentials.setPassword(password);
			vendorCredentialsRepository.save(credentials);
		} else {
			vendorCredentialsRepository.save(new VendorCredentials(vendor, password));
		}

	}

}
