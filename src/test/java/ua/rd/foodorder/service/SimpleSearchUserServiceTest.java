package ua.rd.foodorder.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.service.impl.SimpleVendorService;

@ContextConfiguration(locations = {"classpath:/applicationContext.xml", "classpath:/repositoryContext.xml",
"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleSearchUserServiceTest {

    @Autowired
    SearchUserService searchUserService;
    
    @Test
    public void test(){
        PageRequest pageRequest = new PageRequest(0, 20);
        Page<User> users = searchUserService.searchPageOfUsers("a ak", pageRequest);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(users.getContent());
    }

}
