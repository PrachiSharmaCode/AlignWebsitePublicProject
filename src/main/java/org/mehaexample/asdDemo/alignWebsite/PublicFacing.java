package org.mehaexample.asdDemo.alignWebsite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignpublic.*;
import org.mehaexample.asdDemo.model.alignpublic.*;
import org.mehaexample.asdDemo.restModels.StudentSerachCriteria;
import org.mehaexample.asdDemo.restModels.TopCoopsNumber;
import org.mehaexample.asdDemo.restModels.TopGraduationYearsNumber;
import org.mehaexample.asdDemo.restModels.TopUnderGradDegreesNumber;
import org.mehaexample.asdDemo.restModels.TopUnderGradSchools;

@Path("")
public class PublicFacing {
    UndergraduatesPublicDao undergraduatesPublicDao = new UndergraduatesPublicDao();
    WorkExperiencesPublicDao workExperiencesPublicDao = new WorkExperiencesPublicDao();
    StudentsPublicDao studentsPublicDao = new StudentsPublicDao();
    SingleValueAggregatedDataDao singleValueAggregatedDataDao = new SingleValueAggregatedDataDao();
    MultipleValueAggregatedDataDao multipleValueAggregatedDataDao = new MultipleValueAggregatedDataDao();

    /**
     * This is the function to get top n undergraduate schools
     *
     * @param topUnderGradSchools
     * @return List of n top undergraduate schools
     * @throws SQLException
     */
    @POST
    @Path("top-undergradschools")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUndergradSchools(TopUnderGradSchools topUnderGradSchools) throws SQLException {
		List<TopUndergradSchools> undergrad = new ArrayList();
		try {
            int number = topUnderGradSchools.getNumber();
            if (number < 1) {
                return Response.status(Response.Status.BAD_REQUEST).
                        entity("The number can't be less than one").build();
            }
			undergrad = undergraduatesPublicDao.getTopUndergradSchools(number);
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(undergrad).build();
    }

    /**
     * This is the function to get top coops.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/top-coops
     *
     * @return List of n top coops
     */
    @POST
    @Path("top-coops")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopCoops(TopCoopsNumber topCoopsNumber) throws SQLException {
        List<TopCoops> coops = new ArrayList();
		try {
            int number = topCoopsNumber.getNumber();
            coops = workExperiencesPublicDao.getTopCoops(number);
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(coops).build();
    }

    /**
     * This is the function to get top undergraduate degrees.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/top-undergraddegrees
     *
     * @return List of n top undergraduate degrees
     */
    @POST
    @Path("top-undergraddegrees")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUndergradDegrees(TopUnderGradDegreesNumber topUnderGradDegreesNumber) throws SQLException {
		List<TopUndergradDegrees> degrees = new ArrayList();
		try {
			int number = topUnderGradDegreesNumber.getNumber();
            degrees = undergraduatesPublicDao.getTopUndergradDegrees(number);
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(degrees).build();
    }

    /**
     * This is the function to get top graduation years.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/top-graduationyears
     *
     * @return List of n top graduation years
     */
    @POST
    @Path("top-graduationyears")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopGraduationYears(TopGraduationYearsNumber topGraduationYearsNumber) throws SQLException {
		List<TopGradYears> gradYears = new ArrayList();
		try {
            int number = topGraduationYearsNumber.getNumber();
            gradYears = studentsPublicDao.getTopGraduationYears(number);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(gradYears).build();
    }

    /**
     * This is a function to get all undergrad schools
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/all-schools
     *
     * @return List of UnderGradSchools
     */
    @GET
    @Path("all-schools")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSchools() {
        List<String> allUnderGradSchools = new ArrayList<>();
        try {
            allUnderGradSchools = undergraduatesPublicDao.getListOfAllSchools();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(allUnderGradSchools).build();
    }


    /**
     * This is a function to get list of ALL Coop companies
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/all-coops
     *
     * @return List of UnderGradSchools
     */
    @GET
    @Path("/all-coops")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCoopCompanies() {
        List<String> listOfAllCoopCompanies = new ArrayList<>();
		try {
            listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(listOfAllCoopCompanies).build();
    }

    /**
     * This is the function to get all undergraduate degrees.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/all-undergraddegrees
     *
     * @return List of all undergraduate degrees
     */
    @GET
    @Path("all-undergraddegrees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUndergradDegrees() throws SQLException {
        List<String> degrees = new ArrayList();
		try {
            degrees = undergraduatesPublicDao.getListOfAllUndergraduateDegrees();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(degrees).build();
    }

    /**
     * This is the function to get all graduate years.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/all-grad-years
     *
     * @return List of all graduate years
     */
    @GET
    @Path("all-grad-years")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGradYears() throws SQLException {
        List<Integer> years = new ArrayList();
		try {
            years = studentsPublicDao.getListOfAllGraduationYears();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(years).build();
    }

    /**
     * This is the function to get all students.
     * The body should be in the JSON format like below:
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/students
     *
     * @return List of all students
     */
    @GET
    @Path("students")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() throws SQLException {
        List<StudentsPublic> studentList = new ArrayList();
        try {
            studentList = studentsPublicDao.getListOfAllStudents();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(studentList).build();
    }

    /**
     * This is the function to search for students
     * <p>
     * http://localhost:8080/alignWebsite/webapi/public-facing/students
     *
     * @return the list of student profiles matching the fields.
     */
    @POST
    @Path("students")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStudent(StudentSerachCriteria studentSerachCriteria) {
        Map<String, List<String>> searchCriteriaMap = new HashMap<>();
		List<StudentsPublic> studentRecords = new ArrayList<StudentsPublic>();
		int begin = 1;
		int end = 20;
		try{
			if (studentSerachCriteria.getCoops().size() > 0) {
				searchCriteriaMap.put("coop", studentSerachCriteria.getCoops());
			}

			if (studentSerachCriteria.getUndergraddegree().size() > 0) {
				searchCriteriaMap.put("undergradDegree", studentSerachCriteria.getUndergraddegree());
			}

			if (studentSerachCriteria.getUndergradschool().size() > 0) {
				searchCriteriaMap.put("undergradSchool", studentSerachCriteria.getUndergradschool());
			}

			if (studentSerachCriteria.getGraduationyear().size() > 0) {
				searchCriteriaMap.put("graduationYear", studentSerachCriteria.getGraduationyear());
			}
			if (studentSerachCriteria.getEndindex() != null) {
				end = Integer.valueOf(studentSerachCriteria.getEndindex());
			}
			if (studentSerachCriteria.getBeginindex() != null) {
				begin = Integer.valueOf(studentSerachCriteria.getBeginindex());
			}
			studentRecords = studentsPublicDao.getPublicFilteredStudents(searchCriteriaMap, begin, end);
		} catch (Exception e){
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
        return Response.status(Response.Status.OK).entity(studentRecords).build();
    }

    @GET
    @Path("gender")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGender() throws SQLException {
		List<String> genderBreakdown = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		try {
			int numberOfMale = singleValueAggregatedDataDao.getTotalMaleStudents();
            int numberOfFemale = singleValueAggregatedDataDao.getTotalFemaleStudents();
			jsonObj.put("male", numberOfMale);
			jsonObj.put("female", numberOfFemale);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @GET
    @Path("race")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRace() throws SQLException {
		List<DataCount> race = new ArrayList();
        try {
            race = multipleValueAggregatedDataDao.getListOfRacesCount();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(race).build();
    }

    @GET
    @Path("enrollment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnrollmentStatus() throws SQLException {
		List<String> enrollment = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
		try {
            int numberOfFulltime = singleValueAggregatedDataDao.getTotalFullTimeStudents();
            int numberOfPartTime = singleValueAggregatedDataDao.getTotalPartTimeStudents();
			jsonObj.put("full-time", numberOfFulltime);
			jsonObj.put("part-time", numberOfPartTime);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @GET
    @Path("graduation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraduation() throws SQLException {
        List<String> graduation = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
        try {
            int numberOfGraduated = singleValueAggregatedDataDao.getTotalGraduatedStudents();
            int numberOfTerminated = singleValueAggregatedDataDao.getTotalDroppedOutStudents();
			jsonObj.put("graduated", numberOfGraduated);
			jsonObj.put("terminated", numberOfTerminated);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @GET
    @Path("state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfState() throws SQLException {
		List<DataCount> state = new ArrayList();
        try {
            state = multipleValueAggregatedDataDao.getListOfStudentsStatesCount();
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity(state).build();
    }

    @GET
    @Path("campus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampusData() throws SQLException {
		List<String> campus = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
        try {
            int studentInBoston = singleValueAggregatedDataDao.getTotalStudentsInBoston();
            int studentInSeattle = singleValueAggregatedDataDao.getTotalStudentsInSeattle();
            int studentInCharlotte = singleValueAggregatedDataDao.getTotalStudentsInCharlotte();
            int studentInSiliconValley = singleValueAggregatedDataDao.getTotalStudentsInSiliconValley();
			jsonObj.put("boston", studentInBoston);
			jsonObj.put("seattle", studentInSeattle);
			jsonObj.put("charlotte", studentInCharlotte);
			jsonObj.put("siliconvalley", studentInSiliconValley);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @GET
    @Path("scholarship")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScholarshipData() throws SQLException {
		List<String> scholarship = new ArrayList<>();
		JSONObject jsonObj = new JSONObject();
        try {
            int studentWithScholarship = singleValueAggregatedDataDao.getTotalStudentsWithScholarship();
            int studentWithoutScholarship = singleValueAggregatedDataDao.getTotalStudents();
			jsonObj.put("scholarship", studentWithScholarship);
			jsonObj.put("none", studentWithoutScholarship);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

    @GET
    @Path("highest-education")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfHighestEducation() throws SQLException {
		List<DataCount> education = new ArrayList();
        try {
            education = multipleValueAggregatedDataDao.getListOfHighestDegreesCount();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.OK).entity(education).build();
    }

}
