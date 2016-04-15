package eu.ehealth;



public class Exceptions
{

	
	public static class InvalidUserTypeException extends Exception {

		private static final long serialVersionUID = -2663714895148052527L;

	}
	
	
	
	public static class ServerErrorException extends Exception {

		private static final long serialVersionUID = -135631558615358274L;

	}
	
	
	public static class InvalidCredentialsException extends Exception {

		private static final long serialVersionUID = -4231436054480873478L;

	}
	
	
	public static class InvalidResponseCodeException extends Exception {

		private static final long serialVersionUID = 4875398266036326408L;
		
		
		private String ex_code;
		private String ex_desc;
		
		
		/**
		 * 
		 * @param code
		 */
		public InvalidResponseCodeException(String code, String desc) {
			ex_code = code;
			ex_desc = desc;
		}
		
		
		/**
		 * 
		 * @return
		 */
		public String getExCode() {
			return ex_code;
		}
		
		
		/**
		 * 
		 * @return
		 */
		public String getExDesc() {
			return ex_desc;
		}


	}
	
	
}
