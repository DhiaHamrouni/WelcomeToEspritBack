package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.UserServiceInterface;
import com.example.welcometoesprit.entities.ConfirmationToken;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.MailingRepository;
import com.example.welcometoesprit.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImp extends BaseServiceImp<User,Integer>  implements UserDetailsService,UserServiceInterface {
    private final static  String USER_NOT_FOUND_MSG ="user with email %s not found";

    private JavaMailSender javaMailSender;
    @Autowired
    private MailingRepository mailingRepository;

    private    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired

    private  ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserRepository usersRepository ;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(User appUser) {
        boolean userExists = usersRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        usersRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }

    public int enableAppUser(String email) {
        return usersRepository.enableAppUser(email);
    }

    public void saveUsersToDatabase(MultipartFile file){
        List<String> emails=new ArrayList<>();
        List<String> emails2=new ArrayList<>();
        //intilize l1
        for (User element:usersRepository.findAll()){
            emails2.add(element.getEmail());
        }
        System.out.println(emails2.size());
        if(ExcelUploadService.isValidExcelFile(file)){
            try {
                List<User> users = ExcelUploadService.getUsersDataFromExcel(file.getInputStream());
                //initilize l2
                for (User element:users){
                    emails.add(element.getEmail());
                }
                for (String element:emails){
                    if (emails2.contains(element)){
                        emails.remove(element);
                    }
                }
                for (User element:users){
                    if (emails.contains(element.getEmail())){
                        usersRepository.save(element);
                    }
                }
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public List<User> getUsers(){
        return usersRepository.findAll();
    }

}