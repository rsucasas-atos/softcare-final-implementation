package eu.ehealth.db.wservices.users;

import java.util.ArrayList;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import eu.ehealth.Globals;
import eu.ehealth.StorageComponentMain;
import eu.ehealth.db.DbStorageComponent;
import eu.ehealth.db.wservices.users.dbfunctions.FUsers;
import eu.ehealth.db.xsd.OperationResult;
import eu.ehealth.db.xsd.User;
import eu.ehealth.security.DataBasePasswords;
import eu.ehealth.util.NullChecker;


/**
 *
 * CREATE USER PROCESS:
 * 		1- createPatient / createCarer / createClinician
 * 				- dbHelper.importPersondata
 * 					+ add to PersonData
 * 					- DELETE from address, identifier, communication
 * 				- dbHelper.importSocioDemographic
 * 					+ add to SocioDemographicData
 * 				+ add to Patient / Carer / Clinician
 *  	2- createUser 
 *  			- dbHelper.existUser()
 *  			+ add to AladdinUser
 *  
 *  EXAMPLE with carer:
 *  	...
 *  	OperationResult result = proxy.createCarer(carer, id);
 *		
 *		User user = createNewUser(SystemDictionary.USERTYPE_CARER, result.getCode(), username);
 *		result = proxy.createUser(user);
 *		if (result.getCode().equals("-2"))
 *  	...
 *  
 *
 * @author a572832
 *
 */
public class CreateUser extends DbStorageComponent<OperationResult, User>
{

	
	// REGULAR EXPRESSION : letters 'a-zA-Z' and at least one digit 0-9. Length 8-20
	private final String PSSWD_REG_EXPRESSION = "((?=.*\\d)(?=.*[a-zA-Z]).{8,20})"; 
		
		
	/**
	 * 
	 * @param session
	 */
	public CreateUser(Session session)
	{
		super(session);
	}

	
	@Override
	protected OperationResult dbStorageFunction(ArrayList<User> lo)
	{
		try
		{
			User user = (User) lo.get(0);
			
			// DEBUG - TRACE
			StorageComponentMain.scLog("DEBUG", "METHOD : " + this.getClass().getName());		
			StorageComponentMain.scLog("DEBUG", "Creating new user ...");
			
			OperationResult res = new OperationResult();

			NullChecker nc = new NullChecker();
			user = nc.check(user, User.class);

			try
			{
				// TODO check IP
				//StorageComponentUtilities.checkIP();

				// TODO check forum users
				/*
				String url = StorageComponentImpl.forumSC + "?none=1&password=***&type=***&username=" + user.getUsername();
				if (StorageComponentUtilities.getURLChar(url) == '0')
				{
					SystemDictionary.webguiLog("DEBUG", "The User with same name exists in Forum");
					throw new Exception("The User with same name exists in Forum");
				}
				*/
				
				// validate password
				if (user.getPassword().matches(PSSWD_REG_EXPRESSION)) {
					StorageComponentMain.scLog("DEBUG", "VALIDATING PASSWORD : OK");
				}
				else {
					StorageComponentMain.scLog("ERROR", "VALIDATING PASSWORD ERROR : " + user.getPassword());
					throw new Exception("Invalid password value");
				}

				_session.beginTransaction();

				FUsers fu = new FUsers(_session);
				if (fu.existUser(user.getUsername(), 0) == 1)
				{
					// set OperationResult values
					res = Globals.getOpResult(Globals.ResponseCode.USER_ALREADY_EXISTS.getCode(), "");
				}

				eu.ehealth.db.db.AladdinUser u = new eu.ehealth.db.db.AladdinUser();

				u.setType(new Integer(user.getType().getCode()));
				u.setPersonId(new Integer(user.getPersonID()));
				u.setUsername(user.getUsername());
				// set password value
				if (StorageComponentMain.DATABASE_ENCRYPTION) 
				{
					// encrypted database
					u.setPassword(DataBasePasswords.getPooledEncryptedPsswd(user.getPassword()));
				}
				else 
				{
					// 'normal' database
					u.setPassword(user.getPassword());
				}
				_session.save(u);

				// TODO create user in forum
				/*
				url = StorageComponentImpl.forumSC + "?username=" + user.getUsername() + "&password=" + user.getPassword() + "&type=" + user.getType().getCode();

				char urlChar = StorageComponentUtilities.getURLChar(url);
				if (urlChar == '0')
				{
					SystemDictionary.webguiLog("DEBUG", "Can't create user in forum!");
					throw new Exception("Can't create user in forum!");
				}
				*/
				_session.getTransaction().commit();

				// set OperationResult values
				res = Globals.getOpResult("" + u.getId().toString(), "");
				
				StorageComponentMain.scLog("DEBUG", "... returning response: " + res.getCode() + " ###");
			}
			catch (HibernateException e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_1.getCode(), "");
			}
			catch (Exception e)
			{
				rollbackSession();

				StorageComponentMain.logException(e);

				// set OperationResult values
				res = Globals.getOpResult(Globals.ResponseCode.DATABASE_ERROR_2.getCode(), "");
			}

			return res;
		}
		catch (Exception ex)
		{
			StorageComponentMain.logException(ex);
			// set OperationResult values
			return Globals.getOpResult(Globals.ResponseCode.UNKNOWN_ERROR.getCode(), " : " + ex.toString());
		}
	}
	

}
