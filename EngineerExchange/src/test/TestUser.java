package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.engineerexchange.model.User;

class TestUser {

	@Test
	void testUserConstructor_getsName() {
		final String name = "name";
		final String login = "login";
		
		User u = new User(name,login);
		
		assertEquals(name,u.getName());
	}
	
	
	@Test
	void testUserConstructor_notNull() {
		final String name = null;
		final String login = "login";
		
		User u = new User(name,login);
		
		assertNotNull(u.getName());
	}
	
	@Test
	void testUserConstructor_notEmpty1() {
		final String name = "";
		final String login = "login";
		
		User u = new User(name,login);
		
		assertNotEquals(u.getName(),"");
	}
	
	@Test
	void testUserConstructor_notEmpty2() {
		final String name = " ";
		final String login = "login";
		
		User u = new User(name,login);
		
		assertNotEquals(u.getName()," ");
	}

}
