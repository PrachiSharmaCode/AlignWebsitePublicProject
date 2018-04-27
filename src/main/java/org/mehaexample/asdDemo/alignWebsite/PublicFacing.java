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

import org.json.JSONArray;
import org.json.JSONObject;
import org.mehaexample.asdDemo.dao.alignpublic.MultipleValueAggregatedDataDao;
import org.mehaexample.asdDemo.dao.alignpublic.SingleValueAggregatedDataDao;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.DataCount;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopCoops;
import org.mehaexample.asdDemo.model.alignpublic.TopGradYears;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradDegrees;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradSchools;
import org.mehaexample.asdDemo.restModels.StudentSerachCriteria;
import org.mehaexample.asdDemo.restModels.StudentStatsObject;

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
	 *
	 * Endpoint: https://asd4.ccs.neu.edu:8080/undergradschools
	 */
	@POST
	@Path("/undergradschools")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradSchools(int topUnderGradSchools) {
		List<TopUndergradSchools> undergrad;
		JSONArray result = new JSONArray();

		try {
			undergrad = undergraduatesPublicDao.getTopUndergradSchools(topUnderGradSchools);
			for (TopUndergradSchools school : undergrad) {
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
	 * This is the function to get top n Coop companies
	 *
	 * @param topCoopsNumber
	 * @return List of n top Coop companies
	 * @throws SQLException
	 *
	 * Endpoint: https://asd4.ccs.neu.edu:8080/coops
	 */
	@POST
	@Path("/coops")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopCoops(int topCoopsNumber) {
		List<TopCoops> coops;
		JSONArray result = new JSONArray();

		try {
			coops = workExperiencesPublicDao.getTopCoops(topCoopsNumber);
			for (TopCoops coop : coops) {
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
	 * This is the function to get top n Undergrad Major
	 *
	 * @param topUnderGradMajorsNumber
	 * @return List of n top undergraduate Major
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080 /undergradmajors
	 */
	@POST
	@Path("undergradmajors")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUndergradDegrees(int topUnderGradMajorsNumber) {
		List<TopUndergradDegrees> degrees;
		JSONArray result = new JSONArray();

		try {
			degrees = undergraduatesPublicDao.getTopUndergradDegrees(topUnderGradMajorsNumber);
			for (TopUndergradDegrees degree : degrees) {
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
	 * This is the function to get top n graduation year
	 *
	 * @param topGraduationYearsNumber
	 * @return List of n top graduation year
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080 /graduationyears
	 */
	@POST
	@Path("graduationyears")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTopGraduationYears(int topGraduationYearsNumber) {
		List<TopGradYears> gradYears;
		JSONArray result = new JSONArray();

		try {
			gradYears = studentsPublicDao.getTopGraduationYears(topGraduationYearsNumber);
			for (TopGradYears year : gradYears) {
				JSONObject yearJson = new JSONObject(year);
				result.put(Integer.toString((int) yearJson.get("graduationYear")));
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}

	/**
	 * Request 5
	 * This is the function to get all undergraduate schools
	 *
	 *
	 * @return List of all undergraduate School
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/undergradschools
	 */
	@GET
	@Path("/undergradschools")
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
	 * This is the function to get all graduation year
	 *
	 *
	 * @return List of all graduation year
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/graduationyears
	 */
	@GET
	@Path("graduationyears")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllGradYears(){
		List<Integer> years;
		JSONArray result = new JSONArray();
		try {
			years = studentsPublicDao.getListOfAllGraduationYears();

			if (years == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("No graduation years are found").build();
			} 

			for(Integer year : years){
				result.put(Integer.toString(year));
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return Response.status(Response.Status.OK).entity(result.toString()).build();
	}

	/**
	 * Request 7
	 * This is the function to get all coop companies
	 *
	 * @return List of all coop companies
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/coops
	 */
	@GET
	@Path("/coops")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCoopCompanies() {
		List<String> listOfAllCoopCompanies;
		try {
			listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();

			if (listOfAllCoopCompanies == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("No COOPS are found").build();
			} 

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return Response.status(Response.Status.OK).entity(listOfAllCoopCompanies).build();
	}

	/**
	 * Request 8
	 * This is the function to get all Undergradute Major
	 *
	 * @return List of all Undergradute Major
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/undergradmajors
	 */
	@GET
	@Path("undergradmajors")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUndergradDegrees() {
		List<String> degrees;

		try {
			degrees = undergraduatesPublicDao.getListOfAllUndergraduateDegrees();

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return Response.status(Response.Status.OK).entity(degrees).build();
	}

	/**
	 * Request 9
	 * This is the function to search stduent on the basis
	 * of coop, undergraduate degree, undergraduate school, graduation year.
	 *
	 * @return Number of student coop, undergraduate degree,
	 * 			undergraduate school, graduation year
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/students
	 */
	@POST
	@Path("students")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchStudent(StudentSerachCriteria studentSerachCriteria) {
		Map<String, List<String>> searchCriteriaMap = new HashMap<>();
		List<StudentsPublic> studentList;
		JSONObject finalObj = new JSONObject();
		JSONArray resultArray = new JSONArray();
		int quantity = 0;
		int begin = 0;
		int end = 20;

		try{
			if (studentSerachCriteria.getCoops() != null && studentSerachCriteria.getCoops().size() > 0){
					searchCriteriaMap.put("coop", studentSerachCriteria.getCoops());
			}
			if (studentSerachCriteria.getUndergraddegree() != null && studentSerachCriteria.getUndergraddegree().size() > 0){
					searchCriteriaMap.put("undergradDegree", studentSerachCriteria.getUndergraddegree());
			}
			if (studentSerachCriteria.getUndergradschool() != null && studentSerachCriteria.getUndergradschool().size() > 0){
					searchCriteriaMap.put("undergradSchool", studentSerachCriteria.getUndergradschool());
			}
			if (studentSerachCriteria.getGraduationyear() != null && studentSerachCriteria.getGraduationyear().size() > 0){
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
			quantity = studentsPublicDao.getPublicFilteredStudentsCount(searchCriteriaMap);
			studentList = studentsPublicDao.getPublicFilteredStudents(searchCriteriaMap, 1, 9999);

			finalObj.put("quantity", quantity);

			if(end > quantity && begin < quantity){
				studentList = studentList.subList(begin, quantity);
			} else if (end < quantity && begin < quantity){
				studentList = studentList.subList(begin, end);
			}			

			for(StudentsPublic student : studentList){
				String undergradDegree = "No degree";
				String undergradSchool = "No school";
				String coop = "No coop";if(student.getWorkExperiences().size() > 1 && studentSerachCriteria.getCoops() != null && studentSerachCriteria.getCoops().size() > 0){
					for(int j=0; j<student.getWorkExperiences().size(); j++){
						if(studentSerachCriteria.getCoops().contains(student.getWorkExperiences().get(j).getCoop())){
							coop = student.getWorkExperiences().get(j).getCoop();
						}
					}
				}else if(student.getWorkExperiences().size() > 0){
					coop = student.getWorkExperiences().get(0).getCoop();
				}
				if(student.getUndergraduates().size() > 0){
					undergradDegree = student.getUndergraduates().get(0).getUndergradDegree();
					undergradSchool = student.getUndergraduates().get(0).getUndergradSchool();
				}

				JSONObject jsonObj = new JSONObject();
				jsonObj.put("graduationyear", Integer.toString(student.getGraduationYear()));
				jsonObj.put("coop", coop);
				jsonObj.put("undergraddegree", undergradDegree);
				jsonObj.put("undergradschool", undergradSchool);

				resultArray.put(jsonObj);
			}

			finalObj.put("students", resultArray);

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return Response.status(Response.Status.OK).entity(finalObj.toString()).build();
	}

	/**
	 * Request 10
	 * This is the function to get percentage of male and female student
	 *
	 * @return percentage of male and female student
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/gender
	 */
	@GET
	@Path("gender")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGender() {
		JSONObject jsonObj = new JSONObject();
		try {
			int numberOfMale = singleValueAggregatedDataDao.getTotalMaleStudents();
			int numberOfFemale = singleValueAggregatedDataDao.getTotalFemaleStudents();

			int totalCount = numberOfMale + numberOfFemale;
			
			if(totalCount == 0){
				totalCount = 1;
			}
			
			float malepercent = (float) (((float) numberOfMale/(float) totalCount) * 100.00);
			float femalepercent = (float) (((float) numberOfFemale/(float) totalCount) * 100.00);

			jsonObj.put("male", Float.toString(malepercent));
			jsonObj.put("female", Float.toString(femalepercent));

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
	}

	/**
	 * Request 11
	 * This is the function to get percentage of full-time and part-time student
	 *
	 * @return percentage of full-time and part-time student
	 * @throws SQLException
	 *
	 *  Endpoint:  https://asd4.ccs.neu.edu:8080/enrollment
	 */
	@GET
	@Path("enrollment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEnrollmentStatus() {
		JSONObject jsonObj = new JSONObject();
		try {
			int numberOfFulltime = singleValueAggregatedDataDao.getTotalFullTimeStudents();
			int numberOfPartTime = singleValueAggregatedDataDao.getTotalPartTimeStudents();
			int totalCount = numberOfFulltime + numberOfPartTime;
			
			if(totalCount == 0){
				totalCount = 1;
			}
			
			float fulltimepercent = (float) (((float) numberOfFulltime/(float) totalCount) * 100.00);
			float parttimepercent = (float) (((float) numberOfPartTime/(float) totalCount) * 100.00);

			jsonObj.put("full-time", Float.toString(fulltimepercent));
			jsonObj.put("part-time", Float.toString(parttimepercent));
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
	}

	/**
	 * Request 12
	 * This is the function to get percentage of graduated and terminated student
	 *
	 * @return percentage of graduated and terminated student
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/graduation
	 */
	@GET
	@Path("graduation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGraduation() {
		JSONObject jsonObj = new JSONObject();
		float graduatedpercent = 0;
		float terminatedpercent = 0;
		try {
			int numberOfGraduated = singleValueAggregatedDataDao.getTotalGraduatedStudents();
			int numberOfTerminated = singleValueAggregatedDataDao.getTotalDroppedOutStudents();
			int totalCount = numberOfGraduated + numberOfTerminated;

			if(totalCount == 0){
				totalCount = 1;
			}
			
			if(numberOfGraduated > 0 && numberOfTerminated > 0){
				graduatedpercent = (float) (((float) numberOfGraduated/(float) totalCount) * 100.00);
				terminatedpercent = (float) (((float) numberOfTerminated/(float) totalCount) * 100.00);
			}

			jsonObj.put("graduated", Float.toString(graduatedpercent));
			jsonObj.put("terminated", Float.toString(terminatedpercent));

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
	}

	/**
	 * Request 13
	 * This is the function to get percentage of student from different state
	 *
	 * @return percentage of student from different state
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/state
	 */
	@GET
	@Path("state")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfState() {
		List<DataCount> state;
		JSONObject resultObj = new JSONObject();
		int totalCount = 0;
		try{
			state = multipleValueAggregatedDataDao.getListOfStudentsStatesCount();

			for(int i=0; i<state.size();i++){
				totalCount += state.get(i).getDataValue();
			}
			
			if(totalCount == 0){
				totalCount = 1;
			}

			for(int i=0; i<state.size();i++){
				float percent = (float) (( (float) state.get(i).getDataValue()/(float) totalCount ) * 100.00);
				resultObj.put(state.get(i).getDataKey().toLowerCase(), Float.toString(percent));
			}

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(resultObj.toString()).build();
	}

	/**
	 * Request 14
	 * This is the function to get percentage of student from in Campus
	 *
	 * @return percentage of student in different campus
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/campus
	 */
	@GET
	@Path("campus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCampusData() {
		JSONObject jsonObj = new JSONObject();
		try {
			int studentInBoston = singleValueAggregatedDataDao.getTotalStudentsInBoston();
			int studentInSeattle = singleValueAggregatedDataDao.getTotalStudentsInSeattle();
			int studentInCharlotte = singleValueAggregatedDataDao.getTotalStudentsInCharlotte();
			int studentInSiliconValley = singleValueAggregatedDataDao.getTotalStudentsInSiliconValley();
			int totalCount = studentInBoston + studentInSeattle + studentInCharlotte + studentInSiliconValley;
			
			if(totalCount == 0){
				totalCount = 1;
			}
			
			float bostonpercent = (float) (((float) studentInBoston/(float) totalCount) * 100.00);
			float seattlepercent = (float) (((float) studentInSeattle/(float) totalCount) * 100.00);
			float charlottepercent = (float) (((float) studentInCharlotte/(float) totalCount) * 100.00);
			float siliconvalleypercent = (float) (((float) studentInSiliconValley/(float) totalCount) * 100.00);

			jsonObj.put("boston", Float.toString(bostonpercent));
			jsonObj.put("seattle", Float.toString(seattlepercent));
			jsonObj.put("charlotte", Float.toString(charlottepercent));
			jsonObj.put("siliconvalley", Float.toString(siliconvalleypercent));

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
	}

	/**
	 * Request 15
	 * This is the function to get percentage of student with scholarship
	 *
	 * @return percentage of with scholarship
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/scholarship
	 */
	@GET
	@Path("scholarship")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScholarshipData(){;
	JSONObject jsonObj = new JSONObject();
	try {
		int studentWithScholarship = singleValueAggregatedDataDao.getTotalStudentsWithScholarship();
		int studentWithoutScholarship = singleValueAggregatedDataDao.getTotalStudents();
		int totalCount = studentWithScholarship + studentWithoutScholarship ;
		
		if(totalCount == 0){
			totalCount = 1;
		}
			
		float scholarshippercent = (float) (((float) studentWithScholarship/(float) totalCount) * 100.00);
		float nonepercent = (float) (((float) studentWithoutScholarship/(float) totalCount) * 100.00);
		jsonObj.put("scholarship", Float.toString(scholarshippercent));
		jsonObj.put("none", Float.toString(nonepercent));
	} catch (Exception e) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
	}
	return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();
	}

	/**
	 * Request 16
	 * This is the function to get percentage student in Undergraduate Major
	 *
	 * @return percentage student in Undergraduate Major
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/undergradmajor-percent
	 */
	@GET
	@Path("undergradmajor-percent")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfUndergradMajorPercent() {
		List<DataCount> major;
		JSONObject resultObj = new JSONObject();
		int totalCount = 0;
		try{
			major = multipleValueAggregatedDataDao.getListOfUndergraduateMajorsCount();

			for(int i=0; i<major.size();i++){
				totalCount += major.get(i).getDataValue();
			}
			
			if(totalCount == 0){
				totalCount = 1;
			}

			for(int i=0; i<major.size();i++){
				float percent = (float) (( (float) major.get(i).getDataValue()/(float) totalCount ) * 100.00);
				resultObj.put(major.get(i).getDataKey().toLowerCase(), Float.toString(percent));
			}

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(resultObj.toString()).build();
	}

	/**
	 * Request 17
	 * This is the function to get percentage of Highest Degree
	 *
	 * @return percentage of Highest Degree
	 * @throws SQLException
	 *
	 *  Endpoint:  https://asd4.ccs.neu.edu:8080/highest-education
	 */
	@GET
	@Path("highest-education")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListOfHighestEducation(){
		List<DataCount> education;
		JSONObject resultObj = new JSONObject();
		int totalCount = 0;
		try {
			education = multipleValueAggregatedDataDao.getListOfHighestDegreesCount();
			for(int i=0; i<education.size();i++){
				totalCount += education.get(i).getDataValue();
			}
			
			if(totalCount == 0){
				totalCount = 1;
			}

			for(int i=0; i<education.size();i++){
				float percent = (float) (( (float) education.get(i).getDataValue()/(float) totalCount ) * 100.00);
				resultObj.put(education.get(i).getDataKey().toLowerCase(), Float.toString(percent));
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(resultObj.toString()).build();
	}

	/**
	 * Request 18
	 * This is the function to get total graduated student
	 *
	 * @return total graduated student
	 * @throws SQLException
	 *
	 *  Endpoint:  https://asd4.ccs.neu.edu:8080/stats/graduates
	 */
	@GET
	@Path("stats/graduates")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTotalGraduates() {
		JSONObject jsonObj = new JSONObject();
		try {
			int totalGraduateNumber = singleValueAggregatedDataDao.getTotalGraduatedStudents();
			jsonObj.put("graduates", Integer.toString(totalGraduateNumber));
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();    	    	
	}

	/**
	 * Request 19
	 * This is the function to get total  student
	 *
	 * @return total number of student
	 * @throws SQLException
	 *
	 *  Endpoint:  https://asd4.ccs.neu.edu:8080/stats/total-student-count
	 */
	@GET
	@Path("stats/total-student-count")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTotalStudents1() {
		JSONObject jsonObj = new JSONObject();
		try {
			int totalGraduateNumber = singleValueAggregatedDataDao.getTotalStudents();
			jsonObj.put("studentcount", Integer.toString(totalGraduateNumber));
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();    	
	}

	/**
	 * Request 20
	 * This is the function to get total student in each campus
	 *
	 * @return  total student in each campus
	 * @throws SQLException
	 *
	 *  Endpoint: https://asd4.ccs.neu.edu:8080/stats/student-count
	 */
	@POST
	@Path("stats/student-count")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTotalStudents(StudentStatsObject input) {
		JSONObject jsonObj = new JSONObject();
		List<String> campus = new ArrayList<String>();
		int total = 0;
		try {
			if(input.getCampus().size() < 1){
				campus.add("SEATTLE");
				campus.add("BOSTON");
				campus.add("CHARLOTTE");
				campus.add("SILICON_VALLEY");
				input.setCampus(campus);
			}

			if(input.getCampus().contains("SEATTLE")){
				total+= singleValueAggregatedDataDao.getTotalStudentsInSeattle();
			}
			if(input.getCampus().contains("BOSTON")){
				total+= singleValueAggregatedDataDao.getTotalStudentsInBoston();
			}
			if(input.getCampus().contains("CHARLOTTE")){
				total+= singleValueAggregatedDataDao.getTotalStudentsInCharlotte();
			}
			if(input.getCampus().contains("SILICON_VALLEY")){
				total+= singleValueAggregatedDataDao.getTotalStudentsInSiliconValley();
			}
			jsonObj.put("studentcount", Integer.toString(total));
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
		return Response.status(Response.Status.OK).entity(jsonObj.toString()).build();    	
	}
}
