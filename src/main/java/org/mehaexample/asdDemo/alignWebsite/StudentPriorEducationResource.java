package org.mehaexample.asdDemo.alignWebsite;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mehaexample.asdDemo.dao.PriorEducationDao;
import org.mehaexample.asdDemo.model.PriorEducation;
import org.mehaexample.asdDemo.model.Student;

@Path("studentPriorEducationResource")
public class StudentPriorEducationResource {
	
	PriorEducationDao studentPriorEducationDao = new PriorEducationDao();
	
	@GET
	@Path("{nuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PriorEducation> getPriorEducationInfo(@PathParam("nuid") String nuid) {
		System.out.println("Getting Prior Education Details for student with nuid: " + nuid);
		ArrayList<PriorEducation> list = (ArrayList<PriorEducation>) studentPriorEducationDao.getPriorEducation(nuid);
		return list;
	}
	
	@POST
	@Path("{nuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void savePriorEducationForm(@PathParam("nuid") String nuid, PriorEducation priorEducation){
		studentPriorEducationDao.addPriorEducation(nuid, priorEducation); 
	} 

}
