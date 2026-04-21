package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.RuleDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Rule model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface RuleModelInt {

	public long add(RuleDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(RuleDTO dto) throws ApplicationException;

	public void update(RuleDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(RuleDTO dto) throws ApplicationException;

	public List search(RuleDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public RuleDTO findByPK(long pk) throws ApplicationException;

	public RuleDTO findByRuleCode(String ruleCode) throws ApplicationException;
}