//package ru.itmentor.spring.boot_security.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import ru.itmentor.spring.boot_security.demo.dao.UserDAO;
//import ru.itmentor.spring.boot_security.demo.dao.UserRoleDAO;
//import ru.itmentor.spring.boot_security.demo.model.User;
//import ru.itmentor.spring.boot_security.demo.model.UserRole;
//
//import javax.transaction.Transactional;
//
//@Component
//public class DbInitializer implements CommandLineRunner {
//
//    private final UserDAO userDAO;
//    private final UserRoleDAO userRoleDAO;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public DbInitializer(UserDAO userDAO, UserRoleDAO userRoleDAO, PasswordEncoder passwordEncoder) {
//        this.userDAO = userDAO;
//        this.userRoleDAO = userRoleDAO;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Transactional
//    @Override
//    public void run(String... args) {
//
//        UserRole userRole = new UserRole();
//        userRole.setRole("ROLE_USER");
//        userRoleDAO.save(userRole);
//
//        UserRole adminRole = new UserRole();
//        adminRole.setRole("ROLE_ADMIN");
//        userRoleDAO.save(adminRole);
//
//        User user = new User();
//        user.setName("testUser");
//        user.setSurname("testSurname");
//        user.setUsername("testUser");
//        user.setAge(30);
//        user.setEnabled(true);
//        user.setPassword(passwordEncoder.encode("testPassword"));
//
//        user.addRole(userRole);
//        userDAO.save(user);
//
//        User admin = new User();
//        admin.setName("testAdmin");
//        admin.setSurname("testAdminSurname");
//        admin.setUsername("testAdmin");
//        admin.setAge(35);
//        admin.setEnabled(true);
//        admin.setPassword(passwordEncoder.encode("testAdminPassword"));
//
//        admin.addRole(adminRole);
//        userDAO.save(admin);
//    }
//}
