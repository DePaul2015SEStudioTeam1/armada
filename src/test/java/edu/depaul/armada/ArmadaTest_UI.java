package edu.depaul.armada;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * @author Roland T Craddolph
 * 
 */
@Ignore
public class ArmadaTest_UI {
	private WebDriver driver = new FirefoxDriver();
	final static String URL = "http://localhost:8083";
	
	By containerTable = By.id("containers");
	
	@Before
	public void setUp() throws Exception {
		driver.get(URL);
	}

	@After
	public void tearDown(){
	    driver.quit();
	}
	
	@Test
	public void initialLoading() throws InterruptedException {
		assertEquals("Armada Dashboard", driver.getTitle());
	}
	
	@Test
	public void loadContainers() throws InterruptedException {
		boolean hasContainers =  driver.findElement(containerTable).isDisplayed();
		assertTrue(hasContainers);
	}
}