package eu.ehealth.db.wservices.users;

import java.util.ArrayList;

import org.hibernate.Session;

import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.users.dbfunctions.FUsers;
import eu.ehealth.security.DataBasePasswords;


/**
 * 
 * @author a572832
 *
 */
public class RegisterUser extends DbStorageComponent<String[], String>
{
	
	
	// REGULAR EXPRESSION : letters 'a-zA-Z' and at least one digit 0-9. Length 8-20
	private final String PSSWD_REG_EXPRESSION = "((?=.*\\d)(?=.*[a-zA-Z]).{8,20})"; 

	
	/**
	 * 
	 * @param session
	 */
	public RegisterUser(Session session)
	{
		super(session);
	}

	
	@Override
	protected String[] dbStorageFunction(ArrayList<String> lo)
	{
		try {
			String userName = lo.get(0);
			String password = lo.get(1);
			String userId = lo.get(2);
			
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());		
			StorageComponentMain.scLog("DEBUG", "RegisterUser CALL params : " + userName + " / " + password + " / " + userId);
			
			if ((userId == null) || (userId.trim().length() == 0) || (!checkUserPermissions("", userId, U_ADMIN)))
			{
				return Globals.ResponseCode.PERMISSION_ERROR.toStringArray();
			}

			FUsers fu = new FUsers(_session);
			if (fu.existUser(userName, 0) != 1) {
				// validate password
				if (password.matches(PSSWD_REG_EXPRESSION)) {
					StorageComponentMain.scLog("INFO", "VALIDATING PASSWORD : OK");
				}
				else {
					StorageComponentMain.scLog("ERROR", "VALIDATING PASSWORD ERROR : " + password);
					return Globals.ResponseCode.INVALID_PASSWORD_FORMAT.toStringArray();
				}
				
				_session.beginTransaction();
			
				// PERSONDATA
				eu.ehealth.db.db.PersonData dPersonData = new eu.ehealth.db.db.PersonData();
				dPersonData.setName(userName);
				dPersonData.setSurname(userName);
				
				_session.save(dPersonData);
				
				// CLINICIAN
				eu.ehealth.db.db.Clinician clinician = new eu.ehealth.db.db.Clinician();
				clinician.setPersondata(dPersonData.getId());
				
				_session.save(clinician);
	
				// ALADDINUSER
				eu.ehealth.db.db.AladdinUser u = new eu.ehealth.db.db.AladdinUser();

				u.setType(new Integer(4));
				u.setPersonId(dPersonData.getId());
				u.setUsername(userName);
				//u.setPassword(password);
				// set password value
				if (StorageComponentMain.DATABASE_ENCRYPTION) 
				{
					// encrypted database
					u.setPassword(DataBasePasswords.getPooledEncryptedPsswd(password));
				}
				else 
				{
					// 'normal' database
					u.setPassword(password);
				}
				_session.save(u);
				
				_session.getTransaction().commit();
				
				String[] response = new String[2];
				response[0] = "" + u.getId();
				response[1] = "User registered succesfully";
				return response;
			}
			else {
				return Globals.ResponseCode.USER_ALREADY_EXISTS.toStringArray();
			}
		}
		catch (Exception ex) {
			rollbackSession();
			
			StorageComponentMain.logException(ex);
			
			return Globals.ResponseCode.DATABASE_ERROR_2.toStringArray();
		}
	}
	

}
