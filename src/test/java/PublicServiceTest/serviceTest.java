package PublicServiceTest;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.alignWebsite.PublicFacing;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.dao.alignpublic.MultipleValueAggregatedDataDao;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.enums.*;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.mehaexample.asdDemo.model.alignpublic.*;
import org.mehaexample.asdDemo.restModels.StudentSerachCriteria;
import org.mehaexample.asdDemo.restModels.StudentStatsObject;
import org.mehaexample.asdDemo.restModels.TopUnderGradSchools;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class serviceTest {

    public static PublicFacing publicFacing;
    public static StudentsPublicDao studentsPublicDao;
    public static UndergraduatesPublicDao undergraduatesPublicDao;
    public static WorkExperiencesPublicDao workExperiencesPublicDao;
    public static MultipleValueAggregatedDataDao multipleValueAggregatedDataDao;
    public static StudentsDao studentsDao;
    public static final String LIST_OF_STUDENTS_STATES = "ListOfStudentsStates";
    MultipleValueAggregatedData multipleValueAggregatedData;
    StudentSerachCriteria studentSerachCriteria;
    StudentStatsObject studentStatsObject;
    StudentStatsObject studentStatsObjectEmpty;
    Students newStudent;
    DataCount state;


    @BeforeClass
    public static void init() {
        publicFacing = new PublicFacing();
        studentsPublicDao = new StudentsPublicDao();
        undergraduatesPublicDao = new UndergraduatesPublicDao();
        workExperiencesPublicDao = new WorkExperiencesPublicDao();
        multipleValueAggregatedDataDao = new MultipleValueAggregatedDataDao();
        studentsDao = new StudentsDao();
    }

    @Before
    public void setup() {

        StudentsPublic studentsPublic1 = new StudentsPublic(21, 2015, true);
        StudentsPublic studentsPublic2 = new StudentsPublic(22, 2016, true);
        StudentsPublic studentsPublic3 = new StudentsPublic(23, 2017, true);
        StudentsPublic studentsPublic4 = new StudentsPublic(24, 2018, true);



        studentsPublicDao.createStudent(studentsPublic1);
        studentsPublicDao.createStudent(studentsPublic2);
        studentsPublicDao.createStudent(studentsPublic3);
        studentsPublicDao.createStudent(studentsPublic4);

        UndergraduatesPublic undergraduatesPublic1 =
                new UndergraduatesPublic(21, "cs", "WSU");
        UndergraduatesPublic undergraduatesPublic2 =
                new UndergraduatesPublic(22, "IT", "CSU");
        UndergraduatesPublic undergraduatesPublic3 =
                new UndergraduatesPublic(23, "maths", "NYU");
        UndergraduatesPublic undergraduatesPublic4 =
                new UndergraduatesPublic(24, "technology", "NEU");
        UndergraduatesPublic undergraduatesPublic5 =
                new UndergraduatesPublic(21, "agriculture", "UW");

        undergraduatesPublicDao.createUndergraduate(undergraduatesPublic1);
        undergraduatesPublicDao.createUndergraduate(undergraduatesPublic2);
        undergraduatesPublicDao.createUndergraduate(undergraduatesPublic3);
        undergraduatesPublicDao.createUndergraduate(undergraduatesPublic4);
        undergraduatesPublicDao.createUndergraduate(undergraduatesPublic5);


        WorkExperiencesPublic workExperiencesPublic1 = new WorkExperiencesPublic(21, "lululemon");
        WorkExperiencesPublic workExperiencesPublic2 = new WorkExperiencesPublic(22, "Scality");
        WorkExperiencesPublic workExperiencesPublic3 = new WorkExperiencesPublic(23, "Redfin");
        WorkExperiencesPublic workExperiencesPublic4 = new WorkExperiencesPublic(24, "blackrock");
        WorkExperiencesPublic workExperiencesPublic5 = new WorkExperiencesPublic(21, "FedEx");

        workExperiencesPublicDao.createWorkExperience(workExperiencesPublic1);
        workExperiencesPublicDao.createWorkExperience(workExperiencesPublic2);
        workExperiencesPublicDao.createWorkExperience(workExperiencesPublic3);
        workExperiencesPublicDao.createWorkExperience(workExperiencesPublic4);
        workExperiencesPublicDao.createWorkExperience(workExperiencesPublic5);

        state = new DataCount("WA",2);

        List<String> coopList = new ArrayList<>();
        List<String> degreeList = new ArrayList<>();
        List<String> schoolList = new ArrayList<>();
        List<String> yestList = new ArrayList<>();
        List<String> campus = new ArrayList<>();
        List<String> campusList = new ArrayList<>();


        coopList.add("lululemon");
        degreeList.add("cs");
        schoolList.add("WSU");
        yestList.add("2015");

        campus.add("SEATTLE");
        campus.add("BOSTON");
        campus.add("CHARLOTTE");
        campus.add("SILICON_VALLEY");

        studentSerachCriteria = new StudentSerachCriteria(coopList, degreeList, schoolList, yestList, "0", "1");

        studentStatsObject = new StudentStatsObject(campus);
        studentStatsObjectEmpty = new StudentStatsObject(campusList);

        multipleValueAggregatedData = new MultipleValueAggregatedData("WA",2);
        multipleValueAggregatedData.setAnalyticTerm(LIST_OF_STUDENTS_STATES);

    }

    @After
    public void deleteForDuplicateDatabase() {
        studentsPublicDao.deleteStudentByPublicId(21);
        studentsPublicDao.deleteStudentByPublicId(22);
        studentsPublicDao.deleteStudentByPublicId(23);
        studentsPublicDao.deleteStudentByPublicId(24);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getHigestEducation() {
        JSONObject education = new JSONObject();
        Response res = publicFacing.getListOfHighestEducation();
        Assert.assertEquals(education.toString(), res.getEntity());
        Assert.assertEquals(200, res.getStatus());
    }


    @SuppressWarnings("unchecked")
    @Test
    public void getState() {
        JSONObject state = new JSONObject();
        Response res = publicFacing.getListOfState();
        Assert.assertEquals(state.toString(), res.getEntity());
        System.out.println(res.getEntity());
        Assert.assertEquals(200, res.getStatus());
    }


    @SuppressWarnings("unchecked")
    @Test
    public void getAllSchools() {
        List<String> schools = new ArrayList<>();
        schools.add("UW");
        schools.add("WSU");
        schools.add("CSU");
        schools.add("NYU");
        schools.add("NEU");
        Response res = publicFacing.getAllSchools();
        List response = (List) res.getEntity();
        Assert.assertEquals(schools, response);
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTopSchools() {
        List<String> schools = new ArrayList<>();
        schools.add("UW");
        schools.add("WSU");
        schools.add("CSU");
        JSONArray result = new JSONArray();
        for (String school : schools) {
            result.put(school);
        }
        Response res = publicFacing.getUndergradSchools(3);
        String response = (String) res.getEntity();
        Assert.assertEquals(result.toString(), response);
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTopCoops() {
        List<String> coops = new ArrayList<>();
        coops.add("FedEx");
        coops.add("lululemon");
        coops.add("Scality");
        JSONArray result = new JSONArray();
        for (String coop : coops) {
            result.put(coop);
        }
        Response res = publicFacing.getTopCoops(3);
        String response = (String) res.getEntity();
        Assert.assertEquals(result.toString(), response);
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTopMajor() {
        List<String> undergradMajor = new ArrayList<>();
        undergradMajor.add("agriculture");
        undergradMajor.add("cs");
        undergradMajor.add("IT");
        JSONArray result = new JSONArray();
        for (String major : undergradMajor) {
            result.put(major);
        }
        Response res = publicFacing.getUndergradDegrees(3);
        String response = (String) res.getEntity();
        Assert.assertEquals(result.toString(), response);
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTopYear() {
        List<Integer> years = new ArrayList<>();
        years.add(2015);
        years.add(2016);
        years.add(2017);
        JSONArray result = new JSONArray();
        for (Integer year : years) {
            result.put(Integer.toString(year));
        }
        Response res = publicFacing.getTopGraduationYears(3);
        String response = (String) res.getEntity();
        Assert.assertEquals(result.toString(), response);
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getAllUndergard() {
        List<String> undergrads = new ArrayList<>();
        undergrads.add("agriculture");
        undergrads.add("cs");
        undergrads.add("IT");
        undergrads.add("maths");
        undergrads.add("technology");
        Response res = publicFacing.getAllUndergradDegrees();
        List response = (List) res.getEntity();
        Assert.assertEquals(200, res.getStatus());
        Assert.assertEquals(undergrads, response);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getEnrollment() {

        String full = "full-time";
        String part = "part-time";
        Response res = publicFacing.getEnrollmentStatus();
        //String response = (String) res.getEntity();
        Assert.assertEquals("{\"full-time\"" + ":\"49.09091\",\"part-time\"" + ":\"50.90909\"}", res.getEntity());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getCampus() {

        Response res = publicFacing.getCampusData();
        //String response = (String) res.getEntity();
        Assert.assertEquals("{\"boston\":\"24.324326\",\"charlotte\":\"25.225225\",\"siliconvalley\":\"25.675674\",\"seattle\":\"24.774775\"}", res.getEntity());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getGraduationTest() {

        Response res = publicFacing.getGraduation();
        //String response = (String) res.getEntity();
        Assert.assertEquals("{\"graduated\":\"49.51456\",\"terminated\":\"50.48544\"}", res.getEntity());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void searchStudent() {

        Response res = publicFacing.searchStudent(studentSerachCriteria);
        String response = (String) res.getEntity();
        Assert.assertEquals(200, res.getStatus());
        Assert.assertEquals("{\"quantity\":1,\"students\":[{\"coop\":\"FedEx\",\"undergradschool\":\"UW\",\"graduationyear\":\"2015\",\"undergraddegree\":\"agriculture\"}]}", response);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void getScholarshipTest() {

        Response res = publicFacing.getScholarshipData();
        //String response = (String) res.getEntity();
        Assert.assertEquals("{\"scholarship\":\"54.545456\",\"none\":\"45.454548\"}", res.getEntity());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getGenderTest() {

        Response res = publicFacing.getGender();
        //String response = (String) res.getEntity();
        Assert.assertEquals("{\"female\":\"52.0\",\"male\":\"48.0\"}", res.getEntity());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTotalGraduateTest() {
        Response res = publicFacing.getTotalGraduates();
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTotalStudent1Test() {
        Response res = publicFacing.getTotalStudents1();
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getUndergraduatepercentTest() {
        Response res = publicFacing.getListOfUndergradMajorPercent();
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTotalStudentTest() {
        Response res = publicFacing.getTotalStudents(studentStatsObject);
        Assert.assertEquals(200, res.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getTotalStudentEmptyListTest() {
        Response res = publicFacing.getTotalStudents(studentStatsObjectEmpty);
        Assert.assertEquals(200, res.getStatus());
    }
}