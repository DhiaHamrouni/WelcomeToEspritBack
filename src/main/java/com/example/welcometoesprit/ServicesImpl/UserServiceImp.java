package com.example.welcometoesprit.ServicesImpl;

import com.example.welcometoesprit.ServiceInterface.UserServiceInterface;
import com.example.welcometoesprit.dto.TeacherDto;
import com.example.welcometoesprit.dto.UserDTO;
import com.example.welcometoesprit.entities.ConfirmationToken;
import com.example.welcometoesprit.entities.Event;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.repository.*;
import com.example.welcometoesprit.entities.*;

import com.example.welcometoesprit.repository.MailingRepository;
import com.example.welcometoesprit.repository.UserRepository;

import com.example.welcometoesprit.token.TokenRepository;
import com.itextpdf.text.BaseColor;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;
import java.util.List;


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
    private TokenRepository   tokenRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MailingServiceImp mailingServiceImp;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private InterviewServiceImp interviewServiceImp;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usersRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public Optional<User> loadUserByEmail(String email){
        return usersRepository.findByEmail(email);
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

    public void exportCertificate(HttpServletResponse response,Integer id_user) throws IOException {
        User user =usersRepository.getReferenceById(id_user);

        String link=user.toString();
        ByteArrayOutputStream out = QRCode.from(link).to(ImageType.PNG).stream();

        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        // Get the PdfContentByte object
        PdfContentByte canvas = writer.getDirectContent();
        // Set the color and thickness of the border
        BaseColor baseColor = new BaseColor(255, 0, 0);
        Color color = new Color(baseColor.getRGB());
        canvas.setColorStroke(color);
        canvas.setLineWidth(2);
        // Get the size of the PDF document
        Rectangle pageSize = document.getPageSize();
        float x = pageSize.getLeft() + 20;
        float y = pageSize.getBottom() + 20;
        float width = pageSize.getWidth() - 40;
        float height = pageSize.getHeight() - 40;
        // Draw the border
        canvas.rectangle(x, y, width, height);
        canvas.stroke();
        // Add the Logo's img
        Image logo = Image.getInstance(new File("src\\main\\resources\\Assets\\logo.png").getAbsolutePath());
        logo.setAlignment(Element.ALIGN_LEFT);
        logo.getTransparency();
        logo.scaleToFit(100f, 100f);
        document.add(logo);
        // Add the background's img
//        Image background = Image.getInstance(new File("C:\\Users\\GAMING\\Downloads\\logo.png").getAbsolutePath());
//
//        // Set the absolute position of the image
//        background.setAbsolutePosition(0, 0);
//        background.getTransparency();
//        // Scale the image to fit the page
//        background.scaleAbsolute(document.getPageSize().getWidth(),document.getPageSize().getHeight());

        // Add the image to the writer
//        PdfContentByte content = writer.getDirectContentUnder();
//        content.addImage(background);


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

        ///footerrr
        onEndPage(writer,document);

        ////end document
        document.close();
    }
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        Phrase footer = new Phrase("Dép. SCOLARITÉ |  esprit ►  |  www.esprit.tn");
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom() - 10, 0);
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
    public class PdfGenerator {

        private static final String API_URL = "https://yakpdf.p.rapidapi.com/pdf";
        private static final String API_KEY = "5717faa6c4msh76c264af4543fd1p11a340jsndb7a07637be8";
        private static final String API_HOST = "yakpdf.p.rapidapi.com";

        public static byte[] generatePdf() throws IOException {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            String value = "{\r\n" +
                    "    \"pdf\": {\r\n" +
                    "        \"format\": \"A4\",\r\n" +
                    "        \"printBackground\": true,\r\n" +
                    "        \"scale\": 1\r\n" +
                    "    },\r\n" +
                    "    \"source\": {\r\n" +
                    "        \"html\": \"<!DOCTYPE html><html lang=\\\"en\\\"><head><meta charset=\\\"UTF-8\\\"><meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\"></head><body><h1>Hello World!</h1></body></html>\"\r\n" +
                    "    },\r\n" +
                    "    \"wait\": {\r\n" +
                    "        \"for\": \"navigation\",\r\n" +
                    "        \"timeout\": 250,\r\n" +
                    "        \"waitUntil\": \"load\"\r\n" +
                    "    }\r\n" +
                    "}";
            RequestBody body = RequestBody.create(mediaType, value);
            Request request = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-RapidAPI-Key", API_KEY)
                    .addHeader("X-RapidAPI-Host", API_HOST)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return response.body().bytes();
        }
    }

    public void generateIdentifiant(User user){

        List<User> users = usersRepository.findAll();

        String mid= user.getTypeCours().toString().substring(0,1)+user.getSexe().
                toString().substring(0,1)+user.getNationality().toString().substring(0,1);
        NiveauSuivant niveauSuivant = levelSuggestion(user.getId()) ;
         String year = String.valueOf(user.getRegistrationDate().getYear()).substring(2,4);
        String first =  year+ niveauSuivant.toString().substring(1,2);
        Random rand = new Random();
        String num = String.valueOf(rand.nextInt(9000) + 1000);
        String identifiant =first+mid+num;
        for (User element :users){
            if(Objects.equals(element.getIdentifiant(), identifiant)){
                generateIdentifiant(user);
            }
        }
        user.setIdentifiant(identifiant);
        usersRepository.save(user);
    }
    public void setNewPassword(User user,String newPassword){
        String encodedPassword = bCryptPasswordEncoder
                .encode(newPassword);
        user.setPassword(encodedPassword);
        usersRepository.save(user);
    }



    ///// Badge
    public void badgePdf(HttpServletResponse response,Integer id_user) throws IOException {
        User user =usersRepository.getReferenceById(id_user);

        String link=user.toString();
        ByteArrayOutputStream out = QRCode.from(link).to(ImageType.PNG).stream();

        Document document = new Document(PageSize.B7);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        // Get the PdfContentByte object
        PdfContentByte canvas = writer.getDirectContent();
        // Set the color and thickness of the border
        BaseColor baseColor = new BaseColor(0, 0, 255);
        Color color = new Color(baseColor.getRGB());
        canvas.setColorStroke(color);
        canvas.setLineWidth(2);
        // Get the size of the PDF document
        Rectangle pageSize = document.getPageSize();
        float x = pageSize.getLeft() + 10;
        float y = pageSize.getBottom() + 10;
        float width = pageSize.getWidth() - 25;
        float height = pageSize.getHeight() - 25;
        // Draw the border
        canvas.rectangle(x, y, width, height);
        canvas.stroke();
        // Add the Logo's img
        Image logo = Image.getInstance(new File("src\\main\\resources\\Assets\\logo.png").getAbsolutePath());
        logo.setAlignment(Element.ALIGN_LEFT);
        logo.getTransparency();
        logo.scaleToFit(50f, 50f);
        document.add(logo);

        // Add a title
        Paragraph title = new Paragraph("Badge User");
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(new Font(Font.BOLD,Font.ITALIC));
        document.add(title);

        // Add the recipient's name

        Paragraph name = new Paragraph("NAME : "+user.getLastname()+" "+ user.getFirstname());
        name.setAlignment(Element.ALIGN_JUSTIFIED);
        name.setSpacingBefore(20f);
        document.add(name);

        // Add a message
        Paragraph identifiant = new Paragraph("ID : "+user.getIdentifiant());
        identifiant.setAlignment(Element.ALIGN_JUSTIFIED);
        identifiant.setSpacingBefore(20f);
        document.add(identifiant);
        // Add a message
        Paragraph cin = new Paragraph("CIN : "+user.getCin());
        cin.setAlignment(Element.ALIGN_JUSTIFIED);
        cin.setSpacingBefore(20f);
        document.add(cin);
        //Add a Qr code
        Image QRcode = Image.getInstance(out.toByteArray());
        QRcode.setAlignment(Element.ALIGN_CENTER);
        QRcode.scaleToFit(75f, 75f);
        document.add(QRcode);
        ////end document
        document.close();
    }

    public int[] statistique(String role, String criteria) {
        Role roleEnum = Role.valueOf(role.toUpperCase());
        int[] results = new int[2];
        switch (criteria) {
            case "sexe":
                switch (roleEnum) {
                    case STUDENT:
                        results[0] = usersRepository.countUserByRoleAndSexe(Role.STUDENT, Sexe.FEMME);
                        results[1] = usersRepository.countUserByRoleAndSexe(Role.STUDENT, Sexe.HOMME);
                        break;
                    case ADMIN:
                        results[0] = usersRepository.countUserByRoleAndSexe(Role.ADMIN, Sexe.FEMME);
                        results[1] = usersRepository.countUserByRoleAndSexe(Role.ADMIN, Sexe.HOMME);
                        break;
                    case TEACHER:
                        results[0] = usersRepository.countUserByRoleAndSexe(Role.TEACHER, Sexe.FEMME);
                        results[1] = usersRepository.countUserByRoleAndSexe(Role.TEACHER, Sexe.HOMME);
                        break;
                }
                break;
            case "nationalite":
                switch (roleEnum) {
                    case STUDENT:
                        results[0] = usersRepository.countUserByRoleAndNationality(Role.STUDENT, Nationality.TUNISIENNE);
                        results[1] = usersRepository.countUserByRoleAndNationality(Role.STUDENT, Nationality.ETRANGER);
                        break;
                    case ADMIN:
                        results[0] = usersRepository.countUserByRoleAndNationality(Role.ADMIN, Nationality.TUNISIENNE);
                        results[1] = usersRepository.countUserByRoleAndNationality(Role.ADMIN, Nationality.ETRANGER);
                        break;
                    case TEACHER:
                        results[0] = usersRepository.countUserByRoleAndNationality(Role.TEACHER, Nationality.TUNISIENNE);
                        results[1] = usersRepository.countUserByRoleAndNationality(Role.TEACHER, Nationality.ETRANGER);
                        break;
                }
                break;
            case "annee_inscription":
                switch (roleEnum) {
                    case STUDENT:
                        results[0] = usersRepository.UserByRoleAndRegistDate(Role.STUDENT, LocalDateTime.now().getYear());
                        break;
                    case ADMIN:
                        results[0] = usersRepository.UserByRoleAndRegistDate(Role.ADMIN, LocalDateTime.now().getYear());
                        break;
                    case TEACHER:
                        results[0] = usersRepository.UserByRoleAndRegistDate(Role.TEACHER, LocalDateTime.now().getYear());
                        break;
                }
                break;
            default:
                results[0] = -1;
                results[1] = -1;
                break;
        }
        return results;
    }

