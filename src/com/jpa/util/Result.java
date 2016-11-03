package com.jpa.util;

public enum Result {

	EXCEPTION,				// exception
	APP_PARAMETER_ERR, 		// parameter error
	PARAMETER_ERR, 			// parameter error
	HAVE_NO_RIGHT,			// have no right
	INVALID,				// invalid
	VERIFY_PWD_FAILED,		// verify password failed
	SUCCESS,				// success
	FAILED,					// failed
	CONTAINER_FAILED,		// container failed
	EXISTS,					// exists
	DISABLED,				// disabled
	CLUSH,					// clush 冲突
	NOTHING,				// nothing
	REFERENCED,				// referenced
	REF_PROXY_ERR,			// referenced proxy error
	EXCEED					// exceed 超过,溢出
	
}