package ua.rd.foodorder.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.service.impl.SimpleVendorService;


@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/repositoryContext.xml",
"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleVendorServiceTest {

	@Autowired
	private VendorService vendorService;
	
	@Test
	public void testHashIsGenerated()
	{

	}
}
