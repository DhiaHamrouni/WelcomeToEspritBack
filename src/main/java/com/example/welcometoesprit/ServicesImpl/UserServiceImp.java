package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.UserServiceInterface;
import com.example.welcometoesprit.dto.UserDTO;
import com.example.welcometoesprit.entities.ConfirmationToken;
import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.*;
import com.example.welcometoesprit.entities.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImp extends BaseServiceImp<User,Integer>  implements UserDetailsService,UserServiceInterface {
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private InterviewRepository interviewRepository;
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
    private EventRepository eventRepository;

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

    public void exportCertificate(HttpServletResponse response,Integer id_user) throws IOException {
        User user =usersRepository.getReferenceById(id_user);

        String link=user.toString();
        ByteArrayOutputStream out = QRCode.from(link).to(ImageType.PNG).stream();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        // Add the Logo's img
        Image logo = Image.getInstance(new File("C:\\Users\\GAMING\\Downloads\\logo.png").getAbsolutePath());
        logo.setAlignment(Element.ALIGN_LEFT);
        logo.getTransparency();
        logo.scaleToFit(100f, 100f);
        document.add(logo);
        // Add a title
        Paragraph title = new Paragraph("Certificate of Completion");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Add a subtitle
        Paragraph subtitle = new Paragraph("This is to certify that");
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingBefore(20f);
        document.add(subtitle);

        // Add the recipient's name

        Paragraph name = new Paragraph(user.getLastname()+" "+ user.getFirstname());
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingBefore(20f);
        document.add(name);

        // Add the course name and date
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Event name:"));
        PdfPCell cell2 = new PdfPCell(new Paragraph(user.getEvent().getEventName()));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Completion Date:"));
        PdfPCell cell4 = new PdfPCell(new Paragraph(String.valueOf(user.getEvent().getEndDate())));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.setSpacingBefore(20f);
        document.add(table);

        // Add a message
        Paragraph message = new Paragraph("We hereby certify that the above-named person has successfully completed this event and is awarded this certificate.");
        message.setAlignment(Element.ALIGN_JUSTIFIED);
        message.setSpacingBefore(20f);
        document.add(message);
        //Add a Qr code
        Image QRcode = Image.getInstance(out.toByteArray());
        QRcode.setAlignment(Element.ALIGN_CENTER);
        QRcode.scaleToFit(100f, 100f);
        document.add(QRcode);

        document.close();

    }
    public List<User> exportUserToExcel(HttpServletResponse response) throws IOException {
        List<User> users = usersRepository.findAll();
        UsersExcelExportUtils exportUtils = new UsersExcelExportUtils(users);
        exportUtils.exportDataToExcel(response);
        return users;
    }
    public void assignEventToUser(Integer id_user,Integer id_event){
        User user =usersRepository.getReferenceById(id_user);
        Event event =eventRepository.getReferenceById(id_event);
        user.setEvent(event);
        usersRepository.save(user);
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

    @Transactional
    public String assignInterviewToStudent(Integer idStudent, Date dateInterview,Integer heureInterview) {
        User student = usersRepository.findById(idStudent).get();
        List<User> teachers = usersRepository.findByRole(Role.TEACHER);
        String test="interview not validated";
        for ( User teacher : teachers){
            Set<Interview> interviews = teacher.getInterviewEvaluators();
            for (Interview interview : interviews){
                if((interview.getDateInterview()!=dateInterview)&&(!Objects.equals(interview.getHeureInterview(), heureInterview))){
                    Interview interview1 = new Interview(dateInterview,heureInterview,student,teacher);
                    interviewRepository.save(interview1);
                    student.setInterviewStudent(interview1);
                    teacher.getInterviewEvaluators().add(interview1);
                    //Classroom classroom = classroomRepository.findById(interview.getClassroom().getIdClassroom()).get();
                    //student.getInterviewStudent().setClassroom(classroom);
                    //interview1.setClassroom(classroom);
                    //sendInterviewDetails(idStudent);
                    usersRepository.save(student);
                    usersRepository.save(teacher);

                    test="interview validated";
                    break;
                }else{
                    continue;
                }
            }
        }
        return test;
    }

    public List<UserDTO> getUsersByFirstNameAndRoleAndNiveauSuivant(String firstName) {
        List<User> users = usersRepository.findByFirstNameAndRoleAndNiveauSuivant(firstName);
        return users.stream().map(u -> new UserDTO(u.getFirstname(), u.getLastname(), u.getNiveauSuivant())).collect(Collectors.toList());
    }

    public List<UserDTO> findStudentsByFirstName(UserDTO userDto) {
        List<User> studentList = usersRepository.findByFirstnameAndRole(userDto.getFirstName(), Role.STUDENT);
        log.info("la liste des etudiant du repository est " + studentList);
        List<UserDTO> studentDtoList = new ArrayList<>();
        log.info("la list des etudiants vide est " + studentDtoList);
        for (User student : studentList) {
            UserDTO studentDto = new UserDTO();
            studentDto.setFirstName(student.getFirstname());
            studentDto.setLastName(student.getLastname());
            studentDto.setNiveauSuivant(student.getNiveauSuivant());
            studentDtoList.add(studentDto);
            log.info("la list des etudiants avec un seul estudiant est " + studentDtoList);
        }
        log.info("la list des etudiants finale est " + studentDtoList);
        return studentDtoList;
    }





}