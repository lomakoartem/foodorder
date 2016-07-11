package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.VendorRepository;
import ua.rd.foodorder.service.VendorService;

@Service
@Transactional
public class SimpleVendorService implements VendorService {

	private VendorRepository vendorRepository;

	private SimpleMailMessage templateMessage;
    
	private MailSender mailSender;
	
	@Autowired
	public void setVendorRepository(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}
	
	@Autowired
	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
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
        
        vendorInBD.setName(vendor.getName());
        vendorInBD.setAdditionalInfo(vendor.getAdditionalInfo());
        vendorInBD.setEmail(vendor.getEmail());
        vendorInBD.setActive(vendor.isActive());
        vendorInBD.setLocations(vendor.getLocations());

        return vendorRepository.save(vendorInBD);
    }

	@Override
	public Iterable<Vendor> findAll() {
		return vendorRepository.findAll();
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

		if(vendorInDB == null){
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
	public void generatePasswordAndSendByMail(Vendor vendor) {
		
		 SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	        msg.setTo("abelsharp@gmail.com");
	        msg.setText(String.format(msg.getText(), "5435"));
	        System.out.println(msg.getText());
	        
	        try{
	           // this.mailSender.send(msg);
	        }
	        catch (MailException ex) {
	            // simply log it and go on...
	            System.err.println(ex.getMessage());
	        }
	}

	@Override
	public char[] generatePasswordAndSaveInDatabase(Vendor vendor) {
		return null;
	}

	@Override
	public void sendPasswordByMail(Vendor vendor, char[] password) {
		
	}
	
	
	
}
