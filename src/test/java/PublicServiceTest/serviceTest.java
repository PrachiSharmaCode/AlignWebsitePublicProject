package PublicServiceTest;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.alignWebsite.PublicFacing;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.DataCount;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.UndergraduatesPublic;
import org.mehaexample.asdDemo.restModels.TopUnderGradSchools;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class serviceTest {

    public static PublicFacing publicFacing;
	UndergraduatesPublicDao undergraduatesPublicDao = new UndergraduatesPublicDao(true);
	StudentsPublicDao studentsPublicDao = new StudentsPublicDao(true);
	private static UndergraduatesPublic undergraduate;
	private static UndergraduatesPublic undergraduate2;
	private static UndergraduatesPublic undergraduate3;


    @BeforeClass
	public static void init() {
	}

	@Before
	public void setup() {
		publicFacing = new PublicFacing();
	}


	@SuppressWarnings("unchecked")
	@Test
	public void getHigestEducation(){

		List<DataCount> highestEducation = new ArrayList<>();

		Response res = publicFacing.getListOfHighestEducation();

		List response = (List) res.getEntity();

		Assert.assertEquals(highestEducation , response);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void getState(){

		List<DataCount> State = new ArrayList<>();
		Response res = publicFacing.getListOfState();
		List response = (List) res.getEntity();
		Assert.assertEquals(State , response);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getRace(){

		List<DataCount> Race = new ArrayList<>();
		Response res = publicFacing.getRace();
		List response = (List) res.getEntity();
		Assert.assertEquals(Race , response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllSchools(){

		List<DataCount> schools = new ArrayList<>();
		Response res = publicFacing.getAllSchools();
		List response = (List) res.getEntity();
		Assert.assertEquals(schools , response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllCoops(){

		List<DataCount> coops = new ArrayList<>();
		Response res = publicFacing.getAllCoopCompanies();
		List response = (List) res.getEntity();
		Assert.assertEquals(coops , response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllUndergard(){

		List<DataCount> undergrads = new ArrayList<>();
		Response res = publicFacing.getAllUndergradDegrees();
		List response = (List) res.getEntity();
		Assert.assertEquals(undergrads , response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAllGradYear(){

		List<DataCount> gradYear = new ArrayList<>();
		Response res = publicFacing.getAllGradYears();
		List response = (List) res.getEntity();
		Assert.assertEquals(gradYear , response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getEnrollment(){

		String full = "full-time";
		String part = "part-time";
		Response res = publicFacing.getEnrollmentStatus();
		//String response = (String) res.getEntity();
		Assert.assertEquals( "{\"full-time\"" + ":27,\"part-time\""+":28}" , res.getEntity());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getCampus(){

		Response res = publicFacing.getCampusData();
		//String response = (String) res.getEntity();
		Assert.assertEquals( "{\"boston\":54,\"charlotte\":56,\"siliconvalley\":57,\"seattle\":55}"  , res.getEntity());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getGraduationTest(){

		Response res = publicFacing.getGraduation();
		//String response = (String) res.getEntity();
		Assert.assertEquals( "{\"graduated\":51,\"terminated\":52}"  , res.getEntity());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getScholarshipTest(){

		Response res = publicFacing.getScholarshipData();
		//String response = (String) res.getEntity();
		Assert.assertEquals( "{\"scholarship\":60,\"none\":50}"  , res.getEntity());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getGenderTest(){

		Response res = publicFacing.getGender();
		//String response = (String) res.getEntity();
		Assert.assertEquals( "{\"female\":26,\"male\":24}"  , res.getEntity());
	}
}