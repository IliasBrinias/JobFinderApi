package com.unipi.msc.jobfinderapi.Controller.Responses;

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
}