//     public String statistique(String role,String cretetria) {
//         Role roleEnum = Role.valueOf(role.toUpperCase());
//         switch (cretetria) {
//             case "sexe":
//                 switch (roleEnum) {
//                     case STUDENT:
//                         return "The number of Female Students : " + usersRepository.countUserByRoleAndSexe(Role.STUDENT, Sexe.FEMME)+"\n"+
//                                 "The number of Male Students: " + usersRepository.countUserByRoleAndSexe(Role.STUDENT, Sexe.HOMME);
//                     case ADMIN:
//                         return "The number of Female Admins: " + usersRepository.countUserByRoleAndSexe(Role.ADMIN, Sexe.FEMME)+"\n"+
//                                 "The number of Male Admins: " + usersRepository.countUserByRoleAndSexe(Role.ADMIN, Sexe.HOMME);
//                     case TEACHER:
//                         return "The number of Female Teachers: " + usersRepository.countUserByRoleAndSexe(Role.TEACHER, Sexe.FEMME)+"\n"+
//                                 "The number of Male Teachers: " + usersRepository.countUserByRoleAndSexe(Role.TEACHER, Sexe.HOMME);
//                 }
//             case "nationalite":
//                 switch (roleEnum) {
//                     case STUDENT:
//                         return "le nombre des etudiants ayant la nationalité tunisienne: " + usersRepository.countUserByRoleAndNationality(Role.STUDENT, Nationality.TUNISIENNE)+"\n"+
//                                 "le nombre des etudiants ayant la nationalité etrangere: " + usersRepository.countUserByRoleAndNationality(Role.STUDENT, Nationality.ETRANGER);
//                     case ADMIN:
//                         return "le nombre des admins ayant la nationalité tunisienne: " + usersRepository.countUserByRoleAndNationality(Role.ADMIN, Nationality.TUNISIENNE)+"\n"+
//                                 "le nombre des admins ayant la nationalité etrangere: " + usersRepository.countUserByRoleAndNationality(Role.ADMIN, Nationality.ETRANGER);
//                     case TEACHER:
//                         return "le nombre des enseignants ayant la nationalité tunisienne: " + usersRepository.countUserByRoleAndNationality(Role.TEACHER, Nationality.TUNISIENNE)+"\n"+
//                                 "le nombre des enseignants ayant la nationalité etrangere: " + usersRepository.countUserByRoleAndNationality(Role.TEACHER, Nationality.ETRANGER);
//                 }
//             case "annee_inscription":
//                 switch (roleEnum) {
//                     case STUDENT:
//                         return "le nombre des etudiants inscrit en 2023 : " + usersRepository.UserByRoleAndRegistDate(Role.STUDENT,LocalDateTime.now().getYear());
//                     case ADMIN:
//                         return "le nombre des admins  inscrit en 2023: " + usersRepository.UserByRoleAndRegistDate(Role.ADMIN, LocalDateTime.now().getYear());
//                     case TEACHER:
//                         return "le nombre des enseignants inscrit en 2023: " + usersRepository.UserByRoleAndRegistDate(Role.TEACHER,LocalDateTime.now().getYear());
//                 }
//         }
//         return "verifier le critere ou le role";
//     }

    @Transactional
    public String addInterviewAndAssignToStudent(Integer idStudent, Date dateInterview,Integer heureInterview) {
        User student = usersRepository.findById(idStudent).get();
        List<User> teachers = usersRepository.findByRole(Role.TEACHER);
        User teacher = usersRepository.findTeacherByRole(Role.TEACHER);
        Interview interview1 = new Interview(dateInterview,student,teacher,heureInterview);
        Classroom classroom = classroomRepository.findById(1).get();
        interviewRepository.save(interview1);
        student.setInterviewStudent(interview1);
        interview1.setClassroomInterview(classroom);
        interview1.setEvaluator(teacher);
        interviewRepository.save(interview1);
        usersRepository.save(student);
        interviewServiceImp.sendInterviewDetails(idStudent);

        //log.info("la liste des teachers est "+teachers);

        String test="interview validated";
        /*for ( User teacher : teachers){
            Set<Interview> interviews = teacher.getInterviewEvaluators();

            //log.info("la liste des interview du teacher est "+interviews);

            for (Interview interview : interviews){
                if((interview.getDateInterview()!=dateInterview)&&(!Objects.equals(interview.getHeureInterview(), heureInterview))){
                    Interview interview1 = new Interview(dateInterview,student,teacher,heureInterview);
                    interviewRepository.save(interview1);

                    student.setInterviewStudent(interview1);

                    Classroom classroom = classroomRepository.findById(1).get();
                    student.getInterviewStudent().setClassroomInterview(classroom);
                    interview1.setClassroomInterview(classroom);

                    usersRepository.save(student);
                    interviewRepository.save(interview1);


                    interviewServiceImp.sendInterviewDetails(idStudent);
                    test="interview validated";
                    break;
                }else{
                    continue;
                }
            }
        }*/
        return test;
    }

    @Override
    public void assignInterviewToTeacher(Integer idStudent){
        User student = usersRepository.findById(idStudent).get();
        User teacher = student.getInterviewStudent().getEvaluator();
        Interview interview = student.getInterviewStudent();
        teacher.getInterviewEvaluators().add(interview);
        usersRepository.save(teacher);

    }

    @Override
    public List<UserDTO> findStudentsByFirstName(UserDTO userDto) {
        List<User> studentList = usersRepository.findByFirstnameAndRole(userDto.getFirstName(), Role.STUDENT);
        //log.info("la liste des etudiant du repository est " + studentList);
        List<UserDTO> studentDtoList = new ArrayList<>();
        //log.info("la list des etudiants vide est " + studentDtoList);
        for (User student : studentList) {
            UserDTO studentDto = new UserDTO();
            studentDto.setFirstName(student.getFirstname());
            studentDto.setLastName(student.getLastname());
            studentDto.setNiveauSuivant(student.getNiveauSuivant());
            studentDtoList.add(studentDto);
            //log.info("la list des etudiants avec un seul estudiant est " + studentDtoList);
        }
        //log.info("la list des etudiants finale est " + studentDtoList);
        return studentDtoList;
    }

    @Override
    public List<TeacherDto> findTeachersByFirstNameAndLastName(String firstName, String lastName) {
        List<User> teacherList = usersRepository.findByFirstnameAndLastnameAndRole(firstName, lastName, Role.TEACHER);
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        for (User teacher : teacherList) {
            TeacherDto teacherDto = new TeacherDto();
            teacherDto.setFirstName(teacher.getFirstname());
            teacherDto.setLastName(teacher.getLastname());
            teacherDto.setEmail(teacher.getEmail());
            teacherDtoList.add(teacherDto);
        }
        return teacherDtoList;
    }
    public User getCurrentUser(String token) {
        return tokenRepository.getUserByToken(token);
    }

    public Role findRoleByEmail(String email){
        return usersRepository.findRoleByEmail(email);
    }
}
