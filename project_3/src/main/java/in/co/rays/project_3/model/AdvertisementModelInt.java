package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AdvertisementDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of Advertisement model --->>>
 * 
 * @author Neeraj Mewada
 *
 */
public interface AdvertisementModelInt {

	public long add(AdvertisementDTO dto) throws ApplicationException, DuplicateRecordException;

	public void delete(AdvertisementDTO dto) throws ApplicationException;

	public void update(AdvertisementDTO dto) throws ApplicationException, DuplicateRecordException;

	public List list() throws ApplicationException;

	public List list(int pageNo, int pageSize) throws ApplicationException;

	public List search(AdvertisementDTO dto) throws ApplicationException;

	public List search(AdvertisementDTO dto, int pageNo, int pageSize) throws ApplicationException;

	public AdvertisementDTO findByPK(long pk) throws ApplicationException;

	public AdvertisementDTO findByAdName(String adName) throws ApplicationException;
}