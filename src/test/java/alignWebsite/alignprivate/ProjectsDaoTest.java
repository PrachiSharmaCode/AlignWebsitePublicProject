package alignWebsite.alignprivate;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignprivate.ProjectsDao;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.enums.*;
import org.mehaexample.asdDemo.model.alignprivate.Projects;
import org.mehaexample.asdDemo.model.alignprivate.Students;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class ProjectsDaoTest {
    private static StudentsDao studentsDao;
    private static ProjectsDao projectsDao;

    @BeforeClass
    public static void init() {
        studentsDao = new StudentsDao();
        projectsDao = new ProjectsDao();
    }

    @Before
    public void addDatabasePlaceholder() throws ParseException {
        Students student = new Students("001234567", "tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109",
                Term.FALL, 2014, Term.SPRING, 2016,
                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
        Students student2 = new Students("111234567", "jerrymouse@gmail.com", "Jerry", "",
                "Mouse", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109",
                Term.FALL, 2014, Term.SPRING, 2016,
                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null, true);
        studentsDao.addStudent(student);
        studentsDao.addStudent(student2);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Projects project = new Projects("001234567","Student Website", dateFormat.parse("2018-01-01"),
                dateFormat.parse("2018-04-01"), "My Project");
        projectsDao.createProject(project);
    }

    @After
    public void deletePlaceholder() {
        projectsDao.deleteProjectsByNeuId("001234567");
        studentsDao.deleteStudent("001234567");
        studentsDao.deleteStudent("111234567");
    }

    @Test
    public void getProjectById() {
        int tempId = projectsDao.getProjectsByNeuId("001234567").get(0).getProjectId();
        Projects project = projectsDao.getProjectById(tempId);
        assertTrue(project.getNeuId().equals("001234567"));
        assertTrue(project.getProjectName().equals("Student Website"));
        Projects notFoundProject = projectsDao.getProjectById(-10);
        assertTrue(notFoundProject == null);
    }

    @Test
    public void getProjectsByNeuId() {
        List<Projects> listOfProjects = projectsDao.getProjectsByNeuId("001234567");
        assertTrue(listOfProjects.get(0).getProjectName().equals("Student Website"));

        assertTrue(projectsDao.getProjectsByNeuId("00000000").size() == 0);
    }

    @Test
    public void createUpdateDeleteProject() throws ParseException {
        Projects project = new Projects();

        Students student = studentsDao.getStudentRecord("111234567");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        project.setStartDate(dateFormat.parse("2017-06-01"));
        project.setEndDate(dateFormat.parse("2017-12-01"));
        project.setDescription("Description");
        project.setNeuId(student.getNeuId());
        project.setProjectName("CNAO");

        // create new project
        projectsDao.createProject(project);
        Projects foundProject = projectsDao.getProjectsByNeuId("111234567").get(0);
        assertTrue(foundProject.getProjectName().equals("CNAO"));

        // update found project
        foundProject.setDescription("Description2");
        projectsDao.updateProject(foundProject);
        assertTrue(projectsDao.getProjectsByNeuId("111234567").get(0).getDescription().equals("Description2"));
        project.setProjectId(-100);
        assertFalse(projectsDao.updateProject(project));

        // delete the project
        projectsDao.deleteProjectById(foundProject.getProjectId());
        assertTrue(projectsDao.getProjectById(foundProject.getProjectId()) == null);
        assertFalse(projectsDao.deleteProjectById(-100));
    }

}