package com.unipi.msc.jobfinderapi.Controller.responses;

import com.unipi.msc.jobfinderapi.Constant.SqlErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.exception.ConstraintViolationException;

import java.io.Serializable;

import static com.unipi.msc.jobfinderapi.Constant.ErrorMessages.DUPLICATE_ENTRY_MSG;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse implements Serializable {
    private Boolean success;
    private String msg;
    public static ErrorResponse handleSqlError(Exception e){
        if (e.getCause() instanceof ConstraintViolationException){
            ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
            if (cause.getErrorCode() == SqlErrorCodes.DUPLICATE_ENTRY) {
                return new ErrorResponse(false, DUPLICATE_ENTRY_MSG+" "+cause.getConstraintName());
            }
            cause.printStackTrace();
            return null;
        }
        e.printStackTrace();
        return null;
    }
}
