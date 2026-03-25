package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.OTPVerificationDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of OTP Verification model
 * 
 * @author Neeraj Mewada
 *
 */
public interface OTPVerificationModelInt {

    public long add(OTPVerificationDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(OTPVerificationDTO dto) throws ApplicationException;

    public void update(OTPVerificationDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(OTPVerificationDTO dto) throws ApplicationException;

    public List search(OTPVerificationDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public OTPVerificationDTO findByPK(long pk) throws ApplicationException;

    public OTPVerificationDTO findByUserName(String userName) throws ApplicationException;
}