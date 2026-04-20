package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.WebHookDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of WebHook model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface WebHookModelInt {

	public long add(WebHookDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(WebHookDTO dto) throws ApplicationException;

	public void update(WebHookDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(WebHookDTO dto) throws ApplicationException;

	public List search(WebHookDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public WebHookDTO findByPK(long pk) throws ApplicationException;

	// Custom finder (field ke hisaab se banaya)
	public WebHookDTO findByLogCode(String logCode) throws ApplicationException;

}