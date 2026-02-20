package it.unipv.pois.IlParadisoDellaJVM.NeedForSpecs.model.forum;

public class ForumException extends Exception {
	
	private String error_occurred = "FORUM ERROR";

	public ForumException(String error_occurred) {
		super(error_occurred);
	}
	
	public ForumException(String message, Throwable cause) {
        super(message, cause);
    }
	
	

}

