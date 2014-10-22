import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.sample.controller.pojos.AdCreateForm;
import org.sample.controller.service.SampleServiceImpl;
import org.sample.exceptions.InvalidAdException;

public class RestrictionTest{
	SampleServiceImpl service;
	AdCreateForm adForm;
	String eMessage;
	
	@Before
	public void setUp() {
		service=new SampleServiceImpl();
		adForm= new AdCreateForm();
		
	}
	
	@Test
	public void TitleRestriction(){
		
		eMessage="Title must not be empty";
		adForm.setTitle("");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter a meaningfull Title ";
		adForm.setTitle("abc");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
	}
	
	@Test
	public void RoomDescRestriction(){
		
		eMessage="Room description must not be empty";
		adForm.setTitle("Room");
		adForm.setRoomDesc("");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter more information";
		adForm.setRoomDesc("asdfghjkl");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
	}
	
	@Test
	public void PeopleDescRestriction(){
		
		eMessage="People description must not be empty";
		adForm.setTitle("Room");
		adForm.setRoomDesc("This is a very nice room.");
		adForm.setPeopleDesc("");

		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter more information";
		adForm.setRoomDesc("asdfghjkl");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
	}
	
	@Test
	public void RoomSizeRestriction(){
		
		eMessage="Please enter a valid Room size";
		adForm.setTitle("Room");
		adForm.setRoomDesc("This is a very nice room.");
		adForm.setPeopleDesc("All people are happy here.");
		adForm.setRoomSize("");

		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		adForm.setRoomSize("abc");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
	}
	
	@Test
	public void FromDateRestriction(){
		
		eMessage="Date must not be empty";
		adForm.setTitle("Room");
		adForm.setRoomDesc("This is a very nice room.");
		adForm.setPeopleDesc("All people are happy here.");
		adForm.setRoomSize("11");
		adForm.setFromDate("");

		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter the date correctly MM/dd/yyyy";
		adForm.setFromDate("1/1/2000");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter a future date or today";
		adForm.setFromDate("01/01/2000");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter a future date or today";
		adForm.setFromDate("01/01/2014");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
		
		eMessage="Please enter a future date or today";
		adForm.setFromDate("01/10/2014");
		try {
		service.saveFromAd(adForm);
		}
		catch(InvalidAdException e)
		{
			assertEquals(eMessage,e.getMessage());
		}
	}
}