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
import org.json.JSONArray;
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
     * Request 1
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
    public Response getUndergradSchools(TopUnderGradSchools topUnderGradSchools){
		List<TopUndergradSchools> undergrad;
		JSONArray result = new JSONArray();
		
		int number = topUnderGradSchools.getNumber();
		
		try {
			undergrad = undergraduatesPublicDao.getTopUndergradSchools(number);
			for(TopUndergradSchools school : undergrad){
				JSONObject schoolJson = new JSONObject(school);
				result.put(schoolJson.get("undergradSchool"));
			}
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
    }

    /**
     * Request 2
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
    public Response getTopCoops(TopCoopsNumber topCoopsNumber){
        List<TopCoops> coops;
		JSONArray result = new JSONArray();
        int number = topCoopsNumber.getNumber();

		try {
            coops = workExperiencesPublicDao.getTopCoops(number);
			for(TopCoops coop : coops){
				JSONObject coopJson = new JSONObject(coop);
				result.put(coopJson.get("coop"));
			}
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
    }

    /**
     * Request 3
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
    public Response getUndergradDegrees(TopUnderGradDegreesNumber topUnderGradDegreesNumber){
		List<TopUndergradDegrees> degrees;
		JSONArray result = new JSONArray();
		int number = topUnderGradDegreesNumber.getNumber();
		
		try {
            degrees = undergraduatesPublicDao.getTopUndergradDegrees(number);
			for(TopUndergradDegrees degree : degrees){
				JSONObject degreeJson = new JSONObject(degree);
				result.put(degreeJson.get("undergradDegree"));
			}
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
    }

    /**
     * Request 4
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
    public Response getTopGraduationYears(TopGraduationYearsNumber topGraduationYearsNumber){
		List<TopGradYears> gradYears;
		JSONArray result = new JSONArray();
        int number = topGraduationYearsNumber.getNumber();
        
		try {
            gradYears = studentsPublicDao.getTopGraduationYears(number);
			for(TopGradYears year : gradYears){
				JSONObject yearJson = new JSONObject(year);
				result.put(yearJson.get("graduationYear"));
			}
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(result.toString()).build();
    }

    /**
     * Request 5
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
        List<String> allUnderGradSchools;       
        try {
            allUnderGradSchools = undergraduatesPublicDao.getListOfAllSchools();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        
		return Response.status(Response.Status.OK).entity(allUnderGradSchools).build();
    }


    /**
     * Request 6
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
        List<String> listOfAllCoopCompanies;
		try {
            listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(listOfAllCoopCompanies).build();
    }

    /**
     * Request 7
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
    public Response getAllUndergradDegrees(){
        List<String> degrees;
        
		try {
            degrees = undergraduatesPublicDao.getListOfAllUndergraduateDegrees();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(degrees).build();
    }

    /**
     * Request 8
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
    public Response getAllGradYears(){
        List<Integer> years;
        
		try {
            years = studentsPublicDao.getListOfAllGraduationYears();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
		return Response.status(Response.Status.OK).entity(years).build();
    }

    /**
     * Request 9
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
    public Response getAllStudents(){
        List<StudentsPublic> studentList = new ArrayList<StudentsPublic>();
        
        try {
            studentList = studentsPublicDao.getListOfAllStudents();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        
		return Response.status(Response.Status.OK).entity(studentList).build();
    }

    /**
     * Request 10
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
		List<StudentsPublic> studentRecords;
		int begin = 1;
		int end = 20;
		
		try{
			if (studentSerachCriteria.getCoops() != null) {
				searchCriteriaMap.put("coop", studentSerachCriteria.getCoops());
			}
			if (studentSerachCriteria.getUndergraddegree() != null) {
				searchCriteriaMap.put("undergradDegree", studentSerachCriteria.getUndergraddegree());
			}
			if (studentSerachCriteria.getUndergradschool() != null) {
				searchCriteriaMap.put("undergradSchool", studentSerachCriteria.getUndergradschool());
			}
			if (studentSerachCriteria.getGraduationyear() != null) {
				searchCriteriaMap.put("graduationYear", studentSerachCriteria.getGraduationyear());
			}
			if (studentSerachCriteria.getEndindex() != null) {
				end = Integer.valueOf(studentSerachCriteria.getEndindex());
			}
			if (studentSerachCriteria.getBeginindex() != null) {
				begin = Integer.valueOf(studentSerachCriteria.getBeginindex());
			}
		} catch (Exception e){
			return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
		}
		
		try {
			studentRecords = studentsPublicDao.getPublicFilteredStudents(searchCriteriaMap, begin, end);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		
        return Response.status(Response.Status.OK).entity(studentRecords).build();
    }

 // Request 11
    @GET
    @Path("gender")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGender(){
		JSONObject jsonObj = new JSONObject();
		try {
			int numberOfMale = singleValueAggregatedDataDao.getTotalMaleStudents();
            int numberOfFemale = singleValueAggregatedDataDao.getTotalFemaleStudents();
			jsonObj.put("male", numberOfMale);
			jsonObj.put("female", numberOfFemale);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

 // Request 12
    @GET
    @Path("race")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRace(){
		List<DataCount> race;
        try {
            race = multipleValueAggregatedDataDao.getListOfRacesCount();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		return Response.status(Response.Status.OK).entity(race).build();
    }

 // Request 13
    @GET
    @Path("enrollment")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEnrollmentStatus(){
		JSONObject jsonObj = new JSONObject();
		try {
            int numberOfFulltime = singleValueAggregatedDataDao.getTotalFullTimeStudents();
            int numberOfPartTime = singleValueAggregatedDataDao.getTotalPartTimeStudents();
			jsonObj.put("full-time", numberOfFulltime);
			jsonObj.put("part-time", numberOfPartTime);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

 // Request 14
    @GET
    @Path("graduation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGraduation(){
		JSONObject jsonObj = new JSONObject();
        try {
            int numberOfGraduated = singleValueAggregatedDataDao.getTotalGraduatedStudents();
            int numberOfTerminated = singleValueAggregatedDataDao.getTotalDroppedOutStudents();
			jsonObj.put("graduated", numberOfGraduated);
			jsonObj.put("terminated", numberOfTerminated);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

 // Request 15
    @GET
    @Path("state")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfState(){
		List<DataCount> state;
        try {
            state = multipleValueAggregatedDataDao.getListOfStudentsStatesCount();
		} catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(state).build();
    }

 // Request 16
    @GET
    @Path("campus")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampusData(){
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

 // Request 17
    @GET
    @Path("scholarship")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScholarshipData(){;
		JSONObject jsonObj = new JSONObject();
        try {
            int studentWithScholarship = singleValueAggregatedDataDao.getTotalStudentsWithScholarship();
            int studentWithoutScholarship = singleValueAggregatedDataDao.getTotalStudents();
			jsonObj.put("scholarship", studentWithScholarship);
			jsonObj.put("none", studentWithoutScholarship);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
    }

 // Request 18
    @GET
    @Path("highest-education")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListOfHighestEducation(){
		List<DataCount> education;
        try {
            education = multipleValueAggregatedDataDao.getListOfHighestDegreesCount();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
        return Response.status(Response.Status.OK).entity(education).build();
    }

}
