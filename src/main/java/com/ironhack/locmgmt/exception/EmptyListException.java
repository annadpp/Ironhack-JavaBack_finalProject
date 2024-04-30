package com.ironhack.locmgmt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Empty List")
public class EmptyListException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(EmptyListException.class);

    public EmptyListException(String message) {
        super(message);
<<<<<<< HEAD
<<<<<<< HEAD
        logger.error(message);  // Log the error message using SLF4J
    }
}
=======
        logger.error(message);  //Log the error message using SLF4J
    }
}
>>>>>>> 3a640d4f1dcbc26e5f5e811a256e03b4fba69a81
=======
        logger.error(message);  //Log the error message using SLF4J
    }
}
>>>>>>> 3a640d4f1dcbc26e5f5e811a256e03b4fba69a81
