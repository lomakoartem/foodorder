package ua.rd.foodorder.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordGeneratorAndHashing;
import ua.rd.foodorder.infrastructure.parsers.EmployeeExcelFileParser;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.infrastructure.passwordGenerateAndHash.PasswordHash;
import ua.rd.foodorder.repository.VendorRepository;
import ua.rd.foodorder.service.VendorService;

@Service
@Transactional
public class SimpleVendorService implements VendorService {

    private VendorRepository vendorRepository;

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
        vendorInBD.setPassword(vendor.getPassword());
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
    public boolean generatePasswordAndSendByMail(Vendor vendor) {
        char[] password = PasswordGeneratorAndHashing.generatePswd();
        if (sendPasswordByMail(vendor, password)) {
            vendor.setPassword(PasswordHash.hash(password));
            update(vendor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public char[] generatePasswordAndSaveInDatabase(Vendor vendor) {

        char[] password = PasswordGeneratorAndHashing.generatePswd();
        vendor.setPassword(PasswordHash.hash(password));
        update(vendor);

        return password;
    }

    @Override
    public boolean sendPasswordByMail(Vendor vendor, char[] password) {
        SimpleMailMessage msg = generateEmailMessage(vendor, password);
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

    private SimpleMailMessage generateEmailMessage(Vendor vendor, char[] password) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(vendor.getEmail());
        msg.setText(String.format(msg.getText(), new String(password)));
        return msg;
    }

}
