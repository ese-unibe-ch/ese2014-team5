import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.service.SampleServiceImpl;
import org.sample.exceptions.InvalidAdException;

public class RestrictionTest {

	SampleServiceImpl service;
	AdCreateForm adForm;
	String eMessage;

	@Before
	public void setUp() {

		service = new SampleServiceImpl();
		adForm = new AdCreateForm();

		adForm.setTitle("Room");
		adForm.setRoomDesc("This is a very nice room.");
		adForm.setPeopleDesc("All people are happy here.");
		adForm.setRoomSize("11");
		adForm.setPrice("11");
		adForm.setFromDate("01/01/2015");
		adForm.setStreet("Street");
		adForm.setCity("City");
		
		eMessage = "Please enter a valid postcode";//default for the plz tests
	}

	@Test
	public void TitleRestrictionEmpty() {
		
		eMessage = "Title must not be empty";
		adForm.setTitle("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void TitleRestrictionShort() {
		
		eMessage = "Please enter a meaningfull Title ";
		adForm.setTitle("abc");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void RoomDescRestrictionEmpty() {

		eMessage = "Room description must not be empty";
		adForm.setRoomDesc("");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void RoomDescRestrictionShort() {
		
		eMessage = "Please enter more information in your Room Description";
		adForm.setRoomDesc("asdfghjkl");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void PeopleDescRestrictionEmpty() {

		eMessage = "People description must not be empty";
		adForm.setPeopleDesc("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void PeopleDescRestrictionShort() {
		
		eMessage = "Please enter more information in your People Description";
		adForm.setPeopleDesc("asdfghjkl");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void RoomSizeRestrictionEmpty() {

		eMessage = "Please enter a valid room size";
		adForm.setRoomSize("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void RoomSizeRestrictionFalse() {

		eMessage = "Please enter a valid room size";
		adForm.setRoomSize("a,b.c");
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}
	
	@Test
	public void PriceRestrictionEmpty() {

		eMessage = "Please enter a valid price";
		adForm.setPrice("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void PriceRestrictionFalse() {

		eMessage = "Please enter a valid price";
		adForm.setPrice("a,b.c");
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void FromDateRestrictionEmpty() {

		eMessage = "Date must not be empty";
		adForm.setFromDate("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}

	}

	@Test
	public void FromDateRestrictionFalseFormat() {

		eMessage = "Please enter the date correctly MM/dd/yyyy";
		adForm.setFromDate("1/1/2000");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void FromDateRestrictionPast() {
		
		eMessage = "Please enter a future date or today";
		adForm.setFromDate("01/01/2000");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void StreetRestriction() {

		eMessage = "Street must not be empty";
		adForm.setStreet("");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void CityRestriction() {

		eMessage = "City must not be empty";
		adForm.setCity("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}

	@Test
	public void PlzRestrictionEmpty() {

		adForm.setPlz("");

		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}
	
	@Test
	public void PlzRestrictionFalse() {
		
		adForm.setPlz("a.b,c");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}
	
	@Test
	public void PlzRestrictionShort() {
		
		adForm.setPlz("123");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}
	
	@Test
	public void PlzRestrictionLong() {
		
		adForm.setPlz("123456");
		
		try {
			service.saveFromAd(adForm);
		} catch (InvalidAdException e) {
			assertEquals(eMessage, e.getMessage());
		}
	}
}