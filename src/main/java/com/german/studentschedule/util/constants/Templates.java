package com.german.studentschedule.util.constants;

import java.util.Collections;
import java.util.Map;

public class Templates {
    public static final Map<String,String> UNAUTHORIZED_JSON = Collections.singletonMap("message", "This resource requires authentication");
    public static final Map<String,String> FORBIDDEN_JSON = Collections.singletonMap("message", "This user has no access to this resource");
    public static final Map<String,String> SERVER_ERROR_JSON = Collections.singletonMap("message", "Oops, Internal server error happened");
}
