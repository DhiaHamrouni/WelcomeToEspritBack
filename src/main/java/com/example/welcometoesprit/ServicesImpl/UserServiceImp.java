package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.UserServiceInterface;
import com.example.welcometoesprit.entities.*;
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
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private MailingServiceImp mailingServiceImp;

    @Autowired
    private FileStorageService fileStorageService;


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
        if(ExcelUploadService.isValidExcelFile(file)){
            try {
                List<User> users = ExcelUploadService.getUsersDataFromExcel(file.getInputStream());
                this.usersRepository.saveAll(users);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }
        }
    }

    public List<User> getUsers(){
        return usersRepository.findAll();
    }

    @Override
    public void addFileAndAssignToStudent(MultipartFile file, Integer idUser) throws IOException {
        User user = usersRepository.findById(idUser).get();
        fileStorageService.store(file);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity FileDB = new FileEntity(fileName, file.getContentType(), file.getBytes());
        user.getFiles().add(FileDB);
        usersRepository.save(user);
    }

    @Override
    public NiveauSuivant levelSuggestion(Integer idUser) {
        User user = usersRepository.findById(idUser).get();
        NiveauSuivant niveauSuivant ;

        if (user.getNiveauActuel().equals(NiveauActuel.valueOf("BAC"))){
            niveauSuivant=NiveauSuivant.A1;
            user.setNiveauSuivant(niveauSuivant);
        } else if (user.getNiveauActuel().equals(NiveauActuel.valueOf("PREPA1ER"))) {
            niveauSuivant=NiveauSuivant.P2;
            user.setNiveauSuivant(niveauSuivant);
        } else if (user.getNiveauActuel().equals(NiveauActuel.valueOf("PREPA2EME"))) {
            niveauSuivant=NiveauSuivant.B3;
            user.setNiveauSuivant(niveauSuivant);
        } else if (user.getNiveauActuel().equals(NiveauActuel.valueOf("LICENCE3EMEINFO"))) {
            niveauSuivant=NiveauSuivant.A3;
            user.setNiveauSuivant(niveauSuivant);
        } else if (user.getNiveauActuel().equals(NiveauActuel.valueOf("LICENCE3EMENONINFO"))) {
            niveauSuivant=NiveauSuivant.B3;
            user.setNiveauSuivant(niveauSuivant);
        } else if (user.getNiveauActuel().equals(NiveauActuel.valueOf("MASTERE1ER"))) {
            niveauSuivant=NiveauSuivant.A4;
            user.setNiveauSuivant(niveauSuivant);
        }else{
            niveauSuivant=NiveauSuivant.A1;
            log.info("last else in niveau suggestion function");
            user.setNiveauSuivant(niveauSuivant);
        }
        usersRepository.save(user);
        return niveauSuivant;
    }

    /*@Override
    public NiveauSuivant showNextLevel(Integer idUser) {
        User user = usersRepository.findById(idUser).get();
        return user.getNiveauSuivant();
    }
    */
    @Override
    public void refuseNextLevel(Integer idUser, User user) {

        User user1 = usersRepository.findById(idUser).get();
        mailingServiceImp.sendMailToAdministrationLevel(idUser,user.getNiveauSuivant());
        user1.setNiveauSuivant(user.getNiveauSuivant());
        usersRepository.save(user1);
    }

    @Override
    public NiveauActuel addNiveauActuel(Integer idUser, User user) {
        User user1 = usersRepository.findById(idUser).get();
        user1.setNiveauActuel(user.getNiveauActuel());
        usersRepository.save(user1);
        return user1.getNiveauActuel();

    }

    @Override
    public Integer assignStudentToEvaluator(Integer idStudent, LocalDateTime dateInterview) {
        return null;
    }

    @Override
    public Integer assignStudentToClassroom(Integer idStudent, Integer idEvaluator) {
        return null;
    }

    @Override
    public String assignStudentToInterview(Integer idStudent, LocalDateTime dateInterview) {
        return null;
    }

    public List<User> getStudents() {
        return usersRepository.findByRole(Role.STUDENT);
    }

    public Map<String, Integer> getStudentsCountByLevel() {
        Map<String, Integer> levelCountMap = new HashMap<>();
        List<User> students = usersRepository.findByRole(Role.STUDENT);

        for (User student : students) {
            NiveauActuel niveauActuel =student.getNiveauActuel();
            String level = niveauActuel.name();
            if (levelCountMap.containsKey(level)) {
                levelCountMap.put(level, levelCountMap.get(level) + 1);
            } else {
                levelCountMap.put(level, 1);
            }
        }
        return levelCountMap;
    }

}