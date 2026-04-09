package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.SpaceMissionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

/**
 * Interface of SpaceMission model ---
 * 
 * @author Neeraj Mewada
 *
 */
public interface SpaceMissionModelInt {

    public long add(SpaceMissionDTO dto) throws ApplicationException, DuplicateRecordException;

    public void delete(SpaceMissionDTO dto) throws ApplicationException;

    public void update(SpaceMissionDTO dto) throws ApplicationException, DuplicateRecordException;

    public List list() throws ApplicationException;

    public List list(int pageNo, int pageSize) throws ApplicationException;

    public List search(SpaceMissionDTO dto) throws ApplicationException;

    public List search(SpaceMissionDTO dto, int pageNo, int pageSize) throws ApplicationException;

    public SpaceMissionDTO findByPK(long pk) throws ApplicationException;

    public SpaceMissionDTO findByMissionName(String missionName) throws ApplicationException;
}